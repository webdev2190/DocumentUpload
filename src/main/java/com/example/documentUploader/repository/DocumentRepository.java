//package com.example.documentUploader.repositiry;
//package com.example.documentUploader.repository;
package com.example.documentUploader.repository;


import com.example.documentUploader.entity.DocumentMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<DocumentMetadata, String> {
}

