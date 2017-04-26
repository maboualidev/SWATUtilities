/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

/**
 *
 * @author mabouali
 */
public class TestManagementOperations {
    public static void test0() {
        ManagementOperations mo = new ManagementOperations(1);
        String sampleMOStr = "          0.150  1   10           911.00000   0.00     0.00000 0.00   0.00  0.00";
        mo.parseSWATTXTFormat(sampleMOStr);
        System.out.println("Management Operation in JSON Format: ");
        System.out.println(mo.toJSONString());
        System.out.println("MO to SWAT Text Format:");
        System.out.println(mo.toSWATTXTFormat()+"| length: "+mo.toSWATTXTFormat().length());
        
        ManagementOperations mo2 = new ManagementOperations("          1.200  5                  0.00000");
        System.out.println("MO2 in JSON Format: ");
        System.out.println(mo2.toJSONString());
        System.out.println("MO2 to SWAT Text Format:");
        System.out.println(mo2.toSWATTXTFormat()+"| length: "+mo2.toSWATTXTFormat().length());
        
        ManagementOperations mo3 = new ManagementOperations("                17");
        System.out.println("MO3 in JSON Format: ");
        System.out.println(mo3.toJSONString());
        System.out.println("MO3 to SWAT Text Format:");
        System.out.println(mo3.toSWATTXTFormat()+"| length: "+mo3.toSWATTXTFormat().length());
        
    }
    public static void main(String[] args) {
        System.out.println("Testing SWAT.utilities.io.ManagementOperations!");
        test0();
    }
}
