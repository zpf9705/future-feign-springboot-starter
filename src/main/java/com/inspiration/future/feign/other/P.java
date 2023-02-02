package com.inspiration.future.feign.other;

import io.reactivex.rxjava3.core.Single;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * twice obj
 * @see org.apache.commons.lang3.tuple.Triple
 *
 *
 * @author zpf
 * @since 1.1.0
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

        private static final long serialVersionUID = -3365222529936832584L;
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
            T right_1 = o1.getRight();
            R left_1 = o1.getLeft();
            T right_2 = o2.getRight();
            R left_2 = o2.getLeft();
            return Integer.compare(right_1.hashCode() + left_1.hashCode(),
                    right_2.hashCode() + left_2.hashCode());
        }
    }
}
