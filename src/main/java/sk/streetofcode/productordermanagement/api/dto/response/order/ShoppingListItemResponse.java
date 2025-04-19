package sk.streetofcode.productordermanagement.api.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListItemResponse {
    private long productId;
    private long amount;
}
