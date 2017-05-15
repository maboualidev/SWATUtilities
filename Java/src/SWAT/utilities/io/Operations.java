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
public final class Operations {
    private static enum standardKeys{
        MONTH  (Integer.class,"%2d",2,3),
        DAY    (Integer.class,"%2d",5,6),
        DUMMY1 (String.class,"",8,10),
        IYEAR  (Integer.class,"%4d",12,15),
        MGT_OPT(Integer.class,"%2d",17,18),
        F1     (Integer.class,"%4d",20,23),
        DUMMY2 (String.class,"",25,27),
        F2     (Double.class,"%6.2f",29,34),
        F3     (Double.class,"%12.5f",36,47),
        F4     (Double.class,"%6.2f",49,54),
        F5     (Double.class,"%11.5f",56,66),
        F6     (Double.class,"%8.2f",68,75),
        F7     (Double.class,"%6.2f",77,82),
        F8     (Double.class,"%5.2f",83,87);

        
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
    private final EnumMap<standardKeys,Object> values;
    private final LinkedHashMap<String,standardKeys> availableKeys;
    
    @SuppressWarnings("unchecked")
    public Operations() {
        values = new EnumMap(Operations.standardKeys.class);
        availableKeys = new LinkedHashMap();
    }
    @SuppressWarnings("unchecked")
    public Operations(int MGT_OPT) {
        values = new EnumMap(standardKeys.class);
        availableKeys = new LinkedHashMap();
        setAvailableKeys(MGT_OPT);
    }
    @SuppressWarnings("unchecked")
    public Operations(String str) {
        values = new EnumMap(standardKeys.class);
        availableKeys = new LinkedHashMap();
        this.parseSWATTXTFormat(str);
    }
    
    public Operations setValue(String keyStr, Object value)
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
        availableKeys.put("HUSC", standardKeys.IYEAR);
        availableKeys.put("MGT_OPT", standardKeys.MGT_OPT);
        switch (MGT_OPT) {
            case 1:
                availableKeys.put("TERR_P",standardKeys.F3);
                availableKeys.put("TERR_CN",standardKeys.F4);
                availableKeys.put("TERR_SL",standardKeys.F5);
                break;
            case 2:
                availableKeys.put("DRAIN_D",standardKeys.F3);
                availableKeys.put("DRAIN_T",standardKeys.F4);
                availableKeys.put("DRAIN_G",standardKeys.F5);
                availableKeys.put("DRAIN_IDEP",standardKeys.F6);
                break;
            case 3:
                availableKeys.put("CONT_CM", standardKeys.F3);
                availableKeys.put("CONT_P", standardKeys.F4);
                break;
            case 4:
                availableKeys.put("FILTER_U", standardKeys.F1);
                availableKeys.put("FILTER_RATIO", standardKeys.F2);
                availableKeys.put("FILTER_CON", standardKeys.F3);
                availableKeys.put("FILTER_CH", standardKeys.F4);
                break;
            case 5:
                availableKeys.put("STRIP_N",standardKeys.F3);
                availableKeys.put("STRIP_CN",standardKeys.F4);
                availableKeys.put("STRIP_C",standardKeys.F5);
                availableKeys.put("STRIP_P",standardKeys.F6);
                break;
            case 6:
                availableKeys.put("FIRE_CN", standardKeys.F3);
                break;
            case 7:
                availableKeys.put("GWATI",standardKeys.F1);
                availableKeys.put("GWATN",standardKeys.F2);
                availableKeys.put("GWATSPCON",standardKeys.F3);
                availableKeys.put("GWATD",standardKeys.F4);
                availableKeys.put("GWATW",standardKeys.F5);
                availableKeys.put("GWATL",standardKeys.F6);
                availableKeys.put("GWATS",standardKeys.F7);
                break;
            case 8:
                availableKeys.put("CROPNO_UPD",standardKeys.F1);
                availableKeys.put("HI_UPD",standardKeys.F3);
                availableKeys.put("LAIMX_UPD",standardKeys.F4);
                break;
            case 9:
                availableKeys.put("SO_RES_FLAG",standardKeys.F1);
                availableKeys.put("SO_RES",standardKeys.F3);
                break;
            case 10:
                availableKeys.put("RO_BMP_FLAG",standardKeys.F1);
                availableKeys.put("RO_BMP_SED",standardKeys.F2);
                availableKeys.put("RO_BMP_PP",standardKeys.F3);
                availableKeys.put("RO_BMP_SP",standardKeys.F4);
                availableKeys.put("RO_BMP_PN",standardKeys.F6);
                availableKeys.put("RO_BMP_SN",standardKeys.F7);
                availableKeys.put("RO_BMP_BAC",standardKeys.F8);
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
//    public String[] getAvailableKeys() {
//        return availableKeys.keySet().stream().toArray(String[]::new);
//    }
    
    public Operations parseSWATTXTFormat(String str) {
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
            if (key.equals(standardKeys.DUMMY1) ||
                key.equals(standardKeys.DUMMY2)) {
                if (strBldr.length()!=0) {
                    strBldr.insert(0, " ");
                    for (int idx2=key.getStartIDX(); idx2<=key.getEndIDX();idx2++)
                        strBldr.insert(0, " ");
                }
            } else {
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
        }
        return strBldr.insert(0," ").toString();
    }
}
