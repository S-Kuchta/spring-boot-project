package sk.streetofcode.productordermanagement.implementationJPA.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @Column(nullable = false)
    private long amount;

    public OrderItem(Order order, Product product, long amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
    }
}
