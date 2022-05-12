package com.sjbestudio.appmundoautor;

import java.util.List;

import lombok.Data;

@Data
public class ProductoRequest {
    public Boolean error;
    public List<ProductosVO> data;
}
