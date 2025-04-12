package sk.streetofcode.productordermanagement.implementationJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
