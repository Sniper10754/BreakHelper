package me.sniper10754.breakhelper;

public class BreakableTask extends Thread {
    Runnable task;
    SignalHandler handler;
    volatile boolean isBreakable=false;

    public BreakableTask(Runnable task, SignalHandler handler) {
        this.handler=handler;
        this.task=task;
    }

    public void handleCTRLC(SignalHandler handler) {
        if (this.isBreakable) {
            handler.run();
        }
    }

    @Override
    public void run() {
        isBreakable=true;
        Runtime.getRuntime().addShutdownHook(
                new Thread(
                        () -> BreakableTask.this.handleCTRLC(BreakableTask.this.handler)
                )
        );

        task.run();
        isBreakable=false;
    }
}
