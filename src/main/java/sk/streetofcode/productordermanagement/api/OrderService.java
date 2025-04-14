package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;

public interface OrderService {

    OrderResponse save();
    OrderResponse getById(long id);
    void delete();
    OrderItemAddResponse addItem(long productId, long amount);

}
