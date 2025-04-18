package sk.streetofcode.productordermanagement.implementationJPA.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAddRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductAmountRequest;
import sk.streetofcode.productordermanagement.api.dto.request.product.ProductEditRequest;
import sk.streetofcode.productordermanagement.api.dto.response.product.ProductAmountResponse;
import sk.streetofcode.productordermanagement.api.dto.response.product.ProductResponse;
import sk.streetofcode.productordermanagement.api.exception.NotEnoughProductOnStock;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.implementationJPA.entity.Product;
import sk.streetofcode.productordermanagement.implementationJPA.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getById(long id) {
        return mapProductToProductResponse(getByIdInternal(id));
    }

    @Override
    public Product getByIdInternal(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::mapProductToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse save(ProductAddRequest product) {
        try {
            final Product newProduct = productRepository.save(new Product(
                    product.getName(),
                    product.getDescription(),
                    product.getAmount(),
                    product.getPrice()
            ));

            return mapProductToProductResponse(newProduct.getId(), product);
        } catch (DataAccessException e) {
            logger.error("Error while saving product", e);
            throw new InternalError();
        }
    }

    @Override
    public void delete(long id) {
        if (productExists(id)) {
            productRepository.deleteById(id);
        }
    }

    @Override
    public ProductResponse edit(long id, ProductEditRequest productRequest) {
        final Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        productRepository.save(product);
        return mapProductToProductResponse(product);
    }

    @Override
    public ProductAmountResponse updateAmount(long id, ProductAmountRequest amount) {
        final Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        final ProductAmountResponse response = new ProductAmountResponse();
        long newAmount = amount.getAmount() + product.getAmount();
        product.setAmount(newAmount);
        response.setAmount(newAmount);

        productRepository.save(product);
        return response;
    }

    @Override
    public ProductAmountResponse getAmount(long id) {
        long amount = productRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"))
                .getAmount();

        ProductAmountResponse response = new ProductAmountResponse();
        response.setAmount(amount);
        return response;
    }

    @Override
    public boolean checkAmountNeeded(long id, long amountNeeded) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        if (amountNeeded <= product.getAmount()) {
            return true;
        } else {
            throw new NotEnoughProductOnStock("Not enough product on stock");
        }
    }

    @Override
    public boolean productExists(long id) {
        if (productRepository.existsById(id)) {
            return true;
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAmount(),
                product.getPrice()
        );
    }

    private ProductResponse mapProductToProductResponse(long id, ProductAddRequest product) {
        return new ProductResponse(
                id,
                product.getName(),
                product.getDescription(),
                product.getAmount(),
                product.getPrice()
        );
    }
}
