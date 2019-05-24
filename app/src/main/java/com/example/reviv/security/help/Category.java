package com.example.reviv.security.help;

import android.graphics.drawable.Drawable;

public class Category {
    private String categoryId;
    private Drawable imagen;
    private String title;
    private String desc;


    public Category(String categoryId,String title,String desc,Drawable imagen)
    {
        this.title=title;
        this.imagen=imagen;
        this.categoryId=categoryId;
        this.desc=desc;
    }
    public Category()
    {
        this.title="";
        this.imagen=null;
        this.categoryId="";
        this.desc="";
    }

    public String getTitle(){return this.title;}

    public String getDesc(){return this.desc;}

    public void setTittle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return this.imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}

