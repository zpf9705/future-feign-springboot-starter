package com.inspiration.future.feign.other;

import io.reactivex.rxjava3.core.Single;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author zpf
 * @description twice obj
 * @createTime 2022-10-18 13:38
 */
public abstract class P<R, T> implements Serializable, Comparator<P<R, T>> {

    /**
     * Serialization version
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param left  {@link Triple#left}
     * @param right {@link Triple#right}
     * @return {@link P#of(Object, Object)}
     * @description: static method of to obj
     */
    public static <R, T> P<R, T> of(R left, T right) {
        return new Triple<>(left, right);
    }

    /**
     * @return {@link P#getLeft()}
     * @description: left getter
     */
    public abstract R getLeft();

    /**
     * @return {@link P#getRight()}
     * @description: left getter
     */
    public abstract T getRight();


    public static class Triple<R, T> extends P<R, T> implements Cloneable {

        /**
         * left obj
         */
        private final R left;

        /**
         * right obj
         */
        private final T right;

        /**
         * @param left  {@link Triple#left}
         * @param right {@link Triple#right}
         * @description: to params construct {@link Triple#Triple(Object, Object)}
         */
        public Triple(R left, T right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public R getLeft() {
            return left;
        }

        @Override
        public T getRight() {
            return right;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Triple<R, T> clone() {
            Object o = null;
            try {
                o = super.clone();
            } catch (CloneNotSupportedException e) {
                // to do noting
            }
            if (Objects.isNull(o)) {
                return null;
            }
            return Single.just(o)
                    .ofType(Triple.class)
                    .blockingGet();
        }

        @Override
        public int compare(P<R, T> o1, P<R, T> o2) {
            T right1 = o1.getRight();
            T right2 = o1.getRight();
            T right11 = o2.getRight();
            T right22 = o2.getRight();
            return Integer.compare(right1.hashCode() + right2.hashCode(),
                    right11.hashCode() + right22.hashCode());
        }
    }
}
