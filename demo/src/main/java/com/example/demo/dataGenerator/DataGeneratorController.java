package com.example.demo.dataGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/dataGenerator")
public class DataGeneratorController {
    private final DataGeneratorService dataGeneratorService;
    
    @Autowired
    public DataGeneratorController(DataGeneratorService dataGeneratorService)
    {
        this.dataGeneratorService = dataGeneratorService;
    }

    @GetMapping
    public String getData()
    {
        return dataGeneratorService.generateData();
    }

}
