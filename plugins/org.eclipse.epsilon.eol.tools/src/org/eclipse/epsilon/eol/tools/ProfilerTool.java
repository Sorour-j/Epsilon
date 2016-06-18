/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.tools;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.profiling.FileMarker;
import org.eclipse.epsilon.profiling.Profiler;


public class ProfilerTool extends AbstractTool{
	
	public void start(String targetName, Object data) {
		FileMarker fileMarker = null;
		ModuleElement activeAst = context.getExecutorFactory().getActiveAst();
		if (activeAst instanceof ModuleElement) {
			fileMarker = new FileMarker();
			ModuleElement activeEolAst = (ModuleElement) activeAst;
			fileMarker.setColumn(activeEolAst.getRegion().getStart().getColumn());
			fileMarker.setLine(activeEolAst.getRegion().getStart().getLine());
			fileMarker.setFile(activeEolAst.getFile());
		}
		Profiler.INSTANCE.start(targetName, StringUtil.toString(data, ""), fileMarker);
	}
	
	public void refresh() {
		Profiler.INSTANCE.refresh();
	}
	
	public void reset() {
		Profiler.INSTANCE.reset();
	}
	
	public void start(String targetName) {
		start(targetName, "");
	}
	
	public void stop(String targetName) {
		Profiler.INSTANCE.stop(targetName);
	}
	
	public void stop() {
		Profiler.INSTANCE.stop(null);
	}
}
