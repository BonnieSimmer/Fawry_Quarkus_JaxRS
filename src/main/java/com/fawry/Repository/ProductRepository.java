package com.fawry.Repository;

import com.fawry.StoreManager.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepository {
    // i used thread-safe classes to ensure that the repository can handle concurrent access
    private ConcurrentHashMap<Integer, Product> inventory = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    // Singleton instance
    private static ProductRepository instance;

    public static ProductRepository getInstance() {
        if (instance == null) {
            synchronized (ProductRepository.class) {
                if (instance == null) {
                    instance = new ProductRepository();
                }
            }
        }
        return instance;
    }

    public Product getProduct(int id) {
        return inventory.get(id);
    }

    // List all products
    public List<Product> getProducts() {
        return new ArrayList<>(inventory.values());
    }
    // Add a new product
    public boolean addProduct(String name, double price, int quantity) {
        if (name == null || name.isEmpty() || price <= 0 || quantity < 0) {
            return false;
        }
        int id = counter.incrementAndGet();
        Product product = new Product(id, name, price, quantity);
        inventory.put(id, product);
        return true;
    }
    // Update an existing product
    public boolean updateProduct(int id, String name, double price, int quantity) {
        Product product = inventory.get(id);
        if (product == null || name == null || name.isEmpty() || price <= 0 || quantity < 0) {
            return false;
        }
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        return true;
    }
    // Delete a product
    public boolean deleteProduct(int id) {
        if (inventory.containsKey(id)) {
            inventory.remove(id);
            return true;
        }
        return false;
    }


}
