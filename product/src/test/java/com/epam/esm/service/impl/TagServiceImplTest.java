package com.epam.esm.service.impl;

import com.epam.esm.config.ProductSpringConfiguration;
import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.util.DTOConverter.tag.TagDTOConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ProductSpringConfiguration.class})
class TagServiceImplTest {

    private List<Tag> tagList;
    private Tag tag1;
    private Tag tag2;

    @Autowired
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepositoryImpl tagRepository;

    @BeforeEach
    public void setUp() {
        tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("tagName1");
        tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("tagName2");
        tagList = Arrays.asList(tag1, tag2);
    }

    @Test
    void findTag_whenTagNotFound_thenThrowTagNotFoundException() throws RepositoryException, ServiceException {
        long tagId = 0;
        Mockito.when(tagRepository.find(tagId)).thenReturn(null);
        Assertions.assertThrows(TagNotFoundException.class, () -> tagService.findById(tagId));
    }
    @Test
    void createTag_whenCreated_thenReturnTrue() throws RepositoryException, ServiceException {
        Mockito.when(tagRepository.create(tag1)).thenReturn(tag1.getId());
        Long expected = tag1.getId();
        Long actual = tagService.create(tag1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void find_whenTagExisted_thenReturnTag() throws RepositoryException, ServiceException {
        Mockito.when(tagRepository.find(1L)).thenReturn(tag1);
        TagDTO expected = TagDTOConverter.converterToTagDTO(tag1);
        TagDTO actual = tagService.findById(1L);

        Assertions.assertNotNull(actual, "Tag should not be found");
        Assertions.assertEquals(actual, expected, "The tag is not same like returnedTag");
    }

    @Test
    void findAll() throws RepositoryException, ServiceException {
        List<TagDTO> expected = new ArrayList<>();
        Mockito.when(tagRepository.findAll()).thenReturn(tagList);
        tagList.forEach(tag -> expected.add(TagDTOConverter.converterToTagDTO(tag)));
        List<TagDTO> actual = tagService.findAll();
        Assertions.assertIterableEquals(actual, expected);

    }

}