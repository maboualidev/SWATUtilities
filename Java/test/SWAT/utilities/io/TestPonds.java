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
public class TestPonds {
    public static void test0() throws IOException {
        String filename = "..\\..\\SampleFiles\\PND\\000010000.pnd";
        Ponds pnd = new Ponds(filename);
        System.out.println(pnd.toJSONString());
        System.out.println(pnd.toSWATTXTFormat());
        
        pnd.setPND_FR(1);
        pnd.setPND_K(0.001);
        pnd.writeSWATFileFormat("D:\\000010000.pnd");
        
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.Ponds!");
        test0();
    }
}
