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
public final class HydroResponseUnit {
    public static enum fields {
        TITLE(String.class,""),
        HRU_FR(Double.class,"HRU_FR : Fraction of subbasin area contained in HRU"),
        SLSUBBSN(Double.class,"SLSUBBSN : Average slope length [m]"),
        HRU_SLP(Double.class,"HRU_SLP : Average slope stepness [m/m]"),
        OV_N(Double.class,"OV_N : Manning's \"n\" value for overland flow"),
        LAT_TTIME(Double.class,"LAT_TTIME : Lateral flow travel time [days]"),
        LAT_SED(Double.class,"LAT_SED : Sediment concentration in lateral flow and groundwater flow [mg/l]"),
        SLSOIL(Double.class,"SLSOIL : Slope length for lateral subsurface flow [m]"),
        CANMX(Double.class,"CANMX : Maximum canopy storage [mm]"),
        ESCO(Double.class,"ESCO : Soil evaporation compensation factor"),
        EPCO(Double.class,"EPCO : Plant uptake compensation factor"),
        RSDIN(Double.class,"RSDIN : Initial residue cover [kg/ha]"),
        ERORGN(Double.class,"ERORGN : Organic N enrichment ratio"),
        ERORGP(Double.class,"ERORGP : Organic P enrichment ratio"),
        POT_FR(Double.class,"POT_FR : Fraction of HRU are that drains into pothole"),
        FLD_FR(Double.class,"FLD_FR : Fraction of HRU that drains into floodplain"),
        RIP_FR(Double.class,"RIP_FR : Fraction of HRU that drains into riparian zone"),
        COMMENT(String.class,""),
        POT_TILE(Double.class,"POT_TILE : Average daily outflow to main channel from tile flow (depth [mm] over entire HRU)"),
        POT_VOLX(Double.class,"POT_VOLX : Maximum volume of water stored in the pothole (depth [mm] over entire HRU)"),
        POT_VOL(Double.class,"POT_VOL : Initial volume of water stored in the pothole (depth [mm] over entire HRU)"),
        POT_NSED(Double.class,"POT_NSED : Normal sediment concentration in pothole [mg/l]"),
        POT_NO3L(Double.class,"POT_NO3L : Nitrate decay rate in pothole [1/day]"),
        DEP_IMP(Double.class,"DEP_IMP : Depth to impervious layer in soil profile [mm]"),
        SKIP0(String.class,"NOT REAL Variable. Some how this line is skipped in the .hru file"),
        SKIP1(String.class,"NOT REAL Variable. Some how this line is skipped in the .hru file"),
        SKIP2(String.class,"NOT REAL Variable. Some how this line is skipped in the .hru file"),
        EVPOT(Double.class,"EVPOT: Pothole evaporation coefficient"),
        DIS_STREAM(Double.class,"DIS_STREAM: Average distance to stream [m]"),
        CF(Double.class,"CF: Decomposition response to soil temperature and moisture"),
        CFH(Double.class,"CFH: Maximum humification rate"),
        CFDEC(Double.class,"CFDEC: Undistrurbed soil turnover rate under optimum soil water and temperature"),
        SED_CON(Double.class,"SED_CON: Sediment concentration in runoff, after urban BMP is applied"),
        ORGN_CON(Double.class,"ORGN_CON: Organic nitrogen concentration in runoff, after urban BMP is applied"),
        ORGP_CON(Double.class,"ORGP_CON: Organic phosphorus concentration in runoff, after urban BMP is applied"),
        SOLN_CON(Double.class,"SOLN_CON: Soluble nitrogen concentration un runoff, after urban BMP is applied"),
        SOLP_CON(Double.class,"SOLP_CON: Soluble phosphorus concentration in runoff, after urban BMP is applied"),
        POT_SOLP(Double.class,"POT_SOLP: Phosphorus decay rate in pothole [1/day]"),
        POT_K(Double.class,"POT_K: Saturated conductivity of soil surface under pothole [mm/h]");

        
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
    private final EnumMap<HydroResponseUnit.fields,Object> values;
    
