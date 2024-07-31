package tools;

public interface Delay_Runnable extends Runnable {
    long getDelay();

    default void delay() {
        try {
            Thread.sleep(getDelay());
        } catch (InterruptedException e) {}
    }
}
