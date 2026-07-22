package com.commercesystem.image.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ImageResponse {

    private Long id;

    private String fileName;

    private String originalName;

    private String filePath;

    private Long fileSize;

    private String contentType;

    private LocalDateTime uploadedAt;

}
