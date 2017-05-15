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
public class TestSubbasin {
    public static void test0() throws IOException {
        String filename = "..\\..\\SampleFiles\\SUB\\000010000.sub";
        Subbasin sub = new Subbasin();
        sub.readSWATFileFormat(filename);
        System.out.println(sub.toJSONString());
        System.out.println(sub.toSWATTXTFormat());
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.Subbasin!");
        test0();
    }
}
