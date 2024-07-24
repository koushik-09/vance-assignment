package com.assignment.vance.controller;

import com.assignment.vance.dao.dto.DataDTO;
import com.assignment.vance.response.BaseResponse;
import com.assignment.vance.response.ResponseHandler;
import com.assignment.vance.service.DataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class DataController {
    private DataService dataService;

    private ResponseHandler responseHandler;

    private Log log = LogFactory.getLog(DataController.class);

    @Autowired
    public DataController(DataService dataService, ResponseHandler responseHandler) {
        this.dataService = dataService;
        this.responseHandler = responseHandler;
    }
    @PostMapping("/forex-data")
    public BaseResponse scrapeForexData(@RequestBody DataDTO dataDTO) throws IOException {
        try {
            log.info("***********start of forex-data api in Data Controller " + new Date());
            return dataService.scrapeData(dataDTO);
        }catch (Exception e){
            log.info(e.toString());
            return responseHandler.setMessageResponse(-2);
        }
    }
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
