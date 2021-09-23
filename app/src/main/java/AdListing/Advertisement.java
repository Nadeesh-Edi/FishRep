package AdListing;

import android.os.Build;

import androidx.annotation.RequiresApi;

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
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
