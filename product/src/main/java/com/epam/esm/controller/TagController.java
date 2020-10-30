package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.entityDTO.tag.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity<List<TagDTO>> getTags() {
       if(tagService.findAll().isEmpty()){
           return ResponseEntity.noContent().build();
       }
        return  ResponseEntity.ok().body(tagService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable("id") Long id)  {
        TagDTO tagDTO = tagService.find(id);
        if(tagDTO==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(tagService.find(id));
    }

    @PostMapping()
    public ResponseEntity<String> createTag(@RequestBody Tag tag) throws JsonProcessingException {
       if(tagService.create(tag)==null){
           return ResponseEntity.notFound().build();
       }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") Long id ){
        tagService.delete(id);
        return ResponseEntity.noContent().build();

    }
}
