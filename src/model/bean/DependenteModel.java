/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe que representa um Dependente.
 *
 * @author jeanderson
 */
public class DependenteModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty telefone;
    private StringProperty nascimento;
    private StringProperty parentesco;
    private PacienteModel pacienteModel;

    /**
     * Construtor Padrão.
     *
     * @param codigo
     * @param nome
     * @param telefone
     * @param nascimento
     * @param parentesco
     */
    public DependenteModel(int codigo, String nome, String telefone, String nascimento, String parentesco) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.telefone = new SimpleStringProperty(telefone);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.parentesco = new SimpleStringProperty(parentesco);
    }

    /**
     * Construtor Padrão.
     */
    public DependenteModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.telefone = new SimpleStringProperty();
        this.nascimento = new SimpleStringProperty();
        this.parentesco = new SimpleStringProperty();
    }

    public IntegerProperty getCodigoProperty() {
        return codigo;
    }

    public StringProperty getNomeProperty() {
        return nome;
    }

    public StringProperty getTelefoneProperty() {
        return telefone;
    }

    public StringProperty getNascimentoProperty() {
        return nascimento;
    }

    public StringProperty getParentescoProperty() {
        return parentesco;
    }

    public Integer getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getTelefone() {
        return telefone.get();
    }

    public String getNascimento() {
        return nascimento.get();
    }

    public String getParentesco() {
        return parentesco.get();
    }
    
    public void setCodigo(int codigo){
        this.codigo.set(codigo);
    }
    
    public void setNome(String nome){
        this.nome.set(nome);
    }
    public void setTelefone(String telefone){
        this.telefone.set(telefone);
    }
    public void setNascimento(String nascimento){
        this.nascimento.set(nascimento);
    }
    public void setParentesco(String parentesco){
        this.parentesco.set(parentesco);
    }
    
    /**
     * Método seta o pacienteModel desta Classe.
     * @param pacienteModel 
     */
    public void setPacienteModel(PacienteModel pacienteModel){
        this.pacienteModel = pacienteModel;
    }
    
    /**
     * Retorna o pacienteModel desta classe.
     * @return 
     */
    public PacienteModel getPacienteModel(){
        return this.pacienteModel;
    }

}
