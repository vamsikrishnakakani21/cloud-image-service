package com.commercesystem.image.service;

import com.commercesystem.image.dto.ImageResponse;
import com.commercesystem.image.dto.StorageResult;
import com.commercesystem.image.entity.Image;
import com.commercesystem.image.exception.ImageNotFoundException;
import com.commercesystem.image.mapper.ImageMapper;
import com.commercesystem.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    private final StorageService storageService;

    @Override
    public ImageResponse uploadImage(MultipartFile file) {

        try {

            StorageResult storageResult = storageService.upload(file);

            Image image = new Image();

            image.setFileName(storageResult.key());
            image.setFilePath(storageResult.url());
            image.setOriginalName(file.getOriginalFilename());
            image.setFileSize(file.getSize());
            image.setContentType(file.getContentType());
            image.setUploadedAt(LocalDateTime.now());

            Image saved = imageRepository.save(image);

            return imageMapper.toResponse(saved);

        } catch (IOException ex) {
            throw new RuntimeException("Unable to upload image.", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getAllImages() {

        List<Image> allImages = imageRepository.findAll();

        return allImages.stream().map(imageMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getAllImagesPaged(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Image> allImages = imageRepository.findAll(pageable);

        return allImages.stream().map(imageMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ImageResponse getImage(Long id) {

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id : " + id));
        return imageMapper.toResponse(image);
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id : " + id));

        try {
            storageService.delete(image.getFilePath());
            imageRepository.delete(image);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to delete image.", ex);
        }
    }
}
