package com.example.demo.dataVerification;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.databaseTables.Status;

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
    public Status checkData() throws MalformedURLException, IOException
    {
        //System.out.println("Testeeeee");
        return dataVerificationService.checkData();
    }
}
