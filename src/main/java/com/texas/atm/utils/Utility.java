package com.texas.atm.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utility {

    public static String generateAccountNumber() {
        // Get current timestamp in milliseconds
        long timestamp = Instant.now().toEpochMilli();

        Random random = new Random(timestamp); // Seed the random number generator with the timestamp
        StringBuilder accountNumber = new StringBuilder();

        // Add "SB" as the initial prefix
        accountNumber.append("SB");

        // Generate 8 random digits to make a total of 10 characters including "SB"
        for (int i = 0; i < 8; i++) {
            accountNumber.append(random.nextInt(10)); // Generates a random digit between 0 and 9
        }

        return accountNumber.toString();
    }
    // Function to get the current date with time formatted according to a custom pattern
    public static String getCurrentDateTimeFormatted() {
        // Get the current date with time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a custom date-time formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date with time using the custom formatter
        String formattedDateTime = currentDateTime.format(formatter);

        // Return the formatted date with time
        return formattedDateTime;
    }

}