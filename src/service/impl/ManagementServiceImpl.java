package service.impl;

import global.GlobalData;
import interceptor.Logging;
import model.constans.AirplaneConstans;
import model.dao.Airplane;
import model.dao.Ticket;
import model.enums.LogTypes;
import service.AirlineService;
import service.ManagementService;
import util.impl.MenuUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

public class ManagementServiceImpl implements ManagementService {

    private final AirlineService airlineService = new AirlineServiceImpl();
    private final Logging log = new Logging();

    @Override
    public void manage() {
        Thread robot = manageUnreal();
        Thread fly = flyAirplane();
        Thread changedPrice = changedPrice();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(robot);
        executorService.execute(fly);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleWithFixedDelay(changedPrice,2,7, TimeUnit.SECONDS);

        while (true) {
            try {
                int option = MenuUtil.entryMenu();
                switch (option) {
                    case 0:
                        System.exit(-1);
                    case 1:
                        airlineService.byTicket(false);
                        break;
                    case 2:
                        airlineService.showAllAirplane();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Thread manageUnreal() {
        return new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        airlineService.byTicket(true);
                    } catch (RuntimeException | InterruptedException e) {
                    }
                }
        });
    }

    @Override
    public Thread flyAirplane() {
        return new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        for (Airplane airplane : GlobalData.airplanesAtomic.get()) {
                            if (!airplane.isFlight()) {
                                if (airplane.isFlightMoment()) {
                                    airplane.fly();
                                    log.info(LogTypes.FLIGHTS, airplane.toString());
                                } else if (airplane.getAvailableSeats() == 0) {
                                    airplane.full();
                                }
                            }
                        }
                    } catch (RuntimeException | InterruptedException e) {
                        System.out.println(e.getMessage());
                        }
                    }
                });
            }

    @Override
    public Thread changedPrice() {
        return new Thread(() -> {
            try {
                for (Airplane airplane : GlobalData.airplanesAtomic.get()) {
                    airplane.setPriceByDemandTicket();
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
}
