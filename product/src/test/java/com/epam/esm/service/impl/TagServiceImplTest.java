package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private List<Tag> tagList;
    private Tag tag1;
    private Tag tag2;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService = new TagServiceImpl(tagRepository);

    @BeforeEach
    void setUp() {
        tag1 = tagCreator(1L, "tagName1");
        tag2 = tagCreator(2L, "tagName2");
        tagList = Arrays.asList(tag1, tag2);
    }

    @Test
    void findTag_whenTagNotFound_thenThrowTagNotFoundException() {
        long tagId = 0;
        Mockito.when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        Assertions.assertThrows(TagNotFoundException.class, () -> tagService.findById(tagId));
    }

    @Test
    void createTag_whenCreated_thenReturnTrue() {
        Mockito.when(tagRepository.create(tag1)).thenReturn(Optional.ofNullable(tag1));
        TagDTO expected = TagDTOConverter.converterToTagDTO(tag1);
        TagDTO actual = tagService.create(TagDTOConverter.converterToTagDTO(tag1));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_whenTagExisted_thenReturnTag() {
        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.ofNullable(tag1));
        TagDTO expected = TagDTOConverter.converterToTagDTO(tag1);
        TagDTO actual = tagService.findById(1L);

        Assertions.assertNotNull(actual, "Tag should not be found");

        Assertions.assertEquals(actual, expected, "The tag is not same like returnedTag");
    }

    @Test
    void findAll(){
        List<TagDTO> expected = new ArrayList<>();
        Mockito.when(tagRepository.findAll(1, 2)).thenReturn(Optional.ofNullable(tagList));
        tagList.forEach(tag -> expected.add(TagDTOConverter.converterToTagDTO(tag)));
        List<TagDTO> actual = tagService.findAll(1, 2);

        Assertions.assertIterableEquals(actual, expected);
    }

    private Tag tagCreator(Long id, String name) {
        return new Tag(id, name);
    }
}