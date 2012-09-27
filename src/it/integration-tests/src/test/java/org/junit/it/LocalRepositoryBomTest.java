/*
 * The copyright holders of this work license this file to You under
 * the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.junit.it;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.it.Utils.*;

/**
 * The purpose of this test is to test a functionality of.
 * <p/>
 *
 * @author tibor17
 * @version 4.11
 * @since 4.11, 1.9.2012, 15:17
 */
public class LocalRepositoryBomTest {
    private static File installedArtifactDir(File localRepositoryPath) throws IOException {
        String canonicalPath= localRepositoryPath.getCanonicalPath();
        return new File(canonicalPath + File.separatorChar +
                                    loadItProjectGroupId() + File.separatorChar +
                                    loadItProjectArtifactId() + File.separatorChar +
                                    loadItProjectVersion());
    }

    @Test
    public void existLocalRepository() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assertTrue(localRepositoryPath.getCanonicalPath() + " should be Maven local repository", localRepositoryPath.isDirectory());
    }

    @Test
    public void existLocalRepositoryArtifact() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File artifactPath= installedArtifactDir(localRepositoryPath);
        assertTrue(artifactPath.getCanonicalPath() + " should be Maven artifact dir in local repository",
                    artifactPath.isDirectory());
    }

    @Test
    public void existPOM() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File projectPOM= new File(installedArtifactDir(localRepositoryPath),
                                    loadItProjectArtifactId() + '-' + loadItProjectVersion()+ ".pom");
        assertTrue(projectPOM.getCanonicalPath() + " should be project POM", projectPOM.isFile());
    }

    @Test
    public void existMainJar() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File mainArtifact= new File(installedArtifactDir(localRepositoryPath),
                                    loadItProjectArtifactId() + '-' + loadItProjectVersion()+ ".jar");
        assertTrue(mainArtifact.getCanonicalPath() + " should be main jar", mainArtifact.isFile());
    }

    /* temporarily disabled
	@Test
    public void existMainJavadocJar() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File mainDoc= new File(installedArtifactDir(localRepositoryPath),
                                loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-javadoc.jar");
        assertTrue(mainDoc.getCanonicalPath() + " should be main javadoc jar", mainDoc.isFile());
    }

    @Test
    public void existMainSourcesJar() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File mainSources= new File(installedArtifactDir(localRepositoryPath),
                                    loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-sources.jar");
        assertTrue(mainSources.getCanonicalPath() + " should be main sources jar", mainSources.isFile());
    }

    @Test
    public void existTestJar() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File testJar= new File(installedArtifactDir(localRepositoryPath),
                                loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-tests.jar");
        assertTrue(testJar.getCanonicalPath() + " should be test jar", testJar.isFile());
    }

    @Test
    public void existTestSourcesJar() throws IOException {
        File localRepositoryPath= loadItLocalRepositoryPath();
        assumeTrue(localRepositoryPath.isDirectory());
        File testSources= new File(installedArtifactDir(localRepositoryPath),
                                    loadItProjectArtifactId() + '-' + loadItProjectVersion()+ "-test-sources.jar");
        assertTrue(testSources.getCanonicalPath() + " should be test sources jar", testSources.isFile());
    }*/
}
