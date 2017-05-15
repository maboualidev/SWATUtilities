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
public class TestRunSingleSWATConfig {
    public static void test0(){
        System.out.println("Create Input 1: Ground Water");
        SWATInput input1 = new SWATInput();
        
        input1.setSWATInputType(SWATInput.SWATInputTypes.GroundWater);
        input1.setFileNameFormat("%05d0001.gw");
        
        SWATInputParameter parameter = new SWATInputParameter("GW_REVAP", "0.2", SWATInputParameter.AssignmentType.assign, new int[]{10});
        System.out.println("Adding Parameter:" + parameter);
        input1.addParameters(parameter);
        
        parameter = new SWATInputParameter("REVAPMN", "1000", SWATInputParameter.AssignmentType.assign, new int[]{10,11});
        System.out.println("Adding Parameter:" + parameter);
        input1.addParameters(parameter);
        
        System.out.println("Input 1: \n" + input1);
        
        System.out.println("Create Input 2: Soil");
        SWATInput input2 = new SWATInput();
        input2.setSWATInputType(SWATInput.SWATInputTypes.Soil);
        input2.setFileNameFormat("%05d0001.sol");
        
        parameter = new SWATInputParameter("SOL_AWC", "0.2", SWATInputParameter.AssignmentType.changeRate, new int[]{10,11,12});
        System.out.println("Adding Parameter:" + parameter);
        input2.addParameters(parameter);
        
        System.out.println("Input 2: \n" + input2);
        

        System.out.println("Creating SWAT Configurations:");
        RunSingleSWATConfig config = new RunSingleSWATConfig(true);
        config.addSWATInput(input1).addSWATInput(input2);
        config.addSWATOutput(new SWATOutputFile("output.sub", "output_1.sub", "../Outputs"));
        config.setSWATExecution(new SWATExecution("./tmpFolder","SWAT_64Rel.exe", "../SWATBase.zip", true, false));
        System.out.println(config);
    }
    public static void test1() throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Parsing XML File for RunSingleSWATConfig");
        RunSingleSWATConfig config = new RunSingleSWATConfig(true);
        config.readFromXMLFile("..\\..\\SampleFiles\\SingleSWATRunConfig\\example001.xml");
        System.out.println("The Configuration is:");
        System.out.println(config);
        
        System.out.println(config.toXMLString());
    }
    public static void main(String[] arg) throws ParserConfigurationException, IOException, SAXException {
//        test0();
        test1();
        
    }
    
}
