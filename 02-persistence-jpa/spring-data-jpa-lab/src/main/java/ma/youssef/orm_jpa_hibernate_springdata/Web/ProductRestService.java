package ma.youssef.orm_jpa_hibernate_springdata.Web;

import ma.youssef.orm_jpa_hibernate_springdata.Entities.Product;
import ma.youssef.orm_jpa_hibernate_springdata.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> productList() {
        return productRepository.findAll();
    }
}
