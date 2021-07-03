import me.sniper10754.breakhelper.BreakableTask;
import me.sniper10754.breakhelper.SignalHandler;

public class Main {
    public static void main(String[] args) {
        SignalHandler handler = new SignalHandler() {
            @Override
            public void run() {
                System.out.println("Interrotto");
            }
        };

        BreakableTask task = new BreakableTask(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.err.println("Non fatto :(");
            }
            System.out.println("Fatto!");
        }, handler
        );

        task.start();
    }
}
