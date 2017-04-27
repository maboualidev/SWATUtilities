/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author mabouali
 */
public final class Routing
        implements SWATFormatInput<Routing,Routing.fields> {
    public static enum fields {
        TITLE(String.class,""),
        CHW2(Double.class,"CHW2 : Main channel width [m]"),
        CHD(Double.class,"CHD : Main channel depth [m]"),
        CH_S2(Double.class,"CH_S2 : Main channel slope [m/m]"),
        CH_L2(Double.class,"CH_L2 : Main channel length [km]"),
        CH_N2(Double.class,"CH_N2 : Manning's nvalue for main channel"),
        CH_K2(Double.class,"CH_K2 : Effective hydraulic conductivity [mm/hr]"),
        CH_COV1(Double.class,"CH_COV1: Channel erodibility factor"),
        CH_COV2(Double.class,"CH_COV2 : Channel cover factor"),
        CH_WDR(Double.class,"CH_WDR : Channel width:depth ratio [m/m]"),
        ALPHA_BNK(Double.class,"ALPHA_BNK : Baseflow alpha factor for bank storage [days]"),
        ICANAL(Double.class,"ICANAL : Code for irrigation canal"),
        CH_ONCO(Double.class,"CH_ONCO : Organic nitrogen concentration in the channel [ppm]"),
        CH_OPCO(Double.class,"CH_OPCO : Organic phosphorus concentration in the channel [ppm]"),
        CH_SIDE(Double.class,"CH_SIDE : Change in horizontal distance per unit vertical distance"),
        CH_BNK_BD(Double.class,"CH_BNK_BD : Bulk density of channel bank sediment (g/cc)"),
        CH_BED_BD(Double.class,"CH_BED_BD : Bulk density of channel bed sediment (g/cc)"),
        CH_BNK_KD(Double.class,"CH_BNK_KD : Erodibility of channel bank sediment by jet test (cm3/N-s)"),
        CH_BED_KD(Double.class,"CH_BED_KD : Erodibility of channel bed sediment by jet test (cm3/N-s)"),
        CH_BNK_D50(Double.class,"CH_BNK_D50 : D50 Median particle size diameter of channel bank sediment (µm)"),
        CH_BED_D50(Double.class,"CH_BED_D50 : D50 Median particle size diameter of channel bed sediment (µm)"),
        CH_BNK_TC(Double.class,"CH_BNK_TC : Critical shear stress of channel bank (N/m2)"),
        CH_BED_TC(Double.class,"CH_BED_TC : Critical shear stress of channel bed (N/m2)"),
        CH_ERODMO((new Double[0]).getClass(),"CH_ERODMO : Resistance to erosion"),
        CH_EQN(Double.class,"CH_EQN : Sediment routing methods");
        
        private final Class classType;
        private final String description;
        
        fields(Class classType, String description) {
            this.classType = classType;
            this.description = description;
        }

        public Class getFieldClassType(){
            return classType;            
        }
        public String getFieldClassName(){
            return classType.toString();
        }       
        public String getDescription(){
            return this.description;
        }
    }
    
    private final EnumMap<Routing.fields,Object> values;
    
    public Routing() {
        this.values = new EnumMap(Routing.fields.class);
    }
    public Routing(String filename)
            throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    
    @Override
    public final Routing set(Routing.fields fieldName, Object value)
            throws IllegalArgumentException {
        if (value.getClass().equals(fieldName.getFieldClassType())) {
            values.put(fieldName,value);
        } else {
            throw new IllegalArgumentException(
                    String.format("Expected %s type for field %s, but got %s",
                            fieldName.getFieldClassType(),
                            fieldName,
                            value.getClass()));
        }
        return this;
    }
    public final Routing set(Routing.fields fieldName, Object value, int idx)
            throws IllegalArgumentException {
        // CH_ERODMO Needs to be handled differently
        if (fieldName.equals(fields.CH_ERODMO)) {
            if ((value.getClass().equals(Double.class)) && idx>=0 && idx<12) {
                if (this.values.containsKey(fields.CH_ERODMO)) {
                    ((Double[]) values.get(Routing.fields.CH_ERODMO))[idx] = (Double) value;
                    return this;
                } else {
                    Double[] tmpVar = new Double[12];
                    tmpVar[idx] = (Double) value;
                    values.put(fields.CH_ERODMO, tmpVar);
                    return this;
                }
            } else {
                throw new IllegalArgumentException(
                        String.format("Expected %s type for field %s, and 0<=idx<=11; but got type %s and idx=%d",
                                fieldName.getFieldClassType(),
                                fieldName,
                                value.getClass(),
                                idx));
            }
        } else { // ignoring idx
            return this.set(fieldName,value);
        }
    }
    @Override
    public final Routing set(String fieldNameStr, Object value) {
        return set(Routing.fields.valueOf(fieldNameStr), value);
    }
    public final Routing set(String fieldNameStr, Object value, int idx) {
        return this.set(Routing.fields.valueOf(fieldNameStr), value,idx);
    }
    
    @Override
    public Object get(Routing.fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    @Override
    public Object get(String fieldNameStr) {
        return values.get(Routing.fields.valueOf(fieldNameStr));
    }
    public Object get(Routing.fields fieldName,int idx)
            throws NullPointerException,IllegalArgumentException {
        if (fieldName.equals(fields.CH_ERODMO)) {
            if (idx>=0 && idx<12){
                return (Object) ((Double[]) values.get(fields.CH_ERODMO))[idx];
            } else {
                throw new IllegalArgumentException(
                        String.format("idx must be greater than or equal to 0 and less than 11; got idx=%d",idx));
            }
        } else {
            return values.get(fieldName);
        }
    }
    public Object get(String fieldNameStr,int idx) {
        return this.get(Routing.fields.valueOf(fieldNameStr),idx);
    }
    
    public final String getTITLE() {
	return (String) get(Routing.fields.TITLE);
    }
    public final double getCHW2() {
        return (double) get(Routing.fields.CHW2);
    }
    public final double getCHD() {
        return (double) get(Routing.fields.CHD);
    }
    public final double getCH_S2() {
        return (double) get(Routing.fields.CH_S2);
    }
    public final double getCH_L2() {
        return (double) get(Routing.fields.CH_L2);
    }
    public final double getCH_N2() {
        return (double) get(Routing.fields.CH_N2);
    }
    public final double getCH_K2() {
        return (double) get(Routing.fields.CH_K2);
    }
    public final double getCH_COV1() {
        return (double) get(Routing.fields.CH_COV1);
    }
    public final double getCH_COV2() {
        return (double) get(Routing.fields.CH_COV2);
    }
    public final double getCH_WDR() {
        return (double) get(Routing.fields.CH_WDR);
    }
    public final double getALPHA_BNK() {
        return (double) get(Routing.fields.ALPHA_BNK);
    }
    public final double getICANAL() {
        return (double) get(Routing.fields.ICANAL);
    }
    public final double getCH_ONCO() {
        return (double) get(Routing.fields.CH_ONCO);
    }
    public final double getCH_OPCO() {
        return (double) get(Routing.fields.CH_OPCO);
    }
    public final double getCH_SIDE() {
        return (double) get(Routing.fields.CH_SIDE);
    }
    public final double getCH_BNK_BD() {
        return (double) get(Routing.fields.CH_BNK_BD);
    }
    public final double getCH_BED_BD() {
        return (double) get(Routing.fields.CH_BED_BD);
    }
    public final double getCH_BNK_KD() {
        return (double) get(Routing.fields.CH_BNK_KD);
    }
    public final double getCH_BED_KD() {
        return (double) get(Routing.fields.CH_BED_KD);
    }
    public final double getCH_BNK_D50() {
        return (double) get(Routing.fields.CH_BNK_D50);
    }
    public final double getCH_BED_D50() {
        return (double) get(Routing.fields.CH_BED_D50);
    }
    public final double getCH_BNK_TC() {
        return (double) get(Routing.fields.CH_BNK_TC);
    }
    public final double getCH_BED_TC() {
        return (double) get(Routing.fields.CH_BED_TC);
    }
    public final Double[] getCH_ERODMO() {
        return (Double[]) get(Routing.fields.CH_ERODMO);
    }
    public final double getCH_ERODMO(int idx) {
        Object a = get(Routing.fields.CH_ERODMO,idx);
        if (a==null) {
            throw new NullPointerException(String.format("CH_ERODMO[%d] is not set yet.",idx));
        } else {
            return (double) a;
        }
    }
    public final double getCH_EQN() {
        return (double) get(Routing.fields.CH_EQN);
    }
    
    public final Routing setTITLE(String v) {
	return set(Routing.fields.TITLE,v);
    }
    public final Routing setCHW2(double v) {
        return set(Routing.fields.CHW2,v);
    }
    public final Routing setCHD(double v) {
        return set(Routing.fields.CHD,v);
    }
    public final Routing setCH_S2(double v) {
        return set(Routing.fields.CH_S2,v);
    }
    public final Routing setCH_L2(double v) {
        return set(Routing.fields.CH_L2,v);
    }
    public final Routing setCH_N2(double v) {
        return set(Routing.fields.CH_N2,v);
    }
    public final Routing setCH_K2(double v) {
        return set(Routing.fields.CH_K2,v);
    }
    public final Routing setCH_COV1(double v) {
        return set(Routing.fields.CH_COV1,v);
    }
    public final Routing setCH_COV2(double v) {
        return set(Routing.fields.CH_COV2,v);
    }
    public final Routing setCH_WDR(double v) {
        return set(Routing.fields.CH_WDR,v);
    }
    public final Routing setALPHA_BNK(double v) {
        return set(Routing.fields.ALPHA_BNK,v);
    }
    public final Routing setICANAL(double v) {
        return set(Routing.fields.ICANAL,v);
    }
    public final Routing setCH_ONCO(double v) {
        return set(Routing.fields.CH_ONCO,v);
    }
    public final Routing setCH_OPCO(double v) {
        return set(Routing.fields.CH_OPCO,v);
    }
    public final Routing setCH_SIDE(double v) {
        return set(Routing.fields.CH_SIDE,v);
    }
    public final Routing setCH_BNK_BD(double v) {
        return set(Routing.fields.CH_BNK_BD,v);
    }
    public final Routing setCH_BED_BD(double v) {
        return set(Routing.fields.CH_BED_BD,v);
    }
    public final Routing setCH_BNK_KD(double v) {
        return set(Routing.fields.CH_BNK_KD,v);
    }
    public final Routing setCH_BED_KD(double v) {
        return set(Routing.fields.CH_BED_KD,v);
    }
    public final Routing setCH_BNK_D50(double v) {
        return set(Routing.fields.CH_BNK_D50,v);
    }
    public final Routing setCH_BED_D50(double v) {
        return set(Routing.fields.CH_BED_D50,v);
    }
    public final Routing setCH_BNK_TC(double v) {
        return set(Routing.fields.CH_BNK_TC,v);
    }
    public final Routing setCH_BED_TC(double v) {
        return set(Routing.fields.CH_BED_TC,v);
    }
    public final Routing setCH_ERODMO(double v, int idx) {
        return set(Routing.fields.CH_ERODMO,v,idx);
    }
    public final Routing setCH_ERODMO(Double[] v) {
        if (v.length!=12) {
            throw new IllegalArgumentException(String.format("Input array must have 12 elements. It has %d",v.length));
        }
        return set(Routing.fields.CH_ERODMO,v);
    }
    public final Routing setCH_ERODMO(double[] v) {
        if (v.length!=12) {
            throw new IllegalArgumentException(String.format("Input array must have 12 elements. It has %d",v.length));
        }
        Double[] tmpVar = new Double[12];
        for (int idx=0; idx<12; idx++)
            tmpVar[idx] = v[idx];
        return set(Routing.fields.CH_ERODMO,tmpVar);
    }
    public final Routing setCH_EQN(double v) {
        return set(Routing.fields.CH_EQN,v);
    }
    
    public boolean contains(Routing.fields fieldName)
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr)
            throws IllegalArgumentException {
        return values.containsKey(Routing.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (Routing.fields fieldName: Routing.fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<Routing.fields> fieldNamesList) {
        for (Routing.fields fieldName: Routing.fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        ArrayList<Routing.fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(Routing.fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }
    
    @Override
    public Routing readSWATFileFormat(String filename)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.ISO_8859_1)) {
            String line;
            for (Routing.fields key: Routing.fields.values()) {
                if ((line = reader.readLine()) == null)
                    throw new IOException(
                            String.format("Could not read the value for %s", key.toString()));
                switch (key) {
                    case TITLE:
                        this.set(key, line);
                        break;
                    case CH_ERODMO:
                        Double[] CH_ERODMO = new Double[12];
                        for(int idx = 0; idx<12; idx++)
                            CH_ERODMO[idx] = Double.parseDouble(line.substring(idx*6, (idx+1)*6));
                        this.set(key,CH_ERODMO);
                        break;
                    default:
                        this.set(key, Double.parseDouble(line.substring(0,18)));
                        break;
                }
            }
        }
        return this;
    }
    @Override
    public void writeSWATFileFormat(String filename)
            throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),StandardCharsets.ISO_8859_1)) {
            writer.write(this.toSWATTXTFormat());
        }
    }
    public static Routing newFromSWATFile(String filename)
            throws IOException {
        Routing rte = new Routing();
        return rte.readSWATFileFormat(filename);
    }
    public static List<Routing> newFromSWATFiles(String[] filenames)
            throws IOException {
        ArrayList<Routing> rtes = new ArrayList();
        for (String filename: filenames){
            rtes.add(newFromSWATFile(filename));
        }
        return rtes;
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toString(String stringOutputType) throws IllegalArgumentException{
        switch (stringOutputType){
            case "JSON":
                return this.toJSONString();
            case "SWAT":
                return this.toSWATTXTFormat();
            default:
                throw new IllegalArgumentException(String.format("String output Type could be: 1) JSON, (2) SWAT; But got %s", stringOutputType));
        }
            
    }
    public String toJSONString() {
        StringBuilder strBldr = new StringBuilder("{");
        if (this.containsAllFields()) {
            for (fields key: fields.values()) {
                if (key.equals(fields.CH_ERODMO)) {
                    Double[] CH_ERODMO = this.getCH_ERODMO();
                    strBldr.append(", CH_ERODMO: [");
                    for (int idx=0; idx<12;idx++){
                        if (idx==0) {
                            strBldr.append(String.format("%f", CH_ERODMO[idx]));
                        } else {
                            strBldr.append(String.format(",%f",CH_ERODMO[idx]));
                        }
                    }
                    strBldr.append("]");

                } else {
                    if (key.equals(fields.TITLE)) {
                        strBldr.append( String.format("TITLE: %s",this.get(key)) );
                    } else {
                        strBldr.append( String.format(", %s: %f",key.toString(),this.get(key)) );
                    }
                }
            }
            return strBldr.append("}").toString();
        } else {
            return null;
        }
    }
    public String toSWATTXTFormat() {
        if (this.containsAllFields()) {
            StringBuilder output = new StringBuilder();
            for (Routing.fields key: Routing.fields.values()) {
                switch (key) {
                    case TITLE:
                        output.append(this.values.get(key)).append("\n");
                        break;
                    case CH_ERODMO:
                        for (double e: this.getCH_ERODMO())
                            output.append(String.format("%6.2f",e));
                        output.append("\n");
                        break;
                    default:
                        output.append(String.format("%14s    | %s\n", this.values.get(key).toString(),key.getDescription()));
                        break;
                }
            }
            return output.toString();
        } else {
            return null;
        }
    }
}
