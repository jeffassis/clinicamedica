/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.bean.PacienteModel;
import util.DialogFX;
import util.MaskFormatter;

/**
 *
 * @author jeand
 */
public class PacienteDetalheController implements Initializable {

    @FXML
    private DatePicker dp_nascimento, dp_cliente;
    @FXML
    private TextField txt_matricula, txt_nome, txt_endereco, txt_telefone, txt_sexo, txt_bairro, txt_cidade;
    @FXML
    private TextField txt_cep, txt_documento, txt_tipo, txt_email, txt_observacoes;
    @FXML
    private Button bt_editar, bt_voltar;

    private MaskFormatter formatter;
    private HomeController homeController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.formatter = new MaskFormatter();
        formatter.addComponente(dp_nascimento, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_cliente, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(txt_telefone, MaskFormatter.TEL_DIG, true);
    }

    /**
     * Executa processos inicias para preenchimento da Tela.
     *
     * @param homeController
     * @param pacienteModel
     */
    public void iniciarProcessos(PacienteModel pacienteModel, HomeController homeController) {
        /*Pegamos a referencia de home para chamar outra tela nos eventos do botoes*/
        this.homeController = homeController;
        if (pacienteModel != null) {
            dp_nascimento.getEditor().setText(pacienteModel.getNascimento());
            dp_cliente.getEditor().setText(pacienteModel.getData_cliente());
            txt_matricula.setText(pacienteModel.getCodigoProperty().getValue().toString());
            txt_nome.setText(pacienteModel.getNome());
            txt_endereco.setText(pacienteModel.getEndereco());
            txt_telefone.setText(pacienteModel.getTelefone());
            txt_sexo.setText(pacienteModel.getSexo());
            txt_bairro.setText(pacienteModel.getBairroModel().getNome());
            txt_cidade.setText(pacienteModel.getCidadeModel().getNome());
            txt_cep.setText(pacienteModel.getCep());
            txt_documento.setText(pacienteModel.getDocumento());
            txt_tipo.setText(pacienteModel.getTipo());
            txt_email.setText(pacienteModel.getEmail());
            txt_observacoes.setText(pacienteModel.getObs());
        } else {
            DialogFX.showMessage("Dados do Paciente em branco", "Sem Dados", DialogFX.ERRO);
        }
    }

    /**
     * Evento do botão editar.
     *
     * @param evento
     */
    @FXML
    private void onEditar(ActionEvent evento) {
        ((Node) evento.getSource()).getScene().getWindow().hide();
        this.homeController.meusPacientes();

    }

    /**
     * Evento do botão voltar.
     *
     * @param evento
     */
    @FXML
    private void onVoltar(ActionEvent evento) {
        ((Node) evento.getSource()).getScene().getWindow().hide();
    }

}
