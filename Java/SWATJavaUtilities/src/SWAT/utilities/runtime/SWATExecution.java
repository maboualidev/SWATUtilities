/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.runtime;

/**
 *
 * @author mabouali
 */
public class SWATExecution {
    private String workingDirectory;
    private String SWATBinName;
    private String templateZip;
    private boolean deleteWorkingDirectory;
    private boolean rollBack;

    public SWATExecution(String workingDirectory, String SWATBinName, String templateZip, boolean deleteWorkingDirectory, boolean rollBack) {
        this.workingDirectory = workingDirectory;
        this.SWATBinName = SWATBinName;
        this.templateZip = templateZip;
        this.deleteWorkingDirectory = deleteWorkingDirectory;
        this.rollBack = rollBack;
    }

    public SWATExecution setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
        return this;
    }

    public SWATExecution setSWATBinName(String SWATBinName) {
        this.SWATBinName = SWATBinName;
        return this;
    }    
    public SWATExecution setTemplateZip(String templateZip) {
        this.templateZip = templateZip;
        return this;
    }
    public SWATExecution setDeleteWorkingDirectory(boolean deleteWorkingDirectory) {
        this.deleteWorkingDirectory = deleteWorkingDirectory;
        return this;
    }
    public SWATExecution setRollBack(boolean rollBack) {
        this.rollBack = rollBack;
        return this;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }
    public String getSWATBinName() {
        return SWATBinName;
    }
    public String getTemplateZip() {
        return templateZip;
    }
    public boolean deleteWorkingDirectory() {
        return deleteWorkingDirectory;
    }
    public boolean rollBack() {
        return rollBack;
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toJSONString() {
        return "{WorkingDIrectory: \"" + this.workingDirectory +"\"" +
               ", SWATBinName: \"" + this.SWATBinName + "\"" +
               ", TemplateZip: \"" + this.templateZip +"\"" +
               ", DeleteWorkingDirectory: " + this.deleteWorkingDirectory +
               ", RollBack: " + this.rollBack +"}";
    }
    public String toXMLString(){
        StringBuilder strBldr = new StringBuilder("<SWATExecution>");
        strBldr.append("<WorkingDirectory>").append(this.workingDirectory).append("</WorkingDirectory>")
               .append("<SWATBinName>").append(this.SWATBinName).append("</SWATBinName>")
               .append("<TemplateZIP>").append(this.templateZip).append("</TemplateZIP>")
               .append("<DeleteWorkingDirectory>").append(this.deleteWorkingDirectory).append("</DeleteWorkingDirectory>")
               .append("<RollBack>").append(this.rollBack).append("</RollBack>");
        return strBldr.append("</SWATExecution>").toString();
    }
}
