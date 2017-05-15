/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.runtime;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author mabouali
 */
public class TestRunSingleSWAT {
    public static void test0()
            throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        String configXMLFilename = "..\\..\\SampleFiles\\SingleSWATRunConfig\\example001.xml";
        RunSingleSWAT SWATRun = RunSingleSWAT.newFromXMLFile(configXMLFilename);
        
        SWATRun.runAll();
    }
    public static void test1()
            throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        String configXMLFilename = "..\\..\\SampleFiles\\SingleSWATRunConfig\\example001.xml";
        RunSingleSWAT.executeXMLConfig(configXMLFilename);
    }
    public static void main(String[] args)
            throws ParserConfigurationException, IOException, SAXException, InterruptedException {
//        test0();
        test1();
        
    }
    
}
