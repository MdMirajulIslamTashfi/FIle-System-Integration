package com.example.fileSystemIntegration.service;

import com.example.fileSystemIntegration.entity.Image;
import com.example.fileSystemIntegration.repository.ImageStorageRepository;
import com.example.fileSystemIntegration.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageStorageService {
    private final ImageStorageRepository imageStorageRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        Image imageData = imageStorageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (imageData != null) {
            return "file uploaded successfully: " + imageData.getName();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public byte[] downloadImage(String fileName) throws IOException {
        Optional<Image> imageData = imageStorageRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(imageData.get().getImageData());
        return images;
    }
}
