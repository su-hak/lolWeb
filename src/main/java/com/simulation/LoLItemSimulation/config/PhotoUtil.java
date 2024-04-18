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
import java.net.URLEncoder;
import java.util.UUID;

@Component
public class PhotoUtil {

  @Value("${app.firebase-bucket}")
  private String bucketName;

  @Value("${app.upload-dir}")
  private String uploadDir;

  public String uploadToFirebase(MultipartFile file) {
    try {
      // Authenticate with Google Cloud Storage
      GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream(
              "/firebaseServiceAccountKey.json"));
      Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

      // Get a reference to the Firebase Storage bucket
      Bucket bucket = storage.get(bucketName);

      // Generate a unique file name
      String fileName = generateUniqueFileName(file);

      // Upload file to Firebase Storage with content type
      byte[] fileBytes = file.getBytes();
      Blob blob = bucket.create(
              fileName, // 파일 이름
              fileBytes, // 파일 내용
              file.getContentType() // 컨텐츠 유형
      );

      // Construct the download URL of the uploaded file
      String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/"
              + bucket.getName() + "/o/"
              + URLEncoder.encode(fileName, "UTF-8")
              + "?alt=media&token=" + blob.getGeneratedId(); // 토큰 추가

      return downloadUrl;
    } catch (IOException e) {
      throw new RuntimeException("Failed to upload the file to Firebase Storage", e);
    }
  }

//  public String uploadVideoToFirebase(MultipartFile videoFile) {
//    try {
//      // Authenticate with Google Cloud Storage
//      GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream(
//              "/firebaseServiceAccountKey.json"));
//      Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//
//      // Get a reference to the Firebase Storage bucket
//      Bucket bucket = storage.get(bucketName);
//
//      // Generate a unique file name
//      String fileName = generateUniqueFileName(videoFile);
//
//      // Upload video to Firebase Storage with content type
//      byte[] videoBytes = videoFile.getBytes();
//      Blob blob = bucket.create(
//              fileName, // 파일 이름
//              videoBytes, // 파일 내용
//              "video/*" // 비디오 컨텐츠 유형
//      );
//
//      // Construct the download URL of the uploaded video
//      String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/"
//              + bucket.getName() + "/o/"
//              + URLEncoder.encode(fileName, "UTF-8")
//              + "?alt=media&token=" + blob.getGeneratedId(); // 토큰 추가
//
//      return downloadUrl;
//    } catch (IOException e) {
//      throw new RuntimeException("Failed to upload the video to Firebase Storage", e);
//    }
//  }

  private String generateUniqueFileName(MultipartFile file) {
    String originalFileName = file.getOriginalFilename();
    String ext = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
    return UUID.randomUUID().toString() + ext;
  }
}
