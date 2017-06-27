/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * RollBackManager is a utility that allows the user to backup some files and 
 * restore the original file later. SWAT model, for example, could potentially
 * contain hundreds of files. For example in one of the project I was involved,
 * the SWAT model contained more than 190K files. This utility, i.e. RollBackManager
 * allows you to change the SWAT files during your run; however, keep a copy of
 * the original files. Later, you could give a call to this utility and restore 
 * the files to their original state prior to the changes.
 * 
 * @author Mohammad Abouali
 */
public class RollBackManger {
    /**
     * It stores the default source folder. The default value is the current 
     * folder.
     */
    private String defaultSourceFolder;
    
    /**
     * It stores the location of the backup folder. The default value is the a 
     * folder called "BackupFolder" within the sourceFolder.
     */
    private String backupFolder;
    
    /**
     * Tracks whether the backup folder is already created. It can not be changed
     * once some files are backed up.
     */
    private boolean isBackupFolderAvailable;
    
    /**
     * A map from original file path to its backup. The key is the absolute 
     * path to the original location of the file and the value is the absolute 
     * path to the where it was backed up.
     */
    private Map<String,String> fileList;

    /**
     * default constructor, setting the source folder to the current path and 
     * the back folder to backupFolder within the current path.
     */
    @SuppressWarnings("unchecked")
    public RollBackManger() {
        this.defaultSourceFolder = turn2AbsolutePath("");
        this.backupFolder = getChildAbsolutePath(this.defaultSourceFolder, "backupFolder");
        this.fileList = new HashMap();
        this.isBackupFolderAvailable = false;
    }
    /**
     * Sets the default source folder to the provided one and the backup folder 
     * is set to backupFolder within the default source folder.
     * @param defaultSrcFolder 
     */
    @SuppressWarnings("unchecked")
    public RollBackManger(String defaultSrcFolder) {
        this.defaultSourceFolder = turn2AbsolutePath(defaultSrcFolder);
        this.backupFolder = getChildAbsolutePath(defaultSrcFolder, "backupFolder");
        this.fileList = new HashMap();
        this.isBackupFolderAvailable = false;
    }
    /**
     * Sets both the default source folder and backup folder to those provided 
     * by the user.
     * @param defaultSrcFolder
     * @param backupFolder 
     */
    @SuppressWarnings("unchecked")
    public RollBackManger(String defaultSrcFolder, String backupFolder) {
        this.defaultSourceFolder = turn2AbsolutePath(defaultSrcFolder);
        this.backupFolder = turn2AbsolutePath(backupFolder);
        this.fileList = new HashMap();
        this.isBackupFolderAvailable = false;
    }

    /**
     * changes/sets the default source folder.
     * @param defaultSourceDIR
     * @return 
     */
    public RollBackManger setDefaultSourceDIR(String defaultSourceDIR) {
        this.defaultSourceFolder = turn2AbsolutePath(defaultSourceDIR);
        return this;
    }
    /**
     * Changes/sets the backup folder. It only works if there is no file 
     * already backed up, i.e. the file list is empty. If the fileList is not
     * empty any attempt to change the backup folder will results in throwing an
     * error.
     * @param backupFolder
     * @return 
     * @throws Error
     */
    public RollBackManger setBackupFolder(String backupFolder) {
        if (!fileList.isEmpty()) {
            throw new Error("Cannot change backup folder location once there are some files backed up already.");
        }
        this.backupFolder = turn2AbsolutePath(backupFolder);
        return this;
    }
    
    /**
     * returns the absolute path to the default source Folder
     * @return 
     */
    public String getDefaultSourceFolder() {
        return defaultSourceFolder;
    }
    /**
     * returns the absolute path to the current backup folder.
     * @return 
     */
    public String getBackupFolder() {
        return backupFolder;
    }

    /**
     * It would return the size of the file list, i.e. how many files are already
     * backed up.
     * @return size of the file list
     */
    public int size() {
        return this.fileList.size();
    }
    
    /**
     * It returns a Set containing a list of the absolute path to all the
     * original files that are currently backed up.
     * @return 
     */
    public Set<String> originalFileList() {
        return fileList.keySet();
    }
    
    /**
     * It returns a Collection containing the absolute path to all the backed up
     * files
     * @return 
     */
    public Collection<String> bkpFileList() {
        return fileList.values();
    }
    
    /**
     * Checks whether a file is already backed up or not.
     * @param parentFolder
     * @param filename
     * @return 
     */
    public boolean isBackedUp(String parentFolder, String filename) {
        String originalFileStr = getChildAbsolutePath(parentFolder, filename);
        return fileList.containsKey(originalFileStr);
    }
    /**
     * The same as calling isBackedUp(defaultSrcFolder,filename);
     * @param filename
     * @return 
     */
    public boolean isBackedUp(String filename) {
        return isBackedUp(this.defaultSourceFolder, filename);
    }
    
    /**
     * Checks whether the back folder exists or not. If not, it creates one.
     * @return
     * @throws IOException 
     */
    public RollBackManger prepareBackupFolder()
            throws IOException {
        File backupFolderFile = new File(backupFolder);
        if (!backupFolderFile.exists()) {
            Files.createDirectories(backupFolderFile.toPath());
        }
        this.isBackupFolderAvailable = true;
        return this;
    }
    
