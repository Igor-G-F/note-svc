package com.manus.noteark.notesvc.pojo;

import io.swagger.annotations.ApiModelProperty;

public class NoteRequest extends Note {

    @ApiModelProperty(hidden = true)
    private String id;
    
}
