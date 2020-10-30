package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    private final TagRepositoryImpl tagRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public TagServiceImpl(TagRepositoryImpl tagRepository, ObjectMapper objectMapper) {
        this.tagRepository = tagRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public Long create(Tag tag) throws JsonProcessingException {
        return tagRepository.create(tag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.delete(id);

    }

    @Override
    public TagDTO find(Long id) {
        return TagDTO.converterToTagDTO(tagRepository.find(id));

    }

    @Override
    public List<TagDTO> findAll() {
         List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag: tagRepository.findAll()){
            tagDTOList.add(TagDTO.converterToTagDTO(tag));
        }
       return tagDTOList;

    }
}
