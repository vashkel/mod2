package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import exception.RepositoryException;

import java.util.List;

public interface TagRepository {
     long create(Tag tag) throws RepositoryException;
     boolean delete(Long tagId) throws RepositoryException;
     Tag find(Long id) throws RepositoryException;
     Tag findByName(String tagName) throws RepositoryException;
     List<Tag> findAll() throws RepositoryException;

}
