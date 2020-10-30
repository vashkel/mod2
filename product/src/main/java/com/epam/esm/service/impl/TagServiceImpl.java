package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.RepositoryException;
import exception.ServiceException;
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
    public Long create(Tag tag) throws ServiceException {
        try {
            return tagRepository.create(tag);
        }catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while create tag : ", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            tagRepository.delete(id);
        }catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while delete tag : ", e);
        }

    }

    @Override
    public TagDTO find(Long id) throws ServiceException {
        try {
            return TagDTO.converterToTagDTO(tagRepository.find(id));
        }catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while find tag : ", e);
        }

    }

    @Override
    public List<TagDTO> findAll() throws ServiceException {
         List<TagDTO> tagDTOList = new ArrayList<>();
        try {
            for (Tag tag: tagRepository.findAll()){
                tagDTOList.add(TagDTO.converterToTagDTO(tag));
            }
        }catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while create tag : ", e);
        }
       return tagDTOList;

    }
}
