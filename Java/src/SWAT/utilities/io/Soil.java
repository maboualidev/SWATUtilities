/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import SWAT.utilities.common.Convertor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author mabouali
 */
public final class Soil
        implements SWATFormatInput<Soil,Soil.fields> {
    public static enum fields {
        TITLE     (String.class,""),
        SNAM      (String.class," Soil Name: "),
        HYDGRP    (String.class," Soil Hydrologic Group: "),
        SOL_ZMX   (Double.class," Maximum rooting depth(m) : "),
        ANION_EXCL(Double.class," Porosity fraction from which anions are excluded: "),
        SOL_CRK   (Double.class," Crack volume potential of soil: "),
        COMMENT   (String.class,""),
        SOL_Z     ((new Double [0]).getClass()," Depth                [mm]:"),
        SOL_BD    ((new Double [0]).getClass()," Bulk Density Moist [g/cc]:"),
        SOL_AWC   ((new Double [0]).getClass()," Ave. AW Incl. Rock Frag  :"),
        SOL_K     ((new Double [0]).getClass()," Ksat. (est.)      [mm/hr]:"),
        SOL_CBN   ((new Double [0]).getClass()," Organic Carbon [weight %]:"),
        COL_CLAY  ((new Double [0]).getClass()," Clay           [weight %]:"),
        SOL_SILT  ((new Double [0]).getClass()," Silt           [weight %]:"),
        SOL_SAND  ((new Double [0]).getClass()," Sand           [weight %]:"),
        SOL_ROCK  ((new Double [0]).getClass()," Rock Fragments   [vol. %]:"),
        SOL_ALB   ((new Double [0]).getClass()," Soil Albedo (Moist)      :"),
        USLE_K    ((new Double [0]).getClass()," Erosion K                :"),
        SOL_EC    ((new Double [0]).getClass()," Salinity (EC, Form 5)    :"),
        SOL_PH    ((new Double [0]).getClass()," Soil pH                  :"),
        SOL_CAL   ((new Double [0]).getClass()," Soil CACO3               :"),
        ;
        
        
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
    @Override
    public Class getFieldClassType(String fieldNameStr) {
        return fields.valueOf(fieldNameStr).getFieldClassType();
    }
    
    @SuppressWarnings("unchecked")
    private static final ArrayList<String> SPKEYLIST = 
            new ArrayList(Arrays.asList("SOL_Z","SOL_BD","SOL_AWC",
                                        "SOL_K","SOL_CBN","COL_CLAY",
                                        "SOL_SILT","SOL_SAND","SOL_ROCK",
                                        "SOL_ALB","USLE_K","SOL_EC",
                                        "SOL_PH","SOL_CAL"));
    private int nLayers=-1;
    private final EnumMap<Soil.fields,Object> values;
    
    @SuppressWarnings("unchecked")
    public Soil() {
        this.nLayers=-1;
        this.values = new EnumMap(fields.class);
    }
    @SuppressWarnings("unchecked")
    public Soil(String filename)
            throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    
    @Override
    public Soil set(Soil.fields fieldName, Object value)
            throws IllegalArgumentException {
        if (value.getClass().equals(fieldName.getFieldClassType())) {
            if (SPKEYLIST.indexOf(fieldName.toString())==-1) {
                values.put(fieldName,value);
            } else {
                if (this.nLayers==-1) {
                    nLayers = ((Double[]) value).length;
                    values.put(fieldName,value);
                } else {
                    if (this.nLayers == ((Double[]) value).length) {
                        values.put(fieldName,value);
                    } else {
                        throw new IllegalArgumentException(String.format(
                                "Expected nLayers=%d element in the input values; got %d",
                                nLayers,
                                ((Double[]) value).length));
                    }
                }
            }
        } else {
            throw new IllegalArgumentException(
                    String.format("Expected %s type for field %s, but got %s",
                            fieldName.getFieldClassType(),
                            fieldName,
                            value.getClass()));
        }
        return this;
    }
    @Override
    public Soil set(String fieldNameStr, Object value) {
        return this.set(fields.valueOf(fieldNameStr),value);
    }
    public Soil set(Soil.fields fieldName, Object value, int idx)
            throws IllegalArgumentException {
        if (SPKEYLIST.indexOf(fieldName.toString())==-1) {
            // ignoring idx
            return this.set(fieldName,value);
        } else {
            if (this.values.containsKey(fieldName)) {
                if(idx>=0 && idx<nLayers) {
                    ((Double[])this.values.get(fieldName))[idx] = (Double) value;
                    return this;
                } else {
                    throw new IllegalArgumentException(String.format(
                            "idx must be greater than or equal to 0 and less than %d",nLayers));
                }
            } else {
                if (this.nLayers==-1) {
                    throw new IllegalArgumentException("Number of layers are still unknown.");
                } else {
                    if(idx>=0 && idx<nLayers) {
                        Double[] tmpVar = new Double[nLayers];
                        tmpVar[idx] = (Double) value;
                        this.values.put(fieldName,tmpVar);
                        return this;
                    } else {
                        throw new IllegalArgumentException(String.format(
                                "idx must be greater than or equal to 0 and less than %d",nLayers));
                    }
                }
            }
        }
    }
    public Soil set(String fieldNameStr, Object value, int idx)
            throws IllegalArgumentException {
        return this.set(fields.valueOf(fieldNameStr), value,idx);
    }
    @Override
    public final Soil set(String fieldNameStr, String value) {
        fields fieldName = fields.valueOf(fieldNameStr);
        return set(fieldName,Convertor.convertStringValueTo(value, fieldName.getFieldClassName()));
    }
    
    @Override
    public Object get(Soil.fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    @Override
    public Object get(String fieldNameStr)
            throws NullPointerException {
        return values.get(Soil.fields.valueOf(fieldNameStr));
    }
    public Object get(Soil.fields fieldName,int idx)
            throws NullPointerException,IllegalArgumentException {
        if (SPKEYLIST.indexOf(fieldName.toString()) == -1) {
            // ignoring the idx
            return this.get(fieldName);
        } else {
            Double[] tmpVar = (Double[]) this.get(fieldName);
            if (tmpVar==null) {
                throw new NullPointerException(String.format("%s is not set yet",fieldName.toString()));
            } else {
                if (idx>=0 && idx<nLayers) {
                    return (Object) tmpVar[idx];
                } else {
                    throw new IllegalArgumentException(String.format(
                            "idx must be greater than or equal to 0 and less than %d",
                            nLayers));
                }
            }
        }
    }
    public Object get(String fieldName,int idx)
            throws NullPointerException,IllegalArgumentException {
        return this.get(fields.valueOf(fieldName),idx);
    }
    
    public final int getNLayers(){
        return nLayers;
    }
    public final String getTITLE() {
        return (String) get(fields.TITLE);
    }
    public final String getSNAM() {
        return (String) get(fields.SNAM);
    }
    public final String getHYDGRP() {
        return (String) get(fields.HYDGRP);
    }
    public final double getSOL_ZMX() {
        return (double) get(fields.SOL_ZMX);
    }
    public final double getANION_EXCL() {
        return (double) get(fields.ANION_EXCL);
    }
    public final double getSOL_CRK() {
        return (double) get(fields.SOL_CRK);
    }
    public final String getCOMMENT() {
        return (String) get(fields.COMMENT);
    }
    public final Double[] getSOL_Z() {
        return (Double[]) get(fields.SOL_Z);
    }
    public final Double[] getSOL_BD() {
        return (Double[]) get(fields.SOL_BD);
    }
    public final Double[] getSOL_AWC() {
        return (Double[]) get(fields.SOL_AWC);
    }
    public final Double[] getSOL_K() {
        return (Double[]) get(fields.SOL_K);
    }
    public final Double[] getSOL_CBN() {
        return (Double[]) get(fields.SOL_CBN);
    }
    public final Double[] getCOL_CLAY() {
        return (Double[]) get(fields.COL_CLAY);
    }
    public final Double[] getSOL_SILT() {
        return (Double[]) get(fields.SOL_SILT);
    }
    public final Double[] getSOL_SAND() {
        return (Double[]) get(fields.SOL_SAND);
    }
    public final Double[] getSOL_ROCK() {
        return (Double[]) get(fields.SOL_ROCK);
    }
    public final Double[] getSOL_ALB() {
        return (Double[]) get(fields.SOL_ALB);
    }
    public final Double[] getUSLE_K() {
        return (Double[]) get(fields.USLE_K);
    }
    public final Double[] getSOL_EC() {
        return (Double[]) get(fields.SOL_EC);
    }
    public final Double[] getSOL_PH() {
        return (Double[]) get(fields.SOL_PH);
    }
    public final Double[] getSOL_CAL() {
        return (Double[]) get(fields.SOL_CAL);
    }

    public Soil setTITLE(String v) {
	return set(fields.TITLE,v);
    }
    public Soil setSNAM(String v) {
           return set(fields.SNAM,v);
    }
    public Soil setHYDGRP(String v) {
           return set(fields.HYDGRP,v);
    }
    public Soil setSOL_ZMX(Double v) {
           return set(fields.SOL_ZMX,v);
    }
    public Soil setANION_EXCL(Double v) {
           return set(fields.ANION_EXCL,v);
    }
    public Soil setSOL_CRK(Double v) {
	return set(fields.SOL_CRK,v);
    }
    public Soil setCOMMENT(String v) {
	return set(fields.COMMENT,v);
    }
    public Soil setSOL_Z(Double[] v) {
        return set(fields.SOL_Z,v);
    }
    public Soil setSOL_BD(Double[] v) {
        return set(fields.SOL_BD,v);
    }
    public Soil setSOL_AWC(Double[] v) {
        return set(fields.SOL_AWC,v);
    }
    public Soil setSOL_K(Double[] v) {
        return set(fields.SOL_K,v);
    }
    public Soil setSOL_CBN(Double[] v) {
        return set(fields.SOL_CBN,v);
    }
    public Soil setCOL_CLAY(Double[] v) {
        return set(fields.COL_CLAY,v);
    }
    public Soil setSOL_SILT(Double[] v) {
        return set(fields.SOL_SILT,v);
    }
    public Soil setSOL_SAND(Double[] v) {
        return set(fields.SOL_SAND,v);
    }
    public Soil setSOL_ROCK(Double[] v) {
        return set(fields.SOL_ROCK,v);
    }
    public Soil setSOL_ALB(Double[] v) {
        return set(fields.SOL_ALB,v);
    }
    public Soil setUSLE_K(Double[] v) {
        return set(fields.USLE_K,v);
    }
    public Soil setSOL_EC(Double[] v) {
        return set(fields.SOL_EC,v);
    }
    public Soil setSOL_PH(Double[] v) {
        return set(fields.SOL_PH,v);
    }
    public Soil setSOL_CAL(Double[] v) {
        return set(fields.SOL_CAL,v);
    }
    public Soil setSOL_Z(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_Z,tmpVar);
    }
    public Soil setSOL_BD(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_BD,tmpVar);
    }
    public Soil setSOL_AWC(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_AWC,tmpVar);
    }
    public Soil setSOL_K(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_K,tmpVar);
    }
    public Soil setSOL_CBN(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_CBN,tmpVar);
    }
    public Soil setCOL_CLAY(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.COL_CLAY,tmpVar);
    }
    public Soil setSOL_SILT(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_SILT,tmpVar);
    }
    public Soil setSOL_SAND(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_SAND,tmpVar);
    }
    public Soil setSOL_ROCK(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_ROCK,tmpVar);
    }
    public Soil setSOL_ALB(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_ALB,tmpVar);
    }
    public Soil setUSLE_K(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.USLE_K,tmpVar);
    }
    public Soil setSOL_EC(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_EC,tmpVar);
    }
    public Soil setSOL_PH(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_PH,tmpVar);
    }
    public Soil setSOL_CAL(double[] v) {
        Double[] tmpVar = new Double[v.length];
        for(int idx=0; idx<v.length;idx++)
                tmpVar[idx]=v[idx];
        return set(fields.SOL_CAL,tmpVar);
    }
    public Soil setSOL_Z(Double v, int idx) {
        return set(fields.SOL_Z,v,idx);
    }
    public Soil setSOL_BD(Double v, int idx) {
        return set(fields.SOL_BD,v,idx);
    }
    public Soil setSOL_AWC(Double v, int idx) {
        return set(fields.SOL_AWC,v,idx);
    }
    public Soil setSOL_K(Double v, int idx) {
        return set(fields.SOL_K,v,idx);
    }
    public Soil setSOL_CBN(Double v, int idx) {
        return set(fields.SOL_CBN,v,idx);
    }
    public Soil setCOL_CLAY(Double v, int idx) {
        return set(fields.COL_CLAY,v,idx);
    }
    public Soil setSOL_SILT(Double v, int idx) {
        return set(fields.SOL_SILT,v,idx);
    }
    public Soil setSOL_SAND(Double v, int idx) {
        return set(fields.SOL_SAND,v,idx);
    }
    public Soil setSOL_ROCK(Double v, int idx) {
        return set(fields.SOL_ROCK,v,idx);
    }
    public Soil setSOL_ALB(Double v, int idx) {
        return set(fields.SOL_ALB,v,idx);
    }
    public Soil setUSLE_K(Double v, int idx) {
        return set(fields.USLE_K,v,idx);
    }
    public Soil setSOL_EC(Double v, int idx) {
        return set(fields.SOL_EC,v,idx);
    }
    public Soil setSOL_PH(Double v, int idx) {
        return set(fields.SOL_PH,v,idx);
    }
    public Soil setSOL_CAL(Double v, int idx) {
        return set(fields.SOL_CAL,v,idx);
    }
    
    public boolean contains(Soil.fields fieldName) 
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr) 
            throws IllegalArgumentException {
        return values.containsKey(Soil.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (Soil.fields fieldName: Soil.fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<fields> fieldNamesList) {
        for (Soil.fields fieldName: Soil.fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        ArrayList<fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }
    
    @Override
    public Soil readSWATFileFormat(String filename)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.UTF_8)) {
            String line;
            for (Soil.fields key: Soil.fields.values()) {
                if ((line = reader.readLine()) == null)
                    throw new IOException(
                            String.format("Could not read the value for %s", key.toString()));
                if (key.equals(fields.TITLE) ||
                        key.equals(fields.COMMENT)) {
                    this.set(key, line);
                } else {
                    String dataStr  = line.substring(key.getDescription().length());
                    if (key.equals(fields.SNAM) ||
                            key.equals(fields.HYDGRP)) {
                        this.set(key,dataStr.trim());
                    } else if (SPKEYLIST.indexOf(key.toString())==-1) {
                        this.set(key,Double.parseDouble(dataStr.trim()));
                    } else {
                        if (nLayers == -1) {
                            if (dataStr.length() % 12 != 0 ) {
                                throw new IOException(
                                        String.format(
                                                "Something went wrong parsing %s for key %s",
                                                filename,key.toString()));
                            } else {
                                nLayers = dataStr.length()/12;
                                Double[] tmpVar = new Double[nLayers];
                                for (int idx=0; idx<nLayers; idx++)
                                    tmpVar[idx] = Double.parseDouble(dataStr.substring(idx*12,(idx+1)*12));
                                this.set(key,tmpVar);
                            }
                        } else {
                            if (dataStr.length() != nLayers*12) {
                                throw new IOException(
                                        String.format(
                                                "Something went wrong parsing %s for key %s",
                                                filename,key.toString()));
                            } else {
                                Double[] tmpVar = new Double[nLayers];
                                for (int idx=0; idx<nLayers; idx++)
                                    tmpVar[idx] = Double.parseDouble(dataStr.substring(idx*12,(idx+1)*12));
                                this.set(key,tmpVar);
                            }
                        }
                    }
                }
            }
        }
        return this;
    }
    @Override
    public void writeSWATFileFormat(String filename)
            throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),StandardCharsets.UTF_8)) {
            writer.write(this.toSWATTXTFormat());
        }
    }
    public static Soil newFromSWATFile(String filename)
            throws IOException {
        Soil sol = new Soil();
        return sol.readSWATFileFormat(filename);
    }
    @SuppressWarnings("unchecked")
    public static List<Soil> newFromSWATFiles(String[] filenames)
            throws IOException {
        ArrayList<Soil> sols = new ArrayList();
        for (String filename: filenames){
            sols.add(newFromSWATFile(filename));
        }
        return sols;
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toString(String stringOutputType)
            throws IllegalArgumentException{
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
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("{");
            for (fields key: fields.values()) {
                switch (key){
                    case TITLE:
                        strBldr.append(String.format(
                        "TITLE: \"%s\"",(String) this.get(fields.TITLE)));
                        break;
                    case COMMENT:
                        strBldr.append(String.format(
                            ", COMMENT: \"%s\"",(String) this.get(fields.COMMENT)));
                        break;
                    default:
                        if ((SPKEYLIST.indexOf(key.toString())==-1)) {
                            if (key.equals(fields.SNAM) ||
                                    key.equals(fields.HYDGRP)) {
                                strBldr.append(String.format(
                                    ", %s: \"%s\"",key.toString(),(String) this.get(key)));
                            } else {
                                strBldr.append(String.format(
                                    ", %s: %f",key.toString(),(double) this.get(key)));
                            }
                        } else {
                            StringBuilder strBldr2 = new StringBuilder("[");
                            for (int idx=0; idx<nLayers; idx++) {
                                if (idx!=0)
                                    strBldr2.append(",");
                                strBldr2.append( ((Double) this.get(key,idx)).toString() );
                            }
                            strBldr.append(
                                    String.format(
                                            ", %s: %s",
                                            key.toString(),
                                            strBldr2.append("]") ) );
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
            StringBuilder strBldr = new StringBuilder();
            for (fields key: fields.values()) {
                switch (key){
                    case TITLE:
                    case COMMENT:
                        strBldr.append(String.format(
                        "%s\n",(String) this.get(fields.TITLE)));
                        break;
                    default:
                        if ((SPKEYLIST.indexOf(key.toString())==-1)) {
                            if (key.equals(fields.SNAM) ||
                                    key.equals(fields.HYDGRP)) {
                                strBldr.append(String.format(
                                    "%s%s\n",key.getDescription(),(String) this.get(key)));
                            } else {
                                switch (key) {
                                    case SOL_ZMX:
                                        strBldr.append(String.format(
                                            "%s%.2f\n",key.getDescription(),(Double) this.get(key)));
                                        break;
                                    default:
                                        strBldr.append(String.format(
                                            "%s%.3f\n",key.getDescription(),(Double) this.get(key)));
                                }
                            }
                        } else {
                            strBldr.append(key.getDescription());
                            for (double e: (Double[]) get(key))
                                strBldr.append(String.format("%12.2f", e));
                            strBldr.append("\n");
                        }
                }
            }
            return strBldr.toString();
        } else {
            return null;
        }
    }
}
