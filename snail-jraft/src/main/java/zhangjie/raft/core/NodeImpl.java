package zhangjie.raft.core;

import io.netty.util.HashedWheelTimer;
import zhangjie.raft.core.util.NamedThreadFactory;
import zhangjie.raft.core.util.RepeatedTimer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author zhangjie
 * @Date 2020/5/24 18:46
 **/
public class NodeImpl implements Node{
    /** Timers */
    private RepeatedTimer electionTimer;

    @Override
    public boolean init(Object opts) {
        String name = "JRaft-VoteTimer-node1";
        //可以做成配置参数
        int electionTimeoutMs = 1000;
        this.electionTimer = new RepeatedTimer(name,electionTimeoutMs,
                new HashedWheelTimer(new NamedThreadFactory(name, true), 1, TimeUnit.MILLISECONDS, 2048)) {

            @Override
            protected void onTrigger() {
                handleVoteTimeout();
            }

            @Override
            protected int adjustTimeout(int timeoutMs) {
                return randomTimeout(timeoutMs);
            }
        };
        return false;
    }

    private int randomTimeout(final int timeoutMs) {
        //可以做成配置参数
        int maxElectionDelayMs = 1000;
        return ThreadLocalRandom.current().nextInt(timeoutMs, timeoutMs + maxElectionDelayMs);
    }

    /**
     * 处理预选举和选举事件
     */
    private void handleVoteTimeout() {

    }
}
