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
public class TestHydroResponseUnit {
    public static void test1() throws IOException {
        System.out.println("\nTEST 1: Reading one file and writing it to a new location.");

        String filename = "..\\..\\SampleFiles\\HRU\\000010001.hru";
        HydroResponseUnit hru = HydroResponseUnit.newFromSWATFile(filename);
        System.out.println("Contains all fields: " + hru.containsAllFields());
        System.out.println("Contains all fields ignoring (POT_SOLP and POT_K): " + hru.containsAllFieldsIgnoring(new String[] {"POT_SOLP","POT_K"}));
        System.out.println("Contains POT_SOLP:" + hru.contains(HydroResponseUnit.fields.POT_SOLP));
        System.out.println("Contains POT_K:" + hru.contains(HydroResponseUnit.fields.POT_K));
        
        System.out.println("hru in JSON format: \n"+hru);
        System.out.println("hru in SWAT text format: \n"+hru.toSWATTXTFormat());

        System.out.println("Now Changing HRU_FR to 555.0");
        hru.setHRU_FR(555.0);
        System.out.println("Writing the new file to D:\\test.hru");
        hru.writeSWATFileFormat("D:\\test.hru");
        System.out.println("END OF TEST 1\n");
        
    }
    public static void test2() throws IOException {
        System.out.println("\nTEST 2: Reading multiple files and writing them to a new location.");
        
        String[] filenames = new String[10];
        for (int idx=0; idx<filenames.length;idx++){
            filenames[idx] = String.format("..\\..\\SampleFiles\\HRU\\000%02d0001.hru",idx+1);
        }
        
        System.out.println("Reading all 10 files ...");
        List<HydroResponseUnit> hrus = HydroResponseUnit.newFromSWATFiles(filenames);
        
        System.out.println("Changing GWHT of the files ...");
        for (int idx = 0; idx<filenames.length; idx++) {
            hrus.get(idx).setCANMX(idx+1);
            String outFilename = String.format("D:\\test%02d.hru", idx);
            System.out.printf("Writing %s ...\n", outFilename);
            hrus.get(idx).writeSWATFileFormat(outFilename);
        }
        System.out.println("END OF TEST 2\n");
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.HydroResponseUnit!");
        test1();
        test2();
        
    }
    
}
