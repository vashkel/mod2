package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface TagService {
    Long create (Tag tag) throws JsonProcessingException;
    void delete(Long id);
    TagDTO find(Long id) throws JsonProcessingException;
    List<TagDTO> findAll() throws JsonProcessingException;
}
