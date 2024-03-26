package com.simulation.LoLItemSimulation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  MappingJackson2JsonView jsonView() {
    return new MappingJackson2JsonView();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Google Cloud Storage의 리소스 경로를 사용하여 리소스 핸들러 추가
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("https://storage.googleapis.com/lolweb-ae249.appspot.com/");
  }
}

