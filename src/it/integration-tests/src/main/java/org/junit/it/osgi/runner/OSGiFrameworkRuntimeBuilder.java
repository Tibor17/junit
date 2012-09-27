package org.junit.it.osgi.runner;

import org.junit.it.osgi.FrameworkFactoryLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The purpose of this builder is to build a runner and
 * launch OSGi bundles on the top of Felix framework.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 24.9.2012, 12:14
 */
public final class OSGiFrameworkRuntimeBuilder {
    private static final AtomicInteger workspaceIndex= new AtomicInteger(0);

    private final LinkedHashSet<URL> bundles = new LinkedHashSet<URL>();
    private final Framework framework;
    private long timeout = -1;
    private boolean useShutdownHook;


    public OSGiFrameworkRuntimeBuilder(File root) throws IOException {
        // Load a framework factory
        FrameworkFactory frameworkFactory = FrameworkFactoryLoader.createFrameworkFactory();
        // Create a framework
        Map<String, String> config = new HashMap<String, String>();
        File rootdir= new File(root, "osgi-workspace." + workspaceIndex.getAndIncrement());
        config.put("felix.cache.rootdir", rootdir.getCanonicalPath());
        // Control where OSGi stores its persistent data:
        File cache= new File(rootdir, "felix-cache");
        config.put(Constants.FRAMEWORK_STORAGE, cache.getCanonicalPath());
        // Request OSGi to clean its storage area on startup
        config.put(Constants.FRAMEWORK_STORAGE_CLEAN, "true");
        // Provide the Java 1.5 execution environment
        config.put(Constants.FRAMEWORK_EXECUTIONENVIRONMENT, "J2SE-1.5");
        config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "org.osgi.framework");

        framework = frameworkFactory.newFramework(config);
    }

    private void terminate() {
        try {
            framework.stop();
            framework.waitForStop(0);
        } catch (Exception e) {
            System.err.println("Error stopping framework: " + e);
        }
    }

    public OSGiFrameworkRuntimeBuilder startTimeout(long timeout, TimeUnit unit) {
        if (this.timeout == -1) {
            this.timeout = unit.toMillis(timeout);
            new Timer(true).schedule(new TimerTask() {
                @Override public void run() { System.exit(1); }
            }, this.timeout);
        }
        return this;
    }

    public OSGiFrameworkRuntimeBuilder useShutdownHook() {
        useShutdownHook = true;
        return this;
    }

    public OSGiFrameworkRuntimeBuilder installBundles(Collection<URL> bundles) {
        for (URL bundle : bundles)
            this.bundles.add(bundle);
        return this;
    }

    public OSGiFrameworkRuntime run() {
        if (useShutdownHook) {
            final long startedAt = System.currentTimeMillis();
            Runtime.getRuntime().addShutdownHook(new Thread("Felix Shutdown Hook") {
                public void run() {
                    System.out.println(getClass().getName()
                                        + " test completed within "
                                        + (System.currentTimeMillis() - startedAt)
                                        + " millis");
                    OSGiFrameworkRuntimeBuilder.this.terminate();
                }
            });
        }

        // Start the framework
        try {
            framework.start();
            BundleContext context = framework.getBundleContext();
            for (URL bundle : bundles) {
                Bundle osgiBundle = context.installBundle(bundle.toExternalForm());
                osgiBundle.start();
            }
            return new OSGiFrameworkRuntime(framework);
        } catch (BundleException e) {
            throw new IllegalStateException(e);
        }
    }
}
