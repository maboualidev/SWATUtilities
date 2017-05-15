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
public class TestOperations {
    public static void test0() {
        Operations op = new Operations();
        String sampleOPStr = " 10  1     2009 10    1       0.00     90.00000  90.00                90.00  90.00";
        op.parseSWATTXTFormat(sampleOPStr);
        System.out.println("OP in JSON Format:");
        System.out.println(op);
        System.out.println("OP to SWAT Text Format:");
        System.out.println(op.toSWATTXTFormat()+"| length: "+op.toSWATTXTFormat().length());
        System.out.println(sampleOPStr+"| length: "+sampleOPStr.length());
        
        Operations op2 = new Operations(sampleOPStr);
        System.out.println("OP2 in SWAT Text Format:\n"+op2.toSWATTXTFormat());
    }
    public static void main(String[] args) {
        System.out.println("Testing SWAT.utilities.io.ManagementOperations!");
        test0();
    }
}
