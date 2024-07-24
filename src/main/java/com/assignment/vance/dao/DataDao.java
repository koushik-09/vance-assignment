package com.assignment.vance.dao;

import com.assignment.vance.entity.Data;

import java.util.List;

public interface DataDao {

    void saveScrapedData(List<Data> scrapedData);
}
