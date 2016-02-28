package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class BairroModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private CidadeModel cidadeModel;

    public BairroModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
    }

    public BairroModel(CidadeModel cidadeModel) {
        this.cidadeModel = cidadeModel;
    }

    public BairroModel(int codigo, String nome) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
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

    public CidadeModel getCidadeModel() {
        return cidadeModel;
    }

    public void setCidadeModel(CidadeModel cidadeModel) {
        this.cidadeModel = cidadeModel;
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getNome() {
        return nome.get();
    }
}
