package sk.streetofcode.productordermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAddRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductEditRequest;
import sk.streetofcode.productordermanagement.api.dto.response.product.ProductAmountResponse;
import sk.streetofcode.productordermanagement.api.dto.response.product.ProductResponse;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductAddRequest product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> edit(
            @PathVariable("id") long id,
            @RequestBody ProductEditRequest product
    ) {
        productService.edit(id, product);
        return ResponseEntity.ok().body(productService.get(id));
    }

    @PostMapping("{id}/amount")
    public ResponseEntity<ProductAmountResponse> editAmount(
            @PathVariable long id,
            @RequestBody ProductAmountRequest amount
    ) {
        ProductAmountResponse response = productService.updateAmount(id, amount);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}/amount")
    public ResponseEntity<ProductAmountResponse> getAmount(@PathVariable("id") long id) {
        return ResponseEntity.ok(productService.getAmount(id));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        productService.delete(id);
//        return ResponseEntity.ok().body(productService.get(id));
        return ResponseEntity.ok().build();
    }
}
