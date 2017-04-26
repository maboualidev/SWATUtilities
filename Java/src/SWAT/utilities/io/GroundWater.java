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
public final class GroundWater {
    public static enum fields {
        TITLE(String.class,""),
        SHALLST(Double.class,"SHALLST : Initial depth of water in the shallow aquifer [mm]"),
        DEEPST(Double.class,"DEEPST : Initial depth of water in the deep aquifer [mm]"),
        GW_DELAY(Double.class,"GW_DELAY : Groundwater delay [days]"),
        ALPHA_BF(Double.class,"ALPHA_BF : Baseflow alpha factor [days]"),
        GWQMN(Double.class,"GWQMN : Threshold depth of water in the shallow aquifer required for return flow to occur [mm]"),
        GW_REVAP(Double.class,"GW_REVAP : Groundwater \"revap\" coefficient"),
        REVAPMN(Double.class,"REVAPMN: Threshold depth of water in the shallow aquifer for \"revap\" to occur [mm]"),
        RCHRG_DP(Double.class,"RCHRG_DP : Deep aquifer percolation fraction"),
        GWHT(Double.class,"GWHT : Initial groundwater height [m]"),
        GW_SPYLD(Double.class,"GW_SPYLD : Specific yield of the shallow aquifer [m3/m3]"),
        SHALLST_N(Double.class,"SHALLST_N : Initial concentration of nitrate in shallow aquifer [mg N/l]"),
        GWSOLP(Double.class,"GWSOLP : Concentration of soluble phosphorus in groundwater contribution to streamflow from subbasin [mg P/l]"),
        HLIFE_NGW(Double.class,"HLIFE_NGW : Half-life of nitrate in the shallow aquifer [days]"),
        LAT_ORGN(Double.class,"LAT_ORGN : Organic N in the base flow [mg/L]"),
        LAT_ORGP(Double.class,"LAT_ORGP : Organic P in the base flow [mg/L]"),
        ALPHA_BF_D(Double.class,"ALPHA_BF_D : Baseflow alpha factor for deep aquifer [days]");
        
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
    
    private final EnumMap<fields,Object> values;
    
    public GroundWater() {
        this.values = new EnumMap(fields.class);
    }
    public GroundWater(String filename)
            throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    public GroundWater( String TITLE,
                        double SHALLST, double DEEPST, double GW_DELAY, double ALPHA_BF,
                        double GWQMN, double GW_REVAP, double REVAPMN, double RCHRG_DP,
                        double GWHT, double GW_SPYLD, double SHALLST_N, double GWSOLP,
                        double HLIFE_NGW, double LAT_ORGN, double LAT_ORGP, double ALPHA_BF_D) {
        this.values = new EnumMap(fields.class);
        this    .setTITLE(TITLE)
                .setSHALLST(SHALLST)
                .setDEEPST(DEEPST)
                .setGW_DELAY(GW_DELAY)
                .setALPHA_BF(ALPHA_BF)
                .setGWQMN(GWQMN)
                .setGW_REVAP(GW_REVAP)
                .setREVAPMN(REVAPMN)
                .setRCHRG_DP(RCHRG_DP)
                .setGWHT(GWHT)
                .setGW_SPYLD(GW_SPYLD)
                .setSHALLST_N(SHALLST_N)
                .setGWSOLP(GWSOLP)
                .setHLIFE_NGW(HLIFE_NGW)
                .setLAT_ORGN(LAT_ORGN)
                .setLAT_ORGP(LAT_ORGP)
                .setALPHA_BF_D(ALPHA_BF_D);
    }
    
