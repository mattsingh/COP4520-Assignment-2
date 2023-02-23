public class Guest implements Runnable {
    public int id;
    private Party party;

    public Guest(Party party, int id) {
        this.party = party;
        this.id = id;
    }

    @Override
    public void run() {
        while (!party.isOver) {
            try {
                // Roam around the party
                Thread.sleep((long) (Math.random() * 1000) * 3 * Party.TIME_MULTIPLIER);
                // Join the line
                party.joinLine(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
