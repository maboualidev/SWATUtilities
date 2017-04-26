/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author mabouali
 */
public final class ManagementOperations {

    private static enum standardKeys{
        MONTH  (Integer.class,"%2d",2,3),
        DAY    (Integer.class,"%2d",5,6),
        HUSC   (Double.class,"%8.3f",8,15),
        MGT_OPT(Integer.class,"%2d",17,18),
        MGT1I  (Integer.class,"%4d",20,23),
        MGT2I  (Integer.class,"%3d",25,27),
        MGT3I  (Integer.class,"%2d",29,30),
        MGT4   (Double.class,"%12.5f",32,43),
        MGT5   (Double.class,"%6.2f",45,50),
        MGT6   (Double.class,"%11.5f",52,62),
        MGT7   (Double.class,"%4.2f",64,67),
        MGT8   (Double.class,"%6.2f",69,74),
        MGT9   (Double.class,"%5.2f",76,80);
        
        private final Class classType;
        private final String strFMT;
        private final int startIDX;
        private final int endIDX;
        
        standardKeys(Class classType, String strFMT, int startIDX,int endIDX) {
            this.classType = classType;
            this.strFMT = strFMT;
            this.startIDX = startIDX;
            this.endIDX = endIDX;
        }

        public Class getFieldClassType() {
            return this.classType;            
        }
        public String getFieldClassName() {
            return this.classType.toString();
        }       
        public String getStrFMT() {
            return this.strFMT;
        } 
        public int getStartIDX() {
            return this.startIDX;
        }
        public int getEndIDX() {
            return this.endIDX;
        }
    }
    private final EnumMap<ManagementOperations.standardKeys,Object> values;
    private final LinkedHashMap<String,standardKeys> availableKeys;
    
    public ManagementOperations() {
        values = new EnumMap(standardKeys.class);
        availableKeys = new LinkedHashMap();
    }
    public ManagementOperations(int MGT_OPT) {
        values = new EnumMap(standardKeys.class);
        availableKeys = new LinkedHashMap();
        setAvailableKeys(MGT_OPT);
    }
    public ManagementOperations(String str) {
        values = new EnumMap(standardKeys.class);
        availableKeys = new LinkedHashMap();
        this.parseSWATTXTFormat(str);
    }
    
    public ManagementOperations setValue(String keyStr, Object value)
            throws IllegalArgumentException {
        if (availableKeys.containsKey(keyStr)) {
            standardKeys key = availableKeys.get(keyStr);
            if (key.getFieldClassType().equals(value.getClass())) {
                values.put(key, value);
            } else {
                throw new IllegalArgumentException(
                    String.format("Expected %s type for field %s, but got %s",
                            key.getFieldClassType(),
                            key,
                            value.getClass()));
            }
        } else {
            throw new IllegalArgumentException(keyStr + " is not a valid key.");
        }
        return this;
    }
    
    public Object getValue(String keyStr)
            throws NullPointerException {
        if (availableKeys.containsKey(keyStr)) {
            standardKeys key = availableKeys.get(keyStr);
            if (values.containsKey(key)) {
                return values.get(key);
            } else {
                return null;
            }
        } else {
            throw new NullPointerException(String.format(
                "key: %s does not exist for this mangement operation.",keyStr));
        }
    }
    
