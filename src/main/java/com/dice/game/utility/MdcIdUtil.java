package com.dice.game.utility;

import org.slf4j.MDC;
import java.util.UUID;


public class MdcIdUtil {
    public static String generateMdcId() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String getMdcId() {
        return MDC.get("X-Correlation-ID");
    }

    public static void setMdcId(String token) {
        MDC.put("X-Correlation-ID", token);
    }

    public static void setNewMdcId() {
        MDC.put("X-Correlation-ID", generateMdcId());
    }

    public static void removeMdcId() {
        MDC.remove("X-Correlation-ID");
    }
}
