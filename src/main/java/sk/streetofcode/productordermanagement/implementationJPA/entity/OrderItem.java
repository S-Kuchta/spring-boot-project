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
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int amount;

}
