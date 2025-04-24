package sk.streetofcode.productordermanagement.implementationJPA.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderItemService;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.api.exception.InternalErrorException;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem getByIdInternal(Long id) {
        return orderItemRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem with id " + id + " not found"));
    }

    @Override
    public void save(OrderItem orderItem) {
        try {
            final OrderItem orderItemSaved = orderItemRepository.save(orderItem);
            new OrderItemAddResponse(
                    orderItemSaved.getId(),
                    orderItemSaved.getAmount()
            );
        } catch (DataAccessException e) {
            logger.error("Error while saving OrderItem", e);
            throw new InternalErrorException("Error while saving OrderItem");
        }
    }

    @Override
    public OrderItem getByProductAndOrder(Product product, Order order) {
        return orderItemRepository.findByProductAndOrder(product, order);
    }
}
