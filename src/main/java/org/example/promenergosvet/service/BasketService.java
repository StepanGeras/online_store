package org.example.promenergosvet.service;

import org.example.promenergosvet.entity.Basket;
import org.example.promenergosvet.entity.BasketItem;
import org.example.promenergosvet.entity.Product;
import org.example.promenergosvet.repo.BasketItemRepo;
import org.example.promenergosvet.repo.BasketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    private BasketRepo basketRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketItemRepo basketItemRepo;

    public Basket getBasket(Long cartId) {
        return basketRepo.findById(cartId).orElse(new Basket());
    }

    public void saveBasketItem(BasketItem basketItem) {
        basketItemRepo.save(basketItem);
    }

    public Basket addToCart(Long productId, Basket basket) {
        Product product = productService.getProductById(productId);
        basket.addItem(product);
        return basketRepo.save(basket);
    }

    public Basket removeFromCart(Long basketId, Long productId) {
        Basket basket = getBasket(basketId);
        basket.removeItem(productId);
        return basketRepo.save(basket);
    }

//    public void addProductBasket(Product product) {
//        productList.add(product);
//    }
//
//    public List<Product> getAllBasket () {
//        return productList;
//    }
//
//    public void deleteProductBasket(Product product) {
//        productList.remove(product);
//    }
//
//    public void saveBasket(User user) {
//        userService.save(user);
//
//        for (Product product : productList) {
//            BasketItem basketItem = new BasketItem();
//            basketItem.setProduct(product);
//            basketRepo.save(basketItem);
//        }
//
//        productList.clear();
//    }
//
//    public Map findAll() {
//
//        Map<User, List<Product>> userProductMap = new HashMap<>();
//
//        List<User> userList = userService.findAll();
//
//        for (User user : userList) {
//            List<Long> productIdList = basketRepo.findAllProductIdByUserId(user.getId());
//            List<Product> productList = new ArrayList<>();
//
//            for (Long productId : productIdList) {
//                productList.add(productService.getProductById(productId));
//            }
//
//            userProductMap.put(user, productList);
//
//        }
//
//        return userProductMap;
//
//    }
}
