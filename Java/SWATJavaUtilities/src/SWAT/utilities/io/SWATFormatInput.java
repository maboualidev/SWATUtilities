/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.io.IOException;

/**
 *
 * @author mabouali
 * @param <T>
 * @param <K>
 */
public interface SWATFormatInput <T, K> {
    public Class getFieldClassType(String fieldNameStr);
            
    public Object get(K fieldName);
    public Object get(String fieldNameStr);
    
    public T set(K fieldName,Object value);
    public T set(String fieldNameStr,Object value);
    public T set(String fieldNameStr,String value);
    
    public T readSWATFileFormat(String filename) throws IOException;
    public void writeSWATFileFormat(String filename) throws IOException;
}
