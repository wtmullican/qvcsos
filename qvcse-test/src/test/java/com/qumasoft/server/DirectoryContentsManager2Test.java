//   Copyright 2004-2014 Jim Voris
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//
// $FilePath$
//     $Date: Wednesday, March 21, 2012 10:31:04 PM $
//   $Header: DirectoryContentsManager2Test.java Revision:1.3 Wednesday, March 21, 2012 10:31:04 PM JimVoris $
// $Copyright � 2011-2012 Define this string in the qvcs.keywords.properties property file $

package com.qumasoft.server;

import com.qumasoft.TestHelper;
import com.qumasoft.qvcslib.ArchiveDirManagerInterface;
import com.qumasoft.qvcslib.ArchiveInfoInterface;
import com.qumasoft.qvcslib.QVCSConstants;
import com.qumasoft.qvcslib.QVCSException;
import com.qumasoft.qvcslib.RemoteViewProperties;
import com.qumasoft.qvcslib.ServerResponseFactoryInterface;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A second set of tests for the DirectoryContentsManager class. This set of tests focuses on testing file moves on the trunk to make sure that those moves are correctly propagated to any translucent
 * branches.
 *
 * @author $Owner: JimVoris $
 */
public class DirectoryContentsManager2Test {

    private static ProjectView translucentFeature1BranchProjectView = null;
    private static ProjectView translucentFeature2BranchProjectView = null;
    private ServerResponseFactoryInterface bogusResponseObject = null;
    private DirectoryContentsManager directoryContentsManager = null;
    private static Date viewDate = null;

    /**
     * Set up stuff once for these tests.
     *
     * @throws java.lang.Exception if anything goes wrong.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        TestHelper.deleteViewStore();
        TestHelper.initializeArchiveFiles();
        TestHelper.startServer();
        viewDate = new Date();
        initializeTranslucentFeature1Branch();
        initializeTranslucentFeature2Branch();
    }

    /**
     * Tear down the class level stuff.
     *
     * @throws java.lang.Exception if anything goes wrong.
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        TestHelper.stopServer();
        TestHelper.deleteViewStore();
        TestHelper.removeArchiveFiles();
    }

    /**
     * Set up before each test.
     */
    @Before
    public void setUp() {
        directoryContentsManager = DirectoryContentsManagerFactory.getInstance().getDirectoryContentsManager(TestHelper.getTestProjectName());
        bogusResponseObject = new BogusResponseObject();
        ServerTransactionManager.getInstance().flushClientTransaction(bogusResponseObject);
        ServerTransactionManager.getInstance().clientBeginTransaction(bogusResponseObject);
    }

    /**
     * Tear down after each test.
     */
    @After
    public void tearDown() {
    }

    static private void initializeTranslucentFeature1Branch() throws QVCSException {
        Properties projectProperties = new Properties();
        RemoteViewProperties translucentFeature1BranchProperties = new RemoteViewProperties(getProjectName(), getTranslucentFeature1BranchName(), projectProperties);
        translucentFeature1BranchProperties.setIsReadOnlyViewFlag(false);
        translucentFeature1BranchProperties.setIsDateBasedViewFlag(false);
        translucentFeature1BranchProperties.setIsTranslucentBranchFlag(true);
        translucentFeature1BranchProperties.setIsOpaqueBranchFlag(false);
        translucentFeature1BranchProperties.setBranchParent(QVCSConstants.QVCS_TRUNK_VIEW);
        translucentFeature1BranchProperties.setBranchDate(new Date());
        translucentFeature1BranchProjectView = new ProjectView();
        translucentFeature1BranchProjectView.setProjectName(getProjectName());
        translucentFeature1BranchProjectView.setViewName(getTranslucentFeature1BranchName());
        translucentFeature1BranchProjectView.setRemoteViewProperties(translucentFeature1BranchProperties);
        ViewManager.getInstance().addView(translucentFeature1BranchProjectView);
    }

    static private void initializeTranslucentFeature2Branch() throws QVCSException {
        Properties projectProperties = new Properties();
        RemoteViewProperties translucentFeature2BranchProperties = new RemoteViewProperties(getProjectName(), getTranslucentFeature2BranchName(), projectProperties);
        translucentFeature2BranchProperties.setIsReadOnlyViewFlag(false);
        translucentFeature2BranchProperties.setIsDateBasedViewFlag(false);
        translucentFeature2BranchProperties.setIsTranslucentBranchFlag(true);
        translucentFeature2BranchProperties.setIsOpaqueBranchFlag(false);
        translucentFeature2BranchProperties.setBranchParent(QVCSConstants.QVCS_TRUNK_VIEW);
        translucentFeature2BranchProperties.setBranchDate(new Date());
        translucentFeature2BranchProjectView = new ProjectView();
        translucentFeature2BranchProjectView.setProjectName(getProjectName());
        translucentFeature2BranchProjectView.setViewName(getTranslucentFeature2BranchName());
        translucentFeature2BranchProjectView.setRemoteViewProperties(translucentFeature2BranchProperties);
        ViewManager.getInstance().addView(translucentFeature2BranchProjectView);

    }

