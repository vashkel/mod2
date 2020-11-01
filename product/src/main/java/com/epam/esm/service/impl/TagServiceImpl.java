package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import exception.RepositoryException;
import exception.ServiceException;
import exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
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
            Tag tag = tagRepository.find(id);
            if (tag == null){
                throw new TagNotFoundException(id, "Tag not found");
            }
            return TagDTO.converterToTagDTO(tag);
        }catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while find tag : ", e);
        }

    }

    @Override
    public List<TagDTO> findAll() throws ServiceException {
         List<TagDTO> tagDTOList = new ArrayList<>();
        try {
            List<Tag> tags = tagRepository.findAll();
            if (tags == null){
                throw new TagNotFoundException("Tag not found");
            }
            tags.forEach(tag -> tagDTOList.add(TagDTO.converterToTagDTO(tag)));
        }catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while create tag : ", e);
        }
       return tagDTOList;

    }
}
