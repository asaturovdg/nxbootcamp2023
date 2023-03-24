package ru.wostarnn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Abonent {
    private String phoneNumber;
    private String tariff;
    private double totalRoubles;
    private List<Call> calls = new ArrayList<>();
    public Abonent() {

    }

    public Abonent(String phoneNumber, String tariff) {
        this.phoneNumber = phoneNumber;
        this.tariff = tariff;
        this.totalRoubles = 0.0;
    }

    public void addTotalRoubles(double amount) {
        this.totalRoubles += amount;
    }
    public void addCallToCallList(Call call) {
        calls.add(call);
    }
    public void generateReport() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("reports/" + phoneNumber + ".txt"));
        writer.write(String.format("Tariff index: %s\n", tariff));
        writer.write("----------------------------------------------------------------------------\n");
        writer.write(String.format("Report for phone number %s:\n", phoneNumber));
        writer.write("----------------------------------------------------------------------------\n");
        writer.write("| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n");
        writer.write("----------------------------------------------------------------------------\n");
        for (Call call : calls) {
            writer.write(String.format(Locale.US, "|     %s    | %s | %s | %s |%6.2f |\n", call.getType(), call.getStartInGivenFormat("yyyy-MM-dd HH:mm:ss"), call.getEndInGivenFormat("yyyy-MM-dd HH:mm:ss"), call.getDurationInGivenFormat("HH:mm:ss"), call.getCost()));
        }
        writer.write("----------------------------------------------------------------------------\n");
        writer.write(String.format(Locale.US, "|                                           Total Cost: |%10.2f rubles |\n", totalRoubles));
        writer.write("----------------------------------------------------------------------------\n");
        writer.close();
    }
}
