package me.sniper10754.breakhelper;

public class BreakableTask extends Thread {
    Thread shutdownHook = new Thread(() -> {
        this.handleCTRLC(BreakableTask.this.handler);
        this.afterTask.run();
    });
    volatile Boolean isBreakable=false;
    Runnable task;
    SignalHandler handler;
    Runnable afterTask;

    public BreakableTask(Runnable task, SignalHandler handler, Runnable afterTask) {
        this.handler=handler;
        this.task=task;
        this.afterTask=afterTask;
    }

    public BreakableTask(ProgramFlow task, SignalHandler handler) {
        this.handler=handler;
        this.task=task::beforeBreak;
        this.afterTask=task::afterBreak;
    }

    public void handleCTRLC(SignalHandler handler) {
        if (this.isBreakable) {
            handler.run();
        }
    }

    @Override
    public void run() {
        isBreakable=true;

        try {
            Runtime.getRuntime().addShutdownHook(shutdownHook);

            try {
                task.run();
            } catch (Throwable e) {

            } finally {
                isBreakable=false;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                            "the specified hook has already been registered,\n" +
                            "or it can't be determined that the hook is already running or has already been run"
                    , e
            );
        } catch (IllegalStateException e) {
            shutdownHook.start();
        }
    }
}
