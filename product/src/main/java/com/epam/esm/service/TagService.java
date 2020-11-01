package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import exception.ServiceException;

import java.util.List;

public interface TagService {
    Long create (Tag tag) throws ServiceException;
    void delete(Long id) throws ServiceException;
    TagDTO find(Long id) throws  ServiceException;
    List<TagDTO> findAll() throws  ServiceException;
}
