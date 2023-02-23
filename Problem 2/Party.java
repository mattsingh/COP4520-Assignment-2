import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Party {
    public static final int NUM_GUESTS = 5;
    public static final int TIME_MULTIPLIER = 3;
    public static final int PARTY_DURATION_SECONDS = 10;
    public boolean isOver = false;
    // Fairness policy is applied so that the threads are scheduled in the order
    // they join the line
    public Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        Party party = new Party();
        for (int i = 0; i < NUM_GUESTS; i++) {
            threadExecutor.execute(new Guest(party, i + 1));
        }

        Thread.sleep(PARTY_DURATION_SECONDS * 1000);
        party.isOver = true;

        threadExecutor.shutdown();
        threadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("The party is over");
    }

    public void joinLine(Guest guest) throws InterruptedException {
        System.out.println("Guest " + guest.id + " joined the line");
        // This lock is used to ensure that only one guest is in the showroom at a time
        // and forces the other guest threads to wait until the scheduler gives them the
        // lock
        lock.lock();
        try {
            // Observe the vase
            System.out.println("Guest " + guest.id + " is observing the vase");
            Thread.sleep((long) (Math.random() * 1000) * TIME_MULTIPLIER);
            // Leave the room
            System.out.println("Guest " + guest.id + " left the showroom");
        } finally {
            lock.unlock();
        }
    }
}
