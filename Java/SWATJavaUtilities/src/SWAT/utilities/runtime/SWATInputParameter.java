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
public class SWATInputParameter {
    public static enum AssignmentType {
        assign, changeRate;
    }
    private String name;
    private String value;
    private AssignmentType assignmentType;
    private final List<Integer> applicableHRUs;
    
    @SuppressWarnings("unchecked")
    public SWATInputParameter(){
        this.applicableHRUs = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public SWATInputParameter(String name, String value, AssignmentType assignmentType, List<Integer> applicableHRUs) {
        this.name = name;
        this.value = value;
        this.assignmentType = assignmentType;
        this.applicableHRUs = new ArrayList();
        applicableHRUs.stream().filter((e) -> (this.applicableHRUs.indexOf(e)==-1)).forEachOrdered((e) -> {
            this.applicableHRUs.add(e);
        });
    }
    @SuppressWarnings("unchecked")
    public SWATInputParameter(String name, String value, String assignmentType, List<Integer> applicableHRUs) {
        this.name = name;
        this.value = value;
        this.assignmentType = AssignmentType.valueOf(assignmentType);
        this.applicableHRUs = new ArrayList();
        applicableHRUs.stream().filter((e) -> (this.applicableHRUs.indexOf(e)==-1)).forEachOrdered((e) -> {
            this.applicableHRUs.add(e);
        });
    }
    @SuppressWarnings("unchecked")
    public SWATInputParameter(String name, String value, AssignmentType assignmentType, int[] applicableHRUs) {
        this.name = name;
        this.value = value;
        this.assignmentType = assignmentType;
        this.applicableHRUs = new ArrayList();
        for (Integer e: applicableHRUs) {
            if (this.applicableHRUs.indexOf(e)==-1)
                this.applicableHRUs.add(e);
        }
    }
    @SuppressWarnings("unchecked")
    public SWATInputParameter(String name, String value, String assignmentType, int[] applicableHRUs) {
        this.name = name;
        this.value = value;
        this.assignmentType = AssignmentType.valueOf(assignmentType);
        this.applicableHRUs = new ArrayList();
        for (Integer e: applicableHRUs) {
            if (this.applicableHRUs.indexOf(e)==-1)
                this.applicableHRUs.add(e);
        }
    }

    public SWATInputParameter setName(String name) {
        this.name = name;
        return this;
    }
    public SWATInputParameter setValue(String value) {
        this.value = value;
        return this;
    }
    public SWATInputParameter setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
        return this;
    }
    public SWATInputParameter setAssignmentType(String assignmentType) {
        this.assignmentType = AssignmentType.valueOf(assignmentType);
        return this;
    }
    public SWATInputParameter setApplicableHRUs(List<Integer> applicableHRUs) {
        applicableHRUs.stream().filter((e) -> (this.applicableHRUs.indexOf(e)==-1)).forEachOrdered((e) -> {
            this.applicableHRUs.add(e);
        });
        return this;
    }
    public SWATInputParameter setApplicableHRUs(int[] applicableHRUs) {
        for (Integer e: applicableHRUs) {
            if (this.applicableHRUs.indexOf(e)==-1)
                this.applicableHRUs.add(e);
        }
        return this;
    }
    
    public SWATInputParameter addApplicableHRUs(Integer HRU) {
        if (applicableHRUs.indexOf(HRU)==-1)
            applicableHRUs.add(HRU);
        return this;
    }
    public SWATInputParameter addApplicableHRUs(int idx,Integer HRU) {
        if (applicableHRUs.indexOf(HRU)==-1)
            applicableHRUs.add(idx, HRU);
        return this;
    }
    public SWATInputParameter removeApplicableHRUs(Integer HRU) {
        applicableHRUs.remove(HRU);
        return this;
    }
    public SWATInputParameter removeApplicableHRUs(int idx) {
        applicableHRUs.remove(idx);
        return this;
    }
    public SWATInputParameter removeAllApplicableHRUs() {
        this.applicableHRUs.clear();
        return this;
    }
    
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
    public AssignmentType getAssignmentType() {
        return assignmentType;
    }
    public List<Integer> getApplicableHRUs() {
        return applicableHRUs;
    }
    public Integer getApplicableHRUs(int idx) {
        return this.applicableHRUs.get(idx);
    }
    public int numberOfApplicableHRUs() {
        return this.applicableHRUs.size();
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toJSONString() {
        StringBuilder strBldr = new StringBuilder("{");
        
        strBldr.append(String.format("Name: \"%s\"", this.name))
                .append(String.format(", Value: \"%s\"", this.value))
                .append(String.format(", assignmentType: \"%s\"", this.assignmentType));
        
        strBldr.append(", applicableHRUs: [");
        for (int idx=0;idx<applicableHRUs.size();idx++){
            if (idx == 0) {
                strBldr.append(String.format("%s",applicableHRUs.get(idx)));
            } else {
                strBldr.append(String.format(", %s",applicableHRUs.get(idx)));
            }
        }
        return strBldr.append("]}").toString();
    }
    public String toXMLString(){
        StringBuilder strBldr = new StringBuilder("<Parameter>");
        strBldr.append("<Name>").append(this.name).append("</Name>")
               .append("<Value>").append(this.value).append("</Value>")
               .append("<AssignmentType>").append(this.assignmentType.toString()).append("</AssignmentType>");
        strBldr.append("<ApplicableHRUs>");
        for (int idx=0;idx<this.applicableHRUs.size();idx++) {
            if (idx==0){
                strBldr.append(this.applicableHRUs.get(idx).toString());
            } else {
                strBldr.append(",").append(this.applicableHRUs.get(idx).toString());
            }
        }
        strBldr.append("</ApplicableHRUs>");
        return strBldr.append("</Parameter>").toString();
    }
}
