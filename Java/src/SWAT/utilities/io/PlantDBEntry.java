/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author mabouali
 */
public final class PlantDBEntry {
    /**
     * Stores valid field names for PlantDBEntry.
     */
    public static enum fields {
        /**
         * Plant ID Number in the database
         */
        ICNUM(Integer.class,false,0,false,0),
        CPNM(String.class,false,0,true,4),
        IDC(Integer.class,true,1,true,7),
        
        BIO_E(Double.class,true,0,false,0),
        HVSTI(Double.class,true,0,true,1), 
        BLAI(Double.class,true,0,false,0), 
        FRGRW1(Double.class,true,0,true,1), 
        LAIMX1(Double.class,true,0,true,1),
        FRGRW2(Double.class,true,0,true,1), 
        LAIMX2(Double.class,true,0,true,1), 
        DLAI(Double.class,true,0,true,1), 
        CHTMX(Double.class,true,0,false,0), 
        RDMX(Double.class,true,0,false,0),

        T_OPT(Double.class,false,0,false,0),
        T_BASE(Double.class,false,0,false,0),
        CNYLD(Double.class,true,0,true,1),
        CPYLD(Double.class,true,0,true,1),
        PLTNFR1(Double.class,true,0,true,1),
        PLTNFR2(Double.class,true,0,true,1),
        PLTNFR3(Double.class,true,0,true,1),
        PLTPFR1(Double.class,true,0,true,1),
        PLTPFR2(Double.class,true,0,true,1),
        PLTPFR3(Double.class,true,0,true,1),


        WSYF(Double.class,true,0,true,1),
        USLE_C(Double.class,false,0,false,0),
        GSI(Double.class,true,0,false,0),
        VPDFR(Double.class,true,0,false,0),
        FRGMAX(Double.class,true,0,false,0),
        WAVP(Double.class,true,0,false,0),
        CO2HI(Double.class,false,0,false,0),
        BIOEHI(Double.class,false,0,false,0),
        RSDCO_PL(Double.class,true,0,false,0),
        ALAI_MIN(Double.class,true,0,false,0),

        BIO_LEAF(Double.class,true,0,true,1),
        MAT_YRS(Integer.class,true,0,false,0),
        BMX_TREES(Double.class,true,0,false,0),
        EXT_COEF(Double.class,true,0,false,0),
        BMDIEOFF(Double.class,true,0,false,0),
        RSR1C(Double.class,true,0,false,0),
        RSR2C(Double.class,true,0,false,0);
        
        private final Class classType;
        private final boolean hasLowerLimit;
        private final double lowerLimit;
        private final boolean hasUpperLimit;
        private final double upperLimit;
        
        fields(Class classType,boolean hasLowerLimit, double lowerLimit, boolean hasUpperLimit, double upperLimit){
            this.classType = classType;
            this.hasLowerLimit = hasLowerLimit;
            this.lowerLimit = lowerLimit;
            this.hasUpperLimit = hasUpperLimit;
            this.upperLimit = upperLimit;
        }
        
        public Class getFieldClassType(){
            return classType;            
        }
        public String getFieldClassName(){
            return classType.toString();
        }
        public boolean isLimited() {
            return (this.hasLowerLimit || this.hasUpperLimit);
        }
        public boolean hasLowerLimit() {
            return this.hasLowerLimit;
        }
        public double getLowerLimit() {
            return this.lowerLimit;
        }
        public boolean hasUpperLimit() {
            return this.hasUpperLimit;
        }
        public double getUpperLimit() {
            return this.upperLimit;
        }
    }
    
    /**
     * stores the value for the different fields
     */
    private final EnumMap<fields, Object> values;

