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
    //
    return new MappingJackson2JsonView();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // "/uploads/**" URL 패턴으로 요청이 들어오면 "file:/저장된_경로/" 에서 파일을 찾도록 설정합니다.
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("gs://lolweb-ae249.appspot.com/images/");
  }

}
