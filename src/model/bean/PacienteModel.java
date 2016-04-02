package model.bean;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class Bean que contém as regras de negocio encapsuladas
 *
 * @author jeff-
 */
public class PacienteModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty nascimento;
    private StringProperty endereco;
    private StringProperty telefone;
    private StringProperty cep;
    private StringProperty documento;
    private StringProperty sexo;
    private StringProperty data_cliente;
    private StringProperty tipo;
    private StringProperty email;
    private StringProperty obs;
    private CidadeModel cidadeModel;
    private BairroModel bairroModel;
    private BooleanProperty status;

    /**
     * Construtor que converte a variavel em SimpleProperty
     */
    public PacienteModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.nascimento = new SimpleStringProperty();
        this.endereco = new SimpleStringProperty();
        this.telefone = new SimpleStringProperty();
        this.cep = new SimpleStringProperty();
        this.documento = new SimpleStringProperty();
        this.sexo = new SimpleStringProperty();
        this.data_cliente = new SimpleStringProperty();
        this.tipo = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.obs = new SimpleStringProperty();
        this.status = new SimpleBooleanProperty();
    }

    /**
     * Construtor que passa as variaveis como proprio param SimpleProperty
     *
     * @param codigo
     * @param nome
     * @param nascimento
     * @param endereco
     * @param telefone
     * @param cep
     * @param documento
     * @param sexo
     * @param data_cliente
     * @param tipo
     * @param email
     * @param obs
     */
    public PacienteModel(int codigo, String nome, String nascimento, String endereco, String telefone, String cep, String documento, String sexo, String data_cliente, String tipo, String email, String obs) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.endereco = new SimpleStringProperty(endereco);
        this.telefone = new SimpleStringProperty(telefone);
        this.cep = new SimpleStringProperty(cep);
        this.documento = new SimpleStringProperty(documento);
        this.sexo = new SimpleStringProperty(sexo);
        this.data_cliente = new SimpleStringProperty(data_cliente);
        this.tipo = new SimpleStringProperty(tipo);
        this.email = new SimpleStringProperty(email);
        this.obs = new SimpleStringProperty(obs);
        /*Apenas inicio ele*/
        this.status = new SimpleBooleanProperty();
    }
    
    /**
     * Construtor que passa as variaveis como proprio param SimpleProperty
     *
     * @param codigo
     * @param nome
     * @param nascimento
     * @param endereco
     * @param telefone
     * @param cep
     * @param documento
     * @param sexo
     * @param data_cliente
     * @param tipo
     * @param email
     * @param obs
     */
    public PacienteModel(int codigo, String nome, String nascimento, String endereco, String telefone, String cep, String documento, String sexo, String data_cliente, String tipo, String email, String obs,boolean status) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.endereco = new SimpleStringProperty(endereco);
        this.telefone = new SimpleStringProperty(telefone);
        this.cep = new SimpleStringProperty(cep);
        this.documento = new SimpleStringProperty(documento);
        this.sexo = new SimpleStringProperty(sexo);
        this.data_cliente = new SimpleStringProperty(data_cliente);
        this.tipo = new SimpleStringProperty(tipo);
        this.email = new SimpleStringProperty(email);
        this.obs = new SimpleStringProperty(obs);
        this.status = new SimpleBooleanProperty(status);
    }

    /**
     * Construtor que passa 2 Class como parametros
     *
     * @param cidadeModel
     * @param bairroModel
     */
    public PacienteModel(CidadeModel cidadeModel, BairroModel bairroModel) {
        this.cidadeModel = cidadeModel;
        this.bairroModel = bairroModel;
    }

    /*Getters e Setters não comuns..... */
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

    public StringProperty getNascimentoProperty() {
        return this.nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento.set(nascimento);
    }

    public StringProperty getEnderecoProperty() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public StringProperty getTelefoneProperty() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public StringProperty getCepProperty() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }

    public StringProperty getDocumentoProperty() {
        return this.documento;
    }

    public void setDocumento(String documento) {
        this.documento.set(documento);
    }

    public StringProperty getSexoProperty() {
        return this.sexo;
    }
    public BooleanProperty getStatusProperty(){
        return this.status;
    }

    public void setSexo(String sexo) {
        this.sexo.set(sexo);
    }

    public StringProperty getData_clienteProperty() {
        return this.data_cliente;
    }

    public void setData_cliente(String data_cliente) {
        this.data_cliente.set(data_cliente);
    }

    public StringProperty getTipoProperty() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public StringProperty getEmailProperty() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty getObsProperty() {
        return this.obs;
    }

    public void setObs(String obs) {
        this.obs.set(obs);
    }

    public CidadeModel getCidadeModel() {
        return cidadeModel;
    }

    public void setCidadeModel(CidadeModel cidadeModel) {
        this.cidadeModel = cidadeModel;
    }

    public BairroModel getBairroModel() {
        return bairroModel;
    }

    public void setBairroModel(BairroModel bairroModel) {
        this.bairroModel = bairroModel;
    }
    
    public void setStatus(boolean status){
        this.status.set(status);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getNascimento() {
        return nascimento.get();
    }

    public String getEndereco() {
        return endereco.get();
    }

    public String getTelefone() {
        return telefone.get();
    }

    public String getCep() {
        return cep.get();
    }

    public String getDocumento() {
        return documento.get();
    }

    public String getSexo() {
        return sexo.get();
    }

    public String getData_cliente() {
        return data_cliente.get();
    }

    public String getTipo() {
        return tipo.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getObs() {
        return obs.get();
    }
    
    public boolean getStatus(){
        return this.status.get();
    }
    
}
