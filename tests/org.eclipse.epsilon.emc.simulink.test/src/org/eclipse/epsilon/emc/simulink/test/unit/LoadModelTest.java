/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit;

import static org.eclipse.epsilon.emc.simulink.util.MatlabEngineSetupEnum.ENGINE_JAR;
import static org.eclipse.epsilon.emc.simulink.util.MatlabEngineSetupEnum.LIBRARY_PATH;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.emc.simulink.common.test.AssumeMatlabInstalled;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


/**
 * Ensure location does not have any spaces
 * Check for windows
 */
@RunWith(Parameterized.class)
public class LoadModelTest {

	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String RESOURCES = USER_DIR + File.separator + "resources" + File.separator + "models";
	private static final String WITH_SPACES = RESOURCES + File.separator + "dir with spaces";

	private static String version;
	
	private static String notExisting = "emptyNotExisting";
	private static String empty = "empty";
	private static String feedbackController = "feedbackController";
	
	@ClassRule
	public static AssumeMatlabInstalled installation = new AssumeMatlabInstalled();
	
	@BeforeClass
	public static void setup(){
		version = installation.getVersion();
	}
	
	private static Iterable<Object[]> data;
	
	private static Iterable<Object[]> getData() {
		if (data== null) {
			ArrayList<Object[]> list = new ArrayList<>();
			List<Boolean> bools = Arrays.asList(new Boolean[] {false, true});
			List<String> files = Arrays.asList(new String[] {feedbackController, empty, notExisting});
			List<String> workdirs = Arrays.asList(new String[] {"", RESOURCES, WITH_SPACES});
			for (Boolean store : bools) {
				for (String workdir : workdirs) {
					for (Boolean read : bools) {
						for (String file : files) {
							list.add(new Object[] {read, store, workdir, file});
						}
					}
				}
			}
			data = list;
		}
		return data;
	}
	
	@Parameters(name = "Test-{index} : ReadOnLoad = {0}, StoreOnDisposal = {1}, Workdir = {2}, File = {3}")
	public static Iterable<Object[]> data() {
		return getData();
	}
	
	@Parameter(0)
	public boolean readOnLoad;
	
	@Parameter(1)
	public boolean storeOnDisposal;
	
	@Parameter(2)
	public String workdir;
	
	@Parameter(3)
	public String fileName;
	
	private SimulinkModel model;
	
	private static File getFile(String fileName, String workdir) {
		return new File( ( workdir.equals("") ? RESOURCES : workdir) + File.separator + fileName + ".slx");
	}
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void logName() {
		System.out.println(name.getMethodName());
	}
	
	@Test
	public void testLoadDir() {
		
		model = new SimulinkModel();

		File file = getFile(fileName, workdir);
		model.setFile(file);
		
		if (!workdir.equals("")) {			
			model.setWorkingDir(new File(workdir));
		}
		model.setReadOnLoad(readOnLoad);
		model.setStoredOnDisposal(storeOnDisposal);

		model.setName("M");
		model.setShowInMatlabEditor(false);
		model.setCachingEnabled(false);
		model.setFollowLinks(false);
		try {			
			String path = LIBRARY_PATH.path(version);
			model.setLibraryPath(path);
			String engine = ENGINE_JAR.path(version);
			model.setEngineJarPath(engine);
		} catch (Exception e) {
			fail("Error setting MATLAB Configuration");
		}
		try {
			model.load();
			
			assertTrue(true);
			
			// TEST EOL
			EolModule eolModule = new EolModule();
			try {			
				eolModule.parse("\"hi\".println();");
			} catch (Exception e) {
				fail("Parsing failed");
			}	
			try {
				eolModule.getContext().getModelRepository().addModel(model);
				eolModule.execute();
			} catch (Exception e) {
				fail("Couldn't go on with eol");
			} finally {
				eolModule = null;
			}
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getCause().toString());
		} finally {
			model.dispose();
			try {
				model.getEngine().eval("close_system('?')", model.getSimulinkModelName());
				
			} catch (MatlabException e) {
				e.printStackTrace();
			}
			model = null;
		}
		
	
	}
	
	@AfterClass
	public static void deleteNonExisting() {
		getFile(notExisting, RESOURCES).delete();
		getFile(notExisting, WITH_SPACES).delete();
	}
	

}
