package com.commercesystem.image.controller;

import com.commercesystem.image.dto.ApiResponse;
import com.commercesystem.image.dto.ImageResponse;
import com.commercesystem.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<ImageResponse>> uploadImage(
            @RequestParam("file") MultipartFile file) {

        ImageResponse response = imageService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Image uploaded successfully",
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ImageResponse>>> getAllImages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        int pageIndex = Math.max(page - 1, 0);
        List<ImageResponse> list = imageService.getAllImagesPaged(pageIndex, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Image(s) fetched successfully",
                                list
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageResponse>> getImage(@PathVariable Long id) {

        ImageResponse response = imageService.getImage(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Image fetched successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long id) {

        imageService.deleteImage(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Image deleted successfully",
                        null
                )
        );
    }
}
