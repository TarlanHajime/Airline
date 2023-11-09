package service;

import model.dao.Ticket;
import model.dao.User;

public interface UserService {
    User fillUser(Ticket ticket, boolean isRobot);

}
