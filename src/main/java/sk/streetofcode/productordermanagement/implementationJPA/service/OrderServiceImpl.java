package sk.streetofcode.productordermanagement.implementationJPA.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderItemService;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.dto.request.order.AddItemToShoppingList;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;
import sk.streetofcode.productordermanagement.api.dto.response.order.ShoppingListItemResponse;
import sk.streetofcode.productordermanagement.api.exception.BadRequestException;
import sk.streetofcode.productordermanagement.api.exception.InternalErrorException;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderRepository;

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

            return mapToOrderResponse(newOrder);
        } catch (DataAccessException e) {
            logger.error("Error while saving Order", e);
            throw new InternalErrorException("Error while saving Order");
        }
    }

    @Override
    public OrderResponse getById(long id) {
        return mapToOrderResponse(getByIdInternal(id));
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

        order.getShoppingList()
                .forEach(orderItem -> orderItem.getProduct()
                        .setAmount(orderItem.getProduct().getAmount() + orderItem.getAmount())
                );

        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderResponse addItem(Long orderId, AddItemToShoppingList addItemToShoppingList) {

        final long productId = addItemToShoppingList.getProductId();
        long amount = addItemToShoppingList.getAmount();

        final Order order = getByIdInternal(orderId);
        if (order.isPaid()) {
            throw new BadRequestException("Order with id " + orderId + " is already paid!");
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

        return mapToOrderResponse(order);
    }

    @Override
    public String payOrder(Long orderId) {
        final Order order = getByIdInternal(orderId);

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

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getShoppingList().stream()
                        .map(item -> new ShoppingListItemResponse(
                                item.getProduct().getId(),
                                item.getAmount()
                        )).toList(),
                order.isPaid()
        );
    }

}
