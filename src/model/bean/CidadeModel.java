package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class Bean que contém as regras de negocio encapsuladas
 *
 * @author jeff-
 */
public class CidadeModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty sigla;

    public CidadeModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.sigla = new SimpleStringProperty();
    }

    public CidadeModel(int codigo, String nome, String sigla) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.sigla = new SimpleStringProperty(sigla);
    }

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

    public StringProperty getSiglaProperty() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla.set(sigla);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }

    public String getSigla() {
        return sigla.get();
    }
}
