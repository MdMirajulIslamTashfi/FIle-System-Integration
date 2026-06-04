package com.example.fileSystemIntegration.service;

import com.example.fileSystemIntegration.entity.Document;
import com.example.fileSystemIntegration.repository.DocumentStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentStorageService {
    private final DocumentStorageRepository documentStorageRepository;
    private final String FOLDER_PATH = "/home/tashfi/IdeaProjects/fileSystemIntegration/src/main/resources/static/uploads/documents/";

    public String uploadDocumentToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        Document document = documentStorageRepository.save(Document.builder()
                        .name(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));

        if(document != null) {
            return "Document file Uploaded successfully: " + document.getName();
        }

        return null;
    }

    @Transactional(readOnly = true)
    public Document getDocumentMetadata(String fileName) {
        return documentStorageRepository.findByName(fileName)
                .orElseThrow(() -> new RuntimeException("File not found in database with name: " + fileName));
    }

    // 2. Read the actual bytes from the disk
    public byte[] downloadDocumentFromFileSystem(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new java.io.FileNotFoundException("Physical file not found at path: " + filePath);
        }
        return Files.readAllBytes(file.toPath());
    }
}
