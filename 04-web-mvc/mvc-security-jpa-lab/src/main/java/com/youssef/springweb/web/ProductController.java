package com.youssef.springweb.web;

import com.youssef.springweb.entities.Product;
import com.youssef.springweb.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    //@GetMapping("/products")
    @GetMapping("/user/products")

    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("productList", products); //ou va etre stocke la liste de produits pour l'utiliser a l'html
        return "products"; // retourne le nom du fichier html
    }

    //@GetMapping("/delete")
    //@GetMapping("/admin/delete")
    @PostMapping("/admin/delete")
    public String deleteProduct(@RequestParam(name = "id") Long id){
        productService.deleteById(id);
        //return "redirect:/products";
        return "redirect:/user/products";
    }

    //@GetMapping("/newProduct")
    @GetMapping("/admin/newProduct")

    public  String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    //@PostMapping("/saveProduct")
    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult result){
        if(result.hasErrors()){
            return "new-product";
        }
        productService.addProduct(product);
        //return "redirect:/products";
        return "redirect:/user/products";

    }

    //@GetMapping("/updateProduct")
    @GetMapping("/admin/updateProduct")
    public String updateProduct(Long id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    //@PostMapping("/update")
    @PostMapping("/admin/update")
    public String update(@Valid Product product, BindingResult result){
        if(result.hasErrors()){
            return "update-product";
        }
        productService.updateProductById(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
        //return "redirect:/products";
        return "redirect:/user/products";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "/notAuthorized";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "/login";
    }
}