    public HydroResponseUnit() {
        this.values = new EnumMap(HydroResponseUnit.fields.class);
    }
    public HydroResponseUnit(String filename) throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    
    public final HydroResponseUnit set(fields fieldName, Object value) throws IllegalArgumentException {
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
    public final HydroResponseUnit set(String fieldNameStr, Object value) {
        return set(HydroResponseUnit.fields.valueOf(fieldNameStr), value);
    }
    
    public Object get(HydroResponseUnit.fields fieldName) throws NullPointerException {
        return values.get(fieldName);
    }
    public Object get(String fieldNameStr) {
        return values.get(HydroResponseUnit.fields.valueOf(fieldNameStr));
    }
    
    public final String getTITLE() {
            return (String) get(HydroResponseUnit.fields.TITLE);
    }
    public final double getHRU_FR() {
            return (double) get(HydroResponseUnit.fields.HRU_FR);
    }
    public final double getSLSUBBSN() {
            return (double) get(HydroResponseUnit.fields.SLSUBBSN);
    }
    public final double getHRU_SLP() {
            return (double) get(HydroResponseUnit.fields.HRU_SLP);
    }
    public final double getOV_N() {
            return (double) get(HydroResponseUnit.fields.OV_N);
    }
    public final double getLAT_TTIME() {
            return (double) get(HydroResponseUnit.fields.LAT_TTIME);
    }
    public final double getLAT_SED() {
            return (double) get(HydroResponseUnit.fields.LAT_SED);
    }
    public final double getSLSOIL() {
            return (double) get(HydroResponseUnit.fields.SLSOIL);
    }
    public final double getCANMX() {
            return (double) get(HydroResponseUnit.fields.CANMX);
    }
    public final double getESCO() {
            return (double) get(HydroResponseUnit.fields.ESCO);
    }
    public final double getEPCO() {
            return (double) get(HydroResponseUnit.fields.EPCO);
    }
    public final double getRSDIN() {
            return (double) get(HydroResponseUnit.fields.RSDIN);
    }
    public final double getERORGN() {
            return (double) get(HydroResponseUnit.fields.ERORGN);
    }
    public final double getERORGP() {
            return (double) get(HydroResponseUnit.fields.ERORGP);
    }
    public final double getPOT_FR() {
            return (double) get(HydroResponseUnit.fields.POT_FR);
    }
    public final double getFLD_FR() {
            return (double) get(HydroResponseUnit.fields.FLD_FR);
    }
    public final double getRIP_FR() {
            return (double) get(HydroResponseUnit.fields.RIP_FR);
    }
    public final String getCOMMENT() {
            return (String) get(HydroResponseUnit.fields.COMMENT);
    }
    public final double getPOT_TILE() {
            return (double) get(HydroResponseUnit.fields.POT_TILE);
    }
    public final double getPOT_VOLX() {
            return (double) get(HydroResponseUnit.fields.POT_VOLX);
    }
    public final double getPOT_VOL() {
            return (double) get(HydroResponseUnit.fields.POT_VOL);
    }
    public final double getPOT_NSED() {
            return (double) get(HydroResponseUnit.fields.POT_NSED);
    }
    public final double getPOT_NO3L() {
            return (double) get(HydroResponseUnit.fields.POT_NO3L);
    }
    public final double getDEP_IMP() {
            return (double) get(HydroResponseUnit.fields.DEP_IMP);
    }
    public final String getSKIP0() {
            return (String) get(HydroResponseUnit.fields.SKIP0);
    }
    public final String getSKIP1() {
            return (String) get(HydroResponseUnit.fields.SKIP1);
    }
    public final String getSKIP2() {
            return (String) get(HydroResponseUnit.fields.SKIP2);
    }
    public final double getEVPOT() {
            return (double) get(HydroResponseUnit.fields.EVPOT);
    }
    public final double getDIS_STREAM() {
            return (double) get(HydroResponseUnit.fields.DIS_STREAM);
    }
    public final double getCF() {
            return (double) get(HydroResponseUnit.fields.CF);
    }
    public final double getCFH() {
            return (double) get(HydroResponseUnit.fields.CFH);
    }
    public final double getCFDEC() {
            return (double) get(HydroResponseUnit.fields.CFDEC);
    }
    public final double getSED_CON() {
            return (double) get(HydroResponseUnit.fields.SED_CON);
    }
    public final double getORGN_CON() {
            return (double) get(HydroResponseUnit.fields.ORGN_CON);
    }
    public final double getORGP_CON() {
            return (double) get(HydroResponseUnit.fields.ORGP_CON);
    }
    public final double getSOLN_CON() {
            return (double) get(HydroResponseUnit.fields.SOLN_CON);
    }
    public final double getSOLP_CON() {
            return (double) get(HydroResponseUnit.fields.SOLP_CON);
    }
    public final double getPOT_SOLP() {
            return (double) get(HydroResponseUnit.fields.POT_SOLP);
    }
    public final double getPOT_K() {
            return (double) get(HydroResponseUnit.fields.POT_K);
    }

    public final HydroResponseUnit setTITLE(String v) {
            return set(HydroResponseUnit.fields.TITLE,v);
    }
    public final HydroResponseUnit setHRU_FR(double v) {
            return set(HydroResponseUnit.fields.HRU_FR,v);
    }
    public final HydroResponseUnit setSLSUBBSN(double v) {
            return set(HydroResponseUnit.fields.SLSUBBSN,v);
    }
    public final HydroResponseUnit setHRU_SLP(double v) {
            return set(HydroResponseUnit.fields.HRU_SLP,v);
    }
    public final HydroResponseUnit setOV_N(double v) {
            return set(HydroResponseUnit.fields.OV_N,v);
    }
    public final HydroResponseUnit setLAT_TTIME(double v) {
            return set(HydroResponseUnit.fields.LAT_TTIME,v);
    }
    public final HydroResponseUnit setLAT_SED(double v) {
            return set(HydroResponseUnit.fields.LAT_SED,v);
    }
    public final HydroResponseUnit setSLSOIL(double v) {
            return set(HydroResponseUnit.fields.SLSOIL,v);
    }
    public final HydroResponseUnit setCANMX(double v) {
            return set(HydroResponseUnit.fields.CANMX,v);
    }
    public final HydroResponseUnit setESCO(double v) {
            return set(HydroResponseUnit.fields.ESCO,v);
    }
    public final HydroResponseUnit setEPCO(double v) {
            return set(HydroResponseUnit.fields.EPCO,v);
    }
    public final HydroResponseUnit setRSDIN(double v) {
            return set(HydroResponseUnit.fields.RSDIN,v);
    }
    public final HydroResponseUnit setERORGN(double v) {
            return set(HydroResponseUnit.fields.ERORGN,v);
    }
    public final HydroResponseUnit setERORGP(double v) {
            return set(HydroResponseUnit.fields.ERORGP,v);
    }
    public final HydroResponseUnit setPOT_FR(double v) {
            return set(HydroResponseUnit.fields.POT_FR,v);
    }
    public final HydroResponseUnit setFLD_FR(double v) {
            return set(HydroResponseUnit.fields.FLD_FR,v);
    }
    public final HydroResponseUnit setRIP_FR(double v) {
            return set(HydroResponseUnit.fields.RIP_FR,v);
    }
    public final HydroResponseUnit setCOMMENT(String v) {
            return set(HydroResponseUnit.fields.COMMENT,v);
    }
    public final HydroResponseUnit setPOT_TILE(double v) {
            return set(HydroResponseUnit.fields.POT_TILE,v);
    }
    public final HydroResponseUnit setPOT_VOLX(double v) {
            return set(HydroResponseUnit.fields.POT_VOLX,v);
    }
    public final HydroResponseUnit setPOT_VOL(double v) {
            return set(HydroResponseUnit.fields.POT_VOL,v);
    }
    public final HydroResponseUnit setPOT_NSED(double v) {
            return set(HydroResponseUnit.fields.POT_NSED,v);
    }
    public final HydroResponseUnit setPOT_NO3L(double v) {
            return set(HydroResponseUnit.fields.POT_NO3L,v);
    }
    public final HydroResponseUnit setDEP_IMP(double v) {
            return set(HydroResponseUnit.fields.DEP_IMP,v);
    }
    public final HydroResponseUnit setSKIP0(String v) {
            return set(HydroResponseUnit.fields.SKIP0,v);
    }
    public final HydroResponseUnit setSKIP1(String v) {
            return set(HydroResponseUnit.fields.SKIP1,v);
    }
    public final HydroResponseUnit setSKIP2(String v) {
            return set(HydroResponseUnit.fields.SKIP2,v);
    }
    public final HydroResponseUnit setEVPOT(double v) {
            return set(HydroResponseUnit.fields.EVPOT,v);
    }
    public final HydroResponseUnit setDIS_STREAM(double v) {
            return set(HydroResponseUnit.fields.DIS_STREAM,v);
    }
    public final HydroResponseUnit setCF(double v) {
            return set(HydroResponseUnit.fields.CF,v);
    }
    public final HydroResponseUnit setCFH(double v) {
            return set(HydroResponseUnit.fields.CFH,v);
    }
    public final HydroResponseUnit setCFDEC(double v) {
            return set(HydroResponseUnit.fields.CFDEC,v);
    }
    public final HydroResponseUnit setSED_CON(double v) {
            return set(HydroResponseUnit.fields.SED_CON,v);
    }
    public final HydroResponseUnit setORGN_CON(double v) {
            return set(HydroResponseUnit.fields.ORGN_CON,v);
    }
    public final HydroResponseUnit setORGP_CON(double v) {
            return set(HydroResponseUnit.fields.ORGP_CON,v);
    }
    public final HydroResponseUnit setSOLN_CON(double v) {
            return set(HydroResponseUnit.fields.SOLN_CON,v);
    }
    public final HydroResponseUnit setSOLP_CON(double v) {
            return set(HydroResponseUnit.fields.SOLP_CON,v);
    }
    public final HydroResponseUnit setPOT_SOLP(double v) {
            return set(HydroResponseUnit.fields.POT_SOLP,v);
    }
    public final HydroResponseUnit setPOT_K(double v) {
            return set(HydroResponseUnit.fields.POT_K,v);
    }

    public boolean contains(HydroResponseUnit.fields fieldName) throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr) throws IllegalArgumentException {
        return values.containsKey(HydroResponseUnit.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (HydroResponseUnit.fields fieldName: HydroResponseUnit.fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<fields> fieldNamesList) {
        for (HydroResponseUnit.fields fieldName: HydroResponseUnit.fields.values()) {
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

    public HydroResponseUnit readSWATFileFormat(String filename) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.UTF_8)) {
            String line;
            for (HydroResponseUnit.fields key: HydroResponseUnit.fields.values()) {
                line = reader.readLine();
                if (line == null) {
                    if (!key.equals(fields.POT_SOLP) && !key.equals(fields.POT_K)) {
                        throw new IOException(
                                String.format("Could not read the value for %s", key.toString()));
                    } else {
                        break;
                    }
                }
                if (line.trim().equals("")) {
                    if (key.equals(fields.SKIP0) ||
                        key.equals(fields.SKIP1) ||
                        key.equals(fields.SKIP2)) {
                        this.set(key,"");
                    } else {
                        break;
                    }
                } else {
                    if ( key.getFieldClassType().equals(String.class) ) {
                        this.set(key, line);
                    } else {
                        this.set(key, Double.parseDouble(line.substring(0,20)));
                    }
                }
            }
        }
        return this;
    }
    public void writeSWATFileFormat(String filename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),StandardCharsets.UTF_8)) {
            writer.write(this.toSWATTXTFormat());
        }
    }
    public static HydroResponseUnit newFromSWATFile(String filename) throws IOException {
        HydroResponseUnit hru = new HydroResponseUnit();
        return hru.readSWATFileFormat(filename);
    }
    public static ArrayList<HydroResponseUnit> newFromSWATFiles(String[] filenames) throws IOException {
        ArrayList<HydroResponseUnit> hrus = new ArrayList();
        for (String filename: filenames){
            hrus.add(newFromSWATFile(filename));
        }
        return hrus;
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
        if (this.containsAllFieldsIgnoring(new String[] {"POT_SOLP","POT_K"})) {
            String strFMT = "{TITLE: %s, HRU_FR: %f, SLSUBBSN: %f, HRU_SLP: %f, OV_N: %f, LAT_TTIME: %f, LAT_SED: %f, SLSOIL: %f, CANMX: %f, ESCO: %f, EPCO: %f, RSDIN: %f, ERORGN: %f, ERORGP: %f, POT_FR: %f, FLD_FR: %f, RIP_FR: %f, COMMENT: %s, POT_TILE: %f, POT_VOLX: %f, POT_VOL: %f, POT_NSED: %f, POT_NO3L: %f, DEP_IMP: %f, SKIP0: %s, SKIP1: %s, SKIP2: %s, EVPOT: %f, DIS_STREAM: %f, CF: %f, CFH: %f, CFDEC: %f, SED_CON: %f, ORGN_CON: %f, ORGP_CON: %f, SOLN_CON: %f, SOLP_CON: %f";
            StringBuilder strBldr = new StringBuilder(String.format(
                    strFMT,
                    this.getTITLE(), this.getHRU_FR(), this.getSLSUBBSN(), this.getHRU_SLP(), this.getOV_N(),
                    this.getLAT_TTIME(), this.getLAT_SED(), this.getSLSOIL(), this.getCANMX(), this.getESCO(),
                    this.getEPCO(), this.getRSDIN(), this.getERORGN(), this.getERORGP(), this.getPOT_FR(),
                    this.getFLD_FR(), this.getRIP_FR(), this.getCOMMENT(), this.getPOT_TILE(), this.getPOT_VOLX(),
                    this.getPOT_VOL(), this.getPOT_NSED(), this.getPOT_NO3L(), this.getDEP_IMP(), this.getSKIP0(),
                    this.getSKIP1(), this.getSKIP2(), this.getEVPOT(), this.getDIS_STREAM(), this.getCF(),
                    this.getCFH(), this.getCFDEC(), this.getSED_CON(), this.getORGN_CON(), this.getORGP_CON(),
                    this.getSOLN_CON(), this.getSOLP_CON()));

            if (this.contains(fields.POT_SOLP))
                strBldr.append(String.format(",POT_SOLP: %f", this.getPOT_SOLP()));

            if (this.contains(fields.POT_K))
                strBldr.append(String.format(",POT_SOLP: %f", this.getPOT_K()));

            return strBldr.append("}").toString();
        } else {
            return null;
        }
    }
    public String toSWATTXTFormat(){
        if (this.containsAllFieldsIgnoring(new String[] {"POT_SOLP","POT_K"})) {
            StringBuilder strBldr = new StringBuilder();
            for (fields key: fields.values()) {
                if ( !((key.equals(fields.POT_SOLP) || key.equals(fields.POT_K)) && !this.contains(key)))  {
                    if (key.getFieldClassType().equals(String.class)) {
                        strBldr.append(this.values.get(key)).append("\n");
                    } else {
                        strBldr.append(String.format("%16.4f    | %s\n",
                                                     this.values.get(key),
                                                     key.getDescription()));
                    }
                }
            }
            return strBldr.toString();
        } else {
            return null;
        }
    }
    
}
