package com.example.testing.controllers;

import com.example.testing.models.MessageResponse;
import com.example.testing.models.Product;
import com.example.testing.models.ProductTag;
import com.example.testing.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value="/product",method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value="/product/{productsku}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductbyID(@PathVariable("productsku") String sku) {
        try {
            Product product = productService.getProductbyID(sku);
            if(product == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(product);
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value="/productbytag",method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProductsByTag(@RequestParam String tag) {
        try {
            List<Product> products = productService.getAllProductsByTag(tag);
            if (products != null) {
                return ResponseEntity.ok(products);
            } else {
                throw new Exception("db access error");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value="/product", method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value="/product/{productsku}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("productsku") String sku) {
        try {
            productService.deleteProduct(sku);
            return ResponseEntity.ok(true);
         } catch(Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @RequestMapping(value="/tag/{tagid}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProductTag(@PathVariable("tagid") Integer iid) {
        try {
            productService.deleteProductTag(iid);
            return ResponseEntity.ok(true);
//            return ResponseEntity.ok(new MessageResponse(200,"delete product tag success"));
        } catch(Exception e) {
//            return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(false);
        }
    }


    @RequestMapping(value="/product/{productsku}", method = RequestMethod.POST)
    public ResponseEntity<ProductTag> addProductTag(@PathVariable("productsku") String sku, @RequestParam String tag) {
        try {
            ProductTag newProductTag = productService.addProductTag(sku,tag);
            if(newProductTag==null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(newProductTag);
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
