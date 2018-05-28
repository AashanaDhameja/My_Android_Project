package com.example.hp.bakeology;

import java.io.Serializable;

/**
 * Created by hp on 21-01-2018.
 */

public class Cat_det implements Serializable {

    private String cat_name;
    private String logo_path;

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }


}
