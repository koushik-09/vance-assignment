package com.assignment.vance.service;

import com.assignment.vance.dao.DataDaoImplementation;
import com.assignment.vance.dao.dto.DataDTO;
import com.assignment.vance.entity.Data;
import com.assignment.vance.response.BaseResponse;
import com.assignment.vance.response.ResponseHandler;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImplementation implements DataService{

    private DataDaoImplementation dataDaoImplementation;
    private ResponseHandler responseHandler;

    @Autowired
    public DataServiceImplementation(DataDaoImplementation dataDaoImplementation,
                                     ResponseHandler responseHandler) {
        this.dataDaoImplementation = dataDaoImplementation;
        this.responseHandler = responseHandler;
    }
    @Override
    @Transactional
    public BaseResponse scrapeData(DataDTO dataDTO) throws IOException {
        if (dataDTO.getPeriod().contains("W") ||
                dataDTO.getPeriod().contains("M") ||
                dataDTO.getPeriod().contains("Y")) {

            //obtaining timestamps
            String quote = dataDTO.getFrom().toUpperCase() + dataDTO.getTo().toUpperCase() + "%3DX";
            LocalDateTime currentDate = LocalDateTime.now();
            Timestamp toDateStamp = Timestamp.valueOf(currentDate);
            Timestamp fromDateStamp = Timestamp.valueOf(currentDate.minusDays(1));
            if (dataDTO.getPeriod().charAt(1) == 'W') {
                fromDateStamp = Timestamp.valueOf(currentDate.minusWeeks(Integer.parseInt(dataDTO.getPeriod().charAt(0) + "")));
            } else if (dataDTO.getPeriod().charAt(1) == 'M') {
                fromDateStamp = Timestamp.valueOf(currentDate.minusMonths(Integer.parseInt(dataDTO.getPeriod().charAt(0) + "")));
            } else {
                fromDateStamp = Timestamp.valueOf(currentDate.minusYears(Integer.parseInt(dataDTO.getPeriod().charAt(0) + "")));
            }
            long toDate = toDateStamp.getTime() / 1000;
            long fromDate = fromDateStamp.getTime() / 1000;

            String url = "https://finance.yahoo.com/quote/" + quote + "/history/?period1=" + fromDate + "&period2=" + toDate;
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("table tbody tr");
            List<Data> exchangeData = new ArrayList<>();

            for (Element row : rows) {
                Elements cols = row.select("td");
                if (cols.size() == 7) {
                    String date = cols.get(0).text();
                    Double open = cols.get(1).text().replace(",", "").isEmpty() ? 0: Double.parseDouble(cols.get(1).text().replace(",", ""));
                    Double high =cols.get(2).text().replace(",", "").isEmpty() ? 0: Double.parseDouble(cols.get(2).text().replace(",", ""));
                    Double low = cols.get(3).text().replace(",", "").isEmpty() ? 0:Double.parseDouble(cols.get(3).text().replace(",", ""));
                    Double close = cols.get(4).text().replace(",", "").isEmpty() ? 0:Double.parseDouble(cols.get(4).text().replace(",", ""));
//                    Double volume = cols.get(6).text().replace(",", "").isEmpty() ? 0:Double.parseDouble(cols.get(6).text().replace(",", ""));
                    Double adjClose = cols.get(5).text().replace(",", "").isEmpty()? 0:Double.parseDouble(cols.get(5).text().replace(",", ""));

                    Data rate = new Data();
                    rate.setDate(date);
                    rate.setOpen(open);
                    rate.setHigh(high);
                    rate.setLow(low);
                    rate.setClose(close);
                    rate.setVolume(null);
                    rate.setAdjustedClose(adjClose);
                    rate.setCurrency(dataDTO.getFrom().toUpperCase()+"-"+dataDTO.getTo().toUpperCase());

                    exchangeData.add(rate);
                }else{
                    return responseHandler.setMessageResponse("No Response Received From Server",404,null);
                }
            }
            dataDaoImplementation.saveScrapedData(exchangeData);
            return responseHandler.setMessageResponse("Data Scraped Successfully",200,exchangeData);
        }
        return responseHandler.setMessageResponse("Invalid Time Period or Currency! Try Again",404,null);
    }

    @Override
    @Transactional
    public BaseResponse scrapeDataFromList(DataDTO dataDTO) throws IOException {
        if (dataDTO.getPeriod().contains("W") ||
                dataDTO.getPeriod().contains("M") ||
                dataDTO.getPeriod().contains("Y")) {

            //obtaining timestamps
            LocalDateTime currentDate = LocalDateTime.now();
            Timestamp toDateStamp = Timestamp.valueOf(currentDate);
            Timestamp fromDateStamp = Timestamp.valueOf(currentDate.minusDays(1));
            if (dataDTO.getPeriod().charAt(1) == 'W') {
                fromDateStamp = Timestamp.valueOf(currentDate.minusWeeks(Integer.parseInt(dataDTO.getPeriod().charAt(0) + "")));
            } else if (dataDTO.getPeriod().charAt(1) == 'M') {
                fromDateStamp = Timestamp.valueOf(currentDate.minusMonths(Integer.parseInt(dataDTO.getPeriod().charAt(0) + "")));
            } else {
                fromDateStamp = Timestamp.valueOf(currentDate.minusYears(Integer.parseInt(dataDTO.getPeriod().charAt(0) + "")));
            }
            long toDate = toDateStamp.getTime() / 1000;
            long fromDate = fromDateStamp.getTime() / 1000;

            List<Data> exchangeData = new ArrayList<>();
            for(int i = 0;i<dataDTO.getFromList().size();i++) {
                String quote = dataDTO.getFromList().get(i).toUpperCase() + dataDTO.getToList().get(i).toUpperCase() + "%3DX";
                String url = "https://finance.yahoo.com/quote/" + quote + "/history/?period1=" + fromDate + "&period2=" + toDate;
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("table tbody tr");
                for (Element row : rows) {
                    Elements cols = row.select("td");
                    if (cols.size() == 7) {
                        String date = cols.get(0).text();
                        Double open = cols.get(1).text().replace(",", "").isEmpty() ? 0 : Double.parseDouble(cols.get(1).text().replace(",", ""));
                        Double high = cols.get(2).text().replace(",", "").isEmpty() ? 0 : Double.parseDouble(cols.get(2).text().replace(",", ""));
                        Double low = cols.get(3).text().replace(",", "").isEmpty() ? 0 : Double.parseDouble(cols.get(3).text().replace(",", ""));
                        Double close = cols.get(4).text().replace(",", "").isEmpty() ? 0 : Double.parseDouble(cols.get(4).text().replace(",", ""));
//                    Double volume = cols.get(6).text().replace(",", "").isEmpty() ? 0:Double.parseDouble(cols.get(6).text().replace(",", ""));
                        Double adjClose = cols.get(5).text().replace(",", "").isEmpty() ? 0 : Double.parseDouble(cols.get(5).text().replace(",", ""));

                        Data rate = new Data();
                        rate.setDate(date);
                        rate.setOpen(open);
                        rate.setHigh(high);
                        rate.setLow(low);
                        rate.setClose(close);
                        rate.setVolume(null);
                        rate.setAdjustedClose(adjClose);
                        rate.setCurrency(dataDTO.getFromList().get(i) + "-" + dataDTO.getToList().get(i));

                        exchangeData.add(rate);
                    } else {
                        return responseHandler.setMessageResponse("No Response Received From Server", 404, null);
                    }
                }
            }
            dataDaoImplementation.saveScrapedData(exchangeData);
            return responseHandler.setMessageResponse("Data Scraped Successfully",200,exchangeData);
        }
        return responseHandler.setMessageResponse("Invalid Time Period or Currency! Try Again",404,null);

    }
}
