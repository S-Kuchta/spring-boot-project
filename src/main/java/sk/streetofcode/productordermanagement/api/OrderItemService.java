package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;

public interface OrderItemService {

    OrderItem getByIdInternal(Long id);

    void save(OrderItem orderItem);

    OrderItem getByProductAndOrder(Product product, Order order);

}
