/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mabouali
 */
public final class OperationsFile
        implements SWATFormatInput<OperationsFile,OperationsFile.fields>{
    public static enum fields {
        TITLE, OPERATIONS;
    }
    private String title;
    public List<Operations> ops;
    
    @SuppressWarnings("unchecked")
    public OperationsFile(){
        this.title = "";
        this.ops = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public OperationsFile(String fileName)
            throws IOException{
        this.title = "";
        this.ops = new ArrayList();
        this.readSWATFileFormat(fileName);
    }
    
    public int numberOfOperations() {
        return ops.size();
    }

    public OperationsFile setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public OperationsFile addOperation(Operations op) {
        ops.add(op);
        return this;
    }
    public OperationsFile addOperation(String SWATTextFormatOp) {
        ops.add(new Operations(SWATTextFormatOp));
        return this;
    }
    public OperationsFile addOperation(int idx, Operations op) {
        ops.add(idx, op);
        return this;
    }
    public OperationsFile addOperation(int idx, String SWATTextFormatOp) {
        ops.add(idx, new Operations(SWATTextFormatOp));
        return this;
    }
    
    @Override
    public OperationsFile set(fields fieldName,Object value) {
        switch (fieldName) {
            case TITLE:
                if (value.getClass().equals(String.class)) {
                    this.title = (String) value;
                } else {
                    throw new IllegalArgumentException("TITLE value must be String");
                }
                break;
            case OPERATIONS:
                if (value.getClass().equals(Operations.class)) {
                    this.addOperation((Operations) value);
                } else {
                    throw new IllegalArgumentException("OPERATION value must be of type SWAT.utilities.Operations");
                }
                break;
            default:
                throw new IllegalArgumentException(String.format("%s field is not recognized.", fieldName.toString()));
        }
        return this;
    }
    @Override
    public OperationsFile set(String fieldNameStr,Object value) {
        return set(fields.valueOf(fieldNameStr), value);
    }
    @Override
    public OperationsFile set(String fieldNameStr,String value) {
        if (fieldNameStr.equals("TITLE")) {
            return set(fields.TITLE,value);
        } else {
            throw new IllegalArgumentException("set(String,String) is only defined for \"TITLE\"");
        }
    }
    
    @Override
    public Object get(fields fieldName)
            throws IllegalArgumentException {
        switch (fieldName) {
            case TITLE:
                return this.title;
            case OPERATIONS:
                return this.ops;
            default:
                throw new IllegalArgumentException(String.format("%s is not recognized.",fieldName));
        }
    }
    @Override
    public Object get(String fieldNameStr)
            throws IllegalArgumentException {
        return get(fields.valueOf(fieldNameStr));
    }
    @Override
    public Class getFieldClassType(String fieldNameStr) {
        if (fieldNameStr.equals("TITLE")) {
            return String.class;
        } else if (fieldNameStr.equals("OPERATIONS")) {
            return Operations.class;
        } else {
            throw new IllegalArgumentException(fieldNameStr + " is not recognized.");
        }
    }
    
    @Override
    public OperationsFile readSWATFileFormat(String filename)
            throws IOException{
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename),StandardCharsets.UTF_8)) {
            String line;
            boolean titleIsRead = false;
            while ((line = reader.readLine())!=null) {
                if (titleIsRead) {
                    this.addOperation(line);
                } else {
                    this.title = line.trim();
                    titleIsRead = true;
                }
            }
        }
        return this;
    }
    @Override
    public void writeSWATFileFormat(String filename)
            throws IOException {
        
    }
    public static OperationsFile newFromSWATFile(String filename)
            throws IOException {
        return (new OperationsFile(filename));
    }
    @SuppressWarnings("unchecked")
    public static List<OperationsFile> newFromSWATFiles(String[] filenames)
            throws IOException {
        List opfs = new ArrayList(filenames.length);
        for (String filename: filenames)
            opfs.add(new OperationsFile(filename));
        return opfs;
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
        strBldr.append(String.format("Title: \"%s\"",this.title));
        
        strBldr.append(", Operations: [");
        for (int idx=0; idx<ops.size();idx++) {
            if (idx==0) {
                strBldr.append(ops.get(idx).toJSONString());
            } else {
                strBldr.append(", ").append(ops.get(idx).toJSONString());
            }
        }
        return strBldr.append("]}").toString();
    }
    public String toSWATTXTFormat() {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(this.title).append("\n");
        this.ops.forEach((op) -> {
            strBldr.append(op.toSWATTXTFormat()).append("\n");
        });
        
        return strBldr.toString();
    }
}
