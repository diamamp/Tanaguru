/*
 *  Tanaguru - Automated webpage assessment
 *  Copyright (C) 2008-2013  Open-S Company
 * 
 *  This file is part of Tanaguru.
 * 
 *  Tanaguru is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Contact us by mail: open-s AT open-s DOT com
 */
package org.opens.tanaguru.rules.elementchecker.text;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.opens.tanaguru.entity.audit.TestSolution;
import org.opens.tanaguru.processor.SSPHandler;
import org.opens.tanaguru.ruleimplementation.TestSolutionHandler;
import org.opens.tanaguru.rules.textbuilder.TextElementBuilder;
import org.opens.tanaguru.service.ProcessRemarkService;

/**
 *
 * @author jkowalczyk
 */
public class TextOnlyContainsNonAlphanumericalCharactersCheckerTest extends TestCase {
    
    private static final String DETECTION_MSG = "detected";
    
    TextElementBuilder mockTextElementBuilder;
    SSPHandler mockSSPHandler;
    Element element;
    Elements elements;
    TestSolutionHandler mockTestSolutionHandler;
    ProcessRemarkService mockProcessRemarkService;
    
    public TextOnlyContainsNonAlphanumericalCharactersCheckerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockTextElementBuilder = createMock(TextElementBuilder.class);
        mockSSPHandler = createMock(SSPHandler.class);
        elements = new Elements();
        element = new Element(Tag.valueOf("div"), "");
        mockTestSolutionHandler = createMock(TestSolutionHandler.class);
        mockProcessRemarkService = createMock(ProcessRemarkService.class);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of doCheck method, of class TextOnlyContainsNonAlphanumericalCharactersChecker.
     */
    public void testDoCheckWithEmptyElements() {
        System.out.println("doCheckWithEmptyElements");
        
        /* Prepare test context */
        TextOnlyContainsNonAlphanumericalCharactersChecker instance = 
                new TextOnlyContainsNonAlphanumericalCharactersChecker(
                    mockTextElementBuilder, 
                    DETECTION_MSG);

        replay(mockTextElementBuilder, mockSSPHandler, mockTestSolutionHandler);
        
        /* test */
        instance.doCheck(mockSSPHandler, elements, mockTestSolutionHandler);
        
        /* verification */
        verify(mockTextElementBuilder, mockSSPHandler, mockTestSolutionHandler);
    }

    /**
     * Test of doCheck method, of class TextOnlyContainsNonAlphanumericalCharactersChecker.
     */
    public void testDoCheckWithNullTextToCheck() {
        System.out.println("doCheckWithNullTextToCheck");
        
        /* Prepare test context */
        
        elements.add(element);

        mockTestSolutionHandler.addTestSolution(TestSolution.NOT_APPLICABLE);
        expectLastCall().once();
        
        expect(mockTextElementBuilder.buildTextFromElement(element)).andReturn(null);
        
        TextOnlyContainsNonAlphanumericalCharactersChecker instance = 
                new TextOnlyContainsNonAlphanumericalCharactersChecker(
                    mockTextElementBuilder, 
                    DETECTION_MSG);
        replay(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler);
        
        /* test */
        instance.doCheck(mockSSPHandler, elements, mockTestSolutionHandler);
        
        /* verification */
        verify(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler);
    }
    
    /**
     * Test of doCheck method, of class TextOnlyContainsNonAlphanumericalCharactersChecker.
     */
    public void testDoCheckWithDefaultDetectionResult() {
        System.out.println("doCheckWithDefaultDetectionResult");
        
        /* Prepare test context */
        elements.add(element);

        expect(mockTextElementBuilder.buildTextFromElement(element)).andReturn("<>:;,?.!§%*£$¨^\\- |");
        
        mockTestSolutionHandler.addTestSolution(TestSolution.FAILED);
        expectLastCall().once();
        
        mockProcessRemarkService.addSourceCodeRemarkOnElement(
                    TestSolution.FAILED,
                    element,
                    DETECTION_MSG);
        expectLastCall().once();

        TextOnlyContainsNonAlphanumericalCharactersChecker instance = 
                new TextOnlyContainsNonAlphanumericalCharactersChecker(
                    mockTextElementBuilder, 
                    DETECTION_MSG);
        instance.setProcessRemarkService(mockProcessRemarkService);
        
        replay(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler, 
               mockProcessRemarkService);
        
        /* test */
        instance.doCheck(mockSSPHandler, elements, mockTestSolutionHandler);
        
        /* verification */
        verify(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler,
               mockProcessRemarkService);
    }
    
