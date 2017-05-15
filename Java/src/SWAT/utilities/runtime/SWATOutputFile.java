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
public class SWATOutputFile {
    private String originalName;
    private String newName;
    private String location;

    public SWATOutputFile(String originalName, String newName, String location) {
        this.originalName = originalName;
        this.newName = newName;
        this.location = location;
    }

    public SWATOutputFile setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }
    public SWATOutputFile setNewName(String newName) {
        this.newName = newName;
        return this;
    }
    public SWATOutputFile setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getOriginalName() {
        return originalName;
    }
    public String getNewName() {
        return newName;
    }
    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toJSONString() {
        return "{OriginalName: \"" + originalName + "\""+
               ", newName: \"" + newName + "\"" +
               ", Location: \"" + location + "\"}";
    }
    public String toXMLString() {
        StringBuilder strBldr = new StringBuilder("<File>");
        strBldr.append("<OriginalName>").append(this.originalName).append("</OriginalName>")
               .append("<NewName>").append(this.newName).append("</NewName>")
               .append("<Location>").append(this.location).append("</Location>");
        return strBldr.append("</File>").toString();
    }
}
