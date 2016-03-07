package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class Bean que contém as regras de negocio encapsuladas do médico
 *
 * @author jeff-
 */
public class MedicoModel {

    /*Declaração das variaveis como propriedades */
    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty crm;
    private StringProperty especialidade;
    private StringProperty telefone;
    private StringProperty celular;

    /**
     * Construtor que converte as variaveis para SimpleProperty
     */
    public MedicoModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.crm = new SimpleStringProperty();
        this.especialidade = new SimpleStringProperty();
        this.telefone = new SimpleStringProperty();
        this.celular = new SimpleStringProperty();
    }

    /**
     * Construtor que passa as proprias variaveis como parametros
     *
     * @param codigo
     * @param nome
     * @param crm
     * @param especialidade
     */
    public MedicoModel(int codigo, String nome, String crm, String especialidade, String telefone, String celular) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.crm = new SimpleStringProperty(crm);
        this.especialidade = new SimpleStringProperty(especialidade);
        this.telefone = new SimpleStringProperty(telefone);
        this.celular = new SimpleStringProperty(celular);
    }

    // Getters e Setters não comuns e utilizando Property .....
    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public StringProperty getNomeProperty() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty getCrmProperty() {
        return this.crm;
    }

    public void setCrm(String crm) {
        this.crm.set(crm);
    }

    public StringProperty getEspecialidadeProperty() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade.set(especialidade);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getCrm() {
        return crm.get();
    }

    public String getEspecialidade() {
        return especialidade.get();
    }

    public StringProperty getTelefoneProperty() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public StringProperty getCelularProperty() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular.set(celular);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public String getCelular() {
        return celular.get();
    }
}
