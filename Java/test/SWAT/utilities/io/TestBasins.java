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
public class TestBasins {
    public static void test0() throws IOException {
        String filename = "..\\..\\SampleFiles\\BSN\\basins.bsn";
        Basins bsn = new Basins(filename);
        System.out.println("bsn in JSON Format:");
        System.out.println(bsn);
        System.out.println("bsn in SWAT Text Format:");
        System.out.println(bsn.toSWATTXTFormat());
        
        System.out.println("Changing SMFMX to 5.4");
        bsn.set(Basins.fields.SMFMX, 5.4);
        System.out.println("Writing the new file to D:\\test.bsn");
        bsn.writeSWATFileFormat("D:\\test.bsn");
        //Basins bsn2 = Basins.newFromSWATFile("D:\\test.bsn");
        
        bsn.set("TIMP", "5.4");
        System.out.println("Writing the new file to D:\\test.bsn");
        bsn.writeSWATFileFormat("D:\\test.bsn");
        
        System.out.println("PETFILE: " + bsn.get(Basins.fields.PETFILE));
        System.out.println("WWQFILE: " + bsn.get(Basins.fields.WWQFILE));
        
    }
    public static void test1() throws IOException {
        String filename = "..\\..\\SampleFiles\\BSN\\basins.bsn";
        SWATFormatInput bsn = new Basins(filename);
        System.out.println("Comment 1: " + bsn.get(Basins.fields.COMMENT1.toString()));
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.Basins!");
        test0();
        test1();
    }
}
