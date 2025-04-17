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
    public OrderItemAddResponse addOrderItem(long orderId, OrderAddRequest orderAddRequest) {

        try {
            final OrderItem orderItemSaved =
                    orderItemRepository.save(new OrderItem(
                            orderId,
                            orderAddRequest.getProductId(),
                            orderAddRequest.getAmount()
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
