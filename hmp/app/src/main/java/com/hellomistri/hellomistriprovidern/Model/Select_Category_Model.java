package com.hellomistri.hellomistriprovidern.Model;

public class Select_Category_Model {
    private String id;
    private String cat_subtitle;
    private String cat_name;
    private String description;
    private String cat_status;
    private String cat_img;
    private String cat_video;
    private String cat_icon;
    private String catbanner;

    public Select_Category_Model(String id, String cat_subtitle, String cat_name, String description, String cat_status, String cat_img, String cat_video, String cat_icon, String catbanner) {
        this.id = id;
        this.cat_subtitle = cat_subtitle;
        this.cat_name = cat_name;
        this.description = description;
        this.cat_status = cat_status;
        this.cat_img = cat_img;
        this.cat_video = cat_video;
        this.cat_icon = cat_icon;
        this.catbanner = catbanner;
    }
// Getter Methods

    public String getId() {
        return id;
    }

    public String getCat_subtitle() {
        return cat_subtitle;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getDescription() {
        return description;
    }

    public String getCat_status() {
        return cat_status;
    }

    public String getCat_img() {
        return cat_img;
    }

    public String getCat_video() {
        return cat_video;
    }

    public String getCat_icon() {
        return cat_icon;
    }

    public String getCatbanner() {
        return catbanner;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCat_subtitle(String cat_subtitle) {
        this.cat_subtitle = cat_subtitle;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCat_status(String cat_status) {
        this.cat_status = cat_status;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

    public void setCat_video(String cat_video) {
        this.cat_video = cat_video;
    }

    public void setCat_icon(String cat_icon) {
        this.cat_icon = cat_icon;
    }

    public void setCatbanner(String catbanner) {
        this.catbanner = catbanner;
    }


    public boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
