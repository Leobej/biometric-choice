package com.votemetric.biometricchoice.mapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final Logger logger = LoggerFactory.getLogger(Mapper.class);
    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convertToType(Object source, Class<T> resultClass) {
        logger.debug("converted object from " + source.getClass().getSimpleName() + " to " + resultClass.getSimpleName());
        return modelMapper.map(source, resultClass);
    }

}
