package service.impl;

import model.dao.Baggage;
import model.dao.Ticket;
import model.dao.User;
import service.UserService;
import util.impl.InputUtil;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User fillUser(Ticket ticket, boolean isRobot) {
        if (isRobot) {
            return unRealPerson(ticket);
        } else {
            return realPerson(ticket);
        }
    }

    private User realPerson(Ticket ticket) {
        String name = InputUtil.inputRequiredString("Enter the name: ");
        String surname = InputUtil.inputRequiredString("Enter the surname: ");
        String finNumber = InputUtil.inputRequiredString("Enter the finNumber: ");
        char hasBaggage = InputUtil.inputRequiredChar("Has baggage? (y/n): ");
        Baggage baggage = null;
        if (hasBaggage == 'y') {
            double weight = InputUtil.inputRequiredDouble("Enter baggage weight: ");
            baggage = new Baggage(weight);
        }
        ticket.baggageHas(baggage);
        return new User(name, surname, ticket, finNumber, baggage, ticket.getAirplane().getPrice());
    }

    private User unRealPerson(Ticket ticket) {
        String randomPerson = UUID.randomUUID().toString();
        Random random = new Random();

        String name = randomPerson.substring(0,6);
        String surname = randomPerson.substring(6,12);
        int hasBaggage = random.nextInt(2);
        String finNumber = randomPerson.substring(12,19);
        Baggage baggage = null;
        if (hasBaggage == 1) {
            int weight = random.nextInt(20) + 3;
            baggage = new Baggage(weight);
        }
        ticket.baggageHas(baggage);
        return new User(name, surname, ticket, finNumber, baggage, ticket.getAirplane().getPrice());
    }
}
