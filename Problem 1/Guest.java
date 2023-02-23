public class Guest implements Runnable {
    public int guestCounter = 1;
    private int id;
    private Minotaur host;
    private boolean isCounterGuest;
    private boolean hasBeenThroughLabyrinth = false;

    public Guest(Minotaur host, int id) {
        this.id = id;
        this.host = host;
        this.isCounterGuest = false;
    }

    public Guest(Minotaur host, int id, boolean isCounterGuest) {
        this.id = id;
        this.host = host;
        this.isCounterGuest = isCounterGuest;
    }

    @Override
    public void run() {
        // Simulate walking through labyrinth
        while (!host.done) {
            try {
                host.lock.lock();
                System.out.println("Guest " + id + " is walking through the labyrinth");
                Thread.sleep((long) (Math.random() * 1000) * Minotaur.TIME_MULTIPLIER);
                if (isCounterGuest) { // Counter guest
                    if (!host.thereIsCupcake) {
                        // Another guest has passed through the labyrinth and eaten the cupcake
                        System.out.println("Guest " + id + " has requested a cupcake");
                        host.thereIsCupcake = true;
                        guestCounter++;
                    }
                    if (guestCounter == Minotaur.NUM_GUESTS) {
                        // All guests have passed through the labyrinth
                        // This doesn't affect the counter, but this is the only way to stop the threads
                        host.calculatedNumGuests = guestCounter;
                        host.done = true;
                    }
                    System.out.println("Guest " + id + " has determined that " + guestCounter
                            + " guests have passed through the labyrinth");
                } else if (!hasBeenThroughLabyrinth && host.thereIsCupcake) { // Regular guest
                    // Guest's first time through the labyrinth should eat the cupcake so that the
                    // counter knows that a guest has passed through the labyrinth
                    System.out.println("Guest " + id + " has eaten the cupcake");
                    host.thereIsCupcake = false;
                    hasBeenThroughLabyrinth = true;
                }
                System.out.println("Guest " + id + " has left the labyrinth");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                host.lock.unlock();
            }
        }
    }

}
