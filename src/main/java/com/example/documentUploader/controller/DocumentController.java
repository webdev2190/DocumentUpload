package com.example.documentUploader.controller;

//package com.example.documentUploader.controller;

import com.example.documentUploader.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String timestampedFilePath = documentService.addTimestampToFile(file);
        if (timestampedFilePath != null) {
            return ResponseEntity.ok("File uploaded and timestamped: " + timestampedFilePath);
        } else {
            return ResponseEntity.badRequest().body("Failed to process the file.");
        }
    }
}
