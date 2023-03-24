package ru.wostarnn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CDRProcessor {
    BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/cdr.txt"));
    Map<String, Abonent> abonents = new HashMap<>();

    public CDRProcessor() throws IOException {
        while (reader.ready()) {
            processLine();
        }
        reader.close();
        for (String phoneNumber : abonents.keySet()) {
            abonents.get(phoneNumber).generateReport();
        }
    }

    private void processLine() throws IOException {
        String[] line = readLine();
        if (!abonents.containsKey(line[1])) {
            Abonent abonent = new Abonent();
            switch (line[4]) {
                case "03":
                    abonent = new Abonent(line[1], line[4]);
                    break;
                case "11":
                    abonent = new AbonentWithExtraMinutes(line[1], line[4], 100);
                    break;
                case "06":
                    abonent = new AbonentWithExtraMinutes(line[1], line[4], 300);
                    break;
            }
            abonents.put(line[1], abonent);
        }
        processCall(line);
    }
    private String[] readLine() throws IOException {
        return reader.readLine().split(", ");

    }

    private void processCall(String[] line) {
        Abonent abonent = abonents.get(line[1]);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime1 = LocalDateTime.parse(line[2], format);
        LocalDateTime dateTime2 = LocalDateTime.parse(line[3], format);
        Duration duration = Duration.between(dateTime1, dateTime2);
        long callDurationInMinutesCeil = duration.toMinutes() + (duration.toSecondsPart() == 0 ? 0 : 1);
        double callCost = valueCall(line, abonent, callDurationInMinutesCeil);
        abonent.addTotalRoubles(callCost);
        abonent.addCallToCallList(new Call(line[0], dateTime1, dateTime2, callCost));
    }
    private double valueCall(String[] line, Abonent abonent, long duration) {

        double callCost = 0.0;
        long extraMinutes;
        long minBetweenExtraAndCall;
        switch (line[4]) {
            case "03":
                callCost = duration * 1.5;
                break;
            case "11":
                if (line[0].equals("01")) {
                    extraMinutes = ((AbonentWithExtraMinutes) abonent).getExtraMinutes();
                    minBetweenExtraAndCall = Math.min(extraMinutes, duration);
                    if (minBetweenExtraAndCall != 0) {
                        callCost += minBetweenExtraAndCall * 0.5;
                        ((AbonentWithExtraMinutes) abonent).substractExtraMinutes(minBetweenExtraAndCall);
                        duration -= minBetweenExtraAndCall;
                    }
                    callCost += duration * 1.5;
                }
                break;
            case "06":
                extraMinutes = ((AbonentWithExtraMinutes) abonent).getExtraMinutes();
                minBetweenExtraAndCall = Math.min(extraMinutes, duration);
                if (minBetweenExtraAndCall != 0) {
                    ((AbonentWithExtraMinutes) abonent).substractExtraMinutes(minBetweenExtraAndCall);
                    duration -= minBetweenExtraAndCall;
                }
                callCost += duration;
                break;
        }
        return callCost;
    }
}
