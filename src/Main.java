import fingermanager.Finger;
import tools.DataCollector;

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
        Finger[] fingers = {Finger.THUMB_DISTAL, Finger.INDEX_DISTAL, Finger.INDEX_PROXIMAL, Finger.MIDDLE_DISTAL, Finger.MIDDLE_PROXIMAL, Finger.RING, Finger.PINKY};
        moveAndCollectData(fingers, 3);
        //helloWorld();
        /*
        Finger finger = Finger.MIDDLE_PROXIMAL;
        finger.setPositionTarget(0);
        sleep(2000);
        finger.setPositionTarget(1);
        double pos = finger.getPosition();
        do {
            sleep(20);
            pos = finger.getPosition();
            System.out.println(pos);
        } while(Math.abs(1 - pos) > 0.05);
        System.out.println(pos);
        System.out.println(Math.abs(1 - pos) > 0.05);
        */
    }

    private static void moveAndCollectData(Finger[] fingers, int times) {
        moveFingersTo(fingers, 0);
        DataCollector collector = new DataCollector(fingers, 5, "_measure.tsv");
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
