package com.example.documentUploader.service;
import com.example.documentUploader.entity.DocumentMetadata;
import com.example.documentUploader.repository.DocumentRepository; // Ensure this line is uncommented
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public String addTimestampToFile(MultipartFile file) {
        try {
            File convertedFile = convertToFile(file);
            File timestampedFile = addTimestampToPDF(convertedFile);
            DocumentMetadata metadata = saveDocumentMetadata(file.getOriginalFilename(), timestampedFile.getPath(), file.getSize());
            return metadata.getTimestampedFilePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File convertToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.currentTimeMillis() + "_" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

    private File addTimestampToPDF(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        for (PDPage page : document.getPages()) {
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(25, 25);  // Adjust these coordinates
            String timestamp = "Timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            contentStream.showText(timestamp);
            contentStream.endText();
            contentStream.close();
        }
        File timestampedFile = new File(file.getPath().replace(".pdf", "_timestamped.pdf"));
        document.save(timestampedFile);
        document.close();
        file.delete(); // Optional: Delete the original file
        return timestampedFile;
    }

    private DocumentMetadata saveDocumentMetadata(String originalFilename, String timestampedFilePath, long size) {
        DocumentMetadata metadata = new DocumentMetadata(originalFilename, timestampedFilePath, size, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return documentRepository.save(metadata);
    }

    public DocumentMetadata getDocument(String id) {
        return documentRepository.findById(id).orElse(null);
    }

    public void deleteDocument(String id) {
        documentRepository.deleteById(id);
    }

    public Iterable<DocumentMetadata> getAllDocuments() {
        return documentRepository.findAll();
    }
}
