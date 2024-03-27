package com.trend.service.event;


import lombok.Getter;

@Getter
public class ProductImageStatusUpdateEvent {

    private final String uuid;

    public ProductImageStatusUpdateEvent(String uuid) {
        this.uuid = uuid;
    }
}
