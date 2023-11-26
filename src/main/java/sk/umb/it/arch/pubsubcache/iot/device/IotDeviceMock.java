package sk.umb.it.arch.pubsubcache.iot.device;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IotDeviceMock {
    private static BigDecimal TEMP_MIN = new BigDecimal("18").setScale(2);
    private static BigDecimal TEMP_MAX = new BigDecimal("30").setScale(2);
    private static BigDecimal HUM_MIN = new BigDecimal("40").setScale(2);
    private static BigDecimal HUM_MAX = new BigDecimal("60").setScale(2);

    private static final DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Map<String, Object> readData() {
        Map data = new HashMap();

        data.put("TEMP", between(TEMP_MIN, TEMP_MAX));
        data.put("HUM", between(HUM_MIN, HUM_MAX));
        data.put("TS", LocalDateTime.now().format(TS_FORMAT).toString());

        return data;
    }

    private static BigDecimal between(BigDecimal min, BigDecimal max) {
        int digitCount = Math.max(min.precision(), max.precision());
        int bitCount = (int)(digitCount / Math.log10(2.0));

        BigDecimal alpha = new BigDecimal(new BigInteger(bitCount, new Random()))
                .movePointLeft(digitCount);

        return min.add(max.subtract(min)
                  .multiply(alpha, new MathContext(digitCount)))
                  .round(new MathContext(3));
    }

}