    public PlantDBEntry(){
        this.values = new EnumMap(fields.class);
    }    
    public PlantDBEntry(int ICNUM, String CPNM, int IDC,
            double BIO_E, double HVSTI, double BLAI, double FRGRW1, double LAIMX1, double FRGRW2, double LAIMX2, double DLAI, double CHTMX, double RDMX, 
            double T_OPT, double T_BASE, double CNYLD, double CPYLD, double PLTNFR1, double PLTNFR2, double PLTNFR3, double PLTPFR1, double PLTPFR2, double PLTPFR3,
            double WSYF, double USLE_C, double GSI, double VPDFR, double FRGMAX, double WAVP, double CO2HI, double BIOEHI, double RSDCO_PL, double ALAI_MIN,
            double BIO_LEAF, int MAT_YRS, double BMX_TREES, double EXT_COEF, double BMDIEOFF, double RSR1C, double RSR2C) {
        
        this.values = new EnumMap(fields.class);
        
        this    .setICNUM(ICNUM)                
                .setCPNM(CPNM)
                .setIDC(IDC)
                
                .setBIO_E(BIO_E)
                .setHVSTI(HVSTI) 
                .setBLAI(BLAI) 
                .setFRGRW1(FRGRW1) 
                .setLAIMX1(LAIMX1)
                .setFRGRW2(FRGRW2) 
                .setLAIMX2(LAIMX2) 
                .setDLAI(DLAI) 
                .setCHTMX(CHTMX) 
                .setRDMX(RDMX)

                .setT_OPT(T_OPT)
                .setT_BASE(T_BASE)
                .setCNYLD(CNYLD)
                .setCPYLD(CPYLD)
                .setPLTNFR1(PLTNFR1)
                .setPLTNFR2(PLTNFR2)
                .setPLTNFR3(PLTNFR3)
                .setPLTPFR1(PLTPFR1)
                .setPLTPFR2(PLTPFR2)
                .setPLTPFR3(PLTPFR3)

                .setWSYF(WSYF)
                .setUSLE_C(USLE_C)
                .setGSI(GSI)
                .setVPDFR(VPDFR)
                .setFRGMAX(FRGMAX)
                .setWAVP(WAVP)
                .setCO2HI(CO2HI)
                .setBIOEHI(BIOEHI)
                .setRSDCO_PL(RSDCO_PL)
                .setALAI_MIN(ALAI_MIN)

                .setBIO_LEAF(BIO_LEAF)
                .setMAT_YRS(MAT_YRS)
                .setBMX_TREES(BMX_TREES)
                .setEXT_COEF(EXT_COEF)
                .setBMDIEOFF(BMDIEOFF)
                .setRSR1C(RSR1C)
                .setRSR2C(RSR2C);
    }
            
