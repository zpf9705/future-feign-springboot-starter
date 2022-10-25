package org.future.feign.other;

import java.io.Serializable;

/**
 * @author zpf
 * @description twice obj
 * @createTime 2022-10-18 13:38
 */
public class P<R, T> implements Serializable {

    private final R left;

    private final T right;

    public P(R left, T right) {
        this.left = left;
        this.right = right;
    }

    public P(){
        this(null,null);
    }

    public static <R, T> P<R, T> of(R left, T right) {
        return new P<>(left, right);
    }

    public static <R, T> P<R, T> empty() {
        return new P<>();
    }

    public R getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }
}
