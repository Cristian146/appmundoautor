package com.sjbestudio.appmundoautor;

public class UserModel
{
    String name;
    Boolean error;
    String password;
    String rol;

    public UserModel() {}

    public UserModel(String name, String password, String rol)
    {
        this.name = name;
        this.password = password;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setName(String usuario) {
        this.name = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
