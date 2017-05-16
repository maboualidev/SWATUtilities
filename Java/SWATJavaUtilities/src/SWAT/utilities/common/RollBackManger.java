/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SWAT.utilities.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mabouali
 */
public class RollBackManger {
    private File defaultSourceDIR;
    private File backupFolder;
    private boolean isBackupFolderAvailable;
    private final Set<File> fileList;

    @SuppressWarnings("unchecked")
    public RollBackManger() {
        this.defaultSourceDIR = new File(turn2AbsolutePath(""));
        this.backupFolder = new File(getChildAbsolutePath(defaultSourceDIR.getPath(), "backupFolder"));
        this.fileList = new HashSet();
        this.isBackupFolderAvailable = false;
    }
    @SuppressWarnings("unchecked")
    public RollBackManger(String defaultSrcFolder) {
        this.defaultSourceDIR = new File(turn2AbsolutePath(defaultSrcFolder));
        this.backupFolder = new File(getChildAbsolutePath(defaultSrcFolder, "backupFolder"));
        this.fileList = new HashSet();
        this.isBackupFolderAvailable = false;
    }
    @SuppressWarnings("unchecked")
    public RollBackManger(String defaultSrcFolder, String backupFolder) {
        this.defaultSourceDIR = new File(turn2AbsolutePath(defaultSrcFolder));
        this.backupFolder = new File(turn2AbsolutePath(backupFolder));
        this.fileList = new HashSet();
        this.isBackupFolderAvailable = false;
    }

    public RollBackManger setDefaultSourceDIR(File defaultSourceDIR) {
        this.defaultSourceDIR = defaultSourceDIR;
        return this;
    }
    public RollBackManger setBackupFolder(File backupFolder) {
        if (fileList.isEmpty()) {
            this.backupFolder = backupFolder;
        } else {
            throw new Error("Cannot change backup folder location once there are some files backed up already.");
        }
        return this;
    }

    public File getDefaultSourceDIR() {
        return defaultSourceDIR;
    }
    public File getBackupFolder() {
        return backupFolder;
    }

    public RollBackManger prepareBackupFolder()
            throws IOException {
        if (!backupFolder.exists()) {
            Files.createDirectories(backupFolder.toPath());
        }
        this.isBackupFolderAvailable = true;
        return this;
    }
    public RollBackManger backup(String parentFolder, String filename, boolean forceBackup)
            throws IOException {
        if (!isBackupFolderAvailable)
            prepareBackupFolder();
        
        File srcFile = new File(getChildAbsolutePath(parentFolder, filename));
        File dstFile = new File(getChildAbsolutePath(this.backupFolder.toString(), filename));
        if (forceBackup) {
            fileList.add(srcFile);
            Files.copy(srcFile.toPath(), dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            if (!fileList.contains(srcFile)) {
                fileList.add(srcFile);
                Files.copy(srcFile.toPath(), dstFile.toPath());
            }
        }
        return this;
    }
    public RollBackManger backup(String parentFolder, String filename)
            throws IOException {
        return backup(parentFolder, filename, false);
    }
    public RollBackManger backup(String filename, boolean forceBackup)
            throws IOException {
        return backup(this.defaultSourceDIR.toString(),filename,forceBackup);
    }
    public RollBackManger backup(String filename)
            throws IOException {
        return backup(this.defaultSourceDIR.toString(),filename,false);
    }

    public RollBackManger rollBack(String parentFolder, String filename, boolean deleteBackup)
            throws IOException {
        File dstFile = new File(getChildAbsolutePath(parentFolder, filename));
        if (fileList.contains(dstFile)) {
            File srcFile = new File(getChildAbsolutePath(this.backupFolder.toString(), filename));
            Files.copy(srcFile.toPath(),dstFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
            if (deleteBackup) {
                Files.delete(srcFile.toPath());
                fileList.remove(srcFile);
            }
        }
        return this;
    }
    public RollBackManger rollBack(String parentFolder, String filename)
            throws IOException {
        return rollBack(parentFolder,filename,true);
    }
    public RollBackManger rollBack(String filename, boolean deleteBackup)
            throws IOException {
        return rollBack(this.defaultSourceDIR.toString(),filename,deleteBackup);
    }
    public RollBackManger rollBack(String filename)
            throws IOException {
        return rollBack(this.defaultSourceDIR.toString(),filename,true);
    }
    public RollBackManger rollBackAll(boolean deleteBackup)
            throws IOException {
        for (File f: this.fileList)
            rollBack(f.getParent(),f.getName(),deleteBackup);
        if (deleteBackup) {
            Files.delete(this.backupFolder.toPath());
            this.isBackupFolderAvailable = false;
        }
        return this;
    }
    public RollBackManger rollBackAll()
            throws IOException {
        return rollBackAll(true);
    }

    @Override
    protected void finalize()
            throws Throwable{
        for (File f: this.fileList) {
            Files.delete(f.toPath());
            fileList.remove(f);
        }
        Files.delete(this.backupFolder.toPath());
        this.isBackupFolderAvailable = false;
    }

    private static String turn2AbsolutePath (String path) {
        return (new File(path)).getAbsolutePath();
    }
    private static String getChildAbsolutePath (String parentPath, String childName) {
        return (turn2AbsolutePath(parentPath) + File.separator + childName);
    }




    public static void main(String[] args) {
        File f = new File("C:\\test\\txt.txt");
        System.out.println(f.getParent());
        System.out.println(f.getName());
    }
    
}
