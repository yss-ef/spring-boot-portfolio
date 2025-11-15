package com.youssef.springweb.web;

import com.youssef.springweb.entities.Product;
import com.youssef.springweb.reposetories.ProductRepository;
import com.youssef.springweb.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @GetMapping("/products")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("productList", products); //ou va etre stocke la liste de produits pour l'utiliser a l'html
        return "products"; // retourne le nom du fichier html
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam(name = "id") Long id){
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/newProduct")
    public  String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult result, Model model){
        if(result.hasErrors()){
            return "new-product";
        }
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(Long id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/update")
    public String update(@Valid Product product, BindingResult result, Model model){
        if(result.hasErrors()){
            return "update-product";
        }
        productService.updateProductById(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
        return "redirect:/products";
    }
}
