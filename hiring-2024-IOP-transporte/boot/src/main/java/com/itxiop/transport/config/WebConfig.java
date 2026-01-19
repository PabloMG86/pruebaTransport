package com.itxiop.transport.config;

import com.itxiop.transport.infrastructure.apirest.converters.MarshallingCSVConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // XML soporte
    converters.add(0, new MappingJackson2XmlHttpMessageConverter());
    // CSV custom converter
    converters.add(new MarshallingCSVConverter<>());
  }
}
