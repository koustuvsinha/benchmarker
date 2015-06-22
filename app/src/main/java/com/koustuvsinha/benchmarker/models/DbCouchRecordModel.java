package com.koustuvsinha.benchmarker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by koustuv on 22/6/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DbCouchRecordModel {
    private long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "age")
    private int age;

    @JsonProperty(value = "_id")
    private String newId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }
}
