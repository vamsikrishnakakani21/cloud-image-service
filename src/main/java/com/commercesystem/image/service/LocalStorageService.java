package com.commercesystem.image.service;

import com.commercesystem.image.dto.StorageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Profile("local")
public class LocalStorageService implements StorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public StorageResult upload(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("File cannot be empty.");
        }

        try {

            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();

            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID() + extension;

            // Save file
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return new StorageResult(fileName, filePath.toString());

        } catch (IOException ex) {
            throw new RuntimeException("Unable to upload file in local.", ex);
        }
    }

    @Override
    public void delete(String key) throws IOException {

        Path filePath = Paths.get(uploadDir).resolve(key);

        Files.deleteIfExists(filePath);
    }
}
