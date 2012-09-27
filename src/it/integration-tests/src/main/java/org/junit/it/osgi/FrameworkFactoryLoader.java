package org.junit.it.osgi;

import org.osgi.framework.launch.FrameworkFactory;

import java.util.StringTokenizer;

/**
 * The purpose of
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 17.9.2012, 21:51
 */
public final class FrameworkFactoryLoader {
    private static final IServiceLoader<FrameworkFactory> serviceLoader;

    static {
        StringTokenizer t = new StringTokenizer(System.getProperty("java.version"), ".");
        float version = Float.valueOf(t.nextToken() + "." + t.nextToken());
        Class<FrameworkFactory> service = FrameworkFactory.class;
        ClassLoader cl = service.getClassLoader();
        serviceLoader = version >= 1.6f
          ? new Java6ServiceLoader<FrameworkFactory>(service, cl)
          : new Java5ServiceLoader<FrameworkFactory>(service, cl);
    }

    public static FrameworkFactory createFrameworkFactory() {
        return serviceLoader.loadNew();
    }
}
