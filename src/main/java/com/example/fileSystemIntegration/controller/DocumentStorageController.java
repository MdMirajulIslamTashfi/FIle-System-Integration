package com.example.fileSystemIntegration.controller;

import com.example.fileSystemIntegration.entity.Document;
import com.example.fileSystemIntegration.service.DocumentStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentStorageController {
    private final DocumentStorageService documentStorageService;

    @PostMapping("/uploaddocument")
    public ResponseEntity<?> uploadDocumentToFileSystem(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadDocument = documentStorageService.uploadDocumentToFileSystem(file);
        return ResponseEntity.ok().body(uploadDocument);
    }

    @GetMapping("/downloaddocument/{fileName}")
    public ResponseEntity<?> downloadDocumentFromFileSystem(@PathVariable String fileName) throws IOException {
        try {
            // Fetch document details once to get both file path and content type
            Document document = documentStorageService.getDocumentMetadata(fileName);

            byte[] downloadDocument = documentStorageService.downloadDocumentFromFileSystem(document.getFilePath());
            String contentType = document.getFileType() != null ? document.getFileType() : "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(contentType))
                    .body(downloadDocument);

        } catch (java.io.FileNotFoundException e) {
            return ResponseEntity.status(404).body("File missing from storage disk.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error reading file from disk.");
        }
    }
}
