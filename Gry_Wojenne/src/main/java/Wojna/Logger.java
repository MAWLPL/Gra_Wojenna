package Wojna;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private String filename;

    public Logger(String filename) {
        this.filename = filename;
    }

    public void log(String message) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
