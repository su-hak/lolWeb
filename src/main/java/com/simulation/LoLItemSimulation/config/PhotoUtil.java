package com.simulation.LoLItemSimulation.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
public class PhotoUtil {

  @Value("${app.firebase-bucket}")
  private String firebaseBucket;

  public String uploadToFirebase(MultipartFile file) {
    try {
      // Authenticate with Google Cloud Storage
      GoogleCredentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(getClass().getResourceAsStream(
              "/serviceAccountKey.json")));
      Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

      // Get a reference to the Firebase Storage bucket
      Bucket bucket = storage.get(firebaseBucket);

      // Generate a unique file name
      String fileName = generateUniqueFileName(file);

      // Upload file to Firebase Storage
      byte[] fileBytes = file.getBytes();
      Blob blob = bucket.create(fileName, fileBytes);

      // Return the download URL of the uploaded file
      return blob.getMediaLink();
    } catch (IOException e) {
      throw new RuntimeException("Failed to upload the file to Firebase Storage", e);
    }
  }

  private String generateUniqueFileName(MultipartFile file) {
    String originalFileName = file.getOriginalFilename();
    String ext = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
    return UUID.randomUUID().toString() + ext;
  }
}
