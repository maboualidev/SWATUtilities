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
public class TestPlantDBEntry {
    public static void main(String[] args) {
        System.out.println("Testing SWAT.utilities.io.PlantDBEntry");

        PlantDBEntry e1 = new PlantDBEntry();
        e1.setCPNM("ABCD");
        e1.setICNUM(1);
        e1.setHVSTI(0.65);
        e1.setHVSTI(0.66);
        System.out.println("Does e1 contains \"CPNM\": " + e1.contains("CPNM"));
        System.out.println("Does e1 contains \"ICNUM\": " + e1.contains("ICNUM"));
        System.out.println("Does e1 contains \"IDC\": " + e1.contains("IDC"));
        System.out.println("Does e1 contains \"HVSTI\": " + e1.contains("HVSTI"));
        
        System.out.println("e1.CPNM: " + e1.getCPNM());
        System.out.println("e1.ICNUM: " + e1.getICNUM());
        System.out.println("e1.HVSTI: " + e1.getHVSTI());
        //System.out.println("e1.IDC: " + e1.getIDC());
        
        String A = " 53.123456  | something";
        System.out.println(Double.parseDouble(A.substring(0, 7)));
    }
    
}
