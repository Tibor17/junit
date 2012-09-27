package org.junit.it.osgi.main;

import java.util.Collection;

/**
 * The integration test assertions.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 25.9.2012, 3:10
 */
public final class Assert {
    private Assert() {}

    public static <T> void assertContains(Collection<T> c, T t) {
        assert c.contains(t) : c + " should contain " + t;
    }

    public static void assertTrue(boolean condition, String msg) {
        assert condition : msg;
    }

    public static void assertFalse(boolean condition, String msg) {
        assert !condition : msg;
    }
}
