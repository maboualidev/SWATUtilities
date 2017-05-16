/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.runtime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mabouali
 */
public class RunSingleSWATConfig {
    public String runID;
    public boolean verbose;  
    private int verboseLevel;
    private final List<SWATInput> inputs;
    private final List<SWATOutputFile> outputs;
    private SWATExecution _SWATExecution;

    @SuppressWarnings("unchecked")
    public RunSingleSWATConfig() {
        this.verbose = true;
        this.inputs = new ArrayList();
        this.outputs = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public RunSingleSWATConfig(boolean verbose) {
        this.verbose = verbose;
        this.inputs = new ArrayList();
        this.outputs = new ArrayList();
    }
    @SuppressWarnings("unchecked")
    public RunSingleSWATConfig(String XMLFilename)
            throws ParserConfigurationException, IOException, SAXException {
        this.inputs = new ArrayList();
        this.outputs = new ArrayList();
        this.readFromXMLFile(XMLFilename);
    }

    public RunSingleSWATConfig setVerbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }
    public RunSingleSWATConfig setVerboseLevel(int verboseLevel) {
        if (verboseLevel>0)
            this.verboseLevel = verboseLevel;
        return this;
    }
    public RunSingleSWATConfig setInputs(List<SWATInput> inputs) {
        inputs.stream().filter((e) -> (this.inputs.indexOf(e)==-1)).forEachOrdered((e) -> {
            this.inputs.add(e);
        });
        return this;
    }
    public RunSingleSWATConfig setOutputs(List<SWATOutputFile> outputs) {
        outputs.stream().filter((e) -> (this.outputs.indexOf(e)==-1)).forEachOrdered((e) -> {
            this.outputs.add(e);
        });
        return this;
    }
    public RunSingleSWATConfig setSWATExecution(SWATExecution _SWATExecution) {
        this._SWATExecution = _SWATExecution;
        return this;
    }
    
    public RunSingleSWATConfig addSWATInput(SWATInput input) {
        if (this.inputs.indexOf(input)==-1) 
            this.inputs.add(input);
        return this;
    }
    public RunSingleSWATConfig addSWATInput(int idx, SWATInput input) {
        if (this.inputs.indexOf(input)==-1) 
            this.inputs.add(idx,input);
        return this;
    }
    public RunSingleSWATConfig removeSWATInput(SWATInput input) {
        this.inputs.remove(input);
        return this;
    }
    public RunSingleSWATConfig removeSWATInput(int idx) {
        this.inputs.remove(idx);
        return this;
    }
    public RunSingleSWATConfig removeAllSWATInputs() {
        this.inputs.clear();
        return this;
    }
    
    public RunSingleSWATConfig addSWATOutput(SWATOutputFile output) {
        if (this.outputs.indexOf(output)==-1) 
            this.outputs.add(output);
        return this;
    }
    public RunSingleSWATConfig addSWATOutput(int idx, SWATOutputFile output) {
        if (this.outputs.indexOf(output)==-1) 
            this.outputs.add(idx,output);
        return this;
    }
    public RunSingleSWATConfig removeSWATOutput(SWATOutputFile output) {
        this.outputs.remove(output);
        return this;
    }
    public RunSingleSWATConfig removeSWATOutput(int idx) {
        this.outputs.remove(idx);
        return this;
    }
    public RunSingleSWATConfig removeAllSWATOutputs() {
        this.outputs.clear();
        return this;
    }

    public boolean isVerbose() {
        return verbose;
    }
    public int getVerboseLevel() {
        return verboseLevel;
    }
    public List<SWATInput> getInputs() {
        return inputs;
    }
    public SWATInput getInput(int idx) {
        return this.inputs.get(idx);
    }
    public int numberOfInputs() {
        return this.inputs.size();
    }
    public List<SWATOutputFile> getOutputs() {
        return outputs;
    }
    public SWATOutputFile getOutputs(int idx){
        return this.outputs.get(idx);
    }
    public int numberOfOutputs() {
        return this.outputs.size();
    }
    public SWATExecution getSWATExecution() {
        return _SWATExecution;
    }
    
