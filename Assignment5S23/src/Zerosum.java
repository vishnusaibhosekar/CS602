public class Zerosum {
    private int balance = 0;

    public static void main(String[] args) {
        Zerosum zs = new Zerosum();
        Thread t1 = new Thread(() -> zs.gainCalories());
        Thread t2 = new Thread(() -> zs.burnCalories());
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Balance is: " + zs.balance);
    }

    public synchronized void gainCalories() {
        for (int i = 0; i < 1000; i++) {
            balance++;
            System.out.println("Gaining Calories! Calories count: " + balance);
        }
    }

    public synchronized void burnCalories() {
        for (int i = 0; i < 1000; i++) {
            balance--;
            System.out.println("Burning Calories! Calories count: " + balance);
        }
    }
}
