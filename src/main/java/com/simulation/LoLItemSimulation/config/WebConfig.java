package com.simulation.LoLItemSimulation.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${app.firebase-bucket}")
  private String bucketName;

  @Bean
  MappingJackson2JsonView jsonView() {
    return new MappingJackson2JsonView();
  }

  /* 업로드 파일 용량 설정 */
  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    // 최대 업로드 크기 설정 (여기서는 10MB로 설정)
    factory.setMaxFileSize(DataSize.parse("10MB"));
    factory.setMaxRequestSize(DataSize.parse("10MB"));
    return factory.createMultipartConfig();
  }


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Google Cloud Storage의 리소스 경로를 사용하여 리소스 핸들러 추가
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("https://firebasestorage.googleapis.com/v0/b/"+bucketName+"/o/");
  }
}

