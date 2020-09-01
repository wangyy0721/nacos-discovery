package com.itheima.nacos.provider.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author wangyy
 * @create 2020/9/1
 * @since 1.0.0
 */


@RestController
public class RestProviderController {

    //暴露RESTful接口http://127.0.0.1:56010/service
    @GetMapping(value = "/service")
    public String service(){
        System.out.println("provider invoke");
        return "provider invoke";
    }
}
