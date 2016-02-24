package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class MedicoModel {

    private IntegerProperty codigo;
    private StringProperty nome;
    private StringProperty crm;
    private StringProperty especialidade;

    public MedicoModel() {
        this.codigo = new SimpleIntegerProperty();
        this.nome = new SimpleStringProperty();
        this.crm = new SimpleStringProperty();
        this.especialidade = new SimpleStringProperty();
    }

    public MedicoModel(int codigo, String nome, String crm, String especialidade) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.crm = new SimpleStringProperty(crm);
        this.especialidade = new SimpleStringProperty(especialidade);
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
}
