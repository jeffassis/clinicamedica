package model.bean;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author jeff-
 */
public class ValorExameModel {

    private IntegerProperty codigo;
    private DoubleProperty valor_categoria;
    private DoubleProperty valor_exame;
    private ExameModel exameModel;
    private CategoriaModel categoriaModel;

    public ValorExameModel() {
        this.codigo = new SimpleIntegerProperty();
        this.valor_categoria = new SimpleDoubleProperty();
        this.valor_exame = new SimpleDoubleProperty();
    }

    public ValorExameModel(int codigo, Double valor_paciente, Double valor_exame) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.valor_categoria = new SimpleDoubleProperty(valor_paciente);
        this.valor_exame = new SimpleDoubleProperty(valor_exame);
    }

    public ValorExameModel(ExameModel exameModel, CategoriaModel categoriaModel) {
        this.exameModel = exameModel;
        this.categoriaModel = categoriaModel;
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public DoubleProperty getValor_categoriaProperty() {
        return this.valor_categoria;
    }

    public void setValor_categoria(Double valor_categoria) {
        this.valor_categoria.set(valor_categoria);
    }

    public DoubleProperty getValor_exameProperty() {
        return this.valor_exame;
    }

    public void setValor_exame(Double valor_exame) {
        this.valor_exame.set(valor_exame);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public Double getValor_categoria() {
        return valor_categoria.get();
    }

    public Double getValor_exame() {
        return valor_exame.get();
    }

    public ExameModel getExameModel() {
        return exameModel;
    }

    public void setExameModel(ExameModel exameModel) {
        this.exameModel = exameModel;
    }

    public CategoriaModel getCategoriaModel() {
        return categoriaModel;
    }

    public void setCategoriaModel(CategoriaModel categoriaModel) {
        this.categoriaModel = categoriaModel;
    }
}
