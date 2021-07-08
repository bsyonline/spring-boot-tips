/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.api;

import java.util.concurrent.CompletableFuture;

/**
 * @author rolex
 * @since 2020
 */
public interface CompletableFutureService {

    CompletableFuture<String> fooCompletableFuture();

    CompletableFuture<String> barCompletableFuture();
}