    static private String getProjectName() {
        return TestHelper.getTestProjectName();
    }

    static private String getTranslucentFeature1BranchName() {
        return "2.2.2-Feature1";
    }

    static private String getTranslucentFeature2BranchName() {
        return "2.2.2-Feature2";
    }

    /**
     * Verify test setup. This test just verifies that we have set things up in a useful way for the tests in this unit test.
     *
     * @throws QVCSException if there is a QVCS problem.
     */
    @Test
    public void test1VerifyTestSetup() throws QVCSException, IOException {
        DirectoryContentsManager instance = directoryContentsManager;
        Collection<ProjectView> projectViewCollection = ViewManager.getInstance().getViews(getProjectName());
        ProjectView translucentView = ViewManager.getInstance().getView(getProjectName(), getTranslucentFeature1BranchName());
        assertEquals("Wrong number of branches", 2, projectViewCollection.size());

        ArchiveDirManagerInterface firstDirManager = ArchiveDirManagerFactoryForServer.getInstance().getDirectoryManager(QVCSConstants.QVCS_SERVER_SERVER_NAME, getProjectName(),
                getTranslucentFeature1BranchName(), TestHelper.SUBPROJECT_APPENDED_PATH, QVCSConstants.QVCS_SERVED_PROJECT_TYPE, QVCSConstants.QVCS_SERVER_USER, bogusResponseObject, true);
        assertTrue(firstDirManager != null);
        ArchiveInfoInterface firstDirArchiveInfo = firstDirManager.getArchiveInfo(TestHelper.SUBPROJECT_FIRST_SHORTWORKFILENAME);
        assertTrue(firstDirArchiveInfo != null);

        ArchiveDirManagerInterface secondDirManager = ArchiveDirManagerFactoryForServer.getInstance().getDirectoryManager(QVCSConstants.QVCS_SERVER_SERVER_NAME, getProjectName(),
                getTranslucentFeature1BranchName(), TestHelper.SUBPROJECT2_APPENDED_PATH, QVCSConstants.QVCS_SERVED_PROJECT_TYPE, QVCSConstants.QVCS_SERVER_USER, bogusResponseObject, true);
        assertTrue(secondDirManager != null);
        ArchiveInfoInterface secondDirArchiveInfo = secondDirManager.getArchiveInfo(TestHelper.SUBPROJECT2_FIRST_SHORTWORKFILENAME);
        assertTrue(secondDirArchiveInfo != null);

        DirectoryContents trunkContents = directoryContentsManager.getDirectoryContentsForTrunk(TestHelper.SUBPROJECT_APPENDED_PATH, firstDirManager.getDirectoryID(), bogusResponseObject);
        DirectoryContents feature1BranchContents = directoryContentsManager.getDirectoryContentsForTranslucentBranch(translucentView, TestHelper.SUBPROJECT_APPENDED_PATH,
                firstDirManager.getDirectoryID(), bogusResponseObject);
        assertEquals(trunkContents.getFiles().size(), feature1BranchContents.getFiles().size());

        ServerTransactionManager.getInstance().clientEndTransaction(bogusResponseObject);
    }

