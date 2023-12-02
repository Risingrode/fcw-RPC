package com.wxy.rpc.core.extension;

/**
 *
 * @Author: fcw
 * @Description: Holder 类  - 持有目标对象
 * @Date: 2023-12-02   19:02
 */

public class Holder<T> {

    // 这确保了在多线程环境下对该字段的读取和写入操作都是原子的。
    // volatile 关键字的作用是禁止线程内部的指令重排，保证了多线程环境下的可见性。
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

}
