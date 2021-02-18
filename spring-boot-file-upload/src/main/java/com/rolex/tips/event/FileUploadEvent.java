/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Setter
@Getter
public class FileUploadEvent extends ApplicationEvent {
    private String path;

    public FileUploadEvent(Object source, String path) {
        super(source);
        this.path = path;
    }
}
