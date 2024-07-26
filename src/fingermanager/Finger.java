package fingermanager;

/**
 * The class for managing The Shunk five-finger-hand
 * Notice that each method gets executed imediate. Mayte the use of sleep-methods could make sense.
 */
public enum Finger {

    THUMB_FLEXION(0),
    THUMB_DISTAL(1),
    INDEX_DISTAL(2),
    INDEX_PROXIMAL(3),
    MIDDLE_DISTAL(4),
    MIDDLE_PROXIMAL(5),
    RING(6),
    PINKY(7),
    SPREAD(8);

    private final int index;

    /**
     * <p>initializer for the Enum. The Indes is defined in the c++ package</p>
     * @param index The index of the finger
     */
    Finger(int index) {
        this.index = index;
    }

    /**
     * <p>Sets the Target position of the finger.</p>
     * <p>0 means stretched, and 1 means closed
     * If the given value was too high or too low, it is reduced to the nearest possible value</p>
     * @param target A double between 0 and 1
     * @return True, if a valid value were passed, else false
     */
    public boolean setPositionTarget(double target) {
        return !(setPositionTarget(index, target) == 0);
    }

    /**
     * <p>Set the current speed of the finger.</p>
     * <p>1 is the maximum speed. 0 represents a deactivated finger, and e.g the value 0.5 should result in a finger miving 0.5 times so fast.
     * If the given value was too high or too low, it is reduced to the nearest possible value</p>
     * @param speed A double between 0 and 1
     * @return True, if a valid value were passed, else false
     */
    public boolean setSpeed(double speed) {
        return !(setSpeed(index, speed) == 0);
    }

    /**
     * <p>Sets the maximal force of the finger</p>
     * <p>The value is given in Newton.<br>
     * At the moment, we don't know the exact maximum value of this command. However, if the given value was too high, it gets reduced to the nearest legit value.
     * Also, at the moment, the current force is just an approximation. In our tests, the measured value differed up to 30%.<br>
     * An negative value will be transformed to an positive value </p>
     * @param maxNewton A force in newton
     * @return True, if a valid value were passed, else false
     */
    public boolean setMaxNewton(double maxNewton) {
        return !(setMaxNewton(index, maxNewton) == 0);
    }

    /**
     * @return the actual mA of the finger
     */
    public short getmA() {
        return getmA(0); // Example usage
    }

    /**
     * Returns The actual mA of the finger. The actual value could differ up to 30%.
     * @return the actual Newton of the finger
     */
    public double getNewton() {
        return getNewton(index);
    }

    /**
     * Gets the actual position of the finger
     * @return The position between 0 and 1
     */
    public double getPosition() {
        return getPosition(index);
    }
    
    /**
     * Initialises the five-Finger-Manger.
     * an init method has to be called before any use of onother method.
     */
    public static native void initFiveFingerManager();

    /**
     * Initialises the five-Finger-Manger.
     * An init method has to be called before any use of onother method.
     * @param port the port of the serial device
     */
    public static native void initFiveFingerManagerWindows(String port);

    //Dieser Code lädt die im System gespeicherten Variablen
    static {
        // Get the current value of java.library.path
        String libraryPath = System.getProperty("java.library.path");

        // Print the value
        System.out.println("java.library.path: " + libraryPath);

        //If on windows, libnames begins with a "lib"
        System.loadLibrary(System.getProperty("os.name").toLowerCase().startsWith("win")
            ? "libsvh_java" : "svh_java");

        System.out.println("svh_java library loaded.");
        //initFiveFingerManager();
    }


    //The native c methods
    private static native byte setPositionTarget(int finger, double position);

    private static native byte setSpeed(int finger, double speed);

    private static native byte setMaxNewton(int finger, double newton);
    
    private static native byte setMaxmA(int finger, double mA);

    private static native short getmA(int finger);

    private static native double getNewton(int finger);

    private static native double getPosition(int finger);
}
