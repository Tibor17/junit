package org.junit.it;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.it.Utils.*;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * The purpose of this test is to test a functionality of.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 31.8.2012, 20:00
 */
public final class InvokerTargetBomTest {

    @Test
    public void existInvokerBuildDir() throws IOException {
        File buildDir= loadInvokerBuildDirectory();
        assertTrue(buildDir.getCanonicalPath() + " should be invoker's build directory", buildDir.isDirectory());
    }

    @Test
    public void checkMainJar() throws IOException {
        File buildDir= loadInvokerBuildDirectory();
        assumeTrue(buildDir.isDirectory());
        File mainArtifact= new File(buildDir, loadItProjectArtifactId() + '-' + loadItProjectVersion()+ ".jar");
        assertTrue(mainArtifact.getCanonicalPath() + " should be main jar", mainArtifact.isFile());
    }

    /* temporarily disabled
	@Test
    public void checkMainJavadocJar() throws IOException {
        File buildDir= loadInvokerBuildDirectory();
        assumeTrue(buildDir.isDirectory());
        File mainDoc= new File(buildDir, loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-javadoc.jar");
        assertTrue(mainDoc.getCanonicalPath() + " should be main javadoc jar", mainDoc.isFile());
    }

    @Test
    public void checkMainSourcesJar() throws IOException {
        File buildDir= loadInvokerBuildDirectory();
        assumeTrue(buildDir.isDirectory());
        File mainSources= new File(buildDir, loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-sources.jar");
        assertTrue(mainSources.getCanonicalPath() + " should be main sources jar", mainSources.isFile());
    }

    @Test
    public void checkTestJar() throws IOException {
        File buildDir= loadInvokerBuildDirectory();
        assumeTrue(buildDir.isDirectory());
        File testJar= new File(buildDir, loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-tests.jar");
        assertTrue(testJar.getCanonicalPath() + " should be test jar", testJar.isFile());
    }

    @Test
    public void checkTestSourcesJar() throws IOException {
        File buildDir= loadInvokerBuildDirectory();
        assumeTrue(buildDir.isDirectory());
        File testSources= new File(buildDir, loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-test-sources.jar");
        assertTrue(testSources.getCanonicalPath() + " should be test sources jar", testSources.isFile());
    }

    @Test
    public void existAssemblyZip() throws IOException {
		assumeThat(loadItProjectArtifactId(), equalTo("junit"));
        File assemblyDir= loadInvokerAssemblyDirectory();
        assumeTrue(assemblyDir.isDirectory());
        File zip= new File(assemblyDir, loadItProjectArtifactId() + loadItProjectVersion()+ ".zip");
        assertTrue(zip.getCanonicalPath() + " should be assembly zip", zip.isFile());
    }

    @Test
    public void checkAssemblyZip() throws IOException {
		assumeThat(loadItProjectArtifactId(), equalTo("junit"));
	}*/
}
