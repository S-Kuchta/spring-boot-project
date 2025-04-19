package sk.streetofcode.productordermanagement.implementationJPA.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderItemService;
import sk.streetofcode.productordermanagement.api.dto.request.order.OrderAddRequest;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderItemAddResponse;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItemAddResponse save(OrderItem orderItem) {

        try {
            final OrderItem orderItemSaved =
                    orderItemRepository.save(new OrderItem(
                            orderItem.getOrder(),
                            orderItem.getProduct(),
                            orderItem.getAmount()
                    ));

            return new OrderItemAddResponse(
                    orderItemSaved.getId(),
                    orderItemSaved.getAmount()
            );
        } catch (DataAccessException e) {
            throw new InternalError();
        }
    }
}
