package nl.hu.dp.ovchip.persistency.interfaces;

import nl.hu.dp.ovchip.domain.Product;

import java.util.List;

public interface ProductDAO {

    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    Product findById(int id);
    List<Product> findAll();
}
