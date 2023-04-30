package com.sachinlakshitha.springbootfileupload.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    @Value("${file.upload.path}")
    private String fileUploadPath;

    public void createFolder(String folderName) throws IOException {
        Path path = Paths.get(fileUploadPath + folderName);
        Files.createDirectories(path);
    }

    public void deleteFolder(String folderName) throws IOException {
        File file  = new File(fileUploadPath + folderName);
        if (file.isDirectory()) {
            try {
                FileUtils.deleteDirectory(file); //deletes the whole folder
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String uploadFile(String folderName, String fileName, MultipartFile file) throws IOException {
        createFolder(folderName);
        Path path = Paths.get(fileUploadPath + folderName + "/" + fileName);
        Files.write(path, file.getBytes());
        return fileName;
    }

    public void uploadMultipleFiles(String folderName, MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            uploadFile(folderName, file.getOriginalFilename(), file);
        }
    }

    public byte[] downloadFile(String folderName, String fileName) throws IOException {
        Path path = Paths.get(fileUploadPath + folderName + "/" + fileName);
        return Files.readAllBytes(path);
    }

    public void deleteFile(String folderName, String fileName) throws IOException {
        Path path = Paths.get(fileUploadPath + folderName + "/" + fileName);
        Files.deleteIfExists(path);
    }

    public void deleteAllFiles(String folderName) {
        Path path = Paths.get(fileUploadPath + folderName);
        try {
            Files.list(path).forEach(file -> {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
