import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Minotaur {
    public static final int NUM_GUESTS = 12;
    public static final int TIME_MULTIPLIER = 0;
    public int calculatedNumGuests = 0;
    // Without fairness, the guests sometimes hog the lock, causing the counter to
    // never get a chance to run
    public Lock lock = new ReentrantLock(true);
    public boolean thereIsCupcake = true;
    public boolean done = false;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        Minotaur host = new Minotaur();

        // Create guests
        for (int i = 0; i < NUM_GUESTS; i++) {
            // Any guest can be the counter but here we are just making the first guest the
            // counter
            if (i == 0)
                threadExecutor.execute(new Guest(host, i + 1, true));
            else
                threadExecutor.execute(new Guest(host, i + 1));
        }

        threadExecutor.shutdown();
        threadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("All guests have left the labyrinth");
        System.out.println();

        // Calculate the number of guests to pass through the labyrinth
        System.out.println(host.calculatedNumGuests + " guests passed through the labyrinth");

    }
}