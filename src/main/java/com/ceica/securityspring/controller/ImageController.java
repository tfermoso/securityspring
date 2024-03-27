package com.ceica.securityspring.controller;

import com.ceica.securityspring.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ImageController {


    private AppConfig appConfig;

    @Autowired
    public ImageController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping("/user/image/{imageName}")
    public ResponseEntity<FileSystemResource> getUserImage(@PathVariable String imageName) {
        String imagePath = appConfig.getUserImageDirectory() + File.separator + imageName;
        FileSystemResource fileSystemResource = new FileSystemResource(new File(imagePath));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Cambiar según el tipo de imagen
                .body(fileSystemResource);
    }
}
