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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order", nullable = false)
//    private Order order;

    @Column
    private long orderId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product", nullable = false)
    @Column
    private long productId;

    @Column
    private int amount;

}
