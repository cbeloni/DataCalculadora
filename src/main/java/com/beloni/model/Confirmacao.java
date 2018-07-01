package com.beloni.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="convidados")
public class Confirmacao {
    @Id
    private Integer id_convidado;
    private String nome;
    private String telefone;
    private String mensagem;

    public Integer getId_convidado() {
        return id_convidado;
    }

    public void setId_convidado(Integer id_convidado) {
        this.id_convidado = id_convidado;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
