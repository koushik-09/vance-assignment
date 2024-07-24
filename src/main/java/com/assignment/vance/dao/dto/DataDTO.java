package com.assignment.vance.dao.dto;

import java.util.List;

public class DataDTO {
    private long id;
    private String from;
    private String to;
    private String period;

    private List<String> fromList;
    private List<String> toList;

    public DataDTO() {
    }

    public DataDTO(String from, String to, String period) {
        this.from = from;
        this.to = to;
        this.period = period;
    }

    public DataDTO(List<String> fromList, List<String> toList,String period) {
        this.period = period;
        this.fromList = fromList;
        this.toList = toList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getFromList() {
        return fromList;
    }

    public void setFromList(List<String> fromList) {
        this.fromList = fromList;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }
}
