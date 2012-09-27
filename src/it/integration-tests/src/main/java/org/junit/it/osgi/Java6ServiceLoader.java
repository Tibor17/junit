package org.junit.it.osgi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * The purpose of this service loader is to hide and
 * delegate to Java 6 <tt>java.util.ServiceLoader</tt>.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 17.9.2012, 21:53
 */
final class Java6ServiceLoader<S> implements IServiceLoader<S> {
    private final ServiceLoader<S> serviceLoader;

    Java6ServiceLoader(Class<S> clazz, ClassLoader cl) {
        serviceLoader = ServiceLoader.load(clazz, cl);
    }

    Java6ServiceLoader(Class<S> clazz) {
        this(clazz, Thread.currentThread().getContextClassLoader());
    }

    public synchronized S loadNew() {
        serviceLoader.reload();
        Iterator<S> it = serviceLoader.iterator();
        return it.hasNext() ? it.next() : null;
    }
}
