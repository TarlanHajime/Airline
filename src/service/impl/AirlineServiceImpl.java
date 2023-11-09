package service.impl;

import global.GlobalData;
import interceptor.Logging;
import model.dao.Airplane;
import model.dao.Ticket;
import model.dao.User;
import model.enums.LogTypes;
import model.exceptions.IllegalOperationException;
import model.exceptions.InternalServerError;
import model.exceptions.NotFoundException;
import service.AirlineService;
import service.MultiThreadingToolsService;
import service.UserService;
import util.impl.InputUtil;

import java.util.Map;
import java.util.Random;
import java.util.Set;


public class AirlineServiceImpl implements AirlineService {
    private final UserService userService = new UserServiceImpl();
    private final MultiThreadingToolsService threadingTools = new MultiThreadingTools();
    private final Logging log = new Logging();

    @Override
    public void byTicket(boolean isRobot) {
        long airplaneId;
        if (!isRobot) {
            showAllAirplane();
            airplaneId = InputUtil.inputRequiredInt("Choose ticket: ");
        } else {
            Random random = new Random();
            airplaneId = random.nextInt(GlobalData.airplanesAtomic.get().size()) + 1;
        }
        Airplane airplane = findAirplaneById(airplaneId);
        if (!airplane.isFlight() && !airplane.isFull()) {
            Ticket availableTicket = getAirplaneAvailableTicket(airplane);
            User user = userService.fillUser(availableTicket, isRobot);
            try {
                threadingTools.lock();
                airplane.getSeats().acquire();
                if (!isRobot) {
                    System.out.println("\n----------------------------");
                    System.out.printf("Dear %s, thanks for purchase! \nYour ticket id: %s\n", user.getFullName(), availableTicket != null ? availableTicket.getName() : null);
                    System.out.println("----------------------------\n");
                }
                Map<Ticket, User> ticketAndUser = GlobalData.ticketAndUserAtomic.get();
                ticketAndUser.put(availableTicket, user);
                log.info(LogTypes.SALES, user.toString());
            } catch (InterruptedException e) {
                throw new InternalServerError("Internal exception!");
            } finally {
                threadingTools.unlock();
            }
        } else if (airplane.isFull() && !airplane.isFlight()){
            throw new NotFoundException("Sorry, ticket finished!");
        } else {
            throw new IllegalOperationException("The flight for the selected trip has already taken place!");
        }
    }

    private Ticket getAirplaneAvailableTicket(Airplane airplane) {
        Set<Ticket> ticketSet = GlobalData.ticketAirplanesAtomic.get().get(airplane);
        for (Ticket ticket : ticketSet) {
            try {
                threadingTools.lock();
                if (!ticket.isTaken()) {
                    ticket.takenSeat();
                    return ticket;
                }
            } finally {
                threadingTools.unlock();
            }
        }

        return null;
    }

    private Airplane findAirplaneById(long airplaneId) {
        for (Airplane airplane : GlobalData.airplanesAtomic.get()) {
            if (airplane.getId() == airplaneId) {
                return airplane;
            }
        }
        throw new NotFoundException("Ticket not found for this airplane:" + airplaneId);
    }


    @Override
    public void showAllAirplane() {
        boolean allAirplaneIsFly = true;
        for (Airplane airplane : GlobalData.airplanesAtomic.get()) {
            if (!airplane.isFlight()) {
                allAirplaneIsFly = false;
                System.out.println(airplane.getAvailableFlights());
            }
        }
        if (allAirplaneIsFly) {
            throw new NotFoundException("Sorry ticket finished!");
        }
    }
}
