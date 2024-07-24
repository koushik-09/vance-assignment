package com.assignment.vance.service;

import com.assignment.vance.dao.dto.DataDTO;
import com.assignment.vance.entity.Data;
import com.assignment.vance.response.BaseResponse;

import java.io.IOException;
import java.util.List;

public interface DataService {

    BaseResponse scrapeData(DataDTO dataDTO) throws IOException;

    BaseResponse scrapeDataFromList(DataDTO dataDTO) throws IOException;
}
