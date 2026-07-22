package com.commercesystem.image.service;

import com.commercesystem.image.dto.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    ImageResponse uploadImage(MultipartFile file);

    List<ImageResponse> getAllImages();

    List<ImageResponse> getAllImagesPaged(int page, int size);

    ImageResponse getImage(Long id);

    void deleteImage(Long id);

}
