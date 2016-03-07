package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class ExameModel {

    private IntegerProperty codigo;
    private StringProperty descricao;

    public ExameModel() {
        this.codigo = new SimpleIntegerProperty();
        this.descricao = new SimpleStringProperty();
    }

    public ExameModel(int codigo, String descricao) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public StringProperty getDescricaoProperty() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getDescricao() {
        return descricao.get();
    }

}
