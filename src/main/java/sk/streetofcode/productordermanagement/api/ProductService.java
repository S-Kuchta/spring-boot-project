package sk.streetofcode.productordermanagement.api;


import sk.streetofcode.productordermanagement.api.dto.request.ProductAddRequest;
import sk.streetofcode.productordermanagement.api.dto.request.ProductEditRequest;
import sk.streetofcode.productordermanagement.api.dto.request.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.response.ProductAmountResponse;
import sk.streetofcode.productordermanagement.api.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse get(long id);

    List<ProductResponse> getAll();

    ProductResponse save(ProductAddRequest product);

    void delete(long id);

    ProductResponse edit(long id, ProductEditRequest product);

    boolean productExists(long id);

    ProductAmountResponse updateAmount(long id, ProductAmountRequest amount);

    ProductAmountResponse getAmount(long id);
}
