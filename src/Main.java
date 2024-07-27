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
        moveAndCollectData(Finger.values(), 3);
        //helloWorld();
    }

    private static void moveAndCollectData(Finger[] fingers, int times) {
        moveFingersTo(fingers, 0);
        DataCollector collecter = new DataCollector(fingers, 5, "_measure.tsv");
        for (int i = 0; i < times; i++) {
            moveFingersTo(fingers, 1, collecter);
            moveFingersTo(fingers, 0, collecter);
        }
    }

    private static void helloWorld(){
        for (Finger finger : Finger.values()){
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