    /**
     * It will back up the file that is within the parent folder.
     * @param parentFolder
     * @param filename
     * @param forceBackup if it is set to true, the file is backed up even if it 
     *                    was already backed up. If it is set to false, and the
     *                    file is already backed up it won't be copied again to 
     *                    the backup folder but an error message is printed; 
     *                    however, no exception is thrown.
     * @return
     * @throws IOException 
     */
    public RollBackManger backup(String parentFolder, String filename, boolean forceBackup)
            throws IOException {
        if (!isBackupFolderAvailable)
            prepareBackupFolder();
        
        String originalFileStr = getChildAbsolutePath(parentFolder, filename);
        String bkpFileStr = getChildAbsolutePath(this.backupFolder, filename);

        if (forceBackup) {
            Files.copy( (new File(originalFileStr)).toPath(), 
                        (new File(bkpFileStr)).toPath(), 
                        StandardCopyOption.REPLACE_EXISTING);
            fileList.put(originalFileStr, bkpFileStr);
        } else {
            if (fileList.containsKey(originalFileStr)) {
                System.err.printf("%s already exists.", originalFileStr);
            } else {
                Files.copy((new File(originalFileStr)).toPath(), 
                           (new File(bkpFileStr)).toPath());
                fileList.put(originalFileStr, bkpFileStr);
            }
        }
        return this;
    }
    /**
     * The same as calling backup(parentFolder,filename,false);
     * @param parentFolder
     * @param filename
     * @return
     * @throws IOException 
     */
    public RollBackManger backup(String parentFolder, String filename)
            throws IOException {
        return backup(parentFolder, filename, false);
    }
    /**
     * The same as calling backup(defaultSrcFolder,filename,forceBackup);
     * @param filename
     * @param forceBackup
     * @return
     * @throws IOException 
     */
    public RollBackManger backup(String filename, boolean forceBackup)
            throws IOException {
        return backup(this.defaultSourceFolder,filename,forceBackup);
    }
    /**
     * The same as calling backup(defaultSrcFolder,filename,false);
     * @param filename
     * @return
     * @throws IOException 
     */
    public RollBackManger backup(String filename)
            throws IOException {
        return backup(this.defaultSourceFolder,filename,false);
    }

    /**
     * If the file is backed up it is restored. Otherwise, it does nothing.
     * @param parentFolder original parent folder name for the file to be restored
     * @param filename the filename
     * @param deleteBackup If set to true the backup of the file will be deleted
     *                     and removed from the list.
     * @return
     * @throws IOException 
     */
    public RollBackManger rollBack(String parentFolder, String filename, boolean deleteBackup)
            throws IOException {
        String originalFileStr = getChildAbsolutePath(parentFolder, filename);
        if (fileList.containsKey(originalFileStr)) {
            Path bkpFilePath = (new File(fileList.get(originalFileStr))).toPath();
            Files.copy( bkpFilePath,
                        (new File(originalFileStr)).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            if (deleteBackup) {
                Files.delete(bkpFilePath);
                fileList.remove(originalFileStr);
            }
        }
        return this;
    }
    /**
     * The same as calling rollBack(parentFolder,filename,true);
     * @param parentFolder
     * @param filename
     * @return
     * @throws IOException 
     */
    public RollBackManger rollBack(String parentFolder, String filename)
            throws IOException {
        return rollBack(parentFolder,filename,true);
    }
    /**
     * The same as calling rollBack(defaultSrcFolder,filename,deleteBackup);
     * @param filename
     * @param deleteBackup
     * @return
     * @throws IOException 
     */
    public RollBackManger rollBack(String filename, boolean deleteBackup)
            throws IOException {
        return rollBack(this.defaultSourceFolder,filename,deleteBackup);
    }
    /** 
     * The same as calling rollBack(defaultSrcFolder,filename,true);
     * @param filename
     * @return
     * @throws IOException 
     */
    public RollBackManger rollBack(String filename)
            throws IOException {
        return rollBack(this.defaultSourceFolder,filename,true);
    }
    /**
     * It will roll back or restore all the backup files.
     * @param deleteBackup
     * @return
     * @throws IOException 
     */
    public RollBackManger rollBackAll(boolean deleteBackup)
            throws IOException {
        for (String key: fileList.keySet()) {
            File originalFile = new File(key);
            rollBack(originalFile.getParent(), originalFile.getName(), deleteBackup);
        }
        if (deleteBackup) {
            Files.delete((new File(this.backupFolder)).toPath());
            this.isBackupFolderAvailable = false;
        }
        return this;
    }
    /**
     * The same as calling rollBackAll(true);
     * @return
     * @throws IOException 
     */
    public RollBackManger rollBackAll()
            throws IOException {
        return rollBackAll(true);
    }

    /**
     * Turns the path that is provided as a string into an absolute path.
     * @param path
     * @return 
     */
    public static String turn2AbsolutePath (String path) {
        return (new File(path)).getAbsolutePath();
    }
    /**
     * Returns the absolute path to the child within the provided parent folder.
     * Neither needs to be absolute.
     * @param parentPath
     * @param childName
     * @return 
     */
    public static String getChildAbsolutePath (String parentPath, String childName) {
        return (turn2AbsolutePath(parentPath) + File.separator + childName);
    }



    /**
     * Just testing
     * @param args 
     */
    public static void main(String[] args) {
        File f = new File("C:\\test\\txt.txt");
        System.out.println(f.getParent());
        System.out.println(f.getName());
    }
    
}
