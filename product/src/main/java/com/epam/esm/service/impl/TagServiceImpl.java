package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
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
    public Long create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Tag> createdTag = tagRepository.find(id);
        if (!createdTag.isPresent()) {
            throw new TagNotFoundException("tog not found");
        }
        return tagRepository.delete(id);
    }

    @Override
    public TagDTO findById(Long id) {
        Optional<Tag> tag = tagRepository.find(id);
        if (!tag.isPresent()) {
            throw new TagNotFoundException(id, "Tag not found");
        }
        return TagDTOConverter.converterToTagDTO(tag.get());
    }

    @Override
    public List<TagDTO> findAll() {
        List<TagDTO> tagDTOList = new ArrayList<>();
        List<Tag> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            throw new TagNotFoundException("Tag not found");
        }
        tags.forEach(tag -> tagDTOList.add(TagDTOConverter.converterToTagDTO(tag)));
        return tagDTOList;
    }
}