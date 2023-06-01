package com.don.orderservice.controller;

import com.don.orderservice.dto.CartResponse;
import com.don.orderservice.model.CartItem;
import com.don.orderservice.service.CartService;
import com.don.orderservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final AuthUtil authUtil;

    @PostMapping("/addToCart")
    public String addProductToCart(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CartItem cartItem) {

        String userName = authUtil.getUserName(authorizationHeader);
        if (userName == null) {
            return "error";
        }
        cartService.saveToCart(cartItem, userName);
        return "saved success";
    }

    @GetMapping("/viewMyCart")
    public CartResponse viewMyCart(
            @RequestHeader("Authorization") String authorizationHeader) {

        String userName = authUtil.getUserName(authorizationHeader);
        if(userName!=null){
            return cartService.getMyCart(userName);
        }
        return new CartResponse();
    }

}



