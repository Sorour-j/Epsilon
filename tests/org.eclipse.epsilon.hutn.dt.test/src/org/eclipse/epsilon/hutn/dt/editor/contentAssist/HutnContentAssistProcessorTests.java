/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.dt.editor.contentAssist;

import static org.eclipse.epsilon.hutn.dt.editor.contentAssist.CompletionProposalMatcher.completionProposal;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;

import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class HutnContentAssistProcessorTests extends HutnTestWithFamiliesMetaModel {

	@Test
	public void shouldSuggestConcreteClassNamesWhenAtPackageLevel() throws EolModelLoadingException {
		final String text = "@Spec {"                    +
		                    "	Metamodel {"             +
		                    "		nsUri: \"families\"" + 
		                    "	}"                       +
		                    "}"                          +
		                    "families { ";
				
		assertThat(new HutnContentAssistProcessor().computeCompletionProposals(text),
		           is(arrayContaining(completionProposal("Band"),
		                              completionProposal("Bike"),
		                              completionProposal("District"),
		                              completionProposal("Dog"),
		                              completionProposal("Family"),
		                              completionProposal("Model"),
		                              completionProposal("Person"),
		                              completionProposal("Pet"),
		                              completionProposal("Suburb")
		                              )));
	}
	
	
	@Test
	public void shouldSuggestSlotNamesWhenAtClassLevel() throws EolModelLoadingException {
		final String text = "@Spec {"                    +
		                    "	Metamodel {"             +
		                    "		nsUri: \"families\"" + 
		                    "	}"                       +
		                    "}"                          +
		                    "families { " +
		                    "  Person { ";
		
		assertThat(new HutnContentAssistProcessor().computeCompletionProposals(text),
		           is(arrayContaining(completionProposal("accounts: "),
		                              completionProposal("allParents: "),
		                              completionProposal("friends: "),
		                              completionProposal("name: "),
		                              completionProposal("parents: "),
		                              completionProposal("sharedAccounts: ")
		                              )));
	}
	
	
	// For debugging
	protected static void print(ICompletionProposal[] proposals) {
		for (ICompletionProposal proposal : proposals) {
			System.err.println(proposal.getDisplayString());
		}
	}
}
