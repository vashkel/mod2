package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface TagService {
    /**
     * This method is used to create the tag
     *
     * @param tag the tag to be created
     * @return true if tag was created, false if it were not
     */
    Long create(Tag tag) throws ServiceException;

    /**
     * This method is used to delete the tag by name
     *
     * @param id the id of tag to be deleted
     * @return true if tag was deleted, false if it were not
     * @throws com.epam.esm.exception.TagNotFoundException if tag does not exist
     */
    boolean delete(Long id) throws ServiceException;

    /**
     * This method is used to return tag by id
     *
     * @param id the id of tag to be returned
     * @return Tag
     * @throws com.epam.esm.exception.TagNotFoundException if tag was not found
     */
    TagDTO findById(Long id) throws ServiceException;

    /**
     * This method is used to return the list of tags
     *
     * @return List of all tags or empty List if
     * no certificates were found
     */
    List<TagDTO> findAll() throws ServiceException;
}
