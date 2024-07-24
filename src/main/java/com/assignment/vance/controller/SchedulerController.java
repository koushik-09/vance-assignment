package com.assignment.vance.controller;

import com.assignment.vance.dao.dto.DataDTO;
import com.assignment.vance.response.BaseResponse;
import com.assignment.vance.service.DataServiceImplementation;
import jakarta.persistence.EntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class SchedulerController {
    // Schedule the scraping of forex data

    private DataServiceImplementation dataServiceImplementation;

    private Log log = LogFactory.getLog(SchedulerController.class);
    private EntityManager entityManager;

    @Autowired
    public SchedulerController(DataServiceImplementation dataServiceImplementation,
                               EntityManager entityManager) {
        this.dataServiceImplementation = dataServiceImplementation;
        this.entityManager = entityManager;
    }

    //currency lists
    final ArrayList<String> fromList = new ArrayList<String>(Arrays.asList("GBP","AED"));
    final ArrayList<String> toList = new ArrayList<String>(Arrays.asList("INR","INR"));

//    temporary scheduler to trigger function for every 2 minutes
//    @Scheduled(cron = "0 */2 * * * ?")
//    public void temp() throws IOException {
//        try {
//            log.info("***********Start of Scheduled Scraping - Temp " + new Date());
//            BaseResponse response = dataServiceImplementation.scrapeDataFromList(new DataDTO(fromList,toList,"2W"));
//        }catch (Exception e){
//            log.info(e.toString());
//        }
//    }

    //weekly data scraping
    @Scheduled(cron = "0 0 0 ? * MON")
    public void scrapeWeekly() throws IOException {
        try {
            log.info("***********Start of Scheduled Scraping - Weekly " + new Date());
            BaseResponse response = dataServiceImplementation.scrapeDataFromList(new DataDTO(fromList,toList,"1W"));
        }catch (Exception e){
            log.info(e.toString());
        }
    }

    //monthly data scraping
    @Scheduled(cron = "0 0 0 1 * ?")
    public void scrapeMonthly() throws IOException {
        try {
            log.info("***********Start of Scheduled Scraping - Monthly " + new Date());
            BaseResponse response = dataServiceImplementation.scrapeDataFromList(new DataDTO(fromList,toList,"1M"));

        }catch (Exception e){
            log.info(e.toString());
        }
    }
    //Data scraping every 3 months
    @Scheduled(cron = "0 0 0 */3 * ?")
    public void scrapeEveryThreeMonths() throws IOException {
        try {
            log.info("***********Start of Scheduled Scraping - Once in 3 Months " + new Date());
            BaseResponse response = dataServiceImplementation.scrapeDataFromList(new DataDTO(fromList,toList,"3M"));
        }catch (Exception e){
            log.info(e.toString());
        }
    }
    //Data scraping every 6 months
    @Scheduled(cron = "0 0 0 */6 * ?")
    public void scrapeEverySixMonths() throws IOException {
        try {
            log.info("***********Start of Scheduled Scraping - Once in 6 Months " + new Date());
            BaseResponse response = dataServiceImplementation.scrapeDataFromList(new DataDTO(fromList,toList,"6M"));
        }catch (Exception e){
            log.info(e.toString());
        }
    }

    //Data scraping every year
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void scrapeEveryYear() throws IOException {
        try {
            log.info("***********Start of Scheduled Scraping - Yearly " + new Date());
            BaseResponse response = dataServiceImplementation.scrapeDataFromList(new DataDTO(fromList,toList,"1Y"));
        }catch (Exception e){
            log.info(e.toString());
        }
    }
}
