package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;

public interface OrderItemService {

    OrderItemAddResponse addOrderItem(long orderId, OrderAddRequest orderAddRequest);

}
