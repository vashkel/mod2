package com.epam.esm.modelDTO.tag;

import lombok.Data;

import java.io.Serializable;
@Data
public class TagDTO implements Serializable {
    private static final long serialVersionUID = -1784150257337720793L;

    private Long id;
    private String name;

}
