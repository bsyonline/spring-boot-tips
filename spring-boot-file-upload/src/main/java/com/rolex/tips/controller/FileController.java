/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import com.rolex.tips.event.FileUploadEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
@Slf4j
public class FileController {

    @Value("${upload.tmp.dir}")
    private String tmpDir;
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    @PostMapping("/upload")
    public void upload(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path dir = Paths.get(tmpDir);
        Path path = Paths.get(tmpDir + File.separator + file.getOriginalFilename());
        if (file.getSize() > 1024 * 1024 * 10) {
            throw new RuntimeException("file is too large for system");
        }
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        Files.write(path, bytes);

        publisher.publishEvent(new FileUploadEvent(publisher, path.toString()));
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("file") String fileName) throws IOException {
        Path path = Paths.get(tmpDir + File.separator + fileName);
        if (!Files.exists(path)) {
            throw new RuntimeException("file not found");
        }
        Path filePath = Paths.get(tmpDir).toAbsolutePath().normalize().resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
