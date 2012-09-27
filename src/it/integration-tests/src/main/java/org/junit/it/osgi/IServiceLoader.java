package org.junit.it.osgi;

/**
 * The purpose of
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 17.9.2012, 22:15
 */
public interface IServiceLoader<S> {
    S loadNew();
}
