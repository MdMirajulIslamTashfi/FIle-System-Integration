package com.example.fileSystemIntegration.repository;

import com.example.fileSystemIntegration.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageStorageRepository extends JpaRepository<Image, String> {
    Optional<Image> findByName(String fileName);
}
