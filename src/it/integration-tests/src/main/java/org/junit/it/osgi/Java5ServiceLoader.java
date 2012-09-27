package org.junit.it.osgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * The purpose of
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 17.9.2012, 21:52
 */
final class Java5ServiceLoader<S> implements IServiceLoader<S> {
    private final Class<S> clazz;
    private final ClassLoader cl;

    Java5ServiceLoader(Class<S> clazz, ClassLoader cl) {
        this.clazz = clazz;
        this.cl = cl;
    }

    Java5ServiceLoader(Class<S> clazz) {
        this(clazz, Thread.currentThread().getContextClassLoader());
    }

    public synchronized S loadNew() {
        URL url = cl.getResource("META-INF/services/".concat(clazz.getName()));
        if (url == null) return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            for (String s; (s = br.readLine()) != null; ) {
                s = s.trim();
                // Try to load first non-empty, non-commented line.
                if (s.length() > 0 && s.charAt(0) != '#')
                    return clazz.cast(Class.forName(s).newInstance());
            }
            return null;
        } catch (Exception e) {
            throw new Error(e);
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {}
        }
    }
}
