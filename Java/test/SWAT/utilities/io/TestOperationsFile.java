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
public class TestOperationsFile {
    public static void test0() throws IOException {
        System.out.println("TEST 0:");
        String filename = "..\\..\\SampleFiles\\OPS\\000130001.ops";
        OperationsFile opf = new OperationsFile();
        opf.readSWATFileFormat(filename);
        
        String sampleOPStr = " 10  1     2009 10    1       0.00     90.00000  90.00                90.00  90.00";
        opf.addOperation(sampleOPStr);
        System.out.println(opf.ops.size());
        
        System.out.println(opf.toJSONString());
        System.out.println(opf.toSWATTXTFormat());
        
        System.out.println();
    }
    
    public static void test1() throws IOException {
        System.out.println("TEST 1:");
        String filename = "..\\..\\SampleFiles\\OPS\\000130001.ops";
        OperationsFile opf = new OperationsFile(filename);
        opf.readSWATFileFormat(filename);
        
        String sampleOPStr = " 10  1     2009 10    1       0.00     90.00000  90.00                90.00  90.00";
        opf.addOperation(sampleOPStr);
        System.out.println(opf.ops.size());
        
        System.out.println(opf.toJSONString());
        System.out.println(opf.toSWATTXTFormat());
        
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.OperationsFile!");
        test0();
        test1();
    }
}
