package sk.streetofcode.productordermanagement.implementationJPA.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderItemService;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem getByIdInternal(Long orderItemId) {
        return orderItemRepository
                .findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @Override
    public OrderItemAddResponse save(OrderItem orderItem) {

        try {
            final OrderItem orderItemSaved = orderItemRepository.save(orderItem);

//            final OrderItem orderItemSaved =
//                    orderItemRepository.save(new OrderItem(
//                            orderItem.getOrder(),
//                            orderItem.getProduct(),
//                            orderItem.getAmount()
//                    ));

            return new OrderItemAddResponse(
                    orderItemSaved.getId(),
                    orderItemSaved.getAmount()
            );
        } catch (DataAccessException e) {
            throw new InternalError();
        }
    }

    @Override
    public OrderItem getByProductAndOrder(Product product, Order order) {
        return orderItemRepository.findByProductAndOrder(product, order);
    }
}
