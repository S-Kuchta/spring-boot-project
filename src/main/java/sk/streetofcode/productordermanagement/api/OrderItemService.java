package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;

public interface OrderItemService {

    OrderItem getByIdInternal(Long orderItemId);
    OrderItemAddResponse save(OrderItem orderItem);
    boolean containsProductId(OrderItem orderItem, Long productId);

}
