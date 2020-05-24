package zhangjie.raft.core;

/**
 * 服务生命周期标记接口
 * @param <T>
 */
public interface Lifecycle<T> {
    /**
     * 初始化服务
     * @param opts 初始化参数
     * @return 当初始化成功时返回true
     */
    boolean init(final T opts);
}
