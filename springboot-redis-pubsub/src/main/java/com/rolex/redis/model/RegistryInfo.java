package com.rolex.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
@AllArgsConstructor
public class RegistryInfo {
    int nodeId;
    String ip;
    int port;
    long timestamp;
}
