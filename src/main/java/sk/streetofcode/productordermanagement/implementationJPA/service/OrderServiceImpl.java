package sk.streetofcode.productordermanagement.implementationJPA.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderRepository;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository repository) {
        this.orderRepository = repository;
    }

    @Override
    public OrderResponse save() {
        try {
            final Order newOrder = orderRepository.save(new Order(new ArrayList<>()));


            return mapProductToProductResponse(newOrder.getId(), newOrder);
        } catch (DataAccessException e) {
            logger.error("Error while saving order", e);
            throw new InternalError();
        }
    }

    @Override
    public OrderResponse getById(long id) {
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public OrderItemAddResponse addItem(long productId, long amount) {
        return null;
    }

    private OrderResponse mapProductToProductResponse(long productId, Order order) {
        return new OrderResponse(
                order.getId(),
                order.getShoppingList(),
                order.isPaid()
        );
    }
}
