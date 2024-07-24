package com.assignment.vance.dao;

import com.assignment.vance.entity.Data;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class DataDaoImplementation implements DataDao{

    private EntityManager entityManager;

    @Autowired
    public DataDaoImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void saveScrapedData(List<Data> scrapedData) {
        TypedQuery<Data> typedQuery = entityManager.createQuery("from Data", Data.class);
        List<Data> dataList = typedQuery.getResultList();
        List<String> tempList = new ArrayList<String>();
        for(Data data : dataList){
            tempList.add(data.getDate()+"-"+data.getCurrency());
        }
        LinkedHashSet<Data> dataSet = new LinkedHashSet<>(dataList);
        for(Data data : scrapedData){
            if(!tempList.contains(data.getDate()+"-"+data.getCurrency())){
                dataSet.add(data);
            }
        }
        for(Data data : dataSet){
            entityManager.merge(data);
        }
    }
}
