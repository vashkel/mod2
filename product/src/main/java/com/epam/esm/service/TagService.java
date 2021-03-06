package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.tag.TagDTO;

import java.util.List;

public interface TagService {
    /**
     * This method is used to create the tag
     *
     * @param tagDTO the tag to be created
     * @return true if tag was created, false if it were not
     */
    TagDTO create(TagDTO tagDTO);

    /**
     * This method is used to delete the tag by name
     *
     * @param id the id of tag to be deleted
     * @throws com.epam.esm.exception.TagNotFoundException if tag does not exist
     */
    void delete(Long id);

    /**
     * This method is used to return tag by id
     *
     * @param id the id of tag to be returned
     * @return Tag
     * @throws com.epam.esm.exception.TagNotFoundException if tag was not found
     */
    TagDTO findById(Long id);

    /**
     * This method is used to return the list of tags
     *
     * @return List of all tags or empty List if
     * no certificates were found
     */
    List<TagDTO> findAll(int offset, int limit);

    TagDTO findMostPopularTagWithHighestPriceOfOrders();
}
