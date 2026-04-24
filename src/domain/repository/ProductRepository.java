package domain.repository;

import domain.entity.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findByName(String name);
    void update(Product product);
    void saveAll(List<Product> products);
}