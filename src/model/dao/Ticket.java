package model.dao;

import global.GlobalData;
import model.sequence.SequenceId;
import util.FormatUtil;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class Ticket {
    private final long id;
    private final String name;
    private final String seatNumber;
    private final Airplane airplane;
    private boolean isTaken;

    public Ticket(Airplane airplane) {
        this.id = SequenceId.nextVal();
        this.name = UUID.randomUUID().toString().substring(0,9);
        this.seatNumber = name.substring(0,4);
        this.airplane = airplane;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void baggageHas(Baggage baggage) {
        if (baggage != null && baggage.getWeight() > 10) {
            this.airplane.setPrice(this.airplane.getPrice().add(new BigDecimal(30)));
        }
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Airplane getAirplane() {
        return airplane;
    }
    public void takenSeat() {
        isTaken = true;
    }

    public boolean isTaken() {
        return isTaken;
    }

    @Override
    public String toString() {
        return getTicketDetails(true);
    }

    public String getTicketDetails(boolean forPassenger) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\tticket: {")
                .append("\n\t\tid: ").append(getId())
                .append("\n\t\tticketName: ").append(getName())
                .append("\n\t\tticketPrice: ").append(getSalePrice()).append("$")
                .append("\n\t\tseatNumber: ").append(getSeatNumber());
        if (forPassenger) {
            builder.append("\n\t\tfrom: ").append(getAirplane().getFrom()).append(" to ").append(getAirplane().getTo())
                    .append("\n\t\tflyDate: ").append(getAirplane().getFlyDateTime());
        }
        builder.append("\n\t}\n");

        return builder.toString();
    }

    public String getSalePrice() {
        Map<Ticket, User> ticketUserMap = GlobalData.ticketAndUserAtomic.get();
        User user = ticketUserMap.get(this);

        return FormatUtil.getPriceByFormatter(user.getPurchasePrice());
    }
}
