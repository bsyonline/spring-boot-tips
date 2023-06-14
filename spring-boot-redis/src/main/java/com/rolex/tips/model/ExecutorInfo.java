package com.rolex.tips.model;

import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2023
 */
@Data
public class ExecutorInfo {
    Long id;
    String host;
    String url;
    String type;
    String tenantCode;
    Long ts;



    @Override
    public String toString() {
        return "ExecutorInfo{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                ", ts=" + ts +
                '}';
    }
}
