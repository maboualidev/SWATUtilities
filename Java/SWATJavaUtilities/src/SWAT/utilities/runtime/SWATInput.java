/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mabouali
 */
public class SWATInput {
    public static enum SWATInputTypes {
        Basins,
        GroundWater, 
        HydroResponseUnit,
        Management,
        OperationsFile,
        Ponds,
        Routing,
        Soil,
        Subbasin;
    }
    
    private SWATInputTypes SWATInputType;
    private String fileNameFormat;
    private final List<SWATInputParameter> parameters;
    
    @SuppressWarnings("unchecked")
    public SWATInput() {
        parameters = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public SWATInput(SWATInputTypes SWATInputType,String fileNameFormat) {
        this.SWATInputType = SWATInputType;
        this.fileNameFormat = fileNameFormat;
        parameters = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public SWATInput(String SWATInputType,String fileNameFormat) {
        this.SWATInputType = SWATInputTypes.valueOf(SWATInputType);
        this.fileNameFormat = fileNameFormat;
        parameters = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public SWATInput(SWATInputTypes SWATInputType,String fileNameFormat, List<SWATInputParameter> parameters) {
        this.SWATInputType = SWATInputType;
        this.fileNameFormat = fileNameFormat;
        this.parameters = new ArrayList();
        parameters.stream().filter((p) -> (this.parameters.indexOf(p)==-1)).forEachOrdered((p) -> {
            this.parameters.add(p);
        });
    }
    @SuppressWarnings("unchecked")
    public SWATInput(String SWATInputType,String fileNameFormat, List<SWATInputParameter> parameters) {
        this.SWATInputType = SWATInputTypes.valueOf(SWATInputType);
        this.fileNameFormat = fileNameFormat;
        this.parameters = new ArrayList();
        parameters.stream().filter((p) -> (this.parameters.indexOf(p)==-1)).forEachOrdered((p) -> {
            this.parameters.add(p);
        });
    }

    public SWATInput setSWATInputType(SWATInputTypes SWATInputType) {
        this.SWATInputType = SWATInputType;
        return this;
    }
    public SWATInput setSWATInputType(String SWATInputType) {
        this.SWATInputType = SWATInputTypes.valueOf(SWATInputType);
        return this;
    }
    public SWATInput setFileNameFormat(String fileNameFormat) {
        this.fileNameFormat = fileNameFormat;
        return this;
    }
    public SWATInput setParameters(List<SWATInputParameter> parameters) {
        parameters.stream().filter((p) -> (this.parameters.indexOf(p)==-1)).forEachOrdered((p) -> {
            this.parameters.add(p);
        });
        return this;
    }
    
    public SWATInput addParameters(SWATInputParameter p) {
        if (this.parameters.indexOf(p)==-1)
            this.parameters.add(p);
        return this;
    }
    public SWATInput addParameters(int idx, SWATInputParameter p) {
        if (this.parameters.indexOf(p)==-1)
            this.parameters.add(idx,p);
        return this;
    }
    public SWATInput removeParameters(SWATInputParameter p) {
        this.parameters.remove(p);
        return this;
    }
    public SWATInput removeParameters(int idx) {
        this.parameters.remove(idx);
        return this;
    }
    public SWATInput removeAllParameters(){
        this.parameters.clear();
        return this;
    }

    public SWATInputTypes getSWATInputType() {
        return SWATInputType;
    }
    public String getFileNameFormat() {
        return fileNameFormat;
    }
    public List<SWATInputParameter> getParameters() {
        return parameters;
    }
    public SWATInputParameter getParameters(int idx) {
        return parameters.get(idx);
    }
    public int numberOfParameters() {
        return this.parameters.size();
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toJSONString() {
        StringBuilder strBldr = new StringBuilder("{");
        strBldr.append(String.format("SWATInputType: \"%s\"", this.SWATInputType))
               .append(String.format(", FileNameFormat: \"%s\"", this.fileNameFormat));
        
        strBldr.append(", Parameters: [");
        for (int idx = 0; idx<this.parameters.size(); idx++) {
            if (idx == 0) {
                strBldr.append(this.parameters.get(idx).toJSONString());
            } else {
                strBldr.append(",").append(this.parameters.get(idx).toJSONString());
            }
        }
        return strBldr.append("]}").toString();
    }
    public String toXMLString(){
        StringBuilder strBldr = new StringBuilder("<"+this.SWATInputType.toString()+">");
        strBldr.append("<FileNameFormat>").append(this.fileNameFormat).append("</FileNameFormat>");
        
        strBldr.append("<Parameters>");
        this.parameters.forEach((e) -> {
            strBldr.append(e.toXMLString());
        });
        strBldr.append("</Parameters>");
        
        return strBldr.append("</").append(this.SWATInputType.toString()).append(">").toString();
    }
}
