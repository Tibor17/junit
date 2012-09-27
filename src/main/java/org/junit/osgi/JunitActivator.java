package org.junit.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The purpose of this class is to activate and deactivate OSGi bundle.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 2.9.2012, 20:05
 */
public class JunitActivator implements BundleActivator {
    private volatile BundleContext bundleContext = null;

    /**
     * Implements BundleActivator.start().
     * @param bundleContext - the framework context for the junit bundle.
     **/
    public void start(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
        System.out.println("junit activator started");
    }

    /**
     * Implements BundleActivator.stop()
     * @param bundleContext - the framework context for the junit bundle.
     **/
    public void stop(BundleContext bundleContext) {
        this.bundleContext = null;
        System.out.println("junit activator stopped");
    }

    private Bundle[] getBundles() {
        BundleContext bundleContext = this.bundleContext;
        return bundleContext != null ? bundleContext.getBundles() : null;
    }
}