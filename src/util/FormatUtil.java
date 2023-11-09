package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {
    public static String getDateTimeByFormatter(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm");

        return dateTime.format(dateTimeFormatter);
    }
    public static String getPriceByFormatter(BigDecimal price) {
        return new DecimalFormat("#,##0.00").format(price);
    }
}
