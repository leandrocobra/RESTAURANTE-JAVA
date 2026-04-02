package model;

import model.enums.PerfilUsuario;

public class Usuario {
    private int id;
    private String usuario;
    private String senha;
    private PerfilUsuario perfil;
    private boolean ativo;

    public Usuario(int id, String usuario, String senha, PerfilUsuario perfil){
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.perfil = perfil;
        this.ativo = true;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public PerfilUsuario getPerfil(){
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil){
        this.perfil = perfil;
    }

    public boolean isAtivo(){
        return ativo;
    }

    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }
}
