package com.vmware;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Writer {

    public static synchronized void writeToFile(int number) {
        {
            try {
                FileWriter fStream = new FileWriter("data.txt", true);
                BufferedWriter out = new BufferedWriter(fStream);
                out.write(number + ",");
                out.close();
            } catch (Exception e) {
                System.err.println("Error while writing to file: " +
                        e.getMessage());
            }
        }
    }
}