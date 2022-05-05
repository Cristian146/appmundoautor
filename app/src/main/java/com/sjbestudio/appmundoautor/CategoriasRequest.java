package com.sjbestudio.appmundoautor;

import java.util.List;

import lombok.Data;

@Data
public class CategoriasRequest {
    public Boolean error;
    public List<CategoriasVO> data;
}
