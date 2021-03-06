/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.runtime;

import SWAT.utilities.common.*;
import SWAT.utilities.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author mabouali
 */
public class RunSingleSWAT {
    public RunSingleSWATConfig config;
    private RollBackManger rbm;
    
    public RunSingleSWAT() {
    }
    
    @SuppressWarnings("unchecked")
    public RunSingleSWAT(String XMLFilename)
            throws ParserConfigurationException, IOException, SAXException {
        this.config = new RunSingleSWATConfig(XMLFilename);
    }
    
    public RunSingleSWAT readFromXMLFile(String XMLFilename)
            throws ParserConfigurationException, IOException, SAXException {
        this.config = new RunSingleSWATConfig(XMLFilename);
        return this;
    }
    public static RunSingleSWAT newFromXMLFile(String XMLFilename)
            throws ParserConfigurationException, IOException, SAXException {
        return (new RunSingleSWAT(XMLFilename));
    }
    
    public void prepareWorkingDirectory()
            throws IOException {
        if (config.verbose)
            System.out.println("Preparing the working directory ...");
        Path workingPath = Paths.get(config.getSWATExecution().getWorkingDirectory());
        if (Files.exists(workingPath)) {
            if (config.verbose)
                System.out.println("- Working directory already exists. Doing nothing");
        } else {
            if (config.verbose)
                System.out.println("- Working directory didn't exist. Creating one ...");
            Files.createDirectories(workingPath);
            
            if (config.verbose)
                System.out.println("- Proceeding with unzipping the base SWAT model ...");
            unzipSWATModel();
        }
            
    }
    public void unzipSWATModel()
            throws IOException{
        if (config.verbose)
            System.out.println("Starting to unzip base SWAT model ...");
        
        UnzipUtility.unzip(config.getSWATExecution().getTemplateZip(),
                           config.getSWATExecution().getWorkingDirectory(),
                           (config.verbose && config.getVerboseLevel()>2));
        
    }

