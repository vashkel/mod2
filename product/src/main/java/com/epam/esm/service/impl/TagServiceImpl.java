package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TagServiceImpl implements TagService {

    private static final String NOT_FOUND = "locale.message.TagNotFound";

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
        Optional<Tag> byName = tagRepository.findByName(tagDTO.getName());
        if(byName.isPresent()){
            throw new EntityExistsException("already created");
        }
        Optional<Tag> createdTag = tagRepository.create(tag);
        if (createdTag.isPresent()) {
            tagDTO = TagDTOConverter.converterToTagDTO(createdTag.get());
        }
        return tagDTO;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Tag> createdTag = tagRepository.findById(id);
        if (!createdTag.isPresent()) {
            throw new TagNotFoundException(NOT_FOUND);
        }
        tagRepository.delete(createdTag.get());
    }

    @Transactional(readOnly = true)
    @Override
    public TagDTO findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (!tag.isPresent()) {
            throw new TagNotFoundException(id, NOT_FOUND);
        }
        return TagDTOConverter.converterToTagDTO(tag.get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<TagDTO> findAll(int offset, int limit) {
        List<TagDTO> tagDTOList = new ArrayList<>();
        Optional<List<Tag>> tags = tagRepository.findAll(offset, limit);
        if (!tags.isPresent()) {
            throw new TagNotFoundException(NOT_FOUND);
        }
        tags.get().forEach(tag -> tagDTOList.add(TagDTOConverter.converterToTagDTO(tag)));
        return tagDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public TagDTO findMostPopularTagWithHighestPriceOfOrders() {
        TagDTO tagDTO = null;
        Optional<Tag> tag = tagRepository.findMostPopularTagOfUserWithHighestPriceOfOrders();
        if (tag.isPresent()) {
            tagDTO = TagDTOConverter.converterToTagDTO(tag.get());
        }
        return tagDTO;
    }
}