    private void setAvailableKeys(int MGT_OPT)
            throws IllegalArgumentException {
        availableKeys.clear();
        availableKeys.put("MONTH", standardKeys.MONTH);
        availableKeys.put("DAY", standardKeys.DAY);
        availableKeys.put("HUSC", standardKeys.HUSC);
        availableKeys.put("MGT_OPT", standardKeys.MGT_OPT);
        switch (MGT_OPT) {
            case 1:
                availableKeys.put("PLANT_ID",standardKeys.MGT1I);
                availableKeys.put("CURYR_MAT",standardKeys.MGT3I);
                availableKeys.put("HEAT_UNITS",standardKeys.MGT4);
                availableKeys.put("LAI_INIT",standardKeys.MGT5);
                availableKeys.put("BIO_INIT",standardKeys.MGT6);
                availableKeys.put("HI_TARG",standardKeys.MGT7);
                availableKeys.put("BIO_TARG",standardKeys.MGT8);
                availableKeys.put("CNOP",standardKeys.MGT9);
                break;
            case 2:
                availableKeys.put("IRR_SC",standardKeys.MGT2I);
                availableKeys.put("IRR_NO",standardKeys.MGT3I);
                availableKeys.put("IRR_AMT",standardKeys.MGT4);
                availableKeys.put("IRR_SALT",standardKeys.MGT5);
                availableKeys.put("IRR_EFM",standardKeys.MGT6);
                availableKeys.put("IRR_SQ",standardKeys.MGT7);
                break;
            case 3:
                availableKeys.put("FERT_ID", standardKeys.MGT1I);
                availableKeys.put("FRT_KG", standardKeys.MGT4);
                availableKeys.put("FRT_SURFACE", standardKeys.MGT5);
                break;
            case 4:
                availableKeys.put("PEST_ID", standardKeys.MGT1I);
                availableKeys.put("PST_KG", standardKeys.MGT4);
                availableKeys.put("PST_DEP", standardKeys.MGT5);
                break;
            case 5:
                availableKeys.put("CNOP",standardKeys.MGT4);
                availableKeys.put("HI_OVR",standardKeys.MGT5);
                availableKeys.put("FRAC_HARVK",standardKeys.MGT6);
                break;
            case 6:
                availableKeys.put("TILL_ID", standardKeys.MGT1I);
                availableKeys.put("CNOP", standardKeys.MGT4);
                break;
            case 7:
                availableKeys.put("IHV_GBM", standardKeys.MGT2I);
                availableKeys.put("HARVEFF", standardKeys.MGT4);
                availableKeys.put("HI_OVR", standardKeys.MGT5);
                break;
            case 8:
                //Nothing needs to be done here
                break;
            case 9:
                availableKeys.put("GRZ_DAYS",standardKeys.MGT1I);
                availableKeys.put("MANURE_ID",standardKeys.MGT2I);
                availableKeys.put("BIO_EAT",standardKeys.MGT4);
                availableKeys.put("BIO_TRMP",standardKeys.MGT5);
                availableKeys.put("MANURE_KG",standardKeys.MGT6);
                break;
            case 10:
                availableKeys.put("WSTR_ID",standardKeys.MGT1I);
                availableKeys.put("IRR_SCA",standardKeys.MGT2I);
                availableKeys.put("IRR_NOA",standardKeys.MGT3I);
                availableKeys.put("AUTO_WSTRS",standardKeys.MGT4);
                availableKeys.put("IRR_EFF",standardKeys.MGT5);
                availableKeys.put("IRR_MX",standardKeys.MGT6);
                availableKeys.put("IRR_ASQ",standardKeys.MGT7);
                break;
            case 11:
                availableKeys.put("AFERT_ID",standardKeys.MGT1I);
                availableKeys.put("AUTO_NSTRS",standardKeys.MGT4);
                availableKeys.put("AUTO_NAPP",standardKeys.MGT5);
                availableKeys.put("AUTO_NYR",standardKeys.MGT6);
                availableKeys.put("AUTO_EFF",standardKeys.MGT7);
                availableKeys.put("AFRT_SURFACE",standardKeys.MGT8);
                break;
            case 12:
                availableKeys.put("SWEEPEFF", standardKeys.MGT4);
                availableKeys.put("FR_CURB", standardKeys.MGT5);
                break;
            case 13:
                availableKeys.put("IMP_TRIG", standardKeys.MGT1I);
                break;
            case 14:
                availableKeys.put("FERT_DAYS", standardKeys.MGT1I);
                availableKeys.put("CFRT_ID", standardKeys.MGT2I);
                availableKeys.put("IFRT_FREQ", standardKeys.MGT3I);
                availableKeys.put("CFRT_KG", standardKeys.MGT4);
                break;
            case 15:
                availableKeys.put("CPST_ID", standardKeys.MGT1I);
                availableKeys.put("PEST_DAYS", standardKeys.MGT2I);
                availableKeys.put("IPEST_FREQ", standardKeys.MGT3I);
                availableKeys.put("CPST_KG", standardKeys.MGT4);
                break;
            case 16:
                availableKeys.put("BURN_FRLB", standardKeys.MGT4);
                break;
            case 17:
            case 0:
                availableKeys.remove("MONTH");
                availableKeys.remove("DAY");
                availableKeys.remove("HUSC");
                break;
            default:
                availableKeys.clear();
                throw new IllegalArgumentException(String.format(
                    "MGT_OPT=%d is not recognized.",MGT_OPT));
        }
    }
    public Set<String> getAvailableKeys() {
        return availableKeys.keySet();
    }
    
    public ManagementOperations parseSWATTXTFormat(String str) {
        values.clear();
        availableKeys.clear();
        setAvailableKeys(Integer.parseInt(str.substring(16, 18).trim()));
        getAvailableKeys().forEach((keyStr) -> {
            standardKeys key = availableKeys.get(keyStr);
            int startIDX = key.getStartIDX();
            int endIDX = key.getEndIDX();
            if (startIDX<=str.length()) {
                String subStr = str.substring(startIDX-1, endIDX).trim();
                if (!subStr.isEmpty()){
                    if (key.getFieldClassType().equals(Integer.class)) {
                        values.put(availableKeys.get(keyStr), Integer.parseInt(subStr));
                    }
                    if (key.getFieldClassType().equals(Double.class)) {
                        values.put(availableKeys.get(keyStr), Double.parseDouble(subStr));
                    }
                }
            }
        });
        return this;
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toJSONString() {
        StringBuilder strBldr = new StringBuilder("{");
        getAvailableKeys().forEach((keyStr) -> {
            if (strBldr.toString().length()==1) {
                strBldr.append(String.format("%s: %s",keyStr,getValue(keyStr)));
            } else {
                strBldr.append(String.format(", %s: %s",keyStr,getValue(keyStr)));
            }
        });
        return strBldr.append("}").toString();
    }
    public String toSWATTXTFormat() {
        StringBuilder strBldr = new StringBuilder();
        standardKeys[] keys = standardKeys.values();
        for(int idx=(keys.length-1); idx>=0; idx--) {
            standardKeys key = keys[idx];
            if (values.containsKey(key)) {
                if (strBldr.length()!=0)
                    strBldr.insert(0," ");
                strBldr.insert(0, String.format(key.getStrFMT(),this.values.get(key)) );
            } else {
                if (strBldr.length()!=0) {
                    strBldr.insert(0, " ");
                    for (int idx2=key.getStartIDX(); idx2<=key.getEndIDX();idx2++)
                        strBldr.insert(0, " ");
                }
            }
        }
        return strBldr.insert(0," ").toString();
    }
}
