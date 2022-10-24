package com.example.consultaexames.ui.paciente;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Paciente {

    //atributos
    private String nome;
    private String cpf;
    private String senha;
    private int id;
    private boolean aceite;
    private int sexo;

    //metodos

    public boolean isAceite() {return aceite;}
    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }
    public int getSexo() { return sexo;}
    public void setSexo(int sexo) {this.sexo = sexo;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getCPF() {return cpf;}
    public void setCPF(String cpf) {this.cpf = cpf;}
    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    //CONSTRUTOR - inicializa atributos de um arquivo JSon
    public Paciente (JSONObject jp) {
        try {
            int numero = (int) jp.get("id");
            this.setId(numero);
            int codigo = (int) jp.get("sexo");
            this.setSexo(codigo);
            this.setNome((String) jp.get("nome"));
            this.setSenha((String) jp.get("senha"));
            this.setCPF((String) jp.get("cpf"));
            boolean bool =Boolean.getBoolean(jp.get("aceite").toString());
            this.setAceite(bool);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //CONSTRUTOR - Inicializa os atributos para gerar Objeto Json
    public Paciente () {
        this.setId(0);
        this.setNome("");
        this.setSenha("");
        this.setCPF("");
        this.setAceite(false);
        this.setSexo(2);


    }
    //Metodo retorna o objeto com dados no formato JSON
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.id);
            json.put("nome", this.nome);
            json.put("senha", this.senha);
            json.put("cpf", this.cpf);
            json.put("aceite", this.aceite);
            json.put("sexo", this.sexo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
