package com.itheima.nacos.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class RestConsumerController {

    //要进行远程，需要知识提供方的ip和端口
    @Value("${provider.address}")
    private String provider;

    @GetMapping(value = "/service")//http://127.0.0.1:56020/service
    public String service(){
        //远程调用
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://" + provider + "/service", String.class);
        return "consumer invode|"+result;
    }


    //---------------------从nacos 服务中心获取provide 的地址--------------------
    //指定服务名
    String serviceId = "nacos-restful-provider";

    //通过负载均衡发现地址，流程是从服务发现中心拿nacos-restful-provider服务的列表，通过负载均衡算法获取一个地址
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping(value = "/service1")
    public String service1(){
        //远程调用
        RestTemplate restTemplate = new RestTemplate();

        //发现一个地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        //获取一个http://开头的地址，包括ip和端口
        URI uri = serviceInstance.getUri();
        String result = restTemplate.getForObject(uri + "/service", String.class);
        return "consumer1 invode|"+result;
    }

}