    /**
     * Move a file on one branch, and then move into that same directory on the trunk.
     *
     * @throws QVCSException if there is a QVCS problem.
     */
    @Test
    public void test2MoveOnOneBranchThenMoveOnTrunk() throws QVCSException, IOException {
        ArchiveDirManagerInterface originTrunkDirManager = ArchiveDirManagerFactoryForServer.getInstance().getDirectoryManager(QVCSConstants.QVCS_SERVER_SERVER_NAME, getProjectName(),
                getTranslucentFeature1BranchName(), "", QVCSConstants.QVCS_SERVED_PROJECT_TYPE, QVCSConstants.QVCS_SERVER_USER, bogusResponseObject, true);
        assertTrue(originTrunkDirManager != null);
        int trunkRootDirFileCount = originTrunkDirManager.getArchiveInfoCollection().size();
        ArchiveInfoInterface originArchiveInfo = originTrunkDirManager.getArchiveInfo(TestHelper.SECOND_SHORTWORKFILENAME);
        ArchiveInfoInterface origin3ArchiveInfo = originTrunkDirManager.getArchiveInfo(TestHelper.THIRD_SHORTWORKFILENAME);
        assertTrue(originArchiveInfo != null);
        ArchiveDirManagerInterface destinationTrunkDirManager = ArchiveDirManagerFactoryForServer.getInstance().getDirectoryManager(QVCSConstants.QVCS_SERVER_SERVER_NAME, getProjectName(),
                getTranslucentFeature1BranchName(), TestHelper.SUBPROJECT_APPENDED_PATH, QVCSConstants.QVCS_SERVED_PROJECT_TYPE, QVCSConstants.QVCS_SERVER_USER, bogusResponseObject, true);
        assertTrue(destinationTrunkDirManager != null);
        int trunkDestinationDirOriginalFileCount = destinationTrunkDirManager.getArchiveInfoCollection().size();

        // Move the file on the branch.
        directoryContentsManager.moveFileOnTranslucentBranch(getTranslucentFeature1BranchName(), originTrunkDirManager.getDirectoryID(), destinationTrunkDirManager.getDirectoryID(),
                originArchiveInfo.getFileID(), bogusResponseObject);
        ServerTransactionManager.getInstance().clientEndTransaction(bogusResponseObject);

        // Move a different file on the trunk, and make sure that we update the branch's directory contents.
        ServerTransactionManager.getInstance().clientBeginTransaction(bogusResponseObject);
        ArchiveDirManagerInterface origin2TrunkDirManager = ArchiveDirManagerFactoryForServer.getInstance().getDirectoryManager(QVCSConstants.QVCS_SERVER_SERVER_NAME, getProjectName(),
                getTranslucentFeature1BranchName(), TestHelper.SUBPROJECT2_APPENDED_PATH, QVCSConstants.QVCS_SERVED_PROJECT_TYPE, QVCSConstants.QVCS_SERVER_USER, bogusResponseObject, true);
        assertTrue(origin2TrunkDirManager != null);
        ArchiveInfoInterface origin2ArchiveInfo = origin2TrunkDirManager.getArchiveInfo(TestHelper.SUBPROJECT2_FIRST_SHORTWORKFILENAME);
        assertTrue(origin2ArchiveInfo != null);

        directoryContentsManager.moveFileOnTrunk(QVCSConstants.QVCS_TRUNK_VIEW, origin2TrunkDirManager.getDirectoryID(), destinationTrunkDirManager.getDirectoryID(), origin2ArchiveInfo.getFileID(),
                bogusResponseObject);
        ServerTransactionManager.getInstance().clientEndTransaction(bogusResponseObject);

        // Verify that the directory contents contain what we expect.
        ServerTransactionManager.getInstance().clientBeginTransaction(bogusResponseObject);
        DirectoryContents trunkContents = directoryContentsManager.getDirectoryContentsForTrunk("", originTrunkDirManager.getDirectoryID(), bogusResponseObject);
        assertEquals("Unexpected file count for root directory on trunk", trunkRootDirFileCount, trunkContents.getFiles().size());

        ProjectView translucentView = ViewManager.getInstance().getView(getProjectName(), getTranslucentFeature1BranchName());
        DirectoryContents feature1BranchRooDirContents = directoryContentsManager.getDirectoryContentsForTranslucentBranch(translucentView, "", originTrunkDirManager.getDirectoryID(),
                bogusResponseObject);
        assertEquals("Unexpected file count for branch root directory", trunkRootDirFileCount - 1, feature1BranchRooDirContents.getFiles().size());

        DirectoryContents feature1BranchDestDirContents = directoryContentsManager.getDirectoryContentsForTranslucentBranch(translucentView, TestHelper.SUBPROJECT_APPENDED_PATH,
                destinationTrunkDirManager.getDirectoryID(), bogusResponseObject);
        assertEquals("Unexpected file count for branch destination directory", trunkDestinationDirOriginalFileCount + 2, feature1BranchDestDirContents.getFiles().size());
        ServerTransactionManager.getInstance().clientEndTransaction(bogusResponseObject);

        // Move another file on the trunk.
        ServerTransactionManager.getInstance().clientBeginTransaction(bogusResponseObject);
        directoryContentsManager.moveFileOnTrunk(QVCSConstants.QVCS_TRUNK_VIEW, originTrunkDirManager.getDirectoryID(), destinationTrunkDirManager.getDirectoryID(), origin3ArchiveInfo.getFileID(),
                bogusResponseObject);
        ServerTransactionManager.getInstance().clientEndTransaction(bogusResponseObject);

        // Verify that the results are what we expect.
        ServerTransactionManager.getInstance().clientBeginTransaction(bogusResponseObject);
        feature1BranchDestDirContents = directoryContentsManager.getDirectoryContentsForTranslucentBranch(translucentView, TestHelper.SUBPROJECT_APPENDED_PATH,
                destinationTrunkDirManager.getDirectoryID(), bogusResponseObject);
        assertEquals("Unexpected file count for branch destination directory", trunkDestinationDirOriginalFileCount + 3, feature1BranchDestDirContents.getFiles().size());
        assertTrue("Does not contain file moved on trunk", feature1BranchDestDirContents.containsFileID(origin3ArchiveInfo.getFileID()));
        ServerTransactionManager.getInstance().clientEndTransaction(bogusResponseObject);
    }
}
