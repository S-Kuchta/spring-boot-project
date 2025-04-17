package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;

public interface OrderService {

    OrderResponse save();

    OrderResponse getById(long id);

    Order getByIdInternal(long id);

    void deleteById(long id);

    OrderItemAddResponse addItem(long id, OrderAddRequest orderAddRequest);

}
