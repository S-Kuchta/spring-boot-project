package sk.streetofcode.productordermanagement.implementationJPA.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {

    @Id
    private long id;




}
