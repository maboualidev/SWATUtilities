/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mabouali
 */
public class ScratchTest {
    public static void main(String[] args){
        Object a = (Integer) 1;
        Object b = (Double) 1.0;
//        double c = Integer.class.cast(a).doubleValue();
        double c = ((Number) a).doubleValue();
        double d = ((Number) b).doubleValue();
        int e = ((Number) a).intValue();
        int f = ((Number) b).intValue();
        
        Double.parseDouble("1.0");
        Double.parseDouble("1");
    }
}
