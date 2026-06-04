package com.example.fileSystemIntegration.repository;

import com.example.fileSystemIntegration.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileData, String> {
    Optional<FileData> findByName(String name);
}
