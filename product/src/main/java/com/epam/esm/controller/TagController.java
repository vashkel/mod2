package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.tag.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getTags() throws ServiceException {
        return ResponseEntity.ok().body(tagService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable("id") Long id) throws ServiceException {
        return ResponseEntity.ok().body(tagService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<String> createTag(@RequestBody Tag tag) throws ServiceException {
        if (tagService.create(tag) == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") Long id) throws ServiceException {
        tagService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
