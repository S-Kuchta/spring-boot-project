package sk.streetofcode.productordermanagement.api;


import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAddRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductEditRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.response.product.ProductAmountResponse;
import sk.streetofcode.productordermanagement.api.dto.response.product.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse getById(long id);

    List<ProductResponse> getAll();

    ProductResponse save(ProductAddRequest product);

    void delete(long id);

    ProductResponse edit(long id, ProductEditRequest product);

    boolean productExists(long id);

    ProductAmountResponse updateAmount(long id, ProductAmountRequest amount);

    ProductAmountResponse getAmount(long id);
}
