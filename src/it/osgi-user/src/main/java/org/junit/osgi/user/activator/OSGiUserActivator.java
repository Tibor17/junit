package org.junit.osgi.user.activator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The purpose of this class is to activate
 * and deactivate OSGi bundle for testing purposes.
 * <p/>
 *
 * @author tibor17
 * @version 1.0
 * @since 1.0, 23.9.2012, 19:33
 */
public class OSGiUserActivator implements BundleActivator {
    private volatile BundleContext bundleContext = null;

    /**
     * Implements BundleActivator.start().
     * @param bundleContext - the framework context for the junit bundle.
     **/
    public void start(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
        System.out.println("osgi-user activator started");
    }

    /**
     * Implements BundleActivator.stop()
     * @param bundleContext - the framework context for the junit bundle.
     **/
    public void stop(BundleContext bundleContext) {
        this.bundleContext = null;
        System.out.println("osgi-user activator stopped");
    }

    private Bundle[] getBundles() {
        BundleContext bundleContext = this.bundleContext;
        return bundleContext != null ? bundleContext.getBundles() : null;
    }
}
