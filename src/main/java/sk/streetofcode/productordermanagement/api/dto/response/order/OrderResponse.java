package sk.streetofcode.productordermanagement.api.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    private List<ShoppingListItemResponse> shoppingList;
    private boolean paid;
}
