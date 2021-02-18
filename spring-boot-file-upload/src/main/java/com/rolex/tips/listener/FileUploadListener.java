/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.listener;

import com.rolex.tips.event.FileUploadEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@Component
public class FileUploadListener implements ApplicationListener<FileUploadEvent> {
    @Override
    public void onApplicationEvent(FileUploadEvent fileUploadEvent) {
        log.info("file upload successfully, location is {}", fileUploadEvent.getPath());
    }
}
