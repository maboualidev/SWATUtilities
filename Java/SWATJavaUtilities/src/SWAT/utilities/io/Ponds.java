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
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author mabouali
 */
public final class Ponds
        implements SWATFormatInput<Ponds,Ponds.fields> {
    public static enum fields {
        TITLE     (String.class,"","%s"),
        COMMENT1  (String.class,"","%.3f"),
        PND_FR    (Double.class,"PND_FR : Fraction of subbasin area that drains into ponds. The value for PND_FR should be between 0.0 and 1.0. If PND_FR = 1.0, the pond is at the outlet of the subbasin on the main channel","%.3f"),
        PND_PSA   (Double.class,"PND_PSA: Surface area of ponds when filled to principal spillway [ha]","%.3f"),
        PND_PVOL  (Double.class,"PND_PVOL: Volume of water stored in ponds when filled to the principal spillway [104 m3]","%.3f"),
        PND_ESA   (Double.class,"PND_ESA: Surface area of ponds when filled to emergency spillway [ha]","%.3f"),
        PND_EVOL  (Double.class,"PND_EVOL: Volume of water stored in ponds when filled to the emergency spillway [104 m3]","%.3f"),
        PND_VOL   (Double.class,"PND_VOL: Initial volume of water in ponds [104 m3]","%.3f"),
        PND_SED   (Double.class,"PND_SED: Initial sediment concentration in pond water [mg/l]","%.3f"),
        PND_NSED  (Double.class,"PND_NSED: Normal sediment concentration in pond water [mg/l]","%.3f"),
        PND_K     (Double.class,"PND_K: Hydraulic conductivity through bottom of ponds [mm/hr]","%.3f"),
        IFLOD1    (Integer.class,"IFLOD1: Beginning month of non-flood season","%d"),
        IFLOD2    (Integer.class,"IFLOD2: Ending month of non-flood season","%d"),
        NDTARG    (Double.class,"NDTARG: Number of days needed to reach target storage from current pond storage","%.3f"),
        PSETLP1   (Double.class,"PSETLP1: Phosphorus settling rate in pond for months IPND1 through IPND2 [m/year]","%.3f"),
        PSETLP2   (Double.class,"PSETLP2: Phosphorus settling rate in pond for months other than IPND1-IPND2 [m/year]","%.3f"),
        NSETLP1   (Double.class,"NSETLP1: Initial dissolved oxygen concentration in the reach [mg O2/l]","%.3f"),
        NSETLP2   (Double.class,"NSETLP2: Initial dissolved oxygen concentration in the reach [mg O2/l]","%.3f"),
        CHLAP     (Double.class,"CHLAP: Chlorophyll a production coefficient for ponds [ ]","%.3f"),
        SECCIP    (Double.class,"SECCIP: Water clarity coefficient for ponds [m]","%.3f"),
        PND_NO3   (Double.class,"PND_NO3: Initial concentration of NO3-N in pond [mg N/l]","%.3f"),
        PND_SOLP  (Double.class,"PND_SOLP: Initial concentration of soluble P in pond [mg P/L]","%.3f"),
        PND_ORGN  (Double.class,"PND_ORGN: Initial concentration of organic N in pond [mg N/l]","%.3f"),
        PND_ORGP  (Double.class,"PND_ORGP: Initial concentration of organic P in pond [mg P/l]","%.3f"),
        PND_D50   (Double.class,"PND_D50: Median particle diameter of sediment [um]","%.3f"),
        IPND1     (Integer.class,"IPND1: Beginning month of mid-year nutrient settling \"season\"","%d"),
        IPND2     (Integer.class,"IPND2: Ending month of mid-year nutrient settling \"season\"","%d"),
        COMMENT2  (String.class,"","%s"),
        WET_FR    (Double.class,"WET_FR : Fraction of subbasin area that drains into wetlands","%.3f"),
        WET_NSA   (Double.class,"WET_NSA: Surface area of wetlands at normal water level [ha]","%.3f"),
        WET_NVOL  (Double.class,"WET_NVOL: Volume of water stored in wetlands when filled to normal water level [104 m3] ","%.3f"),
        WET_MXSA  (Double.class,"WET_MXSA: Surface area of wetlands at maximum water level [ha]","%.3f"),
        WET_MXVOL (Double.class,"WET_MXVOL: Volume of water stored in wetlands when filled to maximum water level [104 m3]","%.3f"),
        WET_VOL   (Double.class,"WET_VOL: Initial volume of water in wetlands [104 m3]","%.3f"),
        WET_SED   (Double.class,"WET_SED: Initial sediment concentration in wetland water [mg/l]","%.3f"),
        WET_NSED  (Double.class,"WET_NSED: Normal sediment concentration in wetland water [mg/l]","%.3f"),
        WET_K     (Double.class,"WET_K: Hydraulic conductivity of bottom of wetlands [mm/hr]","%.3f"),
        PSETLW1   (Double.class,"PSETLW1: Phosphorus settling rate in wetland for months IPND1 through IPND2 [m/year]","%.3f"),
        PSETLW2   (Double.class,"PSETLW2: Phosphorus settling rate in wetlands for months other than IPND1-IPND2 [m/year]","%.3f"),
        NSETLW1   (Double.class,"NSETLW1: Nitrogen settling rate in wetlands for months IPND1 through IPND2 [m/year]","%.3f"),
        NSETLW2   (Double.class,"NSETLW2: Nitrogen settling rate in wetlands for months other than IPND1-IPND2 [m/year]","%.3f"),
        CHLAW     (Double.class,"CHLAW: Chlorophyll a production coefficient for wetlands [ ]","%.3f"),
        SECCIW    (Double.class,"SECCIW: Water clarity coefficient for wetlands [m]","%.3f"),
        WET_NO3   (Double.class,"WET_NO3: Initial concentration of NO3-N in wetland [mg N/l]","%.3f"),
        WET_SOLP  (Double.class,"WET_SOLP: Initial concentration of soluble P in wetland [mg P/l]","%.3f"),
        WET_ORGN  (Double.class,"WET_ORGN: Initial concentration of organic N in wetland [mg N/l]","%.3f"),
        WET_ORGP  (Double.class,"WET_ORGP: Initial concentration of organic P in wetland [mg P/l]","%.3f"),
        PNDEVCOEFF(Double.class,"PNDEVCOEFF: Actual pond evaporation is equal to the potential evaporation times the pond evaporation coefficient","%.3f"),
        WETEVCOEFF(Double.class,"WETEVCOEFF: Actual wetland evaporation is equal to the potential evaporation times the wetland evaporation coefficient","%.3f"),
        COMMENT3  (String.class,"","%s"),
        STRFIELD1 (String.class,"","%s"),
        COMMENT4  (String.class,"","%s"),
        STRFIELD2 (String.class,"","%s"),
        COMMENT5  (String.class,"","%s"),
        STRFIELD3 (String.class,"","%s"),
        COMMENT6  (String.class,"","%s"),
        STRFIELD4 (String.class,"","%s");
        
        private final Class classType;
        private final String description;
        private final String strFMT;
        
        fields(Class classType, String description, String strFMT) {
            this.classType = classType;
            this.description = description;
            this.strFMT = strFMT;
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
        public String getStrFMT() {
            return this.strFMT;
        }
    }
    @Override
    public Class getFieldClassType(String fieldNameStr) {
        return fields.valueOf(fieldNameStr).getFieldClassType();
    }
    
    private final EnumMap<fields,Object> values;
    
    @SuppressWarnings("unchecked")
    public Ponds() {
        this.values = new EnumMap(fields.class);
    }
    @SuppressWarnings("unchecked")
    public Ponds(String filename)
            throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    
    @Override
    public final Ponds set(fields fieldName, Object value)
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
    public final Ponds set(String fieldNameStr, Object value) {
        return set(fields.valueOf(fieldNameStr), value);
    }
    @Override
    public final Ponds set(String fieldNameStr, String value) {
        fields fieldName = fields.valueOf(fieldNameStr);
        return set(fieldName,Convertor.convertStringValueTo(value, fieldName.getFieldClassName()));
    }

    @Override
    public Object get(fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    @Override
    public Object get(String fieldNameStr) {
        return values.get(fields.valueOf(fieldNameStr));
    }
    
    public Ponds setTITLE(String v) {
	return this.set(fields.TITLE,v);
    }
    public Ponds setCOMMENT1(String v) {
        return this.set(fields.COMMENT1,v);
    }
    public Ponds setPND_FR(double v) {
        return this.set(fields.PND_FR,v);
    }
    public Ponds setPND_PSA(double v) {
        return this.set(fields.PND_PSA,v);
    }
    public Ponds setPND_PVOL(double v) {
        return this.set(fields.PND_PVOL,v);
    }
    public Ponds setPND_ESA(double v) {
        return this.set(fields.PND_ESA,v);
    }
    public Ponds setPND_EVOL(double v) {
        return this.set(fields.PND_EVOL,v);
    }
    public Ponds setPND_VOL(double v) {
        return this.set(fields.PND_VOL,v);
    }
    public Ponds setPND_SED(double v) {
        return this.set(fields.PND_SED,v);
    }
    public Ponds setPND_NSED(double v) {
        return this.set(fields.PND_NSED,v);
    }
    public Ponds setPND_K(double v) {
        return this.set(fields.PND_K,v);
    }
    public Ponds setIFLOD1(int v) {
        return this.set(fields.IFLOD1,v);
    }
    public Ponds setIFLOD2(int v) {
        return this.set(fields.IFLOD2,v);
    }
    public Ponds setNDTARG(double v) {
        return this.set(fields.NDTARG,v);
    }
    public Ponds setPSETLP1(double v) {
        return this.set(fields.PSETLP1,v);
    }
    public Ponds setPSETLP2(double v) {
        return this.set(fields.PSETLP2,v);
    }
    public Ponds setNSETLP1(double v) {
        return this.set(fields.NSETLP1,v);
    }
    public Ponds setNSETLP2(double v) {
        return this.set(fields.NSETLP2,v);
    }
    public Ponds setCHLAP(double v) {
        return this.set(fields.CHLAP,v);
    }
    public Ponds setSECCIP(double v) {
        return this.set(fields.SECCIP,v);
    }
    public Ponds setPND_NO3(double v) {
        return this.set(fields.PND_NO3,v);
    }
    public Ponds setPND_SOLP(double v) {
        return this.set(fields.PND_SOLP,v);
    }
    public Ponds setPND_ORGN(double v) {
        return this.set(fields.PND_ORGN,v);
    }
    public Ponds setPND_ORGP(double v) {
        return this.set(fields.PND_ORGP,v);
    }
    public Ponds setPND_D50(double v) {
        return this.set(fields.PND_D50,v);
    }
    public Ponds setIPND1(int v) {
        return this.set(fields.IPND1,v);
    }
    public Ponds setIPND2(int v) {
        return this.set(fields.IPND2,v);
    }
    public Ponds setCOMMENT2(String v) {
        return this.set(fields.COMMENT2,v);
    }
    public Ponds setWET_FR(double v) {
        return this.set(fields.WET_FR,v);
    }
    public Ponds setWET_NSA(double v) {
        return this.set(fields.WET_NSA,v);
    }
    public Ponds setWET_NVOL(double v) {
        return this.set(fields.WET_NVOL,v);
    }
    public Ponds setWET_MXSA(double v) {
        return this.set(fields.WET_MXSA,v);
    }
    public Ponds setWET_MXVOL(double v) {
        return this.set(fields.WET_MXVOL,v);
    }
    public Ponds setWET_VOL(double v) {
        return this.set(fields.WET_VOL,v);
    }
    public Ponds setWET_SED(double v) {
        return this.set(fields.WET_SED,v);
    }
    public Ponds setWET_NSED(double v) {
        return this.set(fields.WET_NSED,v);
    }
    public Ponds setWET_K(double v) {
        return this.set(fields.WET_K,v);
    }
    public Ponds setPSETLW1(double v) {
        return this.set(fields.PSETLW1,v);
    }
    public Ponds setPSETLW2(double v) {
        return this.set(fields.PSETLW2,v);
    }
    public Ponds setNSETLW1(double v) {
        return this.set(fields.NSETLW1,v);
    }
    public Ponds setNSETLW2(double v) {
        return this.set(fields.NSETLW2,v);
    }
    public Ponds setCHLAW(double v) {
        return this.set(fields.CHLAW,v);
    }
    public Ponds setSECCIW(double v) {
        return this.set(fields.SECCIW,v);
    }
    public Ponds setWET_NO3(double v) {
        return this.set(fields.WET_NO3,v);
    }
    public Ponds setWET_SOLP(double v) {
        return this.set(fields.WET_SOLP,v);
    }
    public Ponds setWET_ORGN(double v) {
        return this.set(fields.WET_ORGN,v);
    }
    public Ponds setWET_ORGP(double v) {
        return this.set(fields.WET_ORGP,v);
    }
    public Ponds setPNDEVCOEFF(double v) {
        return this.set(fields.PNDEVCOEFF,v);
    }
    public Ponds setWETEVCOEFF(double v) {
        return this.set(fields.WETEVCOEFF,v);
    }
    public Ponds setCOMMENT3(String v) {
        return this.set(fields.COMMENT3,v);
    }
    public Ponds setSTRFIELD1(String v) {
        return this.set(fields.STRFIELD1,v);
    }
    public Ponds setCOMMENT4(String v) {
        return this.set(fields.COMMENT4,v);
    }
    public Ponds setSTRFIELD2(String v) {
        return this.set(fields.STRFIELD2,v);
    }
    public Ponds setCOMMENT5(String v) {
        return this.set(fields.COMMENT5,v);
    }
    public Ponds setSTRFIELD3(String v) {
        return this.set(fields.STRFIELD3,v);
    }
    public Ponds setCOMMENT6(String v) {
        return this.set(fields.COMMENT6,v);
    }
    public Ponds setSTRFIELD4(String v) {
        return this.set(fields.STRFIELD4,v);
    }
    
    public String getTITLE() {
	return (String) this.get(fields.TITLE);
    }
    public String getCOMMENT1() {
        return (String) this.get(fields.COMMENT1);
    }
    public double getPND_FR() {
        return (double) this.get(fields.PND_FR);
    }
    public double getPND_PSA() {
        return (double) this.get(fields.PND_PSA);
    }
    public double getPND_PVOL() {
        return (double) this.get(fields.PND_PVOL);
    }
    public double getPND_ESA() {
        return (double) this.get(fields.PND_ESA);
    }
    public double getPND_EVOL() {
        return (double) this.get(fields.PND_EVOL);
    }
    public double getPND_VOL() {
        return (double) this.get(fields.PND_VOL);
    }
    public double getPND_SED() {
        return (double) this.get(fields.PND_SED);
    }
    public double getPND_NSED() {
        return (double) this.get(fields.PND_NSED);
    }
    public double getPND_K() {
        return (double) this.get(fields.PND_K);
    }
    public int getIFLOD1() {
        return (int) this.get(fields.IFLOD1);
    }
    public int getIFLOD2() {
        return (int) this.get(fields.IFLOD2);
    }
    public double getNDTARG() {
        return (double) this.get(fields.NDTARG);
    }
    public double getPSETLP1() {
        return (double) this.get(fields.PSETLP1);
    }
    public double getPSETLP2() {
        return (double) this.get(fields.PSETLP2);
    }
    public double getNSETLP1() {
        return (double) this.get(fields.NSETLP1);
    }
    public double getNSETLP2() {
        return (double) this.get(fields.NSETLP2);
    }
    public double getCHLAP() {
        return (double) this.get(fields.CHLAP);
    }
    public double getSECCIP() {
        return (double) this.get(fields.SECCIP);
    }
    public double getPND_NO3() {
        return (double) this.get(fields.PND_NO3);
    }
    public double getPND_SOLP() {
        return (double) this.get(fields.PND_SOLP);
    }
    public double getPND_ORGN() {
        return (double) this.get(fields.PND_ORGN);
    }
    public double getPND_ORGP() {
        return (double) this.get(fields.PND_ORGP);
    }
    public double getPND_D50() {
        return (double) this.get(fields.PND_D50);
    }
    public int getIPND1() {
        return (int) this.get(fields.IPND1);
    }
    public int getIPND2() {
        return (int) this.get(fields.IPND2);
    }
    public String getCOMMENT2() {
        return (String) this.get(fields.COMMENT2);
    }
    public double getWET_FR() {
        return (double) this.get(fields.WET_FR);
    }
    public double getWET_NSA() {
        return (double) this.get(fields.WET_NSA);
    }
    public double getWET_NVOL() {
        return (double) this.get(fields.WET_NVOL);
    }
    public double getWET_MXSA() {
        return (double) this.get(fields.WET_MXSA);
    }
    public double getWET_MXVOL() {
        return (double) this.get(fields.WET_MXVOL);
    }
    public double getWET_VOL() {
        return (double) this.get(fields.WET_VOL);
    }
    public double getWET_SED() {
        return (double) this.get(fields.WET_SED);
    }
    public double getWET_NSED() {
        return (double) this.get(fields.WET_NSED);
    }
    public double getWET_K() {
        return (double) this.get(fields.WET_K);
    }
    public double getPSETLW1() {
        return (double) this.get(fields.PSETLW1);
    }
    public double getPSETLW2() {
        return (double) this.get(fields.PSETLW2);
    }
    public double getNSETLW1() {
        return (double) this.get(fields.NSETLW1);
    }
    public double getNSETLW2() {
        return (double) this.get(fields.NSETLW2);
    }
    public double getCHLAW() {
        return (double) this.get(fields.CHLAW);
    }
    public double getSECCIW() {
        return (double) this.get(fields.SECCIW);
    }
    public double getWET_NO3() {
        return (double) this.get(fields.WET_NO3);
    }
    public double getWET_SOLP() {
        return (double) this.get(fields.WET_SOLP);
    }
    public double getWET_ORGN() {
        return (double) this.get(fields.WET_ORGN);
    }
    public double getWET_ORGP() {
        return (double) this.get(fields.WET_ORGP);
    }
    public double getPNDEVCOEFF() {
        return (double) this.get(fields.PNDEVCOEFF);
    }
    public double getWETEVCOEFF() {
        return (double) this.get(fields.WETEVCOEFF);
    }
    public String getCOMMENT3() {
        return (String) this.get(fields.COMMENT3);
    }
    public String getSTRFIELD1() {
        return (String) this.get(fields.STRFIELD1);
    }
    public String getCOMMENT4() {
        return (String) this.get(fields.COMMENT4);
    }
    public String getSTRFIELD2() {
        return (String) this.get(fields.STRFIELD2);
    }
    public String getCOMMENT5() {
        return (String) this.get(fields.COMMENT5);
    }
    public String getSTRFIELD3() {
        return (String) this.get(fields.STRFIELD3);
    }
    public String getCOMMENT6() {
        return (String) this.get(fields.COMMENT6);
    }
    public String getSTRFIELD4() {
        return (String) this.get(fields.STRFIELD4);
    }

    public boolean contains(fields fieldName)
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr)
            throws IllegalArgumentException {
        return values.containsKey(fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (fields fieldName: fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(List<fields> fieldNamesList) {
        for (fields fieldName: fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        List<fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }
    
    @Override
    public Ponds readSWATFileFormat(String filename)
            throws IOException{
        int lineCounter = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.ISO_8859_1)) {
            String line;
            for (fields key: fields.values()) {
                lineCounter++;
                if ((line = reader.readLine())== null) {
                    throw new IOException(
                            String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                } else {
                    if (key.getFieldClassType().equals(String.class)) {
                        this.set(key, line);
                    }
                    if (key.getFieldClassType().equals(Integer.class)) {
                        this.set(key, Integer.parseInt(line.substring(0,20).trim()));
                    }
                    if (key.getFieldClassType().equals(Double.class)) {
                        this.set(key, Double.parseDouble(line.substring(0,20).trim()));
                    }
                }
            }
        }
        return this;
    }
    @Override
    public void writeSWATFileFormat(String filename)throws IOException{
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),StandardCharsets.ISO_8859_1)) {
            writer.write(this.toSWATTXTFormat());
        }
    }
    public static Ponds newFromSWATFile(String filename)
            throws IOException {
        Ponds pnd = new Ponds();
        return pnd.readSWATFileFormat(filename);
    }
    @SuppressWarnings("unchecked")
    public static List<Ponds> newFromSWATFiles(String[] filenames)
            throws IOException {
        ArrayList<Ponds> pnds = new ArrayList();
        for (String filename: filenames){
            pnds.add(newFromSWATFile(filename));
        }
        return pnds;
    }
    
    @Override
    public String toString(){
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
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("{");
            for (fields key: fields.values()) {
                if (key.getFieldClassType().equals(String.class)) {
                    if (key.equals(fields.TITLE)) {
                        strBldr.append(String.format("TITLE: \"%s\"", this.get(key)));
                    } else {
                        strBldr.append(String.format(", %s: \"%s\"", key, this.get(key)));
                    }
                } else {
                    strBldr.append(String.format(", %s: %s", key, this.get(key)));
                }
            }
            return strBldr.append("}").toString();
        } else {
            return null;
        }
    }
    public String toSWATTXTFormat() {
        if (this.containsAllFields()) {
            StringBuilder strBldr = new StringBuilder("");
            for (fields key: fields.values()) {
                if (key.getFieldClassType().equals(String.class)) {
                    strBldr.append(this.get(key)).append("\n");
                } else {
                    StringBuilder tmpStrBldr = new StringBuilder(String.format(key.getStrFMT(), this.get(key)));
                    while (tmpStrBldr.length()<16) {
                        tmpStrBldr.insert(0, " ");
                    }
                    strBldr.append(tmpStrBldr.toString()).append("    | ").append(key.getDescription()).append("\n");
                }
            }
            return strBldr.toString();
        } else {
            return null;
        }
    }
}
