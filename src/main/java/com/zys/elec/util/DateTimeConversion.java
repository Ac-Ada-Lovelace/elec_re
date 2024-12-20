package com.zys.elec.util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConversion {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();

        // Convert LocalDateTime to Instant
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // Convert Instant to Date
        Date date = Date.from(instant);

        // Get time in milliseconds
        long milliseconds = date.getTime();

        System.out.println("Milliseconds since epoch: " + milliseconds);
    }
}