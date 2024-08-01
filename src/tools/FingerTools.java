package tools;
import fingermanager.Finger;

public final class FingerTools {
    private FingerTools() {
    }

    public static double APPR = 0.03;

    public static void moveFingerTo(Finger finger, double targetPosition, Runnable action) {
        moveFingersTo(new Finger[]{finger}, new double[]{targetPosition}, action);
    }

    public static void moveFingerTo(Finger finger, double targetPosition) {
        moveFingerTo(finger, targetPosition, () -> {});
    }

    public static void moveFingersTo(Finger[] fingers, double[] targets, final Runnable action) throws IllegalArgumentException {
        if(fingers.length != targets.length) {
            throw new IllegalArgumentException("The number of fingers does not match the number of targets");
        }
        for (int i = 0; i < fingers.length; i++) {
            fingers[i].setPositionTarget(targets[i]);
        }
        do action.run();
        while (waitUntil(fingers, targets));
        int i = 0;
    }

    private static boolean waitUntil(Finger[] fingers, double[] targets) {

        for (int i = 0; i < fingers.length; i++) {
            if (Math.abs(fingers[i].getPosition() - targets[i]) > APPR) return true;
        }
        return false;

    }

    public static void moveFingersTo(Finger[] fingers, double target, final Runnable action) throws IllegalArgumentException {
        double[] targets = new double[fingers.length];
        for (int i = 0; i < fingers.length; i++) {
            targets[i] = target;
        }
        moveFingersTo(fingers, targets, action);
    }

    public static void moveFingersTo(Finger[] fingers, double target) throws IllegalArgumentException {
        moveFingersTo(fingers, target, () -> {});
    }


    public static void moveFingersTo(Finger[] fingers, double[] targets) throws IllegalArgumentException {
        moveFingersTo(fingers, targets, () -> {});
    }

    public static void waitUntil(long time, Runnable action) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < time) {
            action.run();
        }
    }
}
