/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.io.IOException;
import java.util.List;


/**
 *
 * @author mabouali
 */
public final class TestGroundWater {
    public static void test1() throws IOException {
        System.out.println("\nTEST 1: Reading one file and writing it to a new location.");

        String filename = "..\\..\\SampleFiles\\GW\\000010001.gw";
        GroundWater gw = GroundWater.newFromSWATFile(filename);
        System.out.println("Contains all fields: " + gw.containsAllFields());
        
        System.out.println("gw in JSON format: \n"+gw);  
        System.out.println("gw in SWAT text format: \n"+gw.toSWATTXTFormat());

        System.out.println("Now Changing SHALLST to 555.0");
        gw.setSHALLST(555.0);
        System.out.println("Writing the new file to D:\\test.gw");
        gw.writeSWATFileFormat("D:\\test.gw");
        System.out.println("END OF TEST 1\n");
        
    }
    
    public static void test2() throws IOException {
        System.out.println("\nTEST 2: Reading multiple files and writing them to a new location.");
        
        String[] filenames = new String[10];
        for (int idx=0; idx<filenames.length;idx++){
            filenames[idx] = String.format("..\\..\\SampleFiles\\GW\\000%02d0001.gw",idx+1);
        }
        
        System.out.println("Reading all 10 files ...");
        List<GroundWater> gws = GroundWater.newFromSWATFiles(filenames);
        
        System.out.println("Changing GWHT of the files ...");
        for (int idx = 0; idx<filenames.length; idx++) {
            gws.get(idx).setGWHT(idx+1);
            String outFilename = String.format("D:\\test%02d.hru", idx);
            System.out.printf("Writing %s ...\n", outFilename);
            gws.get(idx).writeSWATFileFormat(outFilename);
        }
        System.out.println("END OF TEST 2\n");
    }
    
    public static void test3() throws IOException {
        System.out.println("\nTEST 3: Reading one file and writing it to a new location.");

        GroundWater gw = GroundWater.newFromSWATFile("..\\..\\SampleFiles\\GW\\000010001.gw");
        
        System.out.println("GW Contains a value for GWHT: " + gw.contains(GroundWater.fields.GWHT));
        System.out.println("END OF TEST 3\n");
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.GroundWater!");
        test1();
        test2();
        test3();
    }
    
}
