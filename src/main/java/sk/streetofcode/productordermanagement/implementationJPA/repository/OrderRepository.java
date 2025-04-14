package sk.streetofcode.productordermanagement.implementationJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
