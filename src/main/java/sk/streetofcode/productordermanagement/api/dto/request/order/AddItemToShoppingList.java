package sk.streetofcode.productordermanagement.api.dto.request.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddItemToShoppingList {
    long productId;
    long amount;
}
