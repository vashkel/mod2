package com.epam.esm.util.DTOConverter.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.modelDTO.tag.TagDTO;

public class TagDTOConverter {

    public static TagDTO converterToTagDTO(Tag tag){
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }
}
