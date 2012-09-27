package org.junit.it.osgi.main;

import org.junit.it.osgi.runner.OSGiFrameworkRuntime;
import org.junit.it.osgi.runner.OSGiFrameworkRuntimeBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.it.Utils.loadItBuildDirectory;
import static org.junit.it.Utils.loadJUnitClasspathAsURL;
import static org.junit.it.osgi.main.Assert.assertContains;
import static org.junit.it.osgi.main.Assert.assertTrue;

/**
 * The purpose of this runner is to test JUnit bundle
 * on the top of Felix framework.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 24.9.2012, 11:09
 */
public final class StandaloneOSGiBundleRunner {

    public static void main(String... args) throws IOException {
        File rootDir = loadItBuildDirectory();
        OSGiFrameworkRuntime runtime = new OSGiFrameworkRuntimeBuilder(rootDir)
                                      .startTimeout(15L, TimeUnit.SECONDS)
                                      .useShutdownHook()
                                      .installBundles(loadJUnitClasspathAsURL())
                                      .run();
        Collection<String> bundles = runtime.getSymbolicNames();
        assertContains(bundles, "org.apache.felix.framework");
        assertContains(bundles, "org.junit");
        String clazz = "junit.runner.Version";
        assertTrue(runtime.hasLoadedClass("org.junit", clazz), clazz + " should be loaded");
        clazz = "org.junit.internal.JUnitSystem";
        assertTrue(runtime.hasLoadedClass("org.junit", clazz), clazz + " should be loaded but private");
        runtime.stop();
    }
}
