package org.example;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class AppConfig {
    //mapper 등록
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
