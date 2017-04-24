/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 *
 * @author mabouali
 */
public class TestRouting {
    public static void test0() {
        System.out.println("Test 1:");
        Routing rte = new Routing();

        rte.set(Routing.fields.CH_ERODMO, 56.0,11);
        rte.set(Routing.fields.CH_ERODMO, 65.0,3);
        
        Double[] CH_ERODMO = (Double[]) rte.get(Routing.fields.CH_ERODMO);
        CH_ERODMO[10] = 35.54;
        for (Double e: CH_ERODMO)
            System.out.print(e+" ");
        System.out.println();
        rte.set(Routing.fields.CH_ERODMO,CH_ERODMO);
        CH_ERODMO = (Double[]) rte.get(Routing.fields.CH_ERODMO);
        for (Double e: CH_ERODMO)
            System.out.print(e+" ");
        System.out.println();
        CH_ERODMO = rte.getCH_ERODMO();
        for (Double e: CH_ERODMO)
            System.out.print(e+" ");
        System.out.println();
        
        System.out.println("Retrieving using get(fieldName,idx):");
        System.out.println(String.format("CH_ERODMO[0]: %f",rte.get(Routing.fields.CH_ERODMO,0)));
        System.out.println(String.format("CH_ERODMO[3]: %f",rte.get(Routing.fields.CH_ERODMO,3)));
        System.out.println(String.format("CH_ERODMO[10]: %f",rte.get(Routing.fields.CH_ERODMO,10)));
        System.out.println(String.format("CH_ERODMO[11]: %f",rte.get(Routing.fields.CH_ERODMO,11)));
        
        System.out.println("Retriving using getCH_ERODMO(idx):");
        System.out.println(String.format("CH_ERODMO[3]: %f",rte.getCH_ERODMO(3)));
        System.out.println(String.format("CH_ERODMO[11]: %f",rte.getCH_ERODMO(11)));
        
        Double CH_ERODMO3 = rte.getCH_ERODMO(3);
        CH_ERODMO3 = 3.0;
        System.out.println(String.format("CH_ERODMO[3]: %f",rte.getCH_ERODMO(3)));
        
        rte.set(Routing.fields.CHD, 56.0);
        System.out.println("CHD: " + rte.get(Routing.fields.CHD));
        System.out.println("CHD: " + rte.getCHD());
        Double CHD = rte.getCHD();
        CHD = 10.0;
        System.out.println("CHD: " + rte.getCHD());
        CHD = (Double) rte.get(Routing.fields.CHD);
        CHD = 11.0;
        System.out.println("CHD: " + rte.getCHD());
        
        System.out.println("END OF TEST 1\n");
    }
    public static void test1() throws IOException {
        System.out.println("\nTEST 1: Reading one file and writing it to a new location.");

        String filename = "..\\..\\SampleFiles\\RTE\\000010000.rte";
        Routing rte = Routing.newFromSWATFile(filename);
        System.out.println("CHW2: " + rte.getCHW2());
        System.out.println("CH_S2: " + rte.getCH_S2());
        System.out.print("CH_ERODMO: ");
        for (double e: rte.getCH_ERODMO())
            System.out.printf("%6.2f ",e);
        System.out.println();
        
        System.out.println("RTE contains all field: " + rte.containsAllFields());
        
        System.out.println("RTE in JSON format:");
        System.out.println(rte.toJSONString());
        System.out.println("RTE in SWAT TXT format:");
        System.out.println(rte.toSWATTXTFormat());
        
        
        System.out.println("Now Changing CHD to 0.987");
        rte.setCHD(0.987);
        System.out.println("Writing the new file to D:\\test.rte");
        rte.writeSWATFileFormat("D:\\test.rte");
        
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Testing SWAT.utilities.io.Routing");
        //test0();
        test1();
        
        
//        EnumMap<Routing.fields,Object> a = new EnumMap(Routing.fields.class);
//        Double[] v = new Double[12];
//        System.out.println(v.length);
//        v[3]=37.0;
//        v[5]=23.0;
//        a.put(Routing.fields.CH_ERODMO, v);
//        System.out.println(((Double[]) a.get(Routing.fields.CH_ERODMO))[5]);
//        ((Double[]) a.get(Routing.fields.CH_ERODMO))[5] = 10.0;
//        System.out.println(((Double[]) a.get(Routing.fields.CH_ERODMO))[5]);
    }
}