    /**
     * Test of doCheck method, of class TextOnlyContainsNonAlphanumericalCharactersChecker.
     */
    public void testDoCheckWithDefaultNotDetectionResult() {
        System.out.println("doCheckWithDefaultNotDetectionResult");
        
        /* Prepare test context */
        elements.add(element);
        
        expect(mockTextElementBuilder.buildTextFromElement(element)).andReturn("test");
        
        mockTestSolutionHandler.addTestSolution(TestSolution.PASSED);
        expectLastCall().once();

        TextOnlyContainsNonAlphanumericalCharactersChecker instance = 
                new TextOnlyContainsNonAlphanumericalCharactersChecker(
                    mockTextElementBuilder, 
                    DETECTION_MSG);
        
        replay(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler);
        
        /* test */
        instance.doCheck(mockSSPHandler, elements, mockTestSolutionHandler);
        
        /* verification */
        verify(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler);
    }
    
    /**
     * Test of doCheck method, of class TextOnlyContainsNonAlphanumericalCharactersChecker.
     */
    public void testDoCheckWithDefaultDetectionResultAndDetectionResultOverridenByConstructor() {
        System.out.println("doCheckWithDefaultDetectionResultAndDetectionResultOverridenByConstructor");
        
        /* Prepare test context */
        elements.add(element);

        expect(mockTextElementBuilder.buildTextFromElement(element)).andReturn(":!)_\"'('&");
        
        mockTestSolutionHandler.addTestSolution(TestSolution.NEED_MORE_INFO);
        expectLastCall().once();

        mockProcessRemarkService.addSourceCodeRemarkOnElement(
                    TestSolution.NEED_MORE_INFO,
                    element,
                    DETECTION_MSG);
        expectLastCall().once();

        TextOnlyContainsNonAlphanumericalCharactersChecker instance = 
                new TextOnlyContainsNonAlphanumericalCharactersChecker(
                    mockTextElementBuilder, 
                    TestSolution.NEED_MORE_INFO,
                    DETECTION_MSG);
        instance.setProcessRemarkService(mockProcessRemarkService);
        
        replay(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler, 
               mockProcessRemarkService);
        
        /* test */
        instance.doCheck(mockSSPHandler, elements, mockTestSolutionHandler);
        
        /* verification */
        verify(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler,
               mockProcessRemarkService);
    }
    
    /**
     * Test of doCheck method, of class TextOnlyContainsNonAlphanumericalCharactersChecker.
     */
    public void testDoCheckWithDefaultNotDetectionResultAndDetectionResultOverridenByConstructor() {
        System.out.println("doCheckWithDefaultNotDetectionResultAndDetectionResultOverridenByConstructor");
        
        /* Prepare test context */
        elements.add(element);
        
        expect(mockTextElementBuilder.buildTextFromElement(element)).andReturn("test");
        
        mockTestSolutionHandler.addTestSolution(TestSolution.PASSED);
        expectLastCall().once();

        TextOnlyContainsNonAlphanumericalCharactersChecker instance = 
                new TextOnlyContainsNonAlphanumericalCharactersChecker(
                    mockTextElementBuilder, 
                    TestSolution.NEED_MORE_INFO,
                    DETECTION_MSG);
        
        replay(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler);
        
        /* test */
        instance.doCheck(mockSSPHandler, elements, mockTestSolutionHandler);
        
        /* verification */
        verify(mockTextElementBuilder,
               mockSSPHandler, 
               mockTestSolutionHandler);
    }

}