    public final RunSingleSWATConfig readFromXMLFile(String XMLFilename)
            throws ParserConfigurationException, IOException, SAXException {
        Document XMLDoc = ((DocumentBuilderFactory.newInstance()).newDocumentBuilder()).parse(new File(XMLFilename));
        XMLDoc.getDocumentElement().normalize();
        if (!XMLDoc.getDocumentElement().getNodeName().equals("RunSingleSWATConfig"))
            throw new Error("The root element of the XML file must be RunSingleSWATConfig.");
        
        // Parsing RundID
        runID = ((Element) XMLDoc.getElementsByTagName("RunID").item(0)).getTextContent().trim();
        
        //Parsing the Inputs
        NodeList nodeList = XMLDoc.getElementsByTagName("Inputs");
        if (nodeList.getLength()!=1) {
            throw new IOException("There must be exactly one <Inputs>...</Inputs> section.");
        } else {
            Element XMLInputElements = (Element) nodeList.item(0); // This is the root for all inputs
            for (SWATInput.SWATInputTypes tagName: SWATInput.SWATInputTypes.values()) { // Searching for different input types
                String tagNameStr = tagName.toString();
                nodeList = XMLInputElements.getElementsByTagName(tagNameStr); // nodeList contains all the inputs of type tagName
                if (nodeList.getLength()>0) { 
                    for (int idx = 0; idx<nodeList.getLength(); idx++) { // Processing each input of type tagName separately
                        Element inputElement = (Element) nodeList.item(idx); // This is the root for one input of type tagName
                        SWATInput _SWATInput = new SWATInput();
                        _SWATInput.setSWATInputType(tagName)
                                  .setFileNameFormat(inputElement.getElementsByTagName("FileNameFormat").item(0).getTextContent().trim());

                        NodeList parameterList = ((Element)inputElement.getElementsByTagName("Parameters").item(0)).getElementsByTagName("Parameter");
                        if (parameterList.getLength()<1)
                            throw new IOException("There must be at least one Parameter defined for each input.");
                        for (int idx2=0; idx2<parameterList.getLength();idx2++) {
                            String name = ((Element) parameterList.item(idx2)).getElementsByTagName("Name").item(0).getTextContent().trim();
                            String value = ((Element) parameterList.item(idx2)).getElementsByTagName("Value").item(0).getTextContent().trim();
                            String assignmentType = ((Element) parameterList.item(idx2)).getElementsByTagName("AssignmentType").item(0).getTextContent().trim();
                            String[] strList = ((Element) parameterList.item(idx2))
                                               .getElementsByTagName("ApplicableHRUs").item(0).getTextContent()
                                               .trim().split(",");
                            int[] applicableHRUs = new int[strList.length];
                            for (int idx3=0; idx3<strList.length;idx3++){
                                applicableHRUs[idx3] = Integer.parseInt(strList[idx3].trim());
                            }

                            _SWATInput.addParameters(new SWATInputParameter(name,value,assignmentType,applicableHRUs));
                        } 

                        // Adding this single input to the config
                        this.addSWATInput(_SWATInput);
                    }

                }
            }
        } // END OF PARSING INPUTS
        
        // Parsing SWATExecution Section
        nodeList = XMLDoc.getElementsByTagName("SWATExecution");
        if (nodeList.getLength()!=1) {
            throw new IOException("There must be exactly one <SWATExecution>...</SWATExecution> section.");
        } else {
            Element XMLSWATExecutionElement = (Element) nodeList.item(0);
            this.setSWATExecution( 
                    new SWATExecution(XMLSWATExecutionElement.getElementsByTagName("WorkingDirectory").item(0).getTextContent().trim(),
                                      XMLSWATExecutionElement.getElementsByTagName("SWATBinName").item(0).getTextContent().trim(),
                                      XMLSWATExecutionElement.getElementsByTagName("TemplateZIP").item(0).getTextContent().trim(),
                                      XMLSWATExecutionElement.getElementsByTagName("DeleteWorkingDirectory").item(0).getTextContent().trim().equals("True"),
                                      XMLSWATExecutionElement.getElementsByTagName("RollBack").item(0).getTextContent().trim().equals("True")) 
            );
        } // END OF PARSING SWATExecution
        
        // Parsing Display Section
        nodeList = XMLDoc.getElementsByTagName("Display");
        if (nodeList.getLength()!=1) {
            throw new IOException("There must be exactly one <Display>...</Display> section.");
        } else {
            Element XMLDisplayElement = (Element) nodeList.item(0);
            this.verbose = XMLDisplayElement.getElementsByTagName("Verbose").item(0).getTextContent().trim().equals("True");
            this.verboseLevel = Integer.parseInt( XMLDisplayElement.getElementsByTagName("VerboseLevel").item(0).getTextContent().trim() );
        } // END OF PARSING Display
        
        // Parsing Outputs Section
        nodeList = XMLDoc.getElementsByTagName("Outputs");
        if (nodeList.getLength()!=1) {
            throw new IOException("There must be exactly one <Outputs>...</Outputs> section.");
        } else {
            Element XMLOutputsElement = (Element) nodeList.item(0); // This is the root for all Outputs.
            // Parsing Files section in the Outputs section
            NodeList OutputFileList = XMLOutputsElement.getElementsByTagName("File");
            for (int idx=0; idx<OutputFileList.getLength(); idx++){
                Element FileElement = (Element) OutputFileList.item(idx);
                this. addSWATOutput(
                        new SWATOutputFile( FileElement.getElementsByTagName("OriginalName").item(0).getTextContent().trim(),
                                            FileElement.getElementsByTagName("NewName").item(0).getTextContent().trim(),
                                            FileElement.getElementsByTagName("Location").item(0).getTextContent().trim()) 
                );
            }// END OF PARSING Files section in the Outputs section
        } // END OF PARSING Outputs
        return this;
    }
    public void writeToXMLFile(String XMLFilename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(XMLFilename),StandardCharsets.UTF_8)) {
            writer.write(this.toXMLString());
        }
    }
    public RunSingleSWATConfig newFromXMLFile(String XMLFilename)
            throws ParserConfigurationException, IOException, SAXException {
        RunSingleSWATConfig config = new RunSingleSWATConfig(XMLFilename);
        return config;
    }
            

    @Override
    public String toString() {
        return this.toJSONString();
    }
    public String toJSONString() {
        StringBuilder strBldr = new StringBuilder("{");
        
        strBldr.append("Inputs: [");
        for (int idx = 0; idx< this.inputs.size(); idx++) {
            if (idx==0) {
                strBldr.append(this.inputs.get(idx).toJSONString());
            } else {
                strBldr.append(", ")
                       .append(this.inputs.get(idx).toJSONString());
            }
        }
        strBldr.append("]");
        
        strBldr.append(", Outputs: [");
        for (int idx = 0; idx< this.outputs.size(); idx++) {
            if (idx==0) {
                strBldr.append(this.outputs.get(idx).toJSONString());
            } else {
                strBldr.append(", ")
                       .append(this.outputs.get(idx).toJSONString());
            }
        }
        strBldr.append("]");
        
        strBldr.append(", SWATExecution: ")
               .append(_SWATExecution.toJSONString());
        
        return strBldr.append("}").toString();
    }
    public String toXMLString() {
        StringBuilder strBldr = new StringBuilder("<RunSingleSWATConfig>");
        strBldr.append("<RunID>").append(this.runID).append("</RunID>");
        
        strBldr.append("<Inputs>");
        this.inputs.forEach((e) -> {
            strBldr.append(e.toXMLString());
        });
        strBldr.append("</Inputs>");
        
        strBldr.append(this._SWATExecution.toXMLString());
        
        strBldr.append("<Outputs>");
        this.outputs.forEach((e) -> {
            strBldr.append(e.toXMLString());
        });
        strBldr.append("</Outputs>");
        
        strBldr.append("<Display>")
               .append("<Verbose>").append(this.verbose).append("</Verbose>")
                .append("<VerboseLevel>").append(this.verboseLevel).append("</VerboseLevel>")
               .append("</Display>");
        
        return strBldr.append("</RunSingleSWATConfig>").toString();
    }
}
