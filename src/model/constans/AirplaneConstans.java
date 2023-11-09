package model.constans;

import java.math.BigDecimal;

public class AirplaneConstans {
    public static final int AVAILABLE_SEATS = 10;
    public static final BigDecimal PRICE_INCREASE_UP_TO_7_SALES_IN_7_SECONDS = new BigDecimal("0.2");
    public static final BigDecimal PRICE_INCREASE_FOR_MORE_THAN_7_SALES_IN_7_SECONDS = new BigDecimal("0.3");
    public static final BigDecimal PRICE_DECREASE_FOR_1_OR_LESS_THAN_1_SALES_IN_7_SECONDS = new BigDecimal("0.2");
    public static final BigDecimal MIN_SALE_PRICE = new BigDecimal(80);
}
