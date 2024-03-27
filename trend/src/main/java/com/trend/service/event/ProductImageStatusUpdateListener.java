package com.trend.service.event;


import com.trend.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductImageStatusUpdateListener {

    private final ProductMapper productMapper;

    @TransactionalEventListener
    public void handleTemporaryStatusUpdateEvent(ProductImageStatusUpdateEvent event) {
        // temporary 상태를 true로 변경
        productMapper.updateImageStatus(event.getUuid());

    }
}
