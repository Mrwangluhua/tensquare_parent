package com.tensquare.gathering.pojo;

import util.IdWorker;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_gathering")
public class Gathering implements Serializable{

    @Id
    private String id;
    private String  name;
    private String summary;
    private String detail;
    private String sponsor;
    private String image;
    private String starttime;
    private String endtime;
    private String address;
    private String enrolltime;
    private String state;
    private String city;

    public Gathering() {
    }

    public Gathering(String id, String name, String summary, String detail, String sponsor, String image, String starttime, String endtime, String address, String enrolltime, String state, String city) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.detail = detail;
        this.sponsor = sponsor;
        this.image = image;
        this.starttime = starttime;
        this.endtime = endtime;
        this.address = address;
        this.enrolltime = enrolltime;
        this.state = state;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnrolltime() {
        return enrolltime;
    }

    public void setEnrolltime(String enrolltime) {
        this.enrolltime = enrolltime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Gathering{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", detail='" + detail + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", image='" + image + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", address='" + address + '\'' +
                ", enrolltime='" + enrolltime + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
