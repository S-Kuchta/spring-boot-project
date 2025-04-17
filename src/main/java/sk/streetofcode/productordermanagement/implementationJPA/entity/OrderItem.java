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

    @JoinColumn(name = "shoppingList")
    private long orderId;

    @JoinColumn(name = "productInShoppingList")
    private long productId;

    @Column(nullable = false)
    private long amount;

    public OrderItem(long orderId, long productId, long amount) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
    }
}