    public final GroundWater set(GroundWater.fields fieldName, Object value)
            throws IllegalArgumentException {
        // Checking if the provided value has proper type and limits
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
    public final GroundWater set(String fieldNameStr, Object value) {
        return set(fields.valueOf(fieldNameStr), value);
    }
    
    public Object get(GroundWater.fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    public Object get(String fieldNameStr) {
        return values.get(GroundWater.fields.valueOf(fieldNameStr));
    }
    
    public final String getTITLE() {
	return (String) get(GroundWater.fields.TITLE);
    }
    public final double getSHALLST() {
        return (double) get(GroundWater.fields.SHALLST);
    }
    public final double getDEEPST() {
        return (double) get(GroundWater.fields.DEEPST);
    }
    public final double getGW_DELAY() {
        return (double) get(GroundWater.fields.GW_DELAY);
    }
    public final double getALPHA_BF() {
        return (double) get(GroundWater.fields.ALPHA_BF);
    }
    public final double getGWQMN() {
        return (double) get(GroundWater.fields.GWQMN);
    }
    public final double getGW_REVAP() {
        return (double) get(GroundWater.fields.GW_REVAP);
    }
    public final double getREVAPMN() {
        return (double) get(GroundWater.fields.REVAPMN);
    }
    public final double getRCHRG_DP() {
        return (double) get(GroundWater.fields.RCHRG_DP);
    }
    public final double getGWHT() {
        return (double) get(GroundWater.fields.GWHT);
    }
    public final double getGW_SPYLD() {
        return (double) get(GroundWater.fields.GW_SPYLD);
    }
    public final double getSHALLST_N() {
        return (double) get(GroundWater.fields.SHALLST_N);
    }
    public final double getGWSOLP() {
        return (double) get(GroundWater.fields.GWSOLP);
    }
    public final double getHLIFE_NGW() {
        return (double) get(GroundWater.fields.HLIFE_NGW);
    }
    public final double getLAT_ORGN() {
        return (double) get(GroundWater.fields.LAT_ORGN);
    }
    public final double getLAT_ORGP() {
        return (double) get(GroundWater.fields.LAT_ORGP);
    }
    public final double getALPHA_BF_D() {
        return (double) get(GroundWater.fields.ALPHA_BF_D);
    }

    public GroundWater setTITLE(String v) {
	return set(GroundWater.fields.TITLE,v);
    }
    public GroundWater setSHALLST(double v) {
        return set(GroundWater.fields.SHALLST,v);
    }
    public GroundWater setDEEPST(double v) {
        return set(GroundWater.fields.DEEPST,v);
    }
    public GroundWater setGW_DELAY(double v) {
        return set(GroundWater.fields.GW_DELAY,v);
    }
    public GroundWater setALPHA_BF(double v) {
        return set(GroundWater.fields.ALPHA_BF,v);
    }
    public GroundWater setGWQMN(double v) {
        return set(GroundWater.fields.GWQMN,v);
    }
    public GroundWater setGW_REVAP(double v) {
        return set(GroundWater.fields.GW_REVAP,v);
    }
    public GroundWater setREVAPMN(double v) {
        return set(GroundWater.fields.REVAPMN,v);
    }
    public GroundWater setRCHRG_DP(double v) {
        return set(GroundWater.fields.RCHRG_DP,v);
    }
    public GroundWater setGWHT(double v) {
        return set(GroundWater.fields.GWHT,v);
    }
    public GroundWater setGW_SPYLD(double v) {
        return set(GroundWater.fields.GW_SPYLD,v);
    }
    public GroundWater setSHALLST_N(double v) {
        return set(GroundWater.fields.SHALLST_N,v);
    }
    public GroundWater setGWSOLP(double v) {
        return set(GroundWater.fields.GWSOLP,v);
    }
    public GroundWater setHLIFE_NGW(double v) {
        return set(GroundWater.fields.HLIFE_NGW,v);
    }
    public GroundWater setLAT_ORGN(double v) {
        return set(GroundWater.fields.LAT_ORGN,v);
    }
    public GroundWater setLAT_ORGP(double v) {
        return set(GroundWater.fields.LAT_ORGP,v);
    }
    public GroundWater setALPHA_BF_D(double v) {
        return set(GroundWater.fields.ALPHA_BF_D,v);
    }
    
    public boolean contains(GroundWater.fields fieldName)
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr)
            throws IllegalArgumentException {
        return values.containsKey(GroundWater.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (GroundWater.fields fieldName: GroundWater.fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<fields> fieldNamesList) {
        for (GroundWater.fields fieldName: GroundWater.fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        ArrayList<fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }

    public GroundWater readSWATFileFormat(String filename)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.UTF_8)) {
            String line;
            for (fields key: fields.values()) {
                if ((line = reader.readLine()) == null)
                    throw new IOException(
                            String.format("Could not read the value for %s", key.toString()));
                if (key.toString().equals("TITLE")) {
                    this.set(key, line);
                } else {
                    this.set(key, Double.parseDouble(line.substring(0,20)));
                }
            }
        }
        return this;
    }
    public void writeSWATFileFormat(String filename)
            throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),StandardCharsets.UTF_8)) {
            writer.write(this.toSWATTXTFormat());
        }
    }
    public static GroundWater newFromSWATFile(String filename)
            throws IOException {
        GroundWater gw = new GroundWater();
        return gw.readSWATFileFormat(filename);
    }
    public static ArrayList<GroundWater> newFromSWATFiles(String[] filenames)
            throws IOException {
        ArrayList<GroundWater> gws = new ArrayList();
        for (String filename: filenames){
            gws.add(newFromSWATFile(filename));
        }
        return gws;
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
            return  String.format("{TITLE:'%s', SHALLST: %.4f, DEEPST: %.4f, GW_DELAY: %.4f, ALPHA_BF: %.4f, GWQMN: %.4f, GW_REVAP: %.4f, REVAPMN: %.4f, RCHRG_DP: %.4f, GWHT: %.4f, GW_SPYLD: %.4f, SHALLST_N: %.4f, GWSOLP: %.4f, HLIFE_NGW: %.4f, LAT_ORGN: %.4f, LAT_ORGP: %.4f, ALPHA_BF_D: %.4f}", 
                                  this.getTITLE(),
                                  this.getSHALLST(),
                                  this.getDEEPST(),
                                  this.getGW_DELAY(),
                                  this.getALPHA_BF(),
                                  this.getGWQMN(),
                                  this.getGW_REVAP(),
                                  this.getREVAPMN(),
                                  this.getRCHRG_DP(),
                                  this.getGWHT(),
                                  this.getGW_SPYLD(),
                                  this.getSHALLST_N(),
                                  this.getGWSOLP(),
                                  this.getHLIFE_NGW(),
                                  this.getLAT_ORGN(),
                                  this.getLAT_ORGP(),
                                  this.getALPHA_BF_D());
        } else {
            return null;
        }
    }
    public String toSWATTXTFormat() {
        if (this.containsAllFields()) {
            StringBuilder output = new StringBuilder();
            for (fields key: fields.values()) {
                if (key.toString().equals("TITLE")) {
                    output.append(this.values.get(key)).append("\n");
                } else {
                    output.append(String.format("%16.4f    | %s\n", this.values.get(key),key.getDescription()));
                }
            }
            return output.toString();
        } else {
            return null;
        }
    }
}
