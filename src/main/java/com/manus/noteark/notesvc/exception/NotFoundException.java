package com.manus.noteark.notesvc.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends NoSuchElementException {

    private static final long serialVersionUID = 1L;
    private static final String exceptionMessage = "Could not find %s.";

    public NotFoundException(String item) {
        super(String.format(exceptionMessage, item));
    }
}