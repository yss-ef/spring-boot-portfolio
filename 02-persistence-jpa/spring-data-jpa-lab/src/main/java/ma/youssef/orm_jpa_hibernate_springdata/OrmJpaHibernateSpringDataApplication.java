package ma.youssef.orm_jpa_hibernate_springdata;

import ma.youssef.orm_jpa_hibernate_springdata.Entities.Product;
import ma.youssef.orm_jpa_hibernate_springdata.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OrmJpaHibernateSpringDataApplication implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrmJpaHibernateSpringDataApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        productRepository.save(new Product(null, "computer", 1999.99, 5));
        productRepository.save(new Product(null, "tablet", 99.98, 5));
        productRepository.save(new Product(null, "phone", 400.88, 5));
        List<Product> products = productRepository.findAll();
        products.forEach(p ->{
            System.out.println(p.toString());
        });
        System.out.println("***********************************************************************************");

        Product product = productRepository.findById(Long.valueOf(1)).get();
        System.out.println(product.toString());

        System.out.println("***********************************************************************************");

        List<Product> products1 = productRepository.findByNameContains("C");
        products1.forEach(p ->{
            System.out.println(p.toString());
        });

        System.out.println("***********************************************************************************");

        List<Product> products2 = productRepository.findByPriceGreaterThan(300);
        products2.forEach(p ->{
            System.out.println(p.toString());
        });
    }
}
