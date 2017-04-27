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
public final class Management
        implements SWATFormatInput<Management,Management.fields> {
    public static enum fields {
            TITLE(String.class,""),
            NMGT(Integer.class,"NMGT:Management code"),
            COMMENT1(String.class,"Initial Plant Growth Parameters"),
            IGRO(Integer.class,"IGRO: Land cover status: 0-none growing; 1-growing"),
            PLANT_ID(Integer.class,"PLANT_ID: Land cover ID number (IGRO = 1)"),
            LAI_INIT(Double.class,"LAI_INIT: Initial leaf are index (IGRO = 1)"),
            BIO_INIT(Double.class,"BIO_INIT: Initial biomass (kg/ha) (IGRO = 1)"),
            PHU_PLT(Double.class,"PHU_PLT: Number of heat units to bring plant to maturity (IGRO = 1)"),
            COMMENT2(String.class,"General Management Parameters"),
            BIOMIX(Double.class,"BIOMIX: Biological mixing efficiency"),
            CN2(Double.class,"CN2: Initial SCS CN II value"),
            USLE_P(Double.class,"USLE_P: USLE support practice factor"),
            BIO_MIN(Double.class,"BIO_MIN: Minimum biomass for grazing (kg/ha)"),
            FILTERW(Double.class,"FILTERW: width of edge of field filter strip (m)"),
            COMMENT3(String.class,"Urban Management Parameters"),
            IURBAN(Integer.class,"IURBAN: urban simulation code, 0-none, 1-USGS, 2-buildup/washoff"),
            URBLU(Integer.class,"URBLU: urban land type"),
            COMMENT4(String.class,"Irrigation Management Parameters"),
            IRRSC(Integer.class,"IRRSC: irrigation code"),
            IRRNO(Integer.class,"IRRNO: irrigation source location"),
            FLOWMIN(Double.class,"FLOWMIN: min in-stream flow for irr diversions (m^3/s)"),
            DIVMAX(Double.class,"DIVMAX: max irrigation diversion from reach (+mm/-10^4m^3)"),
            FLOWFR(Double.class,"FLOWFR: : fraction of flow allowed to be pulled for irr"),
            COMMENT5(String.class,"Tile Drain Management Parameters"),
            DDRAIN(Double.class,"DDRAIN: depth to subsurface tile drain (mm)"),
            TDRAIN(Double.class,"TDRAIN: time to drain soil to field capacity (hr)"),
            GDRAIN(Double.class,"GDRAIN: drain tile lag time (hr)"),
            COMMENT6(String.class,"Management Operations:"),
            NROT(Integer.class,"NROT: number of years of rotation"),
            COMMENT7(String.class,"Operation Schedule:");
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
    
    private final EnumMap<Management.fields,Object> values;
    private final List<ManagementOperations> operationsSchedules;
    
    public Management() {
        this.values = new EnumMap(Management.fields.class);
        this.operationsSchedules = new ArrayList();
    }
    public Management(String filename) throws IOException {
        this.values = new EnumMap(Management.fields.class);
        this.operationsSchedules = new ArrayList();
        this.readSWATFileFormat(filename);
    }
    
    @Override
    public final Management set(Management.fields fieldName, Object value)
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
    @Override
    public final Management set(String fieldNameStr, Object value) {
        return set(Management.fields.valueOf(fieldNameStr), value);
    }
    
    @Override
    public Object get(Management.fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    @Override
    public Object get(String fieldNameStr) {
        return values.get(Management.fields.valueOf(fieldNameStr));
    }

    public Management setTITLE(String v) {
        return set(Management.fields.TITLE,v);
    }
    public Management setNMGT(int v) {
        return set(Management.fields.NMGT,v);
    }
    public Management setIGRO(int v) {
        return set(Management.fields.IGRO,v);
    }
    public Management setPLANT_ID(int v) {
        return set(Management.fields.PLANT_ID,v);
    }
    public Management setLAI_INIT(double v) {
        return set(Management.fields.LAI_INIT,v);
    }
    public Management setBIO_INIT(double v) {
        return set(Management.fields.BIO_INIT,v);
    }
    public Management setPHU_PLT(double v) {
        return set(Management.fields.PHU_PLT,v);
    }
    public Management setBIOMIX(double v) {
        return set(Management.fields.BIOMIX,v);
    }
    public Management setCN2(double v) {
        return set(Management.fields.CN2,v);
    }
    public Management setUSLE_P(double v) {
        return set(Management.fields.USLE_P,v);
    }
    public Management setBIO_MIN(double v) {
        return set(Management.fields.BIO_MIN,v);
    }
    public Management setFILTERW(double v) {
        return set(Management.fields.FILTERW,v);
    }
    public Management setIURBAN(int v) {
        return set(Management.fields.IURBAN,v);
    }
    public Management setURBLU(int v) {
        return set(Management.fields.URBLU,v);
    }
    public Management setIRRSC(int v) {
        return set(Management.fields.IRRSC,v);
    }
    public Management setIRRNO(int v) {
        return set(Management.fields.IRRNO,v);
    }
    public Management setFLOWMIN(double v) {
        return set(Management.fields.FLOWMIN,v);
    }
    public Management setDIVMAX(double v) {
        return set(Management.fields.DIVMAX,v);
    }
    public Management setFLOWFR(double v) {
        return set(Management.fields.FLOWFR,v);
    }
    public Management setDDRAIN(double v) {
        return set(Management.fields.DDRAIN,v);
    }
    public Management setTDRAIN(double v) {
        return set(Management.fields.TDRAIN,v);
    }
    public Management setGDRAIN(double v) {
        return set(Management.fields.GDRAIN,v);
    }
    public Management setNROT(int v) {
        return set(Management.fields.NROT,v);
    }
    
    public String getTITLE() {
        return (String) get(Management.fields.TITLE);
    }
    public int getNMGT() {
        return (int) get(Management.fields.NMGT);
    }
    public int getIGRO() {
        return (int) get(Management.fields.IGRO);
    }
    public int getPLANT_ID() {
        return (int) get(Management.fields.PLANT_ID);
    }
    public double getLAI_INIT() {
        return (double) get(Management.fields.LAI_INIT);
    }
    public double getBIO_INIT() {
        return (double) get(Management.fields.BIO_INIT);
    }
    public double getPHU_PLT() {
        return (double) get(Management.fields.PHU_PLT);
    }
    public double getBIOMIX() {
        return (double) get(Management.fields.BIOMIX);
    }
    public double getCN2() {
        return (double) get(Management.fields.CN2);
    }
    public double getUSLE_P() {
        return (double) get(Management.fields.USLE_P);
    }
    public double getBIO_MIN() {
        return (double) get(Management.fields.BIO_MIN);
    }
    public double getFILTERW() {
        return (double) get(Management.fields.FILTERW);
    }
    public int getIURBAN() {
        return (int) get(Management.fields.IURBAN);
    }
    public int getURBLU() {
        return (int) get(Management.fields.URBLU);
    }
    public int getIRRSC() {
        return (int) get(Management.fields.IRRSC);
    }
    public int getIRRNO() {
        return (int) get(Management.fields.IRRNO);
    }
    public double getFLOWMIN() {
        return (double) get(Management.fields.FLOWMIN);
    }
    public double getDIVMAX() {
        return (double) get(Management.fields.DIVMAX);
    }
    public double getFLOWFR() {
        return (double) get(Management.fields.FLOWFR);
    }
    public double getDDRAIN() {
        return (double) get(Management.fields.DDRAIN);
    }
    public double getTDRAIN() {
        return (double) get(Management.fields.TDRAIN);
    }
    public double getGDRAIN() {
        return (double) get(Management.fields.GDRAIN);
    }
    public int getNROT() {
        return (int) get(Management.fields.NROT);
    }
    
    public Management addOperationsSchedules(ManagementOperations e) {
        operationsSchedules.add(e);
        return this;
    }
    public Management addOperationsSchedules(int idx, ManagementOperations e)
            throws IllegalArgumentException {
        if (idx<operationsSchedules.size())
            operationsSchedules.add(idx,e);
        if (idx == operationsSchedules.size())
            operationsSchedules.add(e);
        if (idx>operationsSchedules.size())
            throw new IllegalArgumentException(String.format("There are only %d management operations currently existing",
                    operationsSchedules.size()));
        return this;
    }
    public int getNumberOfOperationsSchedules() {
        return operationsSchedules.size();
    }
    public ManagementOperations getOperationsSchedules(int idx) {
        return operationsSchedules.get(idx);
    }
    public List<ManagementOperations> getAllOperationsSchedules() {
        return operationsSchedules;
    }

    public boolean contains(Management.fields fieldName)
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr)
            throws IllegalArgumentException {
        return values.containsKey(Management.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (Management.fields fieldName: Management.fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<Management.fields> fieldNamesList) {
        for (Management.fields fieldName: Management.fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        List<Management.fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(Management.fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }
    
    @Override
    public Management readSWATFileFormat(String filename)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.UTF_8)) {
            String line;
            // Part I
            for (fields key: fields.values()) {
                if ((line = reader.readLine())== null) {
                    throw new IOException(
                            String.format("Could not read the value for %s", key.toString()));
                } else {
                    if (key.getFieldClassType().equals(String.class)) {
                        this.set(key, line);
                    }
                    if (key.getFieldClassType().equals(Integer.class)) {
                        this.set(key, Integer.parseInt(line.substring(0,18).trim()));
                    }
                    if (key.getFieldClassType().equals(Double.class)) {
                        this.set(key, Double.parseDouble(line.substring(0,18).trim()));
                    }
                }
            }
            
            // Part I
            while ( (line = reader.readLine()) != null ) {
                operationsSchedules.add(new ManagementOperations(line));
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
    public static Management newFromSWATFile(String filename)
            throws IOException {
        Management mgt = new Management();
        return mgt.readSWATFileFormat(filename);
    }
    public static List<Management> newFromSWATFiles(String[] filenames)
            throws IOException {
        ArrayList<Management> mgts = new ArrayList();
        for (String filename: filenames){
            mgts.add(newFromSWATFile(filename));
        }
        return mgts;
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
    public String toJSONString(){
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("{");
            // PART I
            for (fields key: fields.values()) {
                switch (key) {
                    case TITLE:
                        strBldr.append(String.format("TITLE: \"%s\"", this.get(key)));
                        break;
                    case COMMENT1:
                    case COMMENT2:
                    case COMMENT3:
                    case COMMENT4:
                    case COMMENT5:
                    case COMMENT6:
                    case COMMENT7:
                        strBldr.append(String.format(", %s: \"%s\"", key, this.get(key)));
                        break;
                    default:
                        strBldr.append(String.format(", %s: %s", key, this.get(key)));
                        break;
                }
            }
            // PART II
            strBldr.append(", OperationSchedules: [");
            for (int idx=0; idx<operationsSchedules.size();idx++) {
                if (idx==0) {
                    strBldr.append(operationsSchedules.get(idx).toJSONString());
                } else {
                    strBldr.append(",").append(operationsSchedules.get(idx).toJSONString());
                }
            }
            strBldr.append("]");
            return strBldr.append("}").toString();
        } else {
            return null;
        }
    }
    public String toSWATTXTFormat() {
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("");
            // PART I
            for (fields key: fields.values()) {
                switch (key) {
                    case TITLE:
                    case COMMENT1:
                    case COMMENT2:
                    case COMMENT3:
                    case COMMENT4:
                    case COMMENT5:
                    case COMMENT6:
                    case COMMENT7:
                        strBldr.append(String.format("%s\n", this.get(key)));
                        break;
                    default:
                        if (key.getFieldClassType().equals(Double.class)) {
                            strBldr.append(String.format(
                                    "%16.3f    | %s\n",
                                    this.get(key),
                                    key.getDescription()));
                        }
                        if (key.getFieldClassType().equals(Integer.class)) {
                            strBldr.append(String.format(
                                    "%16d    | %s\n",
                                    this.get(key),
                                    key.getDescription()));
                        }
                }
            }
            // PART II
            for (int idx=0; idx<operationsSchedules.size();idx++) {
                if (idx!=(operationsSchedules.size()-1)) {
                    strBldr.append(operationsSchedules.get(idx).toSWATTXTFormat()).append("\n");
                } else {
                    strBldr.append(operationsSchedules.get(idx).toSWATTXTFormat());
                }
            }
            return strBldr.toString();
        } else {
            return null;
        }
    }
}
