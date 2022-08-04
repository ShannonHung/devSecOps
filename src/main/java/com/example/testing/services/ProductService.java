package com.example.testing.services;

import com.example.testing.models.Product;
import com.example.testing.models.ProductTag;
import com.example.testing.repo.ProductRepository;
import com.example.testing.repo.ProductTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"productCache"})
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Cacheable(value = "productCache", key = "'productList'")
    public List<Product> getAllProducts()  {
        List<Product> products = productRepository.findAll();
        return products;
    }
    @Cacheable(value = "productCache", key = "#sku")
    public Product getProductbyID(String sku) {
        Optional<Product> product = productRepository.findById(sku);
        if(product.isPresent())
            return product.get();
        return null;
    }

    @Cacheable(value = "productCache", key = "#tag")
    public List<Product> getAllProductsByTag(String tag) {
        List<Product> products = productRepository.findByTag(tag);
        if(products!=null) {
            return products;
        } else {
            return null;
        }
    }

    @CachePut(value = "productCache", key = "#product.sku")
    @CacheEvict(value = "productCache", key = "'productList'")
    public Product addProduct(Product product) {
        Product newProduct = productRepository.save(product);
        return newProduct;
    }
    @CacheEvict(value = "productCache",allEntries=true)
    public void deleteProduct(String sku) {
        if (productRepository.existsById(sku))
            productRepository.deleteById(sku);
    }
    @CacheEvict(value = "productCache",allEntries=true)
    public void deleteProductTag(Integer iid) {
        if (productTagRepository.existsById(iid))
            productTagRepository.deleteById(iid);
    }
    @CachePut(value = "productCache",key = "#sku")
    @CacheEvict(value = "productCache",key = "'productList'")
    public ProductTag addProductTag(String sku, String tag) {
        Optional<Product> product = productRepository.findById(sku);
        if(product.isPresent()) {
            Product p = product.get();
            if(p.getItems()==null) {
                throw new RuntimeException("DB access error");
            }
            List<ProductTag> items = p.getItems();
            for(ProductTag pt: items) {
                if(pt.getTag().equals(tag))
                    return pt;
            }
            ProductTag pt = new ProductTag();
            pt.setProduct(p);
            pt.setTag(tag);
            ProductTag newProductTag = productTagRepository.save(pt);
            return newProductTag;
        } else {
            return null;
        }
    }
}
