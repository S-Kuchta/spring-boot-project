package sk.streetofcode.productordermanagement.implementationJPA.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderItemService;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderRepository;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final ProductService productService;
    private final OrderItemService orderItemService;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository repository, ProductService productService, OrderItemService orderItemService) {
        this.orderRepository = repository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @Override
    public OrderResponse save() {
        try {
            final Order newOrder = orderRepository.save(new Order(new ArrayList<>()));

            return mapProductToProductResponse(newOrder);
        } catch (DataAccessException e) {
            logger.error("Error while saving order", e);
            throw new InternalError();
        }
    }

    @Override
    public OrderResponse getById(long id) {
        return mapProductToProductResponse(getByIdInternal(id));
    }

    @Override
    public Order getByIdInternal(long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public void deleteById(long id) {
        if (orderExists(id)) {
            orderRepository.deleteById(id);
        }
    }

    public boolean orderExists(long id) {
        if (orderRepository.existsById(id)) {
            return true;
        } else {
            throw new ResourceNotFoundException("Order with id " + id + " not found");
        }
    }

    @Override
    public OrderItemAddResponse addItem(long orderId, OrderAddRequest orderAddRequest) {

        final long productId = orderAddRequest.getProductId();
        final long amount = orderAddRequest.getAmount();

//        final Product product = productService.getByIdInternal(productId);
        final Order order = getByIdInternal(orderId);

        if (productService.checkAmountNeeded(productId, amount)) {
            productService.updateAmount(productId, new ProductAmountRequest(-Math.abs(amount)));
            orderItemService.addOrderItem(orderId, new OrderAddRequest(productId, amount));
            order.getShoppingList().add(new OrderItem(orderId, productId, amount));
            return new OrderItemAddResponse(productId, amount);
        }

        return null;
    }

    private OrderResponse mapProductToProductResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getShoppingList(),
                order.isPaid()
        );
    }
}
