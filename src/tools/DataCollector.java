package tools;

import fingermanager.Finger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DataCollector implements Delay_Runnable {

    private final BufferedWriter[] file;
    private final Finger[] fingers;
    private final long delay;
    public final long startTime;

    public DataCollector(Finger[] fingers, long delay, String path) {
        file = new BufferedWriter[fingers.length];
        for (int i = 0; i < fingers.length; i++) {
            try {
                file[i] = new BufferedWriter(new FileWriter(fingers[i].toString() + path, true));
                file[i].write("time[ms]\tposition\tcurrent[mA]\tforce[N]\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.fingers = fingers;
        this.delay = delay;
        this.startTime = System.currentTimeMillis();
    }

    public void close() {
        for (BufferedWriter f : file) {
            try {
                f.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        delay();
        for (int i = 0; i < fingers.length; i++) {
            try {
                file[i].write(
                        (System.currentTimeMillis() - startTime) + "\t" +
                                fingers[i].getPosition() + "\t" +
                                fingers[i].getmA() + "\t" +
                                fingers[i].getNewton() + "\n"
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public long getDelay() {
        return delay;
    }
}