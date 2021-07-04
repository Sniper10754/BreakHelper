package me.sniper10754.breakhelper;

public class BreakableTask extends Thread {
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

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            BreakableTask.this.handleCTRLC(BreakableTask.this.handler);
            this.afterTask.run();
        }));

        try {
            task.run();
        } catch (Throwable e) {

        } finally {
            isBreakable=false;
        }

    }
}
