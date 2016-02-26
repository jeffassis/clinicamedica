package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class Bean que contém as regras de negocio encapsuladas do Funcionario
 *
 * @author jeff-
 */
public class FuncionarioModel {

    /*Declaração das variaveis como propriedades */
    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty senha;
    private StringProperty permissao;

    /**
     * Construtor que converte as variaveis para SimpleProperty
     */
    public FuncionarioModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.senha = new SimpleStringProperty();
        this.permissao = new SimpleStringProperty();
    }

    /**
     * Construtor que passa as proprias variaveis como parametros
     *
     * @param codigo
     * @param nome
     * @param senha
     * @param permissao
     */
    public FuncionarioModel(int codigo, String nome, String senha, String permissao) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.senha = new SimpleStringProperty(senha);
        this.permissao = new SimpleStringProperty(permissao);
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

    public StringProperty getSenhaProperty() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public StringProperty getPermissaoProperty() {
        return this.permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao.set(permissao);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getSenha() {
        return senha.get();
    }

    public String getPermissao() {
        return permissao.get();
    }
}
