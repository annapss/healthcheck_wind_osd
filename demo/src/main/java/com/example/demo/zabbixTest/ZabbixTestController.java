package com.example.demo.zabbixTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/zabbixTest")
public class ZabbixTestController {
    private final ZabbixTestService zabbixTestService;
    
    @Autowired
    public ZabbixTestController(ZabbixTestService zabbixTestService)
    {
        this.zabbixTestService = zabbixTestService;
    }

    @GetMapping
    public String getData()
    {
        return zabbixTestService.getData();
    }

}
