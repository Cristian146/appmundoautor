package com.sjbestudio.appmundoautor;

import lombok.Data;

@Data
public class ProductosVO {
    public Integer id;
    public String row_order;
    public String name;
    public Integer category_id;
    public Integer subcategory_id;
    public String indicator;
    public String image;
    public String other_image;
    public String description ;
    public String status;
    public String date_added;

}
