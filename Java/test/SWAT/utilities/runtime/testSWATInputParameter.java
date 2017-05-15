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
public class testSWATInputParameter {
    public static void main(String[] args) {
        System.out.println("Testing SWAT.utilities.runtime.");
    
        SWATInputParameter p = new SWATInputParameter();
        p.setName("GE_REVAP")
                .setValue("0.2")
                .setAssignmentType(SWATInputParameter.AssignmentType.assign);
        p.addApplicableHRUs(1);
        p.addApplicableHRUs(2);
        p.addApplicableHRUs(4);
        p.addApplicableHRUs(4);
        p.addApplicableHRUs(2, 3);
        System.out.println("SWAT Input Parameter: \n" + p);
        
        p.removeApplicableHRUs((Integer) 2); //This will remove the element with the value of 2
        System.out.println("SWAT Input Parameter: \n" + p);
        p.removeApplicableHRUs(2); //This will remove the third element element regardless of the value.
        System.out.println("SWAT Input Parameter: \n" + p);
        
                
    }
}
