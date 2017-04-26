/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.io.IOException;

/**
 *
 * @author mabouali
 */
public class TestManagement {
    public static void test1() throws IOException {
        System.out.println("\nTEST 1: Reading one file and writing it to a new location.");

        String filename = "..\\..\\SampleFiles\\MGT\\000010001.MGT";
        Management mgt = new Management(filename);
        System.out.println("MGT contains all fields: " + mgt.containsAllFields());
        System.out.println("Number of Management Operations in MGT: " + mgt.getNumberOfOperationsSchedules());
        System.out.println("Available Management Operations:");
        mgt.getAllOperationsSchedules().forEach((e) -> {
            System.out.println(e.toSWATTXTFormat());
        });
        System.out.println("MGT in JSON Format:");
        System.out.println(mgt.toJSONString());
        System.out.println("MGT in SWAT Text Format:");
        System.out.println(mgt.toSWATTXTFormat());
        
        System.out.println("Now Changing BIOMIX to 0.987");
        mgt.setBIOMIX(0.987);
        System.out.println("Writing the new file to D:\\test.mgt");
        mgt.writeSWATFileFormat("D:\\test.mgt");
        
        Management mgt2 = new Management("D:\\test.mgt");
        
//        mgt.readSWATFileFormat(filename);
        
//        Routing rte = Routing.newFromSWATFile(filename);
//        System.out.println("CHW2: " + rte.getCHW2());
//        System.out.println("CH_S2: " + rte.getCH_S2());
//        System.out.print("CH_ERODMO: ");
//        for (double e: rte.getCH_ERODMO())
//            System.out.printf("%6.2f ",e);
//        System.out.println();
//        
//        System.out.println("RTE contains all field: " + rte.containsAllFields());
//        
//        System.out.println("RTE in JSON format:");
//        System.out.println(rte.toJSONString());
//        System.out.println("RTE in SWAT TXT format:");
//        System.out.println(rte.toSWATTXTFormat());
//        
//        
//        System.out.println("Now Changing CHD to 0.987");
//        rte.setCHD(0.987);
//        System.out.println("Writing the new file to D:\\test.rte");
//        rte.writeSWATFileFormat("D:\\test.rte");
        
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.Management");
        test1();
    }
}
