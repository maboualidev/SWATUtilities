/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.runtime;

/**
 *
 * @author mabouali
 */
public class TestSWATExecution {
    public static void main(String[] args) {
        System.out.println("Testing SWAT.utilities.runtime!");
        
        SWATExecution SWATExec = new SWATExecution("./TempDrive", "SWAT_64Rel.exe", "../SWATBase.zip", true, false);
        
        System.out.println("SWATExection:");
        System.out.println(SWATExec);
    }
    
}
