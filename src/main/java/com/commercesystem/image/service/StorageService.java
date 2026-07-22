package com.commercesystem.image.service;

import com.commercesystem.image.dto.StorageResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    StorageResult upload(MultipartFile file) throws IOException;

    void delete(String key) throws IOException;

}
