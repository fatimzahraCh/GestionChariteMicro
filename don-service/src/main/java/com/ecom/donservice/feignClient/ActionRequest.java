package com.ecom.donservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "actioncharite-service")
public interface ActionRequest {

    @GetMapping("/actions/{id}")
    ActionDto getActionById(@PathVariable("id") Long id);
}