package zhangjie.raft.core.util;

import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * HashedWheelTimer的包装层
 * @Author zhangjie
 * @Date 2020/5/24 18:39
 **/
public abstract class RepeatedTimer {
    public static final Logger LOG  = LoggerFactory.getLogger(RepeatedTimer.class);

    private final String name;
    private volatile int timeoutMs;
    /** 被包装类 */
    private final Timer timer;



    public RepeatedTimer(final String name,int timeoutMs, final Timer timer) {
        super();
        this.name = name;
        this.timeoutMs = timeoutMs;
        this.timer = timer;
    }


    /**
     *  执行延迟任务
     */
    private void schedule() {
        final TimerTask timerTask = timeout -> {
            try {
                RepeatedTimer.this.run();
            } catch (final Throwable t) {
                LOG.error("Run timer task failed, taskName={}.", RepeatedTimer.this.name, t);
            }
        };
        this.timer.newTimeout(timerTask,adjustTimeout(timeoutMs), TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟任务执行体
     * 与schedule()形成一个循环延迟调用
     */
    public void run() {
        try {
            onTrigger();
        } catch (final Throwable t) {
            LOG.error("Run timer failed.", t);
        }
        schedule();
    }


    /**
     * 子类应为计时器触发实现此方法
     */
    protected abstract void onTrigger();

    /**
     * 每次调度前调整timeoutMs
     * @param timeoutMs timeout millis
     * @return timeout millis
     */
    protected int adjustTimeout(final int timeoutMs) {
        return timeoutMs;
    }


}
