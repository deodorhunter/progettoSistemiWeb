package com.emusicstore.controller;

import com.emusicstore.model.Product;
import com.emusicstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin")
public class AdminProduct {

    @Autowired
    private ProductService productService;
    private Path path;

    @RequestMapping("/product/addProduct")
    public String addProduct(Model model){

        Product product=new Product();
        product.setProductCondition("new");
        product.setProductStatus("active");
        product.setProductSubCategory("no_cat");

        model.addAttribute("product", product);
        return "addProduct";
    }

    @RequestMapping(value="/product/addProduct", method = RequestMethod.POST)
    public String addProductPost(@Valid @ModelAttribute("product") Product product, BindingResult result,
                                 HttpServletRequest request){
        if(result.hasErrors()){
            return "addProduct";
        }
        productService.addProduct(product);

        MultipartFile productImage=product.getProductImage();
        String rootDir =request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDir + "\\WEB-INF\\resources\\images\\" + product.getProductId() + ".png");

        if(productImage!=null && !productImage.isEmpty()){
            try{
                productImage.transferTo(new File(path.toString()));
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Product image saving failed");
            }
        }
        return "redirect:/admin/productInventory";
    }

    @RequestMapping("/product/editProduct/{id}")
    public String editProduct(@PathVariable("id") int id, Model model){

        Product product= productService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @RequestMapping(value="/product/editProduct", method = RequestMethod.POST)
    public String editProductPost(@Valid @ModelAttribute("product") Product product, BindingResult result,
                                 HttpServletRequest request){
        if(result.hasErrors()){
            System.out.println("Ho trovato errori");
            return "editProduct";
        }

        MultipartFile productImage=product.getProductImage();
        String rootDir =request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDir + "\\WEB-INF\\resources\\images\\" + product.getProductId() + ".png");

        //l'edit dell'immagine non funziona. Why?
//        if(productImage!=null && !productImage.isEmpty()){
//            try{
//                productImage.transferTo(new File(path.toString()));
//            }catch (Exception e){
//                e.printStackTrace();
//                throw new RuntimeException("Product image saving failed");
//            }
//        }
        //1: ok cosi funziona! A quanto pare non faceva overwrite, nonostante il metodo transferTo() lo preveda.
        //2: .....non funziona.... lol
        //3: non funziona perche le salvo nel war? Perche se applico il metodo, nella cartella out/../images l'immagine cambia,
        //   ma non cambia nel sito. Bisogna provare a salvarle fuori dal war, o addirittura salvarle su database (non so come)
        if(productImage!=null && !productImage.isEmpty()) {
            try {
                if (Files.exists(path)) {
                    Files.delete(path);
                    productImage.transferTo(new File(path.toString()));

                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Product image saving failed");
            }
        }

        productService.editProduct(product);
        return "redirect:/admin/productInventory";
    }

    @RequestMapping("/product/deleteProduct/{id}")
    public  String deleteProduct(@PathVariable("id") int id, Model model, HttpServletRequest request){
        String rootDir =request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDir + "\\WEB-INF\\resources\\images\\" + id + ".png");
        if(Files.exists(path)){
            try{
                Files.delete(path);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        Product product =productService.getProductById(id);
        productService.deleteProduct(product);

        return "redirect:/admin/productInventory";
    }
}
