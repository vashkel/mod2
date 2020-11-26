package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.modelDTO.TagDTO;
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
    public TagDTO create(TagDTO tagDTO) {
        Tag tag;
        tag = TagDTOConverter.convertFromTagDTO(tagDTO);
        Optional<Tag> tagOptional = tagRepository.create(tag);
        if (tagOptional.isPresent()){
           tagDTO = TagDTOConverter.converterToTagDTO(tag);
        }
        return tagDTO;
    }

    @Override
    public void delete(Long id) {
        Optional<Tag> createdTag = tagRepository.findById(id);
        if (!createdTag.isPresent()) {
            throw new TagNotFoundException("tog not found");
        }
        tagRepository.delete(createdTag.get());
    }

    @Override
    public TagDTO findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (!tag.isPresent()) {
            throw new TagNotFoundException(id, "Tag not found");
        }
        return TagDTOConverter.converterToTagDTO(tag.get());
    }

    @Override
    public List<TagDTO> findAll() {
        List<TagDTO> tagDTOList = new ArrayList<>();
        Optional<List<Tag>> tags = tagRepository.findAll();
        if (tags.get().isEmpty()) {
            throw new TagNotFoundException("Tag not found");
        }
        tags.get().forEach(tag -> tagDTOList.add(TagDTOConverter.converterToTagDTO(tag)));
        return tagDTOList;
    }
}