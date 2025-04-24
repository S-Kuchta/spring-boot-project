package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.dto.request.order.AddItemToShoppingList;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;

public interface OrderService {

    OrderResponse save();

    OrderResponse getById(long id);

    Order getByIdInternal(long id);

    void deleteById(long id);

    OrderResponse addItem(Long orderId, AddItemToShoppingList addItemToShoppingList);

    String payOrder(Long orderId);

}
