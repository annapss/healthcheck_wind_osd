package com.example.demo.dataVerification;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/dataVerification")
public class DataVerificationController {
private final DataVerificationService dataVerificationService;

    @Autowired
    public DataVerificationController(DataVerificationService dataVerificationService)
    {
        this.dataVerificationService = dataVerificationService;
    }

    @GetMapping
    public String checkData() throws MalformedURLException, IOException
    {
        return dataVerificationService.checkData();
    }
}
