package sk.streetofcode.productordermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.dto.response.order.OrderResponse;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> save() {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OrderResponse> deleteById(@PathVariable long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
