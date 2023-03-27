package ru.wostarnn;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Call implements Comparable<Call> {
    private final String type;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final double cost;
    private final Duration duration;

    public Call(String type, LocalDateTime start, LocalDateTime end, double cost) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.cost = cost;
        duration = Duration.between(start, end);
    }

    public String getStartInGivenFormat(String format) {
        return start.format(DateTimeFormatter.ofPattern(format));
    }

    public String getEndInGivenFormat(String format) {
        return end.format(DateTimeFormatter.ofPattern(format));
    }

    public String getDurationInGivenFormat(String format) {
        LocalTime time = LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
        return time.format(DateTimeFormatter.ofPattern(format));
    }

    public LocalDateTime getStart() {
        return start;
    }
    public double getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Call o) {
        return getStart().compareTo(o.getStart());
    }
}
