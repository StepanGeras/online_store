package org.example.promenergosvet.service;

import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.Product;
import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.repo.BasketRepo;
import org.example.promenergosvet.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasketService {

    private final List<Product> productList = new ArrayList<>();

    @Autowired
    private BasketRepo basketRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public void addProductBasket(Product product) {
        productList.add(product);
    }

    public List<Product> getAllBasket () {
        return productList;
    }

    public void deleteProductBasket(Product product) {
        productList.remove(product);
    }

    public void saveBasket(User user) {
        userService.save(user);

        for (Product product : productList) {
            Basket basket = new Basket();
            basket.setProduct(product);
            basket.setUser(userService.findBySurname(user.getSurname()));
            basketRepo.save(basket);
        }

        productList.clear();
    }

    public Map findAll() {

        Map<User, List<Product>> userProductMap = new HashMap<>();

        List<User> userList = userService.findAll();

        for (User user : userList) {
            List<Long> productIdList = basketRepo.findAllProductIdByUserId(user.getId());
            List<Product> productList = new ArrayList<>();

            for (Long productId : productIdList) {
                productList.add(productService.getProductById(productId));
            }

            userProductMap.put(user, productList);

        }

        return userProductMap;

    }
}
