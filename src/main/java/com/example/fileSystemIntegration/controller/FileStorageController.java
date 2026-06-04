package com.example.fileSystemIntegration.controller;

import com.example.fileSystemIntegration.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image/filesystem")
@RequiredArgsConstructor
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @PostMapping("/uploadimage")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadImage = fileStorageService.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/downloadimage/{fileName}")
    public ResponseEntity<?> downloadImageToFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = fileStorageService.downloadImageFromFileSystem(fileName);
        String contentType = fileStorageService.getContentType(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(contentType)).body(imageData);
    }
}
