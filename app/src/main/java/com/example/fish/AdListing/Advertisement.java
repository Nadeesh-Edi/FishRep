package com.example.fish.AdListing;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Advertisement implements Serializable {
    private String title;
    private String location;
    private Float price;
    private Integer contact;
    private String description;
    private Boolean negotiable;
    private String date;
    private String imageUrlMain;
    private String image2;
    private String image3;


    @Exclude
    private String key;
    private String UID;
    private Long dateDifference;

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDateDifference() {
        return dateDifference;
    }

    public void setDateDifference(Long dateDifference) {
        this.dateDifference = dateDifference;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String uID) {
        this.UID = uID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImageUrlMain() {
        return imageUrlMain;
    }

    public void setImageUrlMain(String imageUrlMain) {
        this.imageUrlMain = imageUrlMain;
    }

    public Advertisement() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNegotiable() {
        return negotiable;
    }

    public void setNegotiable(Boolean negotiable) {
        this.negotiable = negotiable;
    }

    public String getDate() {
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDate() {
        this.date = java.time.LocalDate.now().toString();
    }
}
