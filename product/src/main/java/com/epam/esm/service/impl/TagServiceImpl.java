package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while create tag : ", e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            Optional<Tag> createdTag = tagRepository.find(id);
            if (!createdTag.isPresent()) {
                throw new TagNotFoundException("tog not found");
            }
            return tagRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while delete tag : ", e);
        }
    }

    @Override
    public TagDTO findById(Long id) throws ServiceException {
        try {
            Optional<Tag> tag = tagRepository.find(id);
            if (!tag.isPresent()) {
                throw new TagNotFoundException(id, "Tag not found");
            }
            return TagDTOConverter.converterToTagDTO(tag.get());
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while find tag : ", e);
        }
    }

    @Override
    public List<TagDTO> findAll() throws ServiceException {
        List<TagDTO> tagDTOList = new ArrayList<>();
        try {
            List<Tag> tags = tagRepository.findAll();
            if (tags.isEmpty()) {
                throw new TagNotFoundException("Tag not found");
            }
            tags.forEach(tag -> tagDTOList.add(TagDTOConverter.converterToTagDTO(tag)));
        } catch (RepositoryException e) {
            throw new ServiceException("An exception was thrown while create tag : ", e);
        }
        return tagDTOList;
    }
}
