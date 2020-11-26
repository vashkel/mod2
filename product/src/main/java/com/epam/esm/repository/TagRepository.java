package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    /**
     * This method is used to create the tag
     *
     * @param tag the tag to be created
     * @return 1 if tag was created, 0 if it were not
     */
    Optional<Tag> create(Tag tag);

    /**
     * This method is used to delete the tag by id
     *
     * @param tag the tag to be deleted
     * @return true if tag was deleted, false if it were not
     */
    void delete(Tag tag);

    /**
     * This method is used to return tag by id
     *
     * @param id the id of tag to be returned
     * @return Optional Tag or Optional null if tag doesnt exist
     */
    Optional<Tag> findById(Long id);

    /**
     * This method is used to return tag by name
     *
     * @param tagName name of tag to be returned
     * @return Optional tag or Optional null if tag doesnt exist
     */
    Optional<Tag> findByName(String tagName);

    /**
     * This method is used to return the list of tags
     *
     * @return List of all tags or empty List if
     * no certificates were found
     */
    Optional<List<Tag>> findAll();

     /**
      * This method is used to return the list of tags by certificate id
      *
      * @return List of all tags or empty List if
      * no certificates were found
      */
     List<Tag> findAllTagsByCertificateId(Long id);
}
