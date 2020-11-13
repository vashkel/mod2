package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    /**
     * This method is used to create the tag
     *
     * @param tag the tag to be created
     * @return 1 if tag was created, 0 if it were not
     */
    long create(Tag tag) throws RepositoryException;

    /**
     * This method is used to delete the tag by id
     *
     * @param tagId the id of tag to be deleted
     * @return true if tag was deleted, false if it were not
     */
    boolean delete(Long tagId) throws RepositoryException;

    /**
     * This method is used to return tag by id
     *
     * @param id the id of tag to be returned
     * @return Optional Tag or Optional null if tag doesnt exist
     */
    Optional<Tag> find(Long id) throws RepositoryException;

    /**
     * This method is used to return tag by name
     *
     * @param tagName name of tag to be returned
     * @return Optional tag or Optional null if tag doesnt exist
     */
    Optional<Tag> findByName(String tagName) throws RepositoryException;

    /**
     * This method is used to return the list of tags
     *
     * @return List of all tags or empty List if
     * no certificates were found
     */
    List<Tag> findAll() throws RepositoryException;

     /**
      * This method is used to return the list of tags by certificate id
      *
      * @return List of all tags or empty List if
      * no certificates were found
      */
     List<Tag> findAllTagsByCertificateId(Long id) throws RepositoryException;
}
