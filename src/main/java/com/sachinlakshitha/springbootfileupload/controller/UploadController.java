package com.sachinlakshitha.springbootfileupload.controller;

import com.sachinlakshitha.springbootfileupload.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadController {
    private final FileStorageService fileStorageService;
    String folderName = "test-folder";

    @PostMapping("/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileStorageService.uploadFile(folderName, file.getOriginalFilename(), file), HttpStatus.OK);
    }

    @GetMapping("/file/download/{name:.+}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String name) throws IOException {
        byte[] data = fileStorageService.downloadFile(folderName, name);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + name + "\"")
                .body(resource);
    }

    @DeleteMapping("/file/{name:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String name) throws IOException {
        fileStorageService.deleteFile(folderName, name);
        return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/folder/{name:.+}")
    public ResponseEntity<String> deleteFolder(@PathVariable String name) throws IOException {
        fileStorageService.deleteFolder(name);
        return new ResponseEntity<>("Folder deleted successfully", HttpStatus.OK);
    }
}
