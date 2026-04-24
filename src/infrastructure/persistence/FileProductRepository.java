package infrastructure.persistence;

import domain.entity.Product;
import domain.valueobject.ProductType;
import domain.repository.ProductRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileProductRepository implements ProductRepository {
    
    private static final String FILE_PATH = Paths.get("", "data", "inventory.txt").toString();
    private List<Product> products;
    
    public FileProductRepository() {
        this.products = loadFromFile();
    }
    
    private List<Product> loadFromFile() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] productDetails = scanner.nextLine().split(",");
                String name = productDetails[0];
                
                ProductType type = ProductType.Coffee;
                switch(name) {
                    case "Cappuccino":
                        type = ProductType.Cappuccino;
                        break;
                    case "Expresso":
                        type = ProductType.Expresso;
                        break;
                    default:
                        break;
                }
                
                int price = Integer.parseInt(productDetails[1]);
                int preparationTime = Integer.parseInt(productDetails[2]);
                int amountLeft = Integer.parseInt(productDetails[3]);
                
                products.add(new Product(name, type, price, preparationTime, amountLeft));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }
    
    @Override
    public Product findByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
    
    @Override
    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(product.getName())) {
                products.set(i, product);
                break;
            }
        }
    }
    
    @Override
    public void saveAll(List<Product> allProducts) {
        this.products = new ArrayList<>(allProducts);
        saveToFile();
    }
    
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new File(FILE_PATH))) {
            for (Product product : products) {
                writer.println(String.format("%s,%d,%d,%d",
                    product.getName(),
                    product.getPrice(),
                    product.getPreparationTime(),
                    product.getAmountLeft()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}