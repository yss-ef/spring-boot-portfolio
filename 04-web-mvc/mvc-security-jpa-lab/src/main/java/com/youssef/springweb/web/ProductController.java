package com.youssef.springweb.web;

import com.youssef.springweb.entities.Product;
import com.youssef.springweb.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //@GetMapping("/products")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/products")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("productList", products); //ou va être stocke la liste de produits pour l'utiliser à html
        return "products"; // retourne le nom du fichier html
    }

    //@GetMapping("/delete")
    //@GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/delete") // Suppression de l'annotation @GetMapping superflue
    public String deleteProduct(@RequestParam(name = "id") Long id){
        productService.deleteById(id);
        //return "redirect:/products";
        return "redirect:/user/products";
    }

    //@GetMapping("/newProduct")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/newProduct")

    public  String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    //@PostMapping("/saveProduct")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/updateProduct")
    public String updateProduct(@RequestParam("id") Long id, Model model){ // Ajout de @RequestParam pour la clarté
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    //@PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
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
}
