package dev.paul.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class MapperBean {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
