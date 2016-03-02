package model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jeff-
 */
public class AgendamentoModel {

    private IntegerProperty codigo;
    private StringProperty turno;
    private StringProperty motivo;
    private StringProperty data;
    private MedicoModel medicoModel;
    private PacienteModel pacienteModel;

    public AgendamentoModel() {
        this.codigo = new SimpleIntegerProperty();
        this.turno = new SimpleStringProperty();
        this.motivo = new SimpleStringProperty();
        this.data = new SimpleStringProperty();
    }

    public AgendamentoModel(int codigo, String turno, String motivo, String data) {
        this.codigo = new SimpleIntegerProperty(codigo);
        this.turno = new SimpleStringProperty(turno);
        this.motivo = new SimpleStringProperty(motivo);
        this.data = new SimpleStringProperty(data);
    }

    public AgendamentoModel(MedicoModel medicoModel, PacienteModel pacienteModel) {
        this.medicoModel = medicoModel;
        this.pacienteModel = pacienteModel;
    }

    public IntegerProperty getCodigoProperty() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public StringProperty getTurnoProperty() {
        return this.turno;
    }

    public void setTurno(String turno) {
        this.turno.set(turno);
    }

    public StringProperty getMotivoProperty() {
        return this.motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo.set(motivo);
    }

    public StringProperty getDataProperty() {
        return this.data;
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public MedicoModel getMedicoModel() {
        return medicoModel;
    }

    public void setMedicoModel(MedicoModel medicoModel) {
        this.medicoModel = medicoModel;
    }

    public PacienteModel getPacienteModel() {
        return pacienteModel;
    }

    public void setPacienteModel(PacienteModel pacienteModel) {
        this.pacienteModel = pacienteModel;
    }

    public int getCodigo() {
        return codigo.get();
    }

    public String getTurno() {
        return turno.get();
    }

    public String getMotivo() {
        return motivo.get();
    }

    public String getData() {
        return data.get();
    }
}
