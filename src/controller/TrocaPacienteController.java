/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.bean.DependenteModel;
import model.bean.PacienteModel;
import model.dao.DependentesDAO;
import model.dao.PacienteDAO;
import util.ConverterDados;
import util.DialogFX;
import util.Funcionalidades;

/**
 *
 * @author jeand
 */
public class TrocaPacienteController extends Funcionalidades implements Initializable {

    @FXML
    private ComboBox<PacienteModel> cb_paciente;
    @FXML
    private Button bt_trocar, bt_cancelar;
    private DependenteModel dependente;
    private MeusDependentesController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void iniciarProcessos(Object dependente, Object meusDependentesController) {
        super.iniciarProcessos();
        this.dependente = (DependenteModel) dependente;
        this.controller = (MeusDependentesController) meusDependentesController;
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_paciente.setItems(PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS));
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                cb_paciente.setConverter(new ConverterDados(ConverterDados.GET_PACIENTE_NOME).getPacienteConverter(cb_paciente));
            }

        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Evento do botão trocar.
     */
    @FXML
    private void onTrocar(ActionEvent evento) {
        if (cb_paciente.getSelectionModel().getSelectedIndex() != -1) {
            this.dependente.setPacienteModel(cb_paciente.getSelectionModel().getSelectedItem());
            this.controller.getCbDependentes().getSelectionModel().getSelectedItem().setPacienteModel(cb_paciente.getSelectionModel().getSelectedItem());
            controller.onSelectedPaciente();
            ((Node) evento.getSource()).getScene().getWindow().hide();
        } else {
            DialogFX.showMessage("Selecione antes um Paciente", "Paciente não selecionado", DialogFX.ATENCAO);
        }
    }

    /**
     * Evento do botão cancelar.
     *
     * @param evento
     */
    @FXML
    private void onCancelar(ActionEvent evento) {
        ((Node) evento.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void refresh() {
        super.refresh();
        cb_paciente.getSelectionModel().clearSelection();
    }
    
    
}
