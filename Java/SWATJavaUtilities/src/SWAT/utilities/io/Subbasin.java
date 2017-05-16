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
public final class Subbasin
        implements SWATFormatInput<Subbasin, Subbasin.fields> {
    public static enum fields {
        TITLE       (String.class,"","%s"),
        SUB_KM      (Double.class,"SUB_KM : Subbasin area [km2]","%f"),
        COMMENT1    (String.class,"","%s"),
        COMMENT2    (String.class,"Climate in subbasin","%s"),
        LATITUDE    (Double.class,"LATITUDE : Latitude of subbasin [degrees]","%f"),
        ELEV        (Double.class,"ELEV : Elevation of subbasin [m]","%f"),
        IRGAGE      (Integer.class,"IRGAGE: precip gage data used in subbasin","%d"),
        ITGAGE      (Integer.class,"ITGAGE: temp gage data used in subbasin","%d"),
        ISGAGE      (Integer.class,"ISGAGE: solar radiation gage data used in subbasin","%d"),
        IHGAGE      (Integer.class,"IHGAGE: relative humidity gage data used in subbasin","%d"),
        IWGAGE      (Integer.class,"IWGAGE: wind speed gage data used in subbasin","%d"),
        WGNFILE     (String.class,"WGNFILE: name of weather generator data file","%s"),
        FCST_REG    (Double.class,"FCST_REG: Region number used to assign forecast data to the subbasin","%f"),
        COMMENT3    (String.class,"Elevation Bands","%s"),
        COMMENT4    (String.class,"| ELEVB: Elevation at center of elevation bands [m]","%s"),
        ELEVB       ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT5    (String.class,"| ELEVB_FR: Fraction of subbasin area within elevation band","%s"),
        ELEVB_FR    ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT6    (String.class,"| SNOEB: Initial snow water content in elevation band [mm]","%s"),
        SNOEB       ((new Double[0]).getClass(),"","%8.1f"),
        PLAPS       (Double.class,"PLAPS : Precipitation lapse rate [mm/km]","%f"),
        TLAPS       (Double.class,"TLAPS : Temperature lapse rate [°C/km]","%f"),
        SNO_SUB     (Double.class,"SNO_SUB : Initial snow water content [mm]","%f"),
        COMMENT7    (String.class,"Tributary Channels","%s"),
        CH_L1       (Double.class,"CH_L1 : Longest tributary channel length [km]","%f"),
        CH_S1       (Double.class,"CH_S1 : Average slope of tributary channel [m/m]","%f"),
        CH_W1       (Double.class,"CH_W1 : Average width of tributary channel [m]","%f"),
        CH_K1       (Double.class,"CH_K1 : Effective hydraulic conductivity in tributary channel [mm/hr]","%f"),
        CH_N1       (Double.class,"CH_N1 : Manning's \"n\" value for the tributary channels","%f"),
        COMMENT8    (String.class,"Impoundments","%s"),
        PNDFILE     (String.class,"PNDFILE: name of subbasin impoundment file","%s"),
        COMMENT9    (String.class,"Consumptive Water Use","%s"),
        WUSFILE     (String.class,"WUSFILE: name of subbasin water use file","%s"),
        COMMENT10   (String.class,"Climate Change","%s"),
        CO2         (Double.class,"CO2 : Carbon dioxide concentration [ppmv]","%f"),
        COMMENT11   (String.class,"| RFINC:  Climate change monthly rainfall adjustment (January - June)","%s"),
        RFINC1TO6   ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT12   (String.class,"| RFINC:  Climate change monthly rainfall adjustment (July - December)","%s"),
        RFINC7TO12  ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT13   (String.class,"| TMPINC: Climate change monthly temperature adjustment (January - June)","%s"),
        TMPINC1TO6  ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT14   (String.class,"| TMPINC: Climate change monthly temperature adjustment (July - December)","%s"),
        TMPINC7TO12 ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT15   (String.class,"| RADINC: Climate change monthly radiation adjustment (January - June)","%s"),
        RADINC1TO6  ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT16   (String.class,"| RADINC: Climate change monthly radiation adjustment (July - December)","%s"),
        RADINC7TO12 ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT17   (String.class,"| HUMINC: Climate change monthly humidity adjustment (January - June)","%s"),
        HUMINC1TO6  ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT18   (String.class,"| HUMINC: Climate change monthly humidity adjustment (July - December)","%s"),
        HUMINC7TO12 ((new Double[0]).getClass(),"","%8.3f"),
        COMMENT19   (String.class,"| HRU data","%s"),
        HRUTOT      (Integer.class,"HRUTOT : Total number of HRUs modeled in subbasin","%d"),
        COMMENT20   (String.class,"","%s"),
        COMMENT21   (String.class,"HRU: Depressional Storage/Pothole","%s"),
        COMMENT22   (String.class,"","%s"),
        COMMENT23   (String.class,"Floodplain","%s"),
        FLD_HRUFILE (String.class,"","%13s"),
        FLD_MGTFILE (String.class,"","%13s"),
        FLD_SOLFILE (String.class,"","%13s"),
        FLD_CHMFILE (String.class,"","%13s"),
        FLD_GWFILE  (String.class,"","%13s"),
        COMMENT24   (String.class,"HRU: Riparian","%s"),
        RIP_HRUFILE (String.class,"","%13s"),
        RIP_MGTFILE (String.class,"","%13s"),
        RIP_SOLFILE (String.class,"","%13s"),
        RIP_CHMFILE (String.class,"","%13s"),
        RIP_GWFILE  (String.class,"","%13s"),
        COMMENT25   (String.class,"HRU: General","%s"),
        HRUFILE     (String.class,"","%13s"),
        MGTFILE     (String.class,"","%13s"),
        SOLFILE     (String.class,"","%13s"),
        CHMFILE     (String.class,"","%13s"),
        GWFILE      (String.class,"","%13s"),
        OPSFILE     (String.class,"","%13s"),
        SEPTFILE    (String.class,"","%13s"),
        SDRFILE     (String.class,"","%13s");
        
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
    public Subbasin() {
        this.values = new EnumMap(fields.class);
    }
    @SuppressWarnings("unchecked")
    public Subbasin(String filename)
            throws IOException {
        this.values = new EnumMap(fields.class);
        this.readSWATFileFormat(filename);
    }
    
    @Override
    public final Subbasin set(fields fieldName, Object value)
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
    public final Subbasin set(String fieldNameStr, Object value) {
        return set(fields.valueOf(fieldNameStr), value);
    }
    @Override
    public final Subbasin set(String fieldNameStr, String value) {
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
    
    public Subbasin setTITLE(String v) {
        return set(fields.TITLE,v);
    }
    public Subbasin setSUB_KM(Double v) {
        return set(fields.SUB_KM,v);
    }
    public Subbasin setCOMMENT1(String v) {
        return set(fields.COMMENT1,v);
    }
    public Subbasin setCOMMENT2(String v) {
        return set(fields.COMMENT2,v);
    }
    public Subbasin setLATITUDE(Double v) {
        return set(fields.LATITUDE,v);
    }
    public Subbasin setELEV(Double v) {
        return set(fields.ELEV,v);
    }
    public Subbasin getIRGAGE(Integer v) {
        return set(fields.IRGAGE,v);
    }
    public Subbasin setITGAGE(Integer v) {
        return set(fields.ITGAGE,v);
    }
    public Subbasin setISGAGE(Integer v) {
        return set(fields.ISGAGE,v);
    }
    public Subbasin setIHGAGE(Integer v) {
        return set(fields.IHGAGE,v);
    }
    public Subbasin setIWGAGE(Integer v) {
        return set(fields.IWGAGE,v);
    }
    public Subbasin setWGNFILE(String v) {
        return set(fields.WGNFILE,v);
    }
    public Subbasin setFCST_REG(Double v) {
        return set(fields.FCST_REG,v);
    }
    public Subbasin setCOMMENT3(String v) {
        return set(fields.COMMENT3,v);
    }
    public Subbasin setCOMMENT4(String v) {
        return set(fields.COMMENT4,v);
    }
    public Subbasin setELEVB(Double[] v) {
        return set(fields.ELEVB,v);
    }
    public Subbasin setCOMMENT5(String v) {
        return set(fields.COMMENT5,v);
    }
    public Subbasin setELEVB_FR(Double[] v) {
        return set(fields.ELEVB_FR,v);
    }
    public Subbasin setCOMMENT6(String v) {
        return set(fields.COMMENT6,v);
    }
    public Subbasin setSNOEB(Double[] v) {
        return set(fields.SNOEB,v);
    }
    public Subbasin setPLAPS(Double v) {
        return set(fields.PLAPS,v);
    }
    public Subbasin setTLAPS(Double v) {
        return set(fields.TLAPS,v);
    }
    public Subbasin setSNO_SUB(Double v) {
        return set(fields.SNO_SUB,v);
    }
    public Subbasin setCOMMENT7(String v) {
        return set(fields.COMMENT7,v);
    }
    public Subbasin setCH_L1(Double v) {
        return set(fields.CH_L1,v);
    }
    public Subbasin setCH_S1(Double v) {
        return set(fields.CH_S1,v);
    }
    public Subbasin setCH_W1(Double v) {
        return set(fields.CH_W1,v);
    }
    public Subbasin setCH_K1(Double v) {
        return set(fields.CH_K1,v);
    }
    public Subbasin setCH_N1(Double v) {
        return set(fields.CH_N1,v);
    }
    public Subbasin setCOMMENT8(String v) {
        return set(fields.COMMENT8,v);
    }
    public Subbasin setPNDFILE(String v) {
        return set(fields.PNDFILE,v);
    }
    public Subbasin setCOMMENT9(String v) {
        return set(fields.COMMENT9,v);
    }
    public Subbasin setWUSFILE(String v) {
        return set(fields.WUSFILE,v);
    }
    public Subbasin setCOMMENT10(String v) {
        return set(fields.COMMENT10,v);
    }
    public Subbasin setCO2(Double v) {
        return set(fields.CO2,v);
    }
    public Subbasin setCOMMENT11(String v) {
        return set(fields.COMMENT11,v);
    }
    public Subbasin setRFINC1TO6(Double[] v) {
        return set(fields.RFINC1TO6,v);
    }
    public Subbasin setCOMMENT12(String v) {
        return set(fields.COMMENT12,v);
    }
    public Subbasin setRFINC7TO12(Double[] v) {
        return set(fields.RFINC7TO12,v);
    }
    public Subbasin setCOMMENT13(String v) {
        return set(fields.COMMENT13,v);
    }
    public Subbasin setTMPINC1TO6(Double[] v) {
        return set(fields.TMPINC1TO6,v);
    }
    public Subbasin setCOMMENT14(String v) {
        return set(fields.COMMENT14,v);
    }
    public Subbasin setTMPINC7TO12(Double[] v) {
        return set(fields.TMPINC7TO12,v);
    }
    public Subbasin setCOMMENT15(String v) {
        return set(fields.COMMENT15,v);
    }
    public Subbasin setRADINC1TO6(Double[] v) {
        return set(fields.RADINC1TO6,v);
    }
    public Subbasin setCOMMENT16(String v) {
        return set(fields.COMMENT16,v);
    }
    public Subbasin setRADINC7TO12(Double[] v) {
        return set(fields.RADINC7TO12,v);
    }
    public Subbasin setCOMMENT17(String v) {
        return set(fields.COMMENT17,v);
    }
    public Subbasin setHUMINC1TO6(Double[] v) {
        return set(fields.HUMINC1TO6,v);
    }
    public Subbasin setCOMMENT18(String v) {
        return set(fields.COMMENT18,v);
    }
    public Subbasin setHUMINC7TO12(Double[] v) {
        return set(fields.HUMINC7TO12,v);
    }
    public Subbasin setCOMMENT19(String v) {
        return set(fields.COMMENT19,v);
    }
    public Subbasin setHRUTOT(Integer v) {
        return set(fields.HRUTOT,v);
    }
    public Subbasin setCOMMENT20(String v) {
        return set(fields.COMMENT20,v);
    }
    public Subbasin setCOMMENT21(String v) {
        return set(fields.COMMENT21,v);
    }
    public Subbasin setCOMMENT22(String v) {
        return set(fields.COMMENT22,v);
    }
    public Subbasin setCOMMENT23(String v) {
        return set(fields.COMMENT23,v);
    }
    public Subbasin setFLD_HRUFILE(String v) {
        return set(fields.FLD_HRUFILE,v);
    }
    public Subbasin setFLD_MGTFILE(String v) {
        return set(fields.FLD_MGTFILE,v);
    }
    public Subbasin setFLD_SOLFILE(String v) {
        return set(fields.FLD_SOLFILE,v);
    }
    public Subbasin setFLD_CHMFILE(String v) {
        return set(fields.FLD_CHMFILE,v);
    }
    public Subbasin setFLD_GWFILE(String v) {
        return set(fields.FLD_GWFILE,v);
    }
    public Subbasin setCOMMENT24(String v) {
        return set(fields.COMMENT24,v);
    }
    public Subbasin setRIP_HRUFILE(String v) {
        return set(fields.RIP_HRUFILE,v);
    }
    public Subbasin setRIP_MGTFILE(String v) {
        return set(fields.RIP_MGTFILE,v);
    }
    public Subbasin setRIP_SOLFILE(String v) {
        return set(fields.RIP_SOLFILE,v);
    }
    public Subbasin setRIP_CHMFILE(String v) {
        return set(fields.RIP_CHMFILE,v);
    }
    public Subbasin setRIP_GWFILE(String v) {
        return set(fields.RIP_GWFILE,v);
    }
    public Subbasin setCOMMENT25(String v) {
        return set(fields.COMMENT25,v);
    }
    public Subbasin setHRUFILE(String v) {
        return set(fields.HRUFILE,v);
    }
    public Subbasin setMGTFILE(String v) {
        return set(fields.MGTFILE,v);
    }
    public Subbasin setSOLFILE(String v) {
        return set(fields.SOLFILE,v);
    }
    public Subbasin setCHMFILE(String v) {
        return set(fields.CHMFILE,v);
    }
    public Subbasin setGWFILE(String v) {
        return set(fields.GWFILE,v);
    }
    public Subbasin setOPSFILE(String v) {
        return set(fields.OPSFILE,v);
    }
    public Subbasin setSEPTFILE(String v) {
        return set(fields.SEPTFILE,v);
    }
    public Subbasin setSDRFILE(String v) {
        return set(fields.SDRFILE,v);
    }
    
    public String getTITLE() {
	return (String) get(fields.TITLE);
    }
    public Double getSUB_KM() {
        return (Double) get(fields.SUB_KM);
    }
    public String getCOMMENT1() {
        return (String) get(fields.COMMENT1);
    }
    public String getCOMMENT2() {
        return (String) get(fields.COMMENT2);
    }
    public Double getLATITUDE() {
        return (Double) get(fields.LATITUDE);
    }
    public Double getELEV() {
        return (Double) get(fields.ELEV);
    }
    public Integer getIRGAGE() {
        return (Integer) get(fields.IRGAGE);
    }
    public Integer getITGAGE() {
        return (Integer) get(fields.ITGAGE);
    }
    public Integer getISGAGE() {
        return (Integer) get(fields.ISGAGE);
    }
    public Integer getIHGAGE() {
        return (Integer) get(fields.IHGAGE);
    }
    public Integer getIWGAGE() {
        return (Integer) get(fields.IWGAGE);
    }
    public String getWGNFILE() {
        return (String) get(fields.WGNFILE);
    }
    public Double getFCST_REG() {
        return (Double) get(fields.FCST_REG);
    }
    public String getCOMMENT3() {
        return (String) get(fields.COMMENT3);
    }
    public String getCOMMENT4() {
        return (String) get(fields.COMMENT4);
    }
    public Double[] getELEVB() {
        return (Double[]) get(fields.ELEVB);
    }
    public String getCOMMENT5() {
        return (String) get(fields.COMMENT5);
    }
    public Double[] getELEVB_FR() {
        return (Double[]) get(fields.ELEVB_FR);
    }
    public String getCOMMENT6() {
        return (String) get(fields.COMMENT6);
    }
    public Double[] getSNOEB() {
        return (Double[]) get(fields.SNOEB);
    }
    public Double getPLAPS() {
        return (Double) get(fields.PLAPS);
    }
    public Double getTLAPS() {
        return (Double) get(fields.TLAPS);
    }
    public Double getSNO_SUB() {
        return (Double) get(fields.SNO_SUB);
    }
    public String getCOMMENT7() {
        return (String) get(fields.COMMENT7);
    }
    public Double getCH_L1() {
        return (Double) get(fields.CH_L1);
    }
    public Double getCH_S1() {
        return (Double) get(fields.CH_S1);
    }
    public Double getCH_W1() {
        return (Double) get(fields.CH_W1);
    }
    public Double getCH_K1() {
        return (Double) get(fields.CH_K1);
    }
    public Double getCH_N1() {
        return (Double) get(fields.CH_N1);
    }
    public String getCOMMENT8() {
        return (String) get(fields.COMMENT8);
    }
    public String getPNDFILE() {
        return (String) get(fields.PNDFILE);
    }
    public String getCOMMENT9() {
        return (String) get(fields.COMMENT9);
    }
    public String getWUSFILE() {
        return (String) get(fields.WUSFILE);
    }
    public String getCOMMENT10() {
        return (String) get(fields.COMMENT10);
    }
    public Double getCO2() {
        return (Double) get(fields.CO2);
    }
    public String getCOMMENT11() {
        return (String) get(fields.COMMENT11);
    }
    public Double[] getRFINC1TO6() {
        return (Double[]) get(fields.RFINC1TO6);
    }
    public String getCOMMENT12() {
        return (String) get(fields.COMMENT12);
    }
    public Double[] getRFINC7TO12() {
        return (Double[]) get(fields.RFINC7TO12);
    }
    public String getCOMMENT13() {
        return (String) get(fields.COMMENT13);
    }
    public Double[] getTMPINC1TO6() {
        return (Double[]) get(fields.TMPINC1TO6);
    }
    public String getCOMMENT14() {
        return (String) get(fields.COMMENT14);
    }
    public Double[] getTMPINC7TO12() {
        return (Double[]) get(fields.TMPINC7TO12);
    }
    public String getCOMMENT15() {
        return (String) get(fields.COMMENT15);
    }
    public Double[] getRADINC1TO6() {
        return (Double[]) get(fields.RADINC1TO6);
    }
    public String getCOMMENT16() {
        return (String) get(fields.COMMENT16);
    }
    public Double[] getRADINC7TO12() {
        return (Double[]) get(fields.RADINC7TO12);
    }
    public String getCOMMENT17() {
        return (String) get(fields.COMMENT17);
    }
    public Double[] getHUMINC1TO6() {
        return (Double[]) get(fields.HUMINC1TO6);
    }
    public String getCOMMENT18() {
        return (String) get(fields.COMMENT18);
    }
    public Double[] getHUMINC7TO12() {
        return (Double[]) get(fields.HUMINC7TO12);
    }
    public String getCOMMENT19() {
        return (String) get(fields.COMMENT19);
    }
    public Integer getHRUTOT() {
        return (Integer) get(fields.HRUTOT);
    }
    public String getCOMMENT20() {
        return (String) get(fields.COMMENT20);
    }
    public String getCOMMENT21() {
        return (String) get(fields.COMMENT21);
    }
    public String getCOMMENT22() {
        return (String) get(fields.COMMENT22);
    }
    public String getCOMMENT23() {
        return (String) get(fields.COMMENT23);
    }
    public String getFLD_HRUFILE() {
        return (String) get(fields.FLD_HRUFILE);
    }
    public String getFLD_MGTFILE() {
        return (String) get(fields.FLD_MGTFILE);
    }
    public String getFLD_SOLFILE() {
        return (String) get(fields.FLD_SOLFILE);
    }
    public String getFLD_CHMFILE() {
        return (String) get(fields.FLD_CHMFILE);
    }
    public String getFLD_GWFILE() {
        return (String) get(fields.FLD_GWFILE);
    }
    public String getCOMMENT24() {
        return (String) get(fields.COMMENT24);
    }
    public String getRIP_HRUFILE() {
        return (String) get(fields.RIP_HRUFILE);
    }
    public String getRIP_MGTFILE() {
        return (String) get(fields.RIP_MGTFILE);
    }
    public String getRIP_SOLFILE() {
        return (String) get(fields.RIP_SOLFILE);
    }
    public String getRIP_CHMFILE() {
        return (String) get(fields.RIP_CHMFILE);
    }
    public String getRIP_GWFILE() {
        return (String) get(fields.RIP_GWFILE);
    }
    public String getCOMMENT25() {
        return (String) get(fields.COMMENT25);
    }
    public String getHRUFILE() {
        return (String) get(fields.HRUFILE);
    }
    public String getMGTFILE() {
        return (String) get(fields.MGTFILE);
    }
    public String getSOLFILE() {
        return (String) get(fields.SOLFILE);
    }
    public String getCHMFILE() {
        return (String) get(fields.CHMFILE);
    }
    public String getGWFILE() {
        return (String) get(fields.GWFILE);
    }
    public String getOPSFILE() {
        return (String) get(fields.OPSFILE);
    }
    public String getSEPTFILE() {
        return (String) get(fields.SEPTFILE);
    }
    public String getSDRFILE() {
        return (String) get(fields.SDRFILE);
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
        ArrayList<fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
    }
    
    
    @Override
    public Subbasin readSWATFileFormat(String filename)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.ISO_8859_1)) {
            String line="";
            Double[] tmpValues;
            int lineCounter = 0;
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
                    case COMMENT8:
                    case COMMENT9:
                    case COMMENT10:
                    case COMMENT11:
                    case COMMENT12:
                    case COMMENT13:
                    case COMMENT14:
                    case COMMENT15:
                    case COMMENT16:
                    case COMMENT17:
                    case COMMENT18:
                    case COMMENT19:
                    case COMMENT20:
                    case COMMENT21:
                    case COMMENT22:
                    case COMMENT23:
                    case COMMENT24:
                    case COMMENT25:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        this.set(key,line);
                        break;
                    case WGNFILE:
                    case PNDFILE:
                    case WUSFILE:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        this.set(key, line.substring(0,13));
                        break;
                    case ELEVB:
                    case ELEVB_FR:
                    case SNOEB:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        tmpValues = new Double[10];
                        for(int idx=0;idx<10; idx++) {
                            tmpValues[idx] = Double.parseDouble(line.substring(idx*8, (idx+1)*8));
                        }
                        this.set(key,tmpValues);
                        break;
                    case RFINC1TO6:
                    case RFINC7TO12:
                    case TMPINC1TO6:
                    case TMPINC7TO12:
                    case RADINC1TO6:
                    case RADINC7TO12:
                    case HUMINC1TO6:
                    case HUMINC7TO12:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        tmpValues = new Double[6];
                        for(int idx=0;idx<6; idx++) {
                            tmpValues[idx] = Double.parseDouble(line.substring(idx*8, (idx+1)*8));
                        }
                        this.set(key,tmpValues);
                        break;
                    case FLD_HRUFILE:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        if (line.length()>=13)
                            this.set(key, line.substring(0,13).trim());
                        break;
                    case FLD_MGTFILE:
                        if (line.length()>=26)
                            this.set(key, line.substring(13,26).trim());
                        break;
                    case FLD_SOLFILE:
                        if (line.length()>=39)
                            this.set(key, line.substring(26,39).trim());
                        break;
                    case FLD_CHMFILE:
                        if (line.length()>=52)
                            this.set(key, line.substring(39,52).trim());
                        break;
                    case FLD_GWFILE:
                        if (line.length()>=65)
                            this.set(key, line.substring(52,65).trim());
                        break;
                    case RIP_HRUFILE:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        if (line.length()>=13)
                            this.set(key, line.substring(0,13).trim());
                        break;
                    case RIP_MGTFILE:
                        if (line.length()>=26)
                            this.set(key, line.substring(13,26).trim());
                        break;
                    case RIP_SOLFILE:
                        if (line.length()>=39)
                            this.set(key, line.substring(26,39).trim());
                        break;
                    case RIP_CHMFILE:
                        if (line.length()>=52)
                            this.set(key, line.substring(39,52).trim());
                        break;
                    case RIP_GWFILE:
                        if (line.length()>=65)
                            this.set(key, line.substring(52,65).trim());
                        break;
                    case HRUFILE:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        if (line.length()>=13)
                            this.set(key, line.substring(0,13).trim());
                        break;
                    case MGTFILE:
                        if (line.length()>=26)
                            this.set(key, line.substring(13,26).trim());
                        break;
                    case SOLFILE:
                        if (line.length()>=39)
                            this.set(key, line.substring(26,39).trim());
                        break;
                    case CHMFILE:
                        if (line.length()>=52)
                            this.set(key, line.substring(39,52).trim());
                        break;
                    case GWFILE:
                        if (line.length()>=65)
                            this.set(key, line.substring(52,65).trim());
                        break;
                    case OPSFILE:
                        if (line.length()>=78)
                            this.set(key, line.substring(65,78).trim());
                        break;
                    case SEPTFILE:
                        if (line.length()>=91)
                            this.set(key, line.substring(78,91).trim());
                        break;
                    case SDRFILE:
                        if (line.length()>=105)
                            this.set(key, line.substring(91,104).trim());
                        break;
                    default:
                        lineCounter++;
                        if ((line=reader.readLine())==null) {
                            throw new IOException(
                                    String.format("Could not read the value for %s on line %d", key.toString(),lineCounter));
                        }
                        if (key.getFieldClassType().equals(Integer.class)) {
                            this.set(key, Integer.parseInt(line.substring(0,18).trim()));
                        }
                        if (key.getFieldClassType().equals(Double.class)) {
                            this.set(key, Double.parseDouble(line.substring(0,18).trim()));
                        }
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
    public static Subbasin newFromSWATFile(String filename)
            throws IOException {
        return (new Subbasin(filename));
    }
    @SuppressWarnings("unchecked")
    public static List<Subbasin> newFromSWATFiles(String[] filenames)
            throws IOException {
        List<Subbasin> subs = new ArrayList();
        for (String filename: filenames){
            subs.add(newFromSWATFile(filename));
        }
        return subs;
    }
    
    @Override
    public String toString(){
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
        StringBuilder strBldr = new StringBuilder("{");
        for (fields key: fields.values()) {
            if (values.containsKey(key)) {
                if (strBldr.length()!=1)
                    strBldr.append(", ");
                if (key.getFieldClassType().equals(String.class)) {
                    strBldr.append(String.format("%s: \"%s\"", key,this.get(key)));
                }
                if (key.getFieldClassType().equals(Double.class) ||
                    key.getFieldClassType().equals(Integer.class)) {
                    strBldr.append(String.format("%s: %s", key.toString(),this.get(key)));
                }
                if (key.getFieldClassType().equals((new Double[0]).getClass())) {
                    Double[] tmpValues = (Double[]) this.get(key);
                    strBldr.append(key.toString()).append(": [");
                    for (int idx=0; idx<tmpValues.length;idx++) {
                        if (idx==0) {
                            strBldr.append(tmpValues[idx]);
                        } else {
                            strBldr.append(",").append(tmpValues[idx]);
                        }
                    }
                    strBldr.append("]");
                }
            }
        }
        return strBldr.append("}").toString();
    }
    public String toSWATTXTFormat() {
        String tmpStr;
        StringBuilder strBldr = new StringBuilder();
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
                case COMMENT8:
                case COMMENT9:
                case COMMENT10:
                case COMMENT11:
                case COMMENT12:
                case COMMENT13:
                case COMMENT14:
                case COMMENT15:
                case COMMENT16:
                case COMMENT17:
                case COMMENT18:
                case COMMENT19:
                case COMMENT20:
                case COMMENT21:
                case COMMENT22:
                case COMMENT23:
                case COMMENT24:
                case COMMENT25:
                    if (this.values.containsKey(key)) {
                        strBldr.append(this.get(key)).append("\n");
                    } else {
                        strBldr.append(key.getDescription()).append("\n");
                    }
                    break;
                case WGNFILE:
                case PNDFILE:
                case WUSFILE:
                    if (this.values.containsKey(key)) {
                        strBldr.append(this.get(key));
                    } else {
                        strBldr.append(String.format("%13s",""));
                    }
                    strBldr.append("       | ")
                            .append(key.getDescription())
                            .append("\n");
                    break;
                case ELEVB:
                case ELEVB_FR:
                case SNOEB:
                case RFINC1TO6:
                case RFINC7TO12:
                case TMPINC1TO6:
                case TMPINC7TO12:
                case RADINC1TO6:
                case RADINC7TO12:
                case HUMINC1TO6:
                case HUMINC7TO12:
                    if (this.values.containsKey(key)) {
                        for (Double v: (Double []) get(key))
                            strBldr.append(String.format(key.getStrFMT(), v));
                    }
                    strBldr.append("\n");
                    break;
                case FLD_HRUFILE:
                case FLD_MGTFILE:
                case FLD_SOLFILE:
                case FLD_CHMFILE:
                case RIP_HRUFILE:
                case RIP_MGTFILE:
                case RIP_SOLFILE:
                case RIP_CHMFILE:
                case HRUFILE:
                case MGTFILE:
                case SOLFILE:
                case CHMFILE:
                case GWFILE:
                case OPSFILE:
                case SEPTFILE:
                    if (this.values.containsKey(key)) {
                        tmpStr = String.format("%13s",this.get(key));
                        strBldr.append(tmpStr.substring(0,13));
                    } else {
                        strBldr.append(String.format("%13s",""));
                    }
                    break;
                case FLD_GWFILE:
                case RIP_GWFILE:
                case SDRFILE:
                    if (this.values.containsKey(key)) {
                        tmpStr = String.format("%13s",this.get(key));
                        strBldr.append(tmpStr.substring(0,13));
                    }
                    strBldr.append("\n");
                    break;
                default:
                    if (this.values.containsKey(key)) {
                        strBldr.append(String.format("%16s",String.format(key.getStrFMT(), this.get(key))));
                    } else {
                        strBldr.append(String.format("%16s",""));
                    }
                    strBldr.append("    | ")
                            .append(key.getDescription())
                            .append("\n");
            }
        }
        return strBldr.toString();
    }
}
