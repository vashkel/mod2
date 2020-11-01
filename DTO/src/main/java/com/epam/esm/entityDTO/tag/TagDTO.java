package com.epam.esm.entityDTO.tag;

import com.epam.esm.entity.Tag;
import lombok.Data;

import java.io.Serializable;
@Data
public class TagDTO implements Serializable {
    private static final long serialVersionUID = -1784150257337720793L;

    private Long id;
    private String name;

    public static TagDTO converterToTagDTO(Tag tag){
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }


}
