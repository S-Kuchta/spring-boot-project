package sk.streetofcode.productordermanagement.implementationJPA.service;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderItemService;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;
import sk.streetofcode.productordermanagement.api.dto.response.order.ShoppingListItemResponse;
import sk.streetofcode.productordermanagement.api.exception.BadRequestException;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderItemRepository;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderRepository;

import java.util.List;

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
            final Order newOrder = orderRepository.save(new Order());

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
        final Order order = getByIdInternal(id);
        if (order != null) {
            if (order.isPaid()) {
                throw new BadRequestException("Paid order can not be deleted.");
            }

            order.getShoppingList()
                    .forEach(orderItem -> orderItem.getProduct()
                            .setAmount(orderItem.getProduct().getAmount() + orderItem.getAmount())
                    );

            orderRepository.deleteById(id);
        }
    }

    @Override
    public OrderResponse addItem(Long orderId, OrderAddRequest orderAddRequest) {

        final long productId = orderAddRequest.getProductId();
        long amount = orderAddRequest.getAmount();

        final Order order = getByIdInternal(orderId);
        if (order.isPaid()) {
            throw new BadRequestException("Order with id " + orderId + " is already paid");
        }

        final Product product = productService.getByIdInternal(productId);
        OrderItem orderItem = orderItemService.getByProductAndOrder(product, order);
        if (orderItem != null) {
            orderItem.setAmount(orderItem.getAmount() + amount);
        } else {
            orderItem = new OrderItem(order, product, amount);
        }

        if (productService.checkAmountNeeded(productId, amount)) {
            productService.updateAmount(productId, new ProductAmountRequest(-Math.abs(amount)));
            orderItemService.save(orderItem);
        }

        return mapProductToProductResponse(order);
    }

    @Override
    public String payOrder(Long orderId) {
        Order order = getByIdInternal(orderId);

        double sum = 0;
        for (OrderItem orderItem : order.getShoppingList()) {
            double price = orderItem.getProduct().getPrice();
            double amount = orderItem.getAmount();
            sum = sum + (price * amount);
        }

        if (!order.isPaid()) {
            order.setPaid(true);
            orderRepository.save(order);
        } else {
            throw new BadRequestException("Order with id " + orderId + " already paid");
        }

        return String.format("%.1f", sum);
    }

    private OrderResponse mapProductToProductResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                mapOrderItemsToShoppingListResponse(order.getShoppingList()),
                order.isPaid()
        );
    }

    private List<ShoppingListItemResponse> mapOrderItemsToShoppingListResponse(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(orderItem -> new ShoppingListItemResponse(
                        orderItem.getProduct().getId(),
                        orderItem.getAmount()))
                .toList();
    }
}
