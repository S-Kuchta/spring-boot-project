package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;

public interface OrderItemService {

    OrderItemAddResponse save(OrderItem orderItem);

}
