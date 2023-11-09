package global;

import model.dao.Airplane;
import model.dao.Ticket;
import model.dao.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static model.constans.AirplaneConstans.AVAILABLE_SEATS;

public class GlobalData {
    public static AtomicReference<Set<Airplane>> airplanesAtomic = new AtomicReference<>();
    public static AtomicReference<Map<Ticket, User>> ticketAndUserAtomic = new AtomicReference<>();
    public static AtomicReference<Map<Airplane, Set<Ticket>>> ticketAirplanesAtomic = new AtomicReference<>();

    static {
        BigDecimal initialPrice = new BigDecimal(200);
        airplanesAtomic.set(Set.of(
                new Airplane("Baku", "Moskow", initialPrice, AVAILABLE_SEATS,
                        LocalDateTime.now().plus(1, TimeUnit.MINUTES.toChronoUnit())),
                new Airplane("Baku", "Istanbul", initialPrice, AVAILABLE_SEATS,
                        LocalDateTime.now().plus(1, TimeUnit.MINUTES.toChronoUnit())),
                new Airplane("Baku", "London", initialPrice, AVAILABLE_SEATS,
                        LocalDateTime.now().plus(1, TimeUnit.MINUTES.toChronoUnit())),
                new Airplane("Baku", "Roma", initialPrice, AVAILABLE_SEATS,
                        LocalDateTime.now().plus(1, TimeUnit.MINUTES.toChronoUnit()))));


        Map<Airplane, Set<Ticket>> airplaneSetMap = new HashMap<>();
        Map<Ticket, User> ticketAndUser = new HashMap<>();

        for (Airplane airplane : airplanesAtomic.get()) {
            Set<Ticket> tickets = new HashSet<>();
            for (int i = 0; i < AVAILABLE_SEATS; i++) {
                tickets.add(new Ticket(airplane));
            }
            airplaneSetMap.put(airplane,tickets);
            tickets.clear();
        }
        ticketAirplanesAtomic.set(airplaneSetMap);
        ticketAndUserAtomic.set(ticketAndUser);
    }
}
