package sk.streetofcode.productordermanagement.implementationJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;
import sk.streetofcode.productordermanagement.implementationJPA.entity.OrderItem;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findByProductAndOrder(Product product, Order order);

}
