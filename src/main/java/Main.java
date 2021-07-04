import me.sniper10754.breakhelper.BreakableTask;
import me.sniper10754.breakhelper.ProgramFlow;
import me.sniper10754.breakhelper.SignalHandler;

public class Main implements ProgramFlow {
    public static void main(String[] args) {

        BreakableTask task = new BreakableTask(new Main(), () -> System.out.println("Interrupted"));

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
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }
}
