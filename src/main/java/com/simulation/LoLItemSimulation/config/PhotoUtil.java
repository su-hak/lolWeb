package com.simulation.LoLItemSimulation.config;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class PhotoUtil {

  public String ckUpload(MultipartHttpServletRequest request) {

    MultipartFile uploadFile = request.getFile("upload");

    String fileName = getFileName(uploadFile);
    String uploadPath = request.getContextPath() + "/uploads/" + fileName;

    /*톰캣서버 설정시 */
//    String realPath = getPath(request);
//    String savePath = realPath + fileName;
//    uploadFile(savePath, uploadFile);

    /*외부 경로 설정시 */
    String realPath2 = "C:/Users/it/Desktop/LEEJH/lolWeb_Main/src/main/resources/static/uploads/";
    String savePath2 = realPath2 + fileName;
    uploadFile(savePath2, uploadFile);


    return uploadPath;
  }

  private void uploadFile(String savePath, MultipartFile uploadFile) {
    File file = new File(savePath);
    try {
      uploadFile.transferTo(file);
    } catch (IOException e) {
      throw new RuntimeException("파일 업로드를 실패했습니다.", e);
    }
  }

  private String getFileName(MultipartFile uploadFile) {
    String originalFileName = uploadFile.getOriginalFilename();
    String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
    return UUID.randomUUID() + ext;
  }

  private String getPath(MultipartHttpServletRequest request) {
    // 톰캣 사용시 위에 getPath(MultipartHttpServletRequest request) 사용
    // 실제 파일 저장 경로 : 톰캣 서버에 임시저장, 서버 재시작 하면 파일 지워짐.
    String realPath = request.getServletContext().getRealPath("/uploads/");


    Path directoryPath = Paths.get(realPath);
    if (!Files.exists(directoryPath)) {
      try {
        Files.createDirectories(directoryPath);
      } catch (IOException e) {
        throw new RuntimeException("업로드 경로를 생성할 수 없습니다.", e);
      }
    }
    return realPath;
  }
}
