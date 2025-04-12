package sk.streetofcode.productordermanagement.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddRequest {
    private String name;
    private String description;
    private long amount;
    private double price;
}
