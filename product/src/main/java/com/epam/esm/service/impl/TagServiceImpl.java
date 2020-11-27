package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.modelDTO.TagDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public TagDTO create(TagDTO tagDTO) {
        Tag tag;
        tag = TagDTOConverter.convertFromTagDTO(tagDTO);
        Optional<Tag> createdTag = tagRepository.create(tag);
        if (createdTag.isPresent()){
           tagDTO = TagDTOConverter.converterToTagDTO(createdTag.get());
        }
        return tagDTO;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Tag> createdTag = tagRepository.findById(id);
        if (!createdTag.isPresent()) {
            throw new TagNotFoundException("tog not found");
        }
        tagRepository.delete(createdTag.get());
    }

    @Transactional(readOnly = true)
    @Override
    public TagDTO findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (!tag.isPresent()) {
            throw new TagNotFoundException(id, "Tag not found");
        }
        return TagDTOConverter.converterToTagDTO(tag.get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<TagDTO> findAll() {
        List<TagDTO> tagDTOList = new ArrayList<>();
        Optional<List<Tag>> tags = tagRepository.findAll();
        if (!tags.isPresent()) {
            throw new TagNotFoundException("Tag not found");
        }
        tags.get().forEach(tag -> tagDTOList.add(TagDTOConverter.converterToTagDTO(tag)));
        return tagDTOList;
    }
}