package org.junit.it.osgi.runner;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Runner result.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 25.9.2012, 0:15
 */
public class OSGiFrameworkRuntime {
    private final Framework framework;

    public OSGiFrameworkRuntime(Framework framework) {
        this.framework = framework;
    }

    public void stop() {
        try {
            framework.stop();
        } catch (BundleException e) {
            e.printStackTrace(System.err);
        }
    }

    public boolean hasLoadedClass(String bundle, String clazz) {
        BundleContext context = framework.getBundleContext();
        try {
            Bundle b = mapBundles().get(bundle);
            return b != null && b.loadClass(clazz) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public Collection<String> getSymbolicNames() {
        return mapBundles().keySet();
    }

    private Map<String, Bundle> mapBundles() {
        BundleContext context = framework.getBundleContext();
        Bundle[] bundles = context.getBundles();
        HashMap<String, Bundle> map = new HashMap<String, Bundle>(bundles.length);
        for (Bundle bundle : bundles)
            map.put(bundle.getSymbolicName(), bundle);
        return map;
    }
}
