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
public class TestSoil {
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.Soil");
        
        String filename = "..\\..\\SampleFiles\\SOL\\000010001.sol";
        
        System.out.printf("Reading %s ...\n",filename);
        Soil sol = new Soil();
        sol.readSWATFileFormat(filename);
        System.out.println("sol contains all fields: " + sol.containsAllFields());
        System.out.println("nLayers: " + sol.getNLayers());
        System.out.print("SOL_Z:");
        for (Double e: sol.getSOL_Z())
            System.out.print(" " + e);
        System.out.println();
        System.out.println("Bulk Density at Layer 0: " + sol.get("SOL_BD",0));
        System.out.println("Bulk Density at Layer 1: " + sol.get("SOL_BD",1));
        System.out.println("Bulk Density at Layer 2: " + sol.get("SOL_BD",2));
        
        System.out.println("Sol in JSON Format:");
        System.out.println(sol.toJSONString());
        System.out.println("Sol in SWAT Text Format:");
        System.out.println(sol.toSWATTXTFormat());
    }
}
