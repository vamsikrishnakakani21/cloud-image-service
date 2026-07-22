package com.commercesystem.image.mapper;

import com.commercesystem.image.dto.ImageResponse;
import com.commercesystem.image.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageResponse toResponse(Image image) {

        if (null == image)
            return null;

        return ImageResponse.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .originalName(image.getOriginalName())
                .filePath(image.getFilePath())
                .fileSize(image.getFileSize())
                .contentType(image.getContentType())
                .uploadedAt(image.getUploadedAt())
                .build();
    }

}
