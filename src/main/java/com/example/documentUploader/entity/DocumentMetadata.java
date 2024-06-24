package com.example.documentUploader.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DocumentMetadata {
    @Id
    private String id;
    private String originalFilename;
    private String timestampedFilePath;
    private long size;
    private String uploadTimestamp;

    // Constructors
    public DocumentMetadata() {}

    public DocumentMetadata(String originalFilename, String timestampedFilePath, long size, String uploadTimestamp) {
        this.originalFilename = originalFilename;
        this.timestampedFilePath = timestampedFilePath;
        this.size = size;
        this.uploadTimestamp = uploadTimestamp;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getTimestampedFilePath() {
        return timestampedFilePath;
    }

    public void setTimestampedFilePath(String timestampedFilePath) {
        this.timestampedFilePath = timestampedFilePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(String uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }
}
