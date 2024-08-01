import fingermanager.Finger;
import tools.DataCollector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static tools.FingerTools.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the finger manager
        Finger.initFiveFingerManager();

        // Running the provided test functions
        ///testAllFingerSpread();
        //testSpeed();
        //mainPosition();
        //getData(5);
        //Finger[] fingers = {Finger.THUMB_FLEXION, Finger.INDEX_DISTAL, Finger.INDEX_PROXIMAL, Finger.MIDDLE_DISTAL, Finger.MIDDLE_PROXIMAL, Finger.RING, Finger.PINKY};
        //moveAndCollectData(fingers, 1);
        //helloWorld();
        //measureLatency(Finger.MIDDLE_PROXIMAL);
        //grabCube_optimised();
        latency_improved();
    }

    private static void grabCube_optimised() {
        final double MAX_FORCE = 2;
        Finger.SPREAD.setPositionTarget(1);
        moveFingerTo(Finger.THUMB_DISTAL, 1);
        System.out.println("Warte 2 Sekunden");
        sleep(2000);
        Finger[] fingers = {Finger.THUMB_FLEXION, Finger.INDEX_DISTAL, Finger.INDEX_DISTAL, Finger.INDEX_PROXIMAL, Finger.MIDDLE_DISTAL, Finger.MIDDLE_PROXIMAL, Finger.RING, Finger.PINKY};
        for (Finger finger : fingers) {
            if(!(finger.equals(Finger.THUMB_DISTAL) || finger.equals(Finger.PINKY) || finger.equals(Finger.THUMB_FLEXION)))
                System.out.println(finger.toString() + finger.setMaxNewton(MAX_FORCE));
            finger.setPositionTarget(0.7);
        }
        Finger.THUMB_FLEXION.setPositionTarget(0.4);

        double force = Finger.INDEX_DISTAL.getNewton();
        while(force * 2 < MAX_FORCE) {
            sleep(10);
            force = Finger.INDEX_DISTAL.getNewton();
        }
        System.out.println("Warte 5 Sekunden...");
        sleep(5000);
        moveFingersTo(fingers, 0);
    }

    private static void finger_names() {
        for(Finger finger : Finger.values()) {
            System.out.println(finger.toString());
            finger.setPositionTarget(1);
            sleep(2000);
            finger.setPositionTarget(0);
            sleep(2000);
        }
    }

    private static void latency_improved() {
        for(Finger finger : Finger.values()) {
            for (int i = 0; i < 10; i++) {
                double speed = (10-i) * 0.1;
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(finger.toString() + speed))) {
                    for(int j = 0; j < 100; j++) {
                        moveFingerTo(finger, 0);
                        finger.setPositionTarget(1);
                        sleep(50);
                        long time = System.nanoTime() / 1000;
                        finger.setPositionTarget(0);
                        double pos = finger.getPosition();
                        double newPos = pos;
                        while (newPos >= pos) {
                            pos = newPos;
                            newPos = finger.getPosition();
                        }
                        writer.write(System.nanoTime() / 1000 - time + "\n");
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void grabCube() {
        Finger.SPREAD.setPositionTarget(1);
        moveFingerTo(Finger.THUMB_DISTAL, 1);
        System.out.println("Warte 2 Sekunden");
        sleep(2000);
        Finger[] fingers = {Finger.THUMB_FLEXION, Finger.INDEX_DISTAL, Finger.INDEX_DISTAL, Finger.INDEX_PROXIMAL, Finger.MIDDLE_DISTAL, Finger.MIDDLE_PROXIMAL, Finger.RING, Finger.PINKY};
        moveFingersTo(fingers, 0.7);
    }

    private static void measureLatency(Finger finger) {
        moveFingerTo(finger, 0);
        finger.setSpeed(1);
        DataCollector collector = new DataCollector(new Finger[]{finger}, 0, "latenc-test.tsv");
        finger.setPositionTarget(1);
        waitUntil(500, collector);

        System.out.println(System.nanoTime() / 1000 - collector.startTime);

        moveFingerTo(finger, 0, collector);

    }

    private static void moveAndCollectData(Finger[] fingers, int times) {
        moveFingersTo(fingers, 0);
        DataCollector collector = new DataCollector(fingers, 0, "_measure.tsv");
        for (int i = 0; i < times; i++) {
            moveFingersTo(fingers, 0.7, collector);
            moveFingersTo(fingers, 0, collector);
        }
        collector.close();
    }

    private static void helloWorld(){
        for (Finger finger : Finger.values()){
            if (finger.equals(Finger.THUMB_DISTAL)) continue;
            finger.setPositionTarget(1.0);
        }
        sleep(2000);
        Finger.MIDDLE_DISTAL.setPositionTarget(0.0);
        Finger.MIDDLE_PROXIMAL.setPositionTarget(0.0);
        sleep(2000);

    }

    // Wrapper methods to call the corresponding native functions
    private static void testAllFingerSpread() {
        for (Finger finger : Finger.values()) {
            System.out.println("Testing finger: " + finger.name());
            finger.setPositionTarget(1.0);
            sleep(2000);
            finger.setPositionTarget(0.0);
            sleep(2000);
        }
    }

    private static void testSpeed() {
        Finger finger = Finger.MIDDLE_DISTAL;
        for (int i = 0; i < 10; i++) {
            double speed = 1.0 - (i / 10.0);
            finger.setSpeed(speed);
            finger.setPositionTarget(1.0);
            sleep(2000);
            finger.setPositionTarget(0.0);
            sleep(2000);
        }
    }

    // Helper method to sleep for the specified milliseconds
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
