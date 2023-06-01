package com.don.orderservice.service;

import com.don.orderservice.dto.CartItemResponse;
import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.dto.ProductResponse;
import com.don.orderservice.feignClient.ProductService;
import com.don.orderservice.feignClient.UserService;
import com.don.orderservice.model.Cart;
import com.don.orderservice.model.CartItem;
import com.don.orderservice.repository.CartItemRepository;
import com.don.orderservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void saveToCart(CartItem cartItem, String username) {
        ProductResponse productByName = productService.getProductByName(cartItem.getProductName());
        if(productByName!=null){
          log.info("saving products to cart");
        }

        Optional<Cart> userCart = cartRepository.findByName(username);

        Cart cart;
        if (userCart.isPresent()) {
            cart = userCart.get();
        } else {
            cart = new Cart();
            cart.setName(username);
        }

        cart.setUserId(userService.getUserId(username));

        CartItem cartItemToSave = new CartItem();
        cartItemToSave.setProductName(cartItem.getProductName());
        cartItemToSave.setOrderedQuantity(cartItem.getOrderedQuantity());
        cartItemToSave.setCart(cart);

        cartItemRepository.save(cartItemToSave);
    }
    @Override
    public CartResponse getMyCart(String username) {
        Optional<Cart> userCart = cartRepository.findByName(username);
        CartResponse cartResponse = new CartResponse();

        if (userCart.isPresent()) {
            Cart cart = userCart.get();
            cartResponse.setName(cart.getName());

            List<CartItemResponse> cartItemResponses = new ArrayList<>();

            for (CartItem cartItem : cart.getCartItems()) {
                String productName = cartItem.getProductName();
                ProductResponse productResponse = productService.getProductByName(productName);
                double orderedQuantity = cartItem.getOrderedQuantity();

                CartItemResponse cartItemResponse = new CartItemResponse();
                cartItemResponse.setOrderedQuantity(orderedQuantity);
                cartItemResponse.setProductName(productName);
                cartItemResponse.setProductDescription(productResponse.getDescription());
                cartItemResponse.setPrice(productResponse.getPrice());

                cartItemResponses.add(cartItemResponse);
            }

            cartResponse.setCartItemResponses(cartItemResponses);
        }

        return cartResponse;
    }

}
