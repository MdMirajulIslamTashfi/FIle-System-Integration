package com.example.fileSystemIntegration.service;

import com.example.fileSystemIntegration.entity.FileData;
import com.example.fileSystemIntegration.repository.FileStorageRepository;
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
public class FileStorageService {
    private final FileStorageRepository fileStorageRepository;

    private final String FOLDER_PATH = "/home/tashfi/IdeaProjects/fileSystemIntegration/src/main/resources/static/uploads/images/";

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filepath = FOLDER_PATH + file.getOriginalFilename();
        FileData fileData = fileStorageRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filepath)
                .build());

        file.transferTo(new File(filepath));

        if(fileData != null) {
            return "file Uploaded successfully: " + filepath;
        }

        return null;
    }

    @Transactional(readOnly = true)
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileStorageRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    @Transactional(readOnly = true)
    public String getContentType(String fileName) {
        return fileStorageRepository.findByName(fileName)
                .map(FileData::getType)
                .orElse("application/octet-stream");
    }
}
