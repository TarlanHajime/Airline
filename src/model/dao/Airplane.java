package model.dao;

import global.GlobalData;
import model.sequence.SequenceId;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

import static model.constans.AirplaneConstans.*;
import static util.FormatUtil.getDateTimeByFormatter;
import static util.FormatUtil.getPriceByFormatter;


public class Airplane {

    private final long id;
    private final String from;
    private final String to;
    private final Semaphore availableSeats;
    private final LocalDateTime flyDateTime;
    private boolean isFly;
    private boolean isFull;
    private final AtomicReference<BigDecimal> price = new AtomicReference<>(new BigDecimal(0));

    public Airplane(String from, String to, BigDecimal price, int availableSeats, LocalDateTime flyDateTime) {
        this.id = SequenceId.nextVal();
        this.from = from;
        this.to = to;
        this.availableSeats = new Semaphore(availableSeats);
        this.flyDateTime = flyDateTime;
        this.price.set(price);
    }

    public long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Semaphore getSeats() {
        return availableSeats;
    }

    public LocalDateTime getFlyDateTime() {
        return flyDateTime;

    }

    public boolean isFlight() {
        return isFly;
    }

    public boolean isFlightMoment() {
        return Duration.between(LocalDateTime.now(), getFlyDateTime()).isNegative();
    }

    public void fly() {
        isFly = true;
    }

    private Set<Ticket> getAllTicket() {
        Map<Airplane, Set<Ticket>> allAirplane = GlobalData.ticketAirplanesAtomic.get();
        return allAirplane.get(this);
    }

    private BigDecimal calculateTotalSalePrice() {
        double totalSalePrice = 0.0;
        Set<Ticket> ticketsOfThisAirplane = getAllTicket();

        for (Ticket ticket : ticketsOfThisAirplane) {
            Map<Ticket, User> allTicketsAndUsers = GlobalData.ticketAndUserAtomic.get();
            User userOfTicket = allTicketsAndUsers.get(ticket);
            if (userOfTicket != null) {
                totalSalePrice += userOfTicket.getPurchasePrice().doubleValue();
            }
        }
        return new BigDecimal(totalSalePrice);
    }
    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPriceByDemandTicket() {
        if (getTakenSeats() > 1 && getTakenSeats() < 7) {
            setPrice(getPrice().add(getPrice().multiply(PRICE_INCREASE_UP_TO_7_SALES_IN_7_SECONDS)));
        } else if (getTakenSeats() >= 7) {
            setPrice(getPrice().add(getPrice().multiply(PRICE_INCREASE_FOR_MORE_THAN_7_SALES_IN_7_SECONDS)));
        } else {
            setPrice(getPrice().subtract(getPrice().multiply(PRICE_DECREASE_FOR_1_OR_LESS_THAN_1_SALES_IN_7_SECONDS)));
        }

        if (getPrice().compareTo(MIN_SALE_PRICE) < 0) {
            setPrice(MIN_SALE_PRICE);
        }
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public boolean isFull() {
        return isFull;
    }

    public void full() {
        isFull = true;
    }

    public int getTakenSeats() {
        return AVAILABLE_SEATS - getSeats().availablePermits();
    }

    public int getAvailableSeats() {
        return getSeats().availablePermits();
    }

    @Override
    public String toString() {
        return "\n------------------------------------\n" +
                getId() + ". Airplane " + getFrom() + " to " + getTo() + " flight." +
                "\n\t- total profit: " + getPriceByFormatter(calculateTotalSalePrice()) + "$" +
                "\n\t- fly date time: " + getDateTimeByFormatter(getFlyDateTime()) +
                "\n\t- total passengers: " + getTakenSeats() +
                "\n------------------------------------\n";
    }
    public String getAvailableFlights() {

        return "\n------------------------------------\n" +
                getId() + ". Airplane " + getFrom() + " to " + getTo() +
                "\n- fly date time: " + getDateTimeByFormatter(getFlyDateTime()) +
                "\n- ticket price: " + getPriceByFormatter(getPrice()) + "$" +
                "\n------------------------------------\n";
    }
}
