/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.common;

/**
 *
 * @author mabouali
 */
public final class Convertor {
    public static Object convertStringValueTo(String value, String className) {
        Object valueObj;
        if ( className.equals(String.class.toString())) {
            valueObj = value;
            return valueObj;
        }
        if ( className.equals(Integer.class.toString())) {
            valueObj = (Integer) ((Number) Double.parseDouble(value)).intValue();
            return valueObj;
        }
        if ( className.equals(Double.class.toString())) {
            valueObj = Double.parseDouble(value);
            return valueObj;
        }
        if ( className.equals((new Double[0]).getClass().toString())) {
            String[] valueList = value.split(",");
            Double[] valueDouble = new Double[valueList.length];
            for(int idx=0; idx<valueList.length; idx++)
                valueDouble[idx] = Double.parseDouble(valueList[idx]);
            valueObj = valueDouble;
            return valueObj;
        }
        
        // it shouldn't reach here
        throw new Error(String.format("Class %s is not supported yet",className));
    }
    public static Object convertStringValueTo(String value, Class classType) {
        return convertStringValueTo(value, classType.toString());
//        Object valueObj;
//        if ( classType.equals(String.class)) {
//            valueObj = value;
//            return valueObj;
//        }
//        if ( classType.equals(Integer.class)) {
//            valueObj = (Integer) ((Number) Double.parseDouble(value)).intValue();
//            return valueObj;
//        }
//        if ( classType.equals(Double.class)) {
//            valueObj = Double.parseDouble(value);
//            return valueObj;
//        }
//        if ( classType.equals((new Double[0]).getClass())) {
//            String[] valueList = value.split(",");
//            Double[] valueDouble = new Double[valueList.length];
//            for(int idx=0; idx<valueList.length; idx++)
//                valueDouble[idx] = Double.parseDouble(valueList[idx]);
//            valueObj = valueDouble;
//            return valueObj;
//        }
//        
//        // it shouldn't reach here
//        throw new Error(String.format("Class \"%s\" is not supported yet",classType));
    }
    
    public static void main(String[] args) {
        System.out.println(String.class.toString());
        System.out.println(Integer.class.toString());
        System.out.println(Double.class.toString());
        System.out.println((new Double[0]).getClass());
        System.out.println();
        
        Object test;
        test = convertStringValueTo("1.0", Double.class.toString());
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        test = convertStringValueTo("1", Integer.class.toString());
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        test = convertStringValueTo("1", String.class.toString());
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        test = convertStringValueTo("1.0,2.0", (new Double[0]).getClass().toString());
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        System.out.println();
        
        test = convertStringValueTo("1.0", Double.class);
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        test = convertStringValueTo("1", Integer.class);
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        test = convertStringValueTo("1", String.class);
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
        test = convertStringValueTo("1.0,2.0", (new Double[0]).getClass());
        System.out.println("Class Type: " + test.getClass() + ", value: "+test);
    }
    
}
