package com.sjbestudio.appmundoautor;

public class UserModel
{
    String usuario;
    String password;
    String rol;

    public UserModel() {}

    public UserModel(String usuario, String password, String rol)
    {
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