    /**
     * 
     * @param fieldName specifying the field name to be set.
     * @param value Specifies the value for the field. The runtime class type of the
     *        provided value must match that of the one retrieved by:
     *        <code>PlantDBEntry.fields.{fieldName}.getFieldClassType()</code>
     * @return a pointer the object itself (this) enabling it to chain 
     *         multiple <code>set()</code> commands
     * @throws IllegalArgumentException 
     */
    public final PlantDBEntry set(PlantDBEntry.fields fieldName, Object value)
            throws IllegalArgumentException {
        // Checking if the provided value has proper type and limits
        if (value.getClass().equals(fieldName.getFieldClassType())) {
            if (fieldName.isLimited()) {
                if (value.getClass().equals(String.class)) {
                    if ( ((String)value).length()>fieldName.getUpperLimit() ) {
                        
                        throw new IllegalArgumentException(
                                String.format("Maximum length for %s could be %d; instead got an string of length %d",
                                        fieldName.toString(),
                                        (int)fieldName.getUpperLimit(),
                                        ((String)value).length()));
                    }
                } else {
                    if (fieldName.hasLowerLimit && (double)value<fieldName.getLowerLimit()) {
                        throw new IllegalArgumentException(
                            String.format("The lowest value for %s could be %.4f; instead got %.4f",
                                    fieldName.toString(),
                                    fieldName.getLowerLimit(),
                                    (double)value));
                    }
                    if (fieldName.hasUpperLimit && (double)value>fieldName.getUpperLimit()) {
                        throw new IllegalArgumentException(
                            String.format("The highest value for %s could be %.4f; instead got %.4f",
                                    fieldName.toString(),
                                    fieldName.getUpperLimit(),
                                    (double)value));
                    }
                }
            }
            // if it reached here: there was no problem. Otherwise an exception is thrown.
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
    /**
     * 
     * @param fieldNameStr An <code>String</code> specifying the field name. 
     *        must be a valid name based on those in PlantDBEntry.fields; otherwise,
     *        an IllegalArgumentException is thrown.
     * @param value Specifies the value for the field. The runtime class type of the
     *        provided value must match that of the one retrieved by:
     *        <code>PlantDBEntry.fields.{fieldName}.getFieldClassType()</code>
     * @return a pointer the object itself (this) enabling it to chain 
     *         multiple <code>set</code> commands
     */
    public final PlantDBEntry set(String fieldNameStr, Object value) {
        return set(fields.valueOf(fieldNameStr), value);
    }
    
    public Object get(fields fieldName)
            throws NullPointerException {
        return values.get(fieldName);
    }
    public Object get(String fieldNameStr) {
        return values.get(fields.valueOf(fieldNameStr));
    }

    public int getICNUM() {
        return (int) get(PlantDBEntry.fields.ICNUM);
    }
    public String getCPNM() {
        return (String) get(PlantDBEntry.fields.CPNM);
    }
    public int getIDC() {
        return (int) get(PlantDBEntry.fields.IDC);
    }

    public double getBIO_E() {
        return (double) get(PlantDBEntry.fields.BIO_E);
    }
    public double getHVSTI() { 
        return (double) get(PlantDBEntry.fields.HVSTI);
    }
    public double getBLAI() { 
        return (double) get(PlantDBEntry.fields.BLAI);
    }
    public double getFRGRW1() { 
        return (double) get(PlantDBEntry.fields.FRGRW1);
    }
    public double getLAIMX1() {
        return (double) get(PlantDBEntry.fields.LAIMX1);
    }
    public double getFRGRW2() { 
        return (double) get(PlantDBEntry.fields.FRGRW2);
    }
    public double getLAIMX2() { 
        return (double) get(PlantDBEntry.fields.LAIMX2);
    }
    public double getDLAI() { 
        return (double) get(PlantDBEntry.fields.DLAI);
    }
    public double getCHTMX() { 
        return (double) get(PlantDBEntry.fields.CHTMX);
    }
    public double getRDMX() {
        return (double) get(PlantDBEntry.fields.RDMX);
    }

    public double getT_OPT() {
        return (double) get(PlantDBEntry.fields.T_OPT);
    }
    public double getT_BASE() {
        return (double) get(PlantDBEntry.fields.T_BASE);
    }
    public double getCNYLD() {
        return (double) get(PlantDBEntry.fields.CNYLD);
    }
    public double getCPYLD() {
        return (double) get(PlantDBEntry.fields.CPYLD);
    }
    public double getPLTNFR1() {
        return (double) get(PlantDBEntry.fields.PLTNFR1);
    }
    public double getPLTNFR2() {
        return (double) get(PlantDBEntry.fields.PLTNFR2);
    }
    public double getPLTNFR3() {
        return (double) get(PlantDBEntry.fields.PLTNFR2);
    }
    public double getPLTPFR1() {
        return (double) get(PlantDBEntry.fields.PLTPFR1);
    }
    public double getPLTPFR2() {
        return (double) get(PlantDBEntry.fields.PLTPFR2);
    }
    public double getPLTPFR3() {
        return (double) get(PlantDBEntry.fields.PLTPFR3);
    }


    public double getWSYF() {
        return (double) get(PlantDBEntry.fields.WSYF);
    }
    public double getUSLE_C() {
        return (double) get(PlantDBEntry.fields.USLE_C);
    }
    public double getGSI() {
        return (double) get(PlantDBEntry.fields.GSI);
    }
    public double getVPDFR() {
        return (double) get(PlantDBEntry.fields.VPDFR);
    }
    public double getFRGMAX() {
        return (double) get(PlantDBEntry.fields.FRGMAX);
    }
    public double getWAVP() {
        return (double) get(PlantDBEntry.fields.WAVP);
    }
    public double getCO2HI() {
        return (double) get(PlantDBEntry.fields.CO2HI);
    }
    public double getBIOEHI() {
        return (double) get(PlantDBEntry.fields.BIOEHI);
    }
    public double getRSDCO_PL() {
        return (double) get(PlantDBEntry.fields.RSDCO_PL);
    }
    public double getALAI_MIN() {
        return (double) get(PlantDBEntry.fields.ALAI_MIN);
    }

    public double getBIO_LEAF() {
        return (double) get(PlantDBEntry.fields.BIO_LEAF);
    }
    public int getMAT_YRS(){
        return (int) get(PlantDBEntry.fields.MAT_YRS);
    }
    public double getBMX_TREES() {
        return (double) get(PlantDBEntry.fields.BMX_TREES);
    }
    public double getEXT_COEF() {
        return (double) get(PlantDBEntry.fields.EXT_COEF);
    }
    public double getBMDIEOFF() {
        return (double) get(PlantDBEntry.fields.BMDIEOFF);
    }
    public double getRSR1C() {
        return (double) get(PlantDBEntry.fields.RSR1C);
    }
    public double getRSR2C() {
        return (double) get(PlantDBEntry.fields.RSR2C);
    }
    
    public final PlantDBEntry setICNUM(int v) {
        return set(PlantDBEntry.fields.ICNUM,v);
    }
    public final PlantDBEntry setCPNM(String v) {
        return set(PlantDBEntry.fields.CPNM,v);
    }
    public final PlantDBEntry setIDC(int v) {
        return set(PlantDBEntry.fields.IDC,v);
    }

    public final PlantDBEntry setBIO_E(double v) {
        return set(PlantDBEntry.fields.BIO_E,v);
    }
    public final PlantDBEntry setHVSTI(double v) { 
        return set(PlantDBEntry.fields.HVSTI,v);
    }
    public final PlantDBEntry setBLAI(double v) { 
        return set(PlantDBEntry.fields.BLAI,v);
    }
    public final PlantDBEntry setFRGRW1(double v) { 
        return set(PlantDBEntry.fields.FRGRW1,v);
    }
    public final PlantDBEntry setLAIMX1(double v) {
        return set(PlantDBEntry.fields.LAIMX1,v);
    }
    public final PlantDBEntry setFRGRW2(double v) { 
        return set(PlantDBEntry.fields.FRGRW2,v);
    }
    public final PlantDBEntry setLAIMX2(double v) { 
        return set(PlantDBEntry.fields.LAIMX2,v);
    }
    public final PlantDBEntry setDLAI(double v) { 
        return set(PlantDBEntry.fields.DLAI,v);
    }
    public final PlantDBEntry setCHTMX(double v) { 
        return set(PlantDBEntry.fields.CHTMX,v);
    }
    public final PlantDBEntry setRDMX(double v) {
        return set(PlantDBEntry.fields.RDMX,v);
    }

    public final PlantDBEntry setT_OPT(double v) {
        return set(PlantDBEntry.fields.T_OPT,v);
    }
    public final PlantDBEntry setT_BASE(double v) {
        return set(PlantDBEntry.fields.T_BASE,v);
    }
    public final PlantDBEntry setCNYLD(double v) {
        return set(PlantDBEntry.fields.CNYLD,v);
    }
    public final PlantDBEntry setCPYLD(double v) {
        return set(PlantDBEntry.fields.CPYLD,v);
    }
    public final PlantDBEntry setPLTNFR1(double v) {
        return set(PlantDBEntry.fields.PLTNFR1,v);
    }
    public final PlantDBEntry setPLTNFR2(double v) {
        return set(PlantDBEntry.fields.PLTNFR2,v);
    }
    public final PlantDBEntry setPLTNFR3(double v) {
        return set(PlantDBEntry.fields.PLTNFR2,v);
    }
    public final PlantDBEntry setPLTPFR1(double v) {
        return set(PlantDBEntry.fields.PLTPFR1,v);
    }
    public final PlantDBEntry setPLTPFR2(double v) {
        return set(PlantDBEntry.fields.PLTPFR2,v);
    }
    public final PlantDBEntry setPLTPFR3(double v) {
        return set(PlantDBEntry.fields.PLTPFR3,v);
    }


    public final PlantDBEntry setWSYF(double v) {
        return set(PlantDBEntry.fields.WSYF,v);
    }
    public final PlantDBEntry setUSLE_C(double v) {
        return set(PlantDBEntry.fields.USLE_C,v);
    }
    public final PlantDBEntry setGSI(double v) {
        return set(PlantDBEntry.fields.GSI,v);
    }
    public final PlantDBEntry setVPDFR(double v) {
        return set(PlantDBEntry.fields.VPDFR,v);
    }
    public final PlantDBEntry setFRGMAX(double v) {
        return set(PlantDBEntry.fields.FRGMAX,v);
    }
    public final PlantDBEntry setWAVP(double v) {
        return set(PlantDBEntry.fields.WAVP,v);
    }
    public final PlantDBEntry setCO2HI(double v) {
        return set(PlantDBEntry.fields.CO2HI,v);
    }
    public final PlantDBEntry setBIOEHI(double v) {
        return set(PlantDBEntry.fields.BIOEHI,v);
    }
    public final PlantDBEntry setRSDCO_PL(double v) {
        return set(PlantDBEntry.fields.RSDCO_PL,v);
    }
    public final PlantDBEntry setALAI_MIN(double v) {
        return set(PlantDBEntry.fields.ALAI_MIN,v);
    }
    public final PlantDBEntry setBIO_LEAF(double v) {
        return set(PlantDBEntry.fields.BIO_LEAF,v);
    }
    public final PlantDBEntry setMAT_YRS(int v){
        return set(PlantDBEntry.fields.MAT_YRS,v);
    }
    public final PlantDBEntry setBMX_TREES(double v) {
        return set(PlantDBEntry.fields.BMX_TREES,v);
    }
    public final PlantDBEntry setEXT_COEF(double v) {
        return set(PlantDBEntry.fields.EXT_COEF,v);
    }
    public final PlantDBEntry setBMDIEOFF(double v) {
        return set(PlantDBEntry.fields.BMDIEOFF,v);
    }
    public final PlantDBEntry setRSR1C(double v) {
        return set(PlantDBEntry.fields.RSR1C,v);
    }
    public final PlantDBEntry setRSR2C(double v) {
        return set(PlantDBEntry.fields.RSR2C,v);
    }
    
    public boolean contains(PlantDBEntry.fields fieldName)
            throws IllegalArgumentException {
        return values.containsKey(fieldName);
    }
    public boolean contains(String fieldNameStr)
            throws IllegalArgumentException {
        return values.containsKey(PlantDBEntry.fields.valueOf(fieldNameStr));
    }
    public boolean containsAllFields() {
        for (fields fieldName: fields.values()) {
            if (!values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
     public boolean containsAllFieldsIgnoring(List<PlantDBEntry.fields> fieldNamesList) {
        for (PlantDBEntry.fields fieldName: PlantDBEntry.fields.values()) {
            if (!fieldNamesList.contains(fieldName) && !values.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }
    public boolean containsAllFieldsIgnoring(String[] fieldNamesStr) {
        ArrayList<PlantDBEntry.fields> fieldNamesList = new ArrayList(fieldNamesStr.length);
        for (String fieldNameStr: fieldNamesStr)
            fieldNamesList.add(PlantDBEntry.fields.valueOf(fieldNameStr));
        return containsAllFieldsIgnoring(fieldNamesList);
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
    public final String toJSONString()
            throws NullPointerException {        
        String strFMT = "{ICNUM: %d,CPNM: \"%s\",IDC: %d,BIO_E: %f,HVSTI: %f, BLAI: %f, FRGRW1: %f, LAIMX1: %f,FRGRW2: %f, LAIMX2: %f, DLAI: %f, CHTMX: %f, RDMX: %f,T_OPT: %f,T_BASE: %f,CNYLD: %f,CPYLD: %f,PLTNFR1: %f,PLTNFR2: %f,PLTNFR3: %f,PLTPFR1: %f,PLTPFR2: %f,PLTPFR3: %f,WSYF: %f,USLE_C: %f,GSI: %f,VPDFR: %f,FRGMAX: %f,WAVP: %f,CO2HI: %f,BIOEHI: %f,RSDCO_PL: %f,ALAI_MIN: %f,BIO_LEAF: %f,MAT_YRS: %d,BMX_TREES: %f,EXT_COEF: %f,BMDIEOFF: %f";
        StringBuilder resultStrBuilder = new StringBuilder(String.format(
                strFMT,
                this.getICNUM(), this.getCPNM(), this.getIDC(),
                
                this.getBIO_E(), this.getHVSTI(), this.getBLAI(), this.getFRGRW1(), this.getLAIMX1(),
                this.getFRGRW2(), this.getLAIMX2(), this.getDLAI(), this.getCHTMX(), this.getRDMX(),
                
                this.getT_OPT(), this.getT_BASE(), this.getCNYLD(), this.getCPYLD(), this.getPLTNFR1(),
                this.getPLTNFR2(), this.getPLTNFR3(), this.getPLTPFR1(), this.getPLTPFR2(), this.getPLTPFR3(),
                
                this.getWSYF(), this.getUSLE_C(), this.getGSI(), this.getVPDFR(), this.getFRGMAX(),
                this.getWAVP(), this.getCO2HI(), this.getBIOEHI(), this.getRSDCO_PL(), this.getALAI_MIN(),
                
                this.getBIO_LEAF(), this.getMAT_YRS(), this.getBMX_TREES(), this.getEXT_COEF(),
                this.getBMDIEOFF()));
        
        if (this.contains(fields.RSR1C))
            resultStrBuilder.append(String.format(",RSR1c: %f",this.getRSR1C()));
        
        if (this.contains(fields.RSR2C))
            resultStrBuilder.append(String.format(",RSR2c: %f",this.getRSR2C()));
        
        
        return resultStrBuilder.append("}").toString();
    }
    public final String toSWATTXTFormat()
            throws NullPointerException{
        String strFMT = "%d %4s %d\n%.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f\n%.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f\n%.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f\n%.4f %d %.4f %.4f %.4f";
        
        StringBuilder resultStrBuilder = new StringBuilder(String.format(
                    strFMT,
                    this.getICNUM(),this.getCPNM().subSequence(0, 4),this.getIDC(),
                    
                    this.getBIO_E(), this.getHVSTI(), this.getBLAI(), this.getFRGRW1(), this.getLAIMX1(),
                    this.getFRGRW2(), this.getLAIMX2(), this.getDLAI(), this.getCHTMX(), this.getRDMX(),
                    
                    this.getT_OPT(), this.getT_BASE(), this.getCNYLD(), this.getCPYLD(), this.getPLTNFR1(),
                    this.getPLTNFR2(), this.getPLTNFR3(), this.getPLTPFR1(), this.getPLTPFR2(), this.getPLTPFR3(),
                    
                    this.getWSYF(), this.getUSLE_C(), this.getGSI(), this.getVPDFR(), this.getFRGMAX(),
                    this.getWAVP(), this.getCO2HI(), this.getBIOEHI(), this.getRSDCO_PL(), this.getALAI_MIN(),
                    this.getBIO_LEAF(), this.getMAT_YRS(), this.getBMX_TREES(), this.getEXT_COEF(),
                    this.getBMDIEOFF()));
        
        if (this.contains(fields.RSR1C))
            resultStrBuilder.append(String.format(" %f",this.getRSR1C()));
        if (this.contains(fields.RSR2C))
            resultStrBuilder.append(String.format(" %f",this.getRSR2C()));
        
        return resultStrBuilder.append("\n").toString();
    }
}
