package sk.streetofcode.productordermanagement.implementationJPA.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> shoppingList;

    @Column(nullable = false)
    private boolean paid;

    public Order(List<OrderItem> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
