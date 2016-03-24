package model.bean;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class MensalidadeModel {

    private IntegerProperty codigo;
    private DoubleProperty valor;
    private DoubleProperty desconto;
    private StringProperty mes;
    private StringProperty data_pagto;
    private PacienteModel pacienteModel;
    /*Variavel que vai ter o valor com desconto*/
    private DoubleProperty valorComDesconto;
    /*Variavel booleana para verificar o status da mensalidade*/
    private BooleanProperty status;

    public MensalidadeModel() {
        this.codigo = new SimpleIntegerProperty();
        this.valor = new SimpleDoubleProperty();
        this.desconto = new SimpleDoubleProperty();
        this.mes = new SimpleStringProperty();
        this.data_pagto = new SimpleStringProperty();
        this.valorComDesconto = new SimpleDoubleProperty();
        this.status = new SimpleBooleanProperty();
    }

    public MensalidadeModel(int codigo, Double valor, Double desconto, String mes, String data_pagto, Double valorComDesconto, Boolean status) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.valor = new SimpleDoubleProperty(valor);
        this.desconto = new SimpleDoubleProperty(desconto);
        this.mes = new SimpleStringProperty(mes);
        this.data_pagto = new SimpleStringProperty(data_pagto);
        this.valorComDesconto = new SimpleDoubleProperty(valorComDesconto);
        this.status = new SimpleBooleanProperty(status);
    }

    /*Construtor do PacienteModel*/
    public MensalidadeModel(PacienteModel pacienteModel) {
        this.pacienteModel = pacienteModel;
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public DoubleProperty getValorProperty() {
        return this.valor;
    }

    public void setValor(Double valor) {
        this.valor.set(valor);
    }

    public DoubleProperty getDescontoProperty() {
        return this.desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto.set(desconto);
    }

    public StringProperty getMesProperty() {
        return this.mes;
    }

    public void setMes(String mes) {
        this.mes.set(mes);
    }

    public StringProperty getData_pagtoProperty() {
        return this.data_pagto;
    }

    public void setData_pagto(String data_pagto) {
        this.data_pagto.set(data_pagto);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public Double getValor() {
        return valor.get();
    }

    public Double getDesconto() {
        return desconto.get();
    }

    public String getMes() {
        return mes.get();
    }

    public String getData_pagto() {
        return data_pagto.get();
    }

    /*Getter e Setter do PacienteModel*/
    public PacienteModel getPacienteModel() {
        return pacienteModel;
    }

    public void setPacienteModel(PacienteModel pacienteModel) {
        this.pacienteModel = pacienteModel;
    }

    /**
     * Getters e Setters da variavel Booleana
     */
    public BooleanProperty getStatusProperty() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status.set(true);
    }

    public Boolean getStatus() {
        return status.get();
    }

    /*Não há set para não quebrar nosso encapsulamento, pois a regra de negocio
    deve ser feita somente aqui*/
    /**
     * Retornar o Valor com o desconto.
     *
     * @return
     */
    public DoubleProperty getValorComDesconto() {
        if (valor != null && desconto != null) {
            this.valorComDesconto.set(valor.getValue() - desconto.getValue());
            return this.valorComDesconto;
        } else {
            this.valorComDesconto.set(0);
            return valorComDesconto;
        }
    }
}
