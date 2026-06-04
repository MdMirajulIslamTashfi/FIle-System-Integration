package com.example.fileSystemIntegration.repository;

import com.example.fileSystemIntegration.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentStorageRepository extends JpaRepository<Document, String> {
    Optional<Document> findByName(String fileName);
}
