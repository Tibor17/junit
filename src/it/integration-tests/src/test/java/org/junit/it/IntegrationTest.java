package org.junit.it;

import junit.runner.Version;
import org.junit.Test;
import org.junit.it.osgi.FrameworkFactoryLoader;
import org.junit.it.osgi.main.StandaloneOSGiBundleRunner;
import org.junit.it.osgi.runner.OSGiFrameworkRuntime;
import org.junit.it.osgi.runner.OSGiFrameworkRuntimeBuilder;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.tests.AllTests;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.it.Utils.forkProcess;
import static org.junit.it.Utils.loadItBasedir;
import static org.junit.it.Utils.loadItBuildDirectory;
import static org.junit.it.Utils.loadItOutputDirectory;
import static org.junit.it.Utils.loadItProjectVersion;
import static org.junit.it.Utils.loadOSGiRunnerClasspath;
import static org.junit.runner.JUnitCore.runClasses;

/**
 * The purpose of this test is to test a functionality of JUnit including resources files.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 31.8.2012, 19:55
 */
public final class IntegrationTest {

    @Test
    public void allTests() {
        long startedAt = System.currentTimeMillis();
        Class test= AllTests.class;
        Result result= runClasses(test);
        System.out.println("-------------------------------------------------------");
        System.out.println(" T E S T S");
        System.out.println("-------------------------------------------------------");
        System.out.printf("Running %s", test.getName());
        System.out.println();
        System.out.printf("Tests run: %d, Failures: %d, Skipped: %d, Time elapsed: %.3f sec",
          result.getRunCount(), result.getFailureCount(), result.getIgnoreCount(), result.getRunTime() / 1000f);
        System.out.println();
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTestHeader());
            System.out.println(failure.getTrace());
        }
        assertTrue(result.wasSuccessful());
        System.out.println("allTests " + (System.currentTimeMillis() - startedAt));
    }

    @Test
    public void checkVersion() throws IOException {
        long startedAt = System.currentTimeMillis();
        String version= Version.id();
        String bundleVersion= loadItProjectVersion();
        assertThat(version, equalTo(bundleVersion));
        System.out.println("checkVersion " + (System.currentTimeMillis() - startedAt));
    }

    @Test
    public void smokeTestOSGi() throws Exception {
        long startedAt = System.currentTimeMillis();
		File rootDir = loadItBuildDirectory();
        OSGiFrameworkRuntime runtime = new OSGiFrameworkRuntimeBuilder(rootDir)
                                      .startTimeout(15L, TimeUnit.SECONDS)
                                      .run();
		runtime.stop();
        System.out.println("smokeTestOSGi " + (System.currentTimeMillis() - startedAt));
    }

    @Test
    public void itOSGi() throws IOException, InterruptedException {
        long startedAt = System.currentTimeMillis();
        String classpath
          = loadOSGiRunnerClasspath() + File.pathSeparator + loadItOutputDirectory().getCanonicalPath();

        String[] args
          = {"-Xms32m", "-Xmx32m",
            "-ea", "-verbose:class",
            "-classpath", "\"" + classpath + "\"",
            StandaloneOSGiBundleRunner.class.getName()};

        int exitCode= forkProcess("java", args, loadItBasedir(), true, System.out, null, true);
        System.out.println("itOSGi " + (System.currentTimeMillis() - startedAt));
        assertTrue(exitCode == 0);
    }
}
