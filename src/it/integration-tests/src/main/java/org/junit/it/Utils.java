package org.junit.it;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * The purpose of this utility is to load test.properties.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 31.8.2012, 19:43
 */
public final class Utils {
    /**
     * See pom.xml, property 'default.path.separator'.
     */
    public static char DEFAULT_PATH_SEPARATOR = ';';

    // Suppresses default constructor, ensuring non-instantiability.
    private Utils() {}

    private static String loadTestProperty(String key) throws IOException {
        FileReader f= new FileReader(new File(System.getProperty("user.dir"), "test.properties"));
        try {
            Properties testProperties= new Properties();
            testProperties.load(f);
            return testProperties.getProperty(key);
        } finally {
            f.close();
        }
    }

    public static String loadItProjectGroupId() throws IOException {
        return loadTestProperty("it.project.groupId");
    }

    public static String loadItProjectArtifactId() throws IOException {
        return loadTestProperty("it.project.artifactId");
    }

    public static String loadItProjectVersion() throws IOException {
        return loadTestProperty("it.project.version");
    }

    public static File loadItLocalRepositoryPath() throws IOException {
        return new File(loadTestProperty("it.localRepositoryPath"));
    }

    public static File loadItBasedir() throws IOException {
        return new File(loadTestProperty("it.project.basedir"));
    }

    public static File loadItBuildDirectory() throws IOException {
        return new File(loadTestProperty("it.project.build.directory"));
    }

    public static File loadItOutputDirectory() throws IOException {
        return new File(loadTestProperty("it.project.build.outputDirectory"));
    }

    public static File loadInvokerBasedir() throws IOException {
        return new File(loadTestProperty("invoker.basedir"));
    }

    public static File loadInvokerBuildDirectory() throws IOException {
        return new File(loadTestProperty("invoker.build.directory"));
    }

    public static File loadInvokerAssemblyDirectory() throws IOException {
        return loadInvokerBuildDirectory();
    }

    public static File loadTestProperties() throws IOException {
        return new File(loadItBasedir(), "test.properties");
    }

    public static String loadOSGiRunnerClasspath() throws IOException {
        return loadCP(new File(loadItBasedir(), "runner.cp"));
    }

    public static String loadJUnitClasspath() throws IOException {
        //usually only junit:jar
        return loadCP(new File(loadItBasedir(), "junit.cp"));
    }

    public static Collection<URL> loadJUnitClasspathAsURL() throws IOException {
        String junitClasspath = loadJUnitClasspath();
        StringTokenizer t = new StringTokenizer(junitClasspath, DEFAULT_PATH_SEPARATOR+"");
        ArrayList<URL> bundles = new ArrayList<URL>();
        while (t.hasMoreTokens()) {
            String bundlePath = t.nextToken().trim();
            URL bundleUrl = new File(bundlePath).toURL();
            if (!bundles.contains(bundleUrl)) bundles.add(bundleUrl);
        }
        return bundles;
    }

    private static String loadCP(File cpFile) throws IOException {
        BufferedReader runnerClasspath= null;
        try {
            runnerClasspath = new BufferedReader(new FileReader(cpFile));
            return runnerClasspath.readLine().trim().replace(DEFAULT_PATH_SEPARATOR, File.pathSeparatorChar);
        } finally {
            if (runnerClasspath != null)
                runnerClasspath.close();
        }
    }

    public static void main(String... args) throws IOException, InterruptedException {
        System.exit(forkProcess("java", new String[] {"-version"}, null, true, null, null, true));
    }

    public static int forkProcess(String command, String[] arguments,
                                  File workingDirectory,
                                  boolean redirectErrorStream, PrintStream stdOut,
                                  Map<String, String> envProperties,
                                  boolean verbose)
                                    throws InterruptedException, IOException {
        if (command == null) throw new NullPointerException();
        ArrayList<String> processCommand = new ArrayList<String>();
        processCommand.add(command);
        if (arguments != null) processCommand.addAll(Arrays.asList(arguments));
        ProcessBuilder process = new ProcessBuilder(processCommand);
        process.directory(workingDirectory == null ? new File(System.getProperty("user.dir")) : workingDirectory);
        if (envProperties != null) process.environment().putAll(envProperties);
        if (redirectErrorStream) process.redirectErrorStream(true);
        if (verbose) {
            StringBuilder s = new StringBuilder("forked process:");
            String lineSeparator= System.getProperty("line.separator");
            if (!process.environment().isEmpty()) {
                s.append(lineSeparator).append("system environment = {");
                for (Map.Entry<String, String> env : process.environment().entrySet())
                    s.append(env.getKey()).append('=').append(env.getValue()).append(',');
                s.append("}");
            }
            s.append(lineSeparator).append(command).append(' ');
            if ("java".equals(command)) {
                s.append("-Duser.dir=")
                  .append('"').append(process.directory().getCanonicalPath()).append('"');
            }
            for (String cmd : processCommand) s.append(cmd).append(' ');
            System.out.println(s);
        }
        Process p = process.start();
        writeProcessOutput(p, stdOut == null ? System.out : stdOut);
        return p.waitFor();
    }

    private static void writeProcessOutput(Process process, PrintStream stdOut) throws IOException {
        BufferedReader reader
          = new BufferedReader(new InputStreamReader(new BufferedInputStream(process.getInputStream())));
        for (String line; (line = reader.readLine()) != null; ) {
            stdOut.println(line);
        }
    }
}
