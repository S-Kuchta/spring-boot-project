package sk.streetofcode.productordermanagement.api.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAddResponse {
    private long productId;
    private long amount;
}
