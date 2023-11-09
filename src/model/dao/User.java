package model.dao;

import model.sequence.SequenceId;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class User {
    private final long id;
    private final String name;
    private final String surname;
    private final Ticket ticket;
    private String finNumber;
    private final Baggage baggage;
    private final BigDecimal purchasePrice;
    private final LocalDateTime saleDate;


    public User(String name, String surname, Ticket ticket, String finNumber, Baggage baggage, BigDecimal purchasePrice) {
        this.id = SequenceId.nextVal();
        this.name = name;
        this.surname = surname;
        this.ticket = ticket;
        this.baggage = baggage;
        setFinNumber(finNumber);
        this.saleDate = LocalDateTime.now();
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice == null ? new BigDecimal(0) : purchasePrice;
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }
    private void setFinNumber(String finNumber) {
        if (finNumber.length() == 7) {
            this.finNumber = finNumber.toUpperCase();
        }
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public String getFinNumber() {
        return finNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Baggage getBaggage() {
        return baggage;
    }

    @Override
    public String toString() {
        return getPassengerInfo(true);
    }

    public String getPassengerInfo(boolean forPassenger) {
        StringBuilder builder = new StringBuilder();
        if (forPassenger) {
            builder.append("\n-----------------------------------------\n");
        }
        builder.append("id: ").append(getId())
                .append("\nfull name: ").append(getName()).append(" ").append(getSurname())
                .append("\nfinNumber: ").append(getFinNumber());

        if (getBaggage() != null) {
            builder.append(getBaggage());
        }

        builder.append(getTicket().getTicketDetails(forPassenger))
                .append("\n\tsaleDate: ").append(getSaleDate());
        if (forPassenger) {
            builder.append("\n-----------------------------------------\n");
        }
        return builder.toString();
    }
}