    public static SWATFormatInput loadInput(SWATInput.SWATInputTypes inputType, String filename)
            throws IOException {
        SWATFormatInput input;
        switch (inputType) {
            case Basins:
                input = new Basins(filename);
                break;
            case GroundWater:
                input = new GroundWater(filename);
                break;
            case HydroResponseUnit:
                input = new HydroResponseUnit(filename);
                break;
            case Management:
                input = new Management(filename);
                break;
            case OperationsFile:
                input = new OperationsFile(filename);
                break;
            case Ponds:
                input = new Ponds(filename);
                break;
            case Routing:
                input = new Routing(filename);
                break;
            case Soil:
                input = new Soil(filename);
                break;
            case Subbasin:
                input = new Subbasin(filename);
                break;
            default:
                throw new Error(String.format ("Input type %s is not recognized.",inputType.toString()));
        }
        return input;
    }
    @SuppressWarnings("unchecked")
    public void modifySWATInputFiles()
            throws IOException {
        if (config.verbose)
            System.out.println("Modifying SWAT Input files: ...");
        
        rbm = new RollBackManger(config.getSWATExecution().getWorkingDirectory());
        // It is possible that the config file has different section that 
        // affects the same file. In order to reduce disk I/O all files are 
        // first modified in memory then they are written to disk.
        // Map Input takes care of this.
        Map<File,SWATFormatInput> inputs = new HashMap();
        for (SWATInput inputConfig: config.getInputs()) {
            SWATInput.SWATInputTypes inputType = inputConfig.getSWATInputType();
            if (config.verbose)
                System.out.println("- Input type: " + inputConfig.getSWATInputType());
            String fileFMT = inputConfig.getFileNameFormat();
            
            for (SWATInputParameter paramConfig: inputConfig.getParameters()) {
                String paramName = paramConfig.getName();
                String paramValue = paramConfig.getValue();
                SWATInputParameter.AssignmentType assignmentType = paramConfig.getAssignmentType();
                if (config.verbose && config.getVerboseLevel()>1)
                    System.out.println("- - Parameter Name: " + paramName);
                
                // getting the FileList that needs to be modified
                File[] fileList;
                List<Integer> HRUIDs = paramConfig.getApplicableHRUs();
                if (HRUIDs.contains(-1)) {
                    // All HRUs
                    fileList = (new File(config.getSWATExecution().getWorkingDirectory()))
                            .listFiles( (d,name) -> name.matches(fileFMT.replaceAll("%.*?d", ".*?")));
                } else {
                    // Only specific HRUs
                    fileList = new File[HRUIDs.size()];
                    for (int idx=0; idx<HRUIDs.size();idx++) {
                        String baseFilename = String.format(fileFMT, (int) HRUIDs.get(idx));
                        fileList[idx] = new File(RollBackManger.getChildAbsolutePath(config.getSWATExecution().getWorkingDirectory(), baseFilename));
                    }
                }
                    
                for (File file: fileList) {
                    if (config.verbose && config.getVerboseLevel()>1)
                        System.out.println("- - - File: " + file);
                    if (config.getSWATExecution().rollBack()) {
                        rbm.backup(file.getName());
                    }
                    if (!inputs.containsKey(file))
                        inputs.put(file, loadInput(inputType, file.toString()));
                    SWATFormatInput input = inputs.get(file);
                    switch (assignmentType) {
                        case assign:
                            input.set(paramName, paramValue);
                            break;
                        case changeRate:
                            Double changeRateValue = Double.parseDouble(paramValue);
                            if (input.getFieldClassType(paramName).equals(Double.class) ||
                                input.getFieldClassType(paramName).equals(Integer.class)) {
                                Double valueNumber = (1.0+changeRateValue)*Double.parseDouble(input.get(paramName).toString());
                                input.set(paramName, valueNumber.toString());
                            }
                            if (input.getFieldClassType(paramName).equals((new Double[0]).getClass())) {
                                Double[] valueNumber = (Double[]) input.get(paramName);
                                StringBuilder strBldr = new StringBuilder();
                                for (int idx = 0; idx<valueNumber.length; idx++) {
                                    if (idx == 0 ) {
                                        strBldr.append((1.0+changeRateValue)*valueNumber[idx]);
                                    } else {
                                        strBldr.append(",").append((1.0+changeRateValue)*valueNumber[idx]);
                                    }
                                }
                                input.set(paramName, strBldr.toString());
                            }
                            break;
                        default:
                            throw new Error("Specified assignment type is not recognized.");
                    }
                } // END OF Looping over HRUs
            } // END OF Looping over parameters
        } // END OF Looping over inputs
        
        // updating the inputfiles
        for (File file: inputs.keySet()) 
            inputs.get(file).writeSWATFileFormat(file.toString());
    }
    
    
    public void preRunTasks()
            throws IOException {
        prepareWorkingDirectory();
        modifySWATInputFiles();
    }
    public Integer runSWAT()
            throws IOException, InterruptedException {
        Integer exitValue = null;
        if (config.verbose)
            System.out.println("Running SWAT model");

        String OS = System.getProperty("os.name");
        if (OS.toLowerCase().contains("windows")) {
            if (config.verbose)
                System.out.printf("- Windows (%s) System detected: running swat in CMD ...\n",OS);
            
            String cmd = String.format("cmd /c \"cd /D %s & %s\"", 
                                       config.getSWATExecution().getWorkingDirectory(),
                                       config.getSWATExecution().getSWATBinName());
            Process p = Runtime.getRuntime().exec(cmd);
            if (config.verbose && config.getVerboseLevel()>1) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) !=null) {
                        System.out.println("- - "+line);
                    }
                }
            }
            p.waitFor();
            exitValue = (Integer) p.exitValue();
            p.destroy();
        }

        if (null == exitValue) {
            throw new Error("This program is executed on an unsupported and/or unidentified operating system.");
        } else switch (exitValue) {
            case 0:
                if (config.verbose)
                    System.out.println("- SWAT executed successfully. You could proceed with post SWAT run tasks.");
                break;
            default:
                throw new Error("Something went wrong while running SWAT.");
        }
        return exitValue;
    }
    
    public void rollBackSWATInputFiles()
            throws IOException {
        if (config.getSWATExecution().rollBack()) {
            if (config.verbose)
                System.out.println("Rolling back SWAT Inputs to their original states ...");
            rbm.rollBackAll(true);
        }    
    }
    public void backUpOutputFiles()
            throws IOException {
        if (config.verbose)
            System.out.println("Making a backup of the outputs ...");
        List<SWATOutputFile> outputs = config.getOutputs();
        for (SWATOutputFile output: outputs) {
            Path outputFolder = Paths.get(output.getLocation());
            Path srcFile = Paths.get(config.getSWATExecution().getWorkingDirectory(),output.getOriginalName());
            Path dstFile = Paths.get(output.getLocation(),output.getNewName());
            if (config.verbose && config.getVerboseLevel()>1)
                System.out.printf("Backing up %s to %s ...\n",output.getOriginalName(),output.getNewName());
            if (!Files.exists(outputFolder)) {
                if (config.verbose && config.getVerboseLevel()>1)
                    System.out.println("- - Creating " + outputFolder);
                Files.createDirectories(outputFolder);
            }
            Files.move(srcFile, dstFile,StandardCopyOption.REPLACE_EXISTING);
        }
    }
    public void deleteWorkingDirectory()
            throws IOException {
        if (config.getSWATExecution().deleteWorkingDirectory()) {
            if (config.verbose) 
                System.out.println("Removing the SWAT working directory ...");
            Files.list(Paths.get(config.getSWATExecution().getWorkingDirectory())).forEach(
                (file) -> {
                    if (config.verbose && config.getVerboseLevel()>2)
                        System.out.println("Removing " + file);
                    try {
                        Files.delete(file);
                    } catch (IOException ex) {
                        Logger.getLogger(RunSingleSWAT.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            
            Files.delete(Paths.get(config.getSWATExecution().getWorkingDirectory()));
        }
    }
    public void postRunTasks()
            throws IOException {
        backUpOutputFiles();
        rollBackSWATInputFiles();
        deleteWorkingDirectory();
    }
    
    public Integer runAll()
            throws IOException, InterruptedException{
        prepareWorkingDirectory();
        modifySWATInputFiles();        
        Integer exitValue = runSWAT();
        rollBackSWATInputFiles();
        backUpOutputFiles();
        deleteWorkingDirectory();
        return exitValue;
    }
    
    public static Integer executeXMLConfig(String XMLFile)
            throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        RunSingleSWAT SWATRun = RunSingleSWAT.newFromXMLFile(XMLFile);
        return SWATRun.runAll();
    }
}
