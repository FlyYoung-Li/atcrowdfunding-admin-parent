package com.crowd.entitydemo;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/29 11:58
 */
public class Address {
    private String country;
    private String province;
    private String city;

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address() {
    }

    public Address(String country, String province, String city) {
        this.country = country;
        this.province = province;
        this.city = city;
    }
}
