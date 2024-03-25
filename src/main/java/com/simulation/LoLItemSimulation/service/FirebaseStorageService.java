package com.simulation.LoLItemSimulation.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    @Value("${app.firebase-bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = generateFileName(file.getOriginalFilename());
        BlobId blobId = BlobId.of(bucketName, "images/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Storage storage = StorageOptions.getDefaultInstance().getService();
        storage.create(blobInfo, file.getBytes());

        return getUploadedFileUrl(fileName);
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "-" + originalFileName;
    }

    private String getUploadedFileUrl(String fileName) {
        return "https://storage.googleapis.com/" + bucketName + "/images/" + fileName;
    }
}