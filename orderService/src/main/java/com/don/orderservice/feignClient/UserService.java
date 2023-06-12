package com.don.orderservice.feignClient;

import com.don.orderservice.dto.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/users")
public interface UserService {
    @GetMapping("/getUserId/{userName}")
    Long getUserId(@PathVariable String userName);

    @GetMapping("/getUserByName/{name}")
    UserResponse getUserByName(@PathVariable String name);
}

