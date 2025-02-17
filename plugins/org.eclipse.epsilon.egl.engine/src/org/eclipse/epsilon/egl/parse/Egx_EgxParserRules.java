package org.eclipse.epsilon.egl.parse;

// $ANTLR 3.1b1 EgxParserRules.g 2019-01-24 14:02:14

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
public class Egx_EgxParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final int T__144=144;
    public static final int T__143=143;
    public static final int T__146=146;
    public static final int MODELDECLARATIONPARAMETER=73;
    public static final int T__145=145;
    public static final int BREAKALL=39;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int VAR=48;
    public static final int MODELDECLARATIONPARAMETERS=72;
    public static final int T__141=141;
    public static final int THROW=53;
    public static final int EGXMODULE=87;
    public static final int PARAMLIST=25;
    public static final int EXPRLIST=54;
    public static final int EXPRRANGE=55;
    public static final int BREAK=38;
    public static final int ELSE=32;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int FORMAL=24;
    public static final int IF=31;
    public static final int MultiplicativeExpression=57;
    public static final int TYPE=65;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__135=135;
    public static final int T__134=134;
    public static final int T__131=131;
    public static final int NewExpression=47;
    public static final int T__130=130;
    public static final int CASE=35;
    public static final int Letter=16;
    public static final int LINE_COMMENT=22;
    public static final int T__129=129;
    public static final int T__126=126;
    public static final int JavaIDDigit=18;
    public static final int T__125=125;
    public static final int LAMBDAEXPR=64;
    public static final int MAP=75;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int T__166=166;
    public static final int T__165=165;
    public static final int T__168=168;
    public static final int T__167=167;
    public static final int T__162=162;
    public static final int T__161=161;
    public static final int MERGE=86;
    public static final int T__164=164;
    public static final int MODELDECLARATION=68;
    public static final int T__163=163;
    public static final int EXPRESSIONINBRACKETS=59;
    public static final int T__160=160;
    public static final int TRANSACTION=41;
    public static final int FLOAT_TYPE_SUFFIX=7;
    public static final int ITEMSELECTOR=74;
    public static final int COMMENT=21;
    public static final int ModelElementType=45;
    public static final int IMPORT=67;
    public static final int DELETE=52;
    public static final int ARROW=11;
    public static final int T__159=159;
    public static final int T__158=158;
    public static final int T__155=155;
    public static final int SPECIAL_ASSIGNMENT=27;
    public static final int T__154=154;
    public static final int T__157=157;
    public static final int T__156=156;
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
    public static final int Annotation=23;
    public static final int CONTINUE=40;
    public static final int ENUMERATION_VALUE=66;
    public static final int OPERATOR=58;
    public static final int EXPONENT=6;
    public static final int STRING=14;
    public static final int T__148=148;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int NAMESPACE=69;
    public static final int TARGET=83;
    public static final int T__92=92;
    public static final int COLLECTION=42;
    public static final int NEW=49;
    public static final int EXTENDS=80;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int PRE=78;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int POST=79;
    public static final int T__90=90;
    public static final int ALIAS=70;
    public static final int DRIVER=71;
    public static final int KEYVAL=76;
    public static final int POINT_POINT=10;
    public static final int GUARD=81;
    public static final int T__99=99;
    public static final int TEMPLATE=84;
    public static final int T__95=95;
    public static final int HELPERMETHOD=28;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int StatementBlock=29;
    public static final int T__98=98;
    public static final int ABORT=43;
    public static final int StrangeNameLiteral=15;
    public static final int FOR=30;
    public static final int BLOCK=62;
    public static final int T__170=170;
    public static final int PARAMETERS=46;
    public static final int SpecialNameChar=17;
    public static final int BOOLEAN=12;
    public static final int NAME=19;
    public static final int SWITCH=34;
    public static final int T__169=169;
    public static final int FeatureCall=60;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int T__123=123;
    public static final int OVERWRITE=85;
    public static final int T__120=120;
    public static final int NativeType=56;
    public static final int INT=8;
    public static final int ANNOTATIONBLOCK=50;
    public static final int RETURN=37;
    public static final int KEYVALLIST=77;
    public static final int FEATURECALL=63;
    public static final int CollectionType=44;
    public static final int T__119=119;
    public static final int ASSIGNMENT=26;
    public static final int T__118=118;
    public static final int T__115=115;
    public static final int WS=20;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__117=117;
    public static final int T__116=116;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int EscapeSequence=13;
    public static final int EOLMODULE=61;
    public static final int DIGIT=5;
    public static final int EXECUTABLEANNOTATION=51;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int WHILE=33;
    public static final int T__109=109;
    public static final int GENERATE=82;
    public static final int T__104=104;
    public static final int POINT=9;
    public static final int T__103=103;
    public static final int T__106=106;
    public static final int DEFAULT=36;
    public static final int T__105=105;

    // delegates
    // delegators
    public EgxParser gEgx;


        public Egx_EgxParserRules(TokenStream input, EgxParser gEgx) {
            this(input, new RecognizerSharedState(), gEgx);
        }
        public Egx_EgxParserRules(TokenStream input, RecognizerSharedState state, EgxParser gEgx) {
            super(input, state);
            this.gEgx = gEgx;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EgxParser.tokenNames; }
    public String getGrammarFileName() { return "EgxParserRules.g"; }


    public static class generationRule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start generationRule
    // EgxParserRules.g:50:1: generationRule : r= 'rule' rule= NAME ( 'transform' formalParameter )? ob= '{' ( guard | target | template | parameters | pre | post | overwrite | merge )* cb= '}' ;
    public final Egx_EgxParserRules.generationRule_return generationRule() throws RecognitionException {
        Egx_EgxParserRules.generationRule_return retval = new Egx_EgxParserRules.generationRule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token r=null;
        Token rule=null;
        Token ob=null;
        Token cb=null;
        Token string_literal1=null;
        Egx_EolParserRules.formalParameter_return formalParameter2 = null;

        Egx_ErlParserRules.guard_return guard3 = null;

        Egx_EgxParserRules.target_return target4 = null;

        Egx_EgxParserRules.template_return template5 = null;

        Egx_EgxParserRules.parameters_return parameters6 = null;

        Egx_ErlParserRules.pre_return pre7 = null;

        Egx_ErlParserRules.post_return post8 = null;

        Egx_EgxParserRules.overwrite_return overwrite9 = null;

        Egx_EgxParserRules.merge_return merge10 = null;


        org.eclipse.epsilon.common.parse.AST r_tree=null;
        org.eclipse.epsilon.common.parse.AST rule_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal1_tree=null;

        try {
            // EgxParserRules.g:55:2: (r= 'rule' rule= NAME ( 'transform' formalParameter )? ob= '{' ( guard | target | template | parameters | pre | post | overwrite | merge )* cb= '}' )
            // EgxParserRules.g:55:4: r= 'rule' rule= NAME ( 'transform' formalParameter )? ob= '{' ( guard | target | template | parameters | pre | post | overwrite | merge )* cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            r=(Token)match(input,163,FOLLOW_163_in_generationRule64); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            r_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(r);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(r_tree, root_0);
            }
            rule=(Token)match(input,NAME,FOLLOW_NAME_in_generationRule69); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rule_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(rule);
            adaptor.addChild(root_0, rule_tree);
            }
            // EgxParserRules.g:55:24: ( 'transform' formalParameter )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==164) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // EgxParserRules.g:55:25: 'transform' formalParameter
                    {
                    string_literal1=(Token)match(input,164,FOLLOW_164_in_generationRule72); if (state.failed) return retval;
                    pushFollow(FOLLOW_formalParameter_in_generationRule75);
                    formalParameter2=gEgx.formalParameter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameter2.getTree());

                    }
                    break;

            }

            ob=(Token)match(input,93,FOLLOW_93_in_generationRule82); if (state.failed) return retval;
            // EgxParserRules.g:56:10: ( guard | target | template | parameters | pre | post | overwrite | merge )*
            loop2:
            do {
                int alt2=9;
                switch ( input.LA(1) ) {
                case 161:
                    {
                    alt2=1;
                    }
                    break;
                case 165:
                    {
                    alt2=2;
                    }
                    break;
                case 166:
                    {
                    alt2=3;
                    }
                    break;
                case 167:
                    {
                    alt2=4;
                    }
                    break;
                case 159:
                    {
                    alt2=5;
                    }
                    break;
                case 160:
                    {
                    alt2=6;
                    }
                    break;
                case 168:
                    {
                    alt2=7;
                    }
                    break;
                case 169:
                case 170:
                    {
                    alt2=8;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // EgxParserRules.g:56:11: guard
            	    {
            	    pushFollow(FOLLOW_guard_in_generationRule86);
            	    guard3=gEgx.guard();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard3.getTree());

            	    }
            	    break;
            	case 2 :
            	    // EgxParserRules.g:56:19: target
            	    {
            	    pushFollow(FOLLOW_target_in_generationRule90);
            	    target4=target();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, target4.getTree());

            	    }
            	    break;
            	case 3 :
            	    // EgxParserRules.g:56:28: template
            	    {
            	    pushFollow(FOLLOW_template_in_generationRule94);
            	    template5=template();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, template5.getTree());

            	    }
            	    break;
            	case 4 :
            	    // EgxParserRules.g:56:39: parameters
            	    {
            	    pushFollow(FOLLOW_parameters_in_generationRule98);
            	    parameters6=parameters();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, parameters6.getTree());

            	    }
            	    break;
            	case 5 :
            	    // EgxParserRules.g:56:52: pre
            	    {
            	    pushFollow(FOLLOW_pre_in_generationRule102);
            	    pre7=gEgx.pre();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre7.getTree());

            	    }
            	    break;
            	case 6 :
            	    // EgxParserRules.g:56:58: post
            	    {
            	    pushFollow(FOLLOW_post_in_generationRule106);
            	    post8=gEgx.post();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, post8.getTree());

            	    }
            	    break;
            	case 7 :
            	    // EgxParserRules.g:56:65: overwrite
            	    {
            	    pushFollow(FOLLOW_overwrite_in_generationRule110);
            	    overwrite9=overwrite();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, overwrite9.getTree());

            	    }
            	    break;
            	case 8 :
            	    // EgxParserRules.g:56:77: merge
            	    {
            	    pushFollow(FOLLOW_merge_in_generationRule114);
            	    merge10=merge();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, merge10.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            cb=(Token)match(input,94,FOLLOW_94_in_generationRule120); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              r.setType(GENERATE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {
              
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(ob);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cb);
              	
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end generationRule

    public static class target_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start target
    // EgxParserRules.g:60:1: target : g= 'target' expressionOrStatementBlock ;
    public final Egx_EgxParserRules.target_return target() throws RecognitionException {
        Egx_EgxParserRules.target_return retval = new Egx_EgxParserRules.target_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token g=null;
        Egx_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock11 = null;


        org.eclipse.epsilon.common.parse.AST g_tree=null;

        try {
            // EgxParserRules.g:61:2: (g= 'target' expressionOrStatementBlock )
            // EgxParserRules.g:61:4: g= 'target' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            g=(Token)match(input,165,FOLLOW_165_in_target137); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            g_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(g);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(g_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_target140);
            expressionOrStatementBlock11=gEgx.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock11.getTree());
            if ( state.backtracking==0 ) {
              g.setType(TARGET);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end target

    public static class template_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start template
    // EgxParserRules.g:65:1: template : g= 'template' expressionOrStatementBlock ;
    public final Egx_EgxParserRules.template_return template() throws RecognitionException {
        Egx_EgxParserRules.template_return retval = new Egx_EgxParserRules.template_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token g=null;
        Egx_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock12 = null;


        org.eclipse.epsilon.common.parse.AST g_tree=null;

        try {
            // EgxParserRules.g:66:2: (g= 'template' expressionOrStatementBlock )
            // EgxParserRules.g:66:4: g= 'template' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            g=(Token)match(input,166,FOLLOW_166_in_template156); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            g_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(g);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(g_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_template159);
            expressionOrStatementBlock12=gEgx.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock12.getTree());
            if ( state.backtracking==0 ) {
              g.setType(TEMPLATE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end template

    public static class parameters_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start parameters
    // EgxParserRules.g:70:1: parameters : g= 'parameters' expressionOrStatementBlock ;
    public final Egx_EgxParserRules.parameters_return parameters() throws RecognitionException {
        Egx_EgxParserRules.parameters_return retval = new Egx_EgxParserRules.parameters_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token g=null;
        Egx_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock13 = null;


        org.eclipse.epsilon.common.parse.AST g_tree=null;

        try {
            // EgxParserRules.g:71:2: (g= 'parameters' expressionOrStatementBlock )
            // EgxParserRules.g:71:4: g= 'parameters' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            g=(Token)match(input,167,FOLLOW_167_in_parameters175); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            g_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(g);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(g_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_parameters178);
            expressionOrStatementBlock13=gEgx.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock13.getTree());
            if ( state.backtracking==0 ) {
              g.setType(PARAMETERS);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end parameters

    public static class overwrite_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start overwrite
    // EgxParserRules.g:75:1: overwrite : g= 'overwrite' expressionOrStatementBlock ;
    public final Egx_EgxParserRules.overwrite_return overwrite() throws RecognitionException {
        Egx_EgxParserRules.overwrite_return retval = new Egx_EgxParserRules.overwrite_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token g=null;
        Egx_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock14 = null;


        org.eclipse.epsilon.common.parse.AST g_tree=null;

        try {
            // EgxParserRules.g:76:2: (g= 'overwrite' expressionOrStatementBlock )
            // EgxParserRules.g:76:4: g= 'overwrite' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            g=(Token)match(input,168,FOLLOW_168_in_overwrite194); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            g_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(g);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(g_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_overwrite197);
            expressionOrStatementBlock14=gEgx.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock14.getTree());
            if ( state.backtracking==0 ) {
              g.setType(OVERWRITE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end overwrite

    public static class merge_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start merge
    // EgxParserRules.g:80:1: merge : (g= 'merge' | 'protectRegions' ) expressionOrStatementBlock ;
    public final Egx_EgxParserRules.merge_return merge() throws RecognitionException {
        Egx_EgxParserRules.merge_return retval = new Egx_EgxParserRules.merge_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token g=null;
        Token string_literal15=null;
        Egx_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock16 = null;


        org.eclipse.epsilon.common.parse.AST g_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal15_tree=null;

        try {
            // EgxParserRules.g:81:2: ( (g= 'merge' | 'protectRegions' ) expressionOrStatementBlock )
            // EgxParserRules.g:81:4: (g= 'merge' | 'protectRegions' ) expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EgxParserRules.g:81:4: (g= 'merge' | 'protectRegions' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==169) ) {
                alt3=1;
            }
            else if ( (LA3_0==170) ) {
                alt3=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // EgxParserRules.g:81:5: g= 'merge'
                    {
                    g=(Token)match(input,169,FOLLOW_169_in_merge214); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    g_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(g);
                    adaptor.addChild(root_0, g_tree);
                    }

                    }
                    break;
                case 2 :
                    // EgxParserRules.g:81:15: 'protectRegions'
                    {
                    string_literal15=(Token)match(input,170,FOLLOW_170_in_merge216); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal15_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(string_literal15);
                    adaptor.addChild(root_0, string_literal15_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_expressionOrStatementBlock_in_merge220);
            expressionOrStatementBlock16=gEgx.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock16.getTree());
            if ( state.backtracking==0 ) {
              g.setType(MERGE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end merge

    // Delegated rules


 

    public static final BitSet FOLLOW_163_in_generationRule64 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NAME_in_generationRule69 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_164_in_generationRule72 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_formalParameter_in_generationRule75 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_generationRule82 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_guard_in_generationRule86 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_target_in_generationRule90 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_template_in_generationRule94 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_parameters_in_generationRule98 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_pre_in_generationRule102 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_post_in_generationRule106 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_overwrite_in_generationRule110 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_merge_in_generationRule114 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L,0x000007E380000000L});
    public static final BitSet FOLLOW_94_in_generationRule120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_165_in_target137 = new BitSet(new long[]{0x0000000000000000L,0x0000001020000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_target140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_166_in_template156 = new BitSet(new long[]{0x0000000000000000L,0x0000001020000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_template159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_167_in_parameters175 = new BitSet(new long[]{0x0000000000000000L,0x0000001020000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_parameters178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_168_in_overwrite194 = new BitSet(new long[]{0x0000000000000000L,0x0000001020000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_overwrite197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_169_in_merge214 = new BitSet(new long[]{0x0000000000000000L,0x0000001020000000L});
    public static final BitSet FOLLOW_170_in_merge216 = new BitSet(new long[]{0x0000000000000000L,0x0000001020000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_merge220 = new BitSet(new long[]{0x0000000000000002L});

}
