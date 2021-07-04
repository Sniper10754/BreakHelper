import me.sniper10754.breakhelper.BreakableTask;
import me.sniper10754.breakhelper.ProgramFlow;
import me.sniper10754.breakhelper.SignalHandler;

public class Main implements ProgramFlow {
    public static void main(String[] args) {

        SignalHandler handler = () -> System.out.println("Interrupted");

        BreakableTask task = new BreakableTask(new Main(), handler);

        task.start();
    }

    @Override
    public void beforeBreak() {
        System.out.println("Before!");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.err.println("Not done :(");
        }
    }

    @Override
    public void afterBreak() {
        System.out.println("After!");
    }
}
