/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.DependenteModel;
import model.dao.DependentesDAO;
import util.AutoCompleteComboBox;
import util.ConverterDados;
import util.DialogFX;
import util.Funcionalidades;
import util.Log;
import util.MaskFormatter;
import util.ProcessosStage;

/**
 *
 * @author jeanderson
 */
public class MeusDependentesController extends Funcionalidades implements Initializable {
    
    @FXML
    private ComboBox<DependenteModel> cb_dependentes;
    @FXML
    private ComboBox<String> cb_parent;
    @FXML
    private Button bt_cadastrar, bt_cancelar, bt_dados, bt_alterar;
    @FXML
    private MenuItem mn_editar, mn_excluir;
    @FXML
    private DatePicker dp_nascimento;
    @FXML
    private TextField txt_codigo, txt_paciente, txt_dependente, txt_telefone;
    
    private boolean editar, abriuVerDados;
    private AutoCompleteComboBox autoCompleteComboBox;
    private MaskFormatter formatter;
    /*Vamos precisar da referencia de HomeController*/
    private HomeController controller;
    private PacienteDetalheController control;
    private Stage palco;
    private ProcessosStage processo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> parentesco = FXCollections.observableArrayList();
        parentesco.addAll("Pai", "Mãe", "Filho", "Filha", "Irmão", "Irmã", "Primo", "Prima", "Tio", "Tia", "Sobrinho", "Sobrinha");
        cb_parent.setItems(parentesco);
        /*O botão de alterar paciente só funcionara quando for clicado em editar*/
        bt_alterar.setDisable(true);
        cb_dependentes.setConverter(new ConverterDados(ConverterDados.GET_DEPENDENTE_NOME).getDependenteConverter(cb_dependentes));
        this.autoCompleteComboBox = new AutoCompleteComboBox(cb_dependentes);
        this.formatter = new MaskFormatter(dp_nascimento);
        formatter.setMask(MaskFormatter.DATA_BARRA);
        formatter.showMask();
        formatter.addComponente(txt_telefone, MaskFormatter.TEL_8DIG, true);
        
    }

    /**
     * Inicializa Processos importantes e pega a referencia de HomeController.
     *
     * @param homeController
     */
    @Override
    public void iniciarProcessos(Object homeController) {
        this.controller = (HomeController) homeController;
        cb_dependentes.getItems().clear();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_dependentes.setItems(DependentesDAO.executeQuery(null, DependentesDAO.QUERY_TODOS));
                return null;
            }
            
            @Override
            protected void succeeded() {
                super.succeeded();
                autoCompleteComboBox.saveData();
            }
            
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Evento que é lançado ao selecionar um Paciente.
     */
    @FXML
    public void onSelectedPaciente() {
        if (!cb_dependentes.getSelectionModel().isSelected(-1)) {
            DependenteModel dm = cb_dependentes.getSelectionModel().getSelectedItem();
            txt_codigo.setText(dm.getCodigoProperty().getValue().toString());
            txt_paciente.setText(dm.getPacienteModel().getNome());
            txt_dependente.setText(dm.getNome());
            txt_telefone.setText(dm.getTelefone());
            dp_nascimento.getEditor().setText(dm.getNascimento());
            cb_parent.setValue(dm.getParentesco());
        }
    }

    /**
     * Evento que é lançado quando o botão Cadastrar e pressionado.
     *
     * @param evento
     */
    @FXML
    private void onCadastrar(ActionEvent evento) {
        if (editar) {
            DependenteModel dm = cb_dependentes.getSelectionModel().getSelectedItem();
            dm.setNome(txt_dependente.getText().trim());
            dm.setNascimento(dp_nascimento.getEditor().getText());
            dm.setParentesco(cb_parent.getSelectionModel().getSelectedItem());
            dm.setTelefone(txt_telefone.getText());
            if (DependentesDAO.executeUpdates(dm, DependentesDAO.UPDATE) && DependentesDAO.executeUpdates(dm, DependentesDAO.UPDATE_TROCA)) {
                bt_cadastrar.setText("Cadastrar");
                editar = false;
                txt_dependente.setEditable(false);
                txt_telefone.setEditable(false);
                dp_nascimento.getEditor().setEditable(false);
                bt_alterar.setDisable(true);
                bt_cadastrar.setDisable(false);
                /*Altera no comboBox também*/
                cb_dependentes.getItems().set(cb_dependentes.getSelectionModel().getSelectedIndex(), dm);
                DialogFX.showMessage("Dados do dependente alterado com sucesso", "Sucesso!", DialogFX.SUCESS);
            } else {
                DialogFX.showMessage("Houve um erro ao alterar dados", "ERRO!", DialogFX.ERRO);
            }
        } else {
            
        }
    }

    /**
     * Evento que é lançado quando o MenuItem editar e pressionado.
     */
    @FXML
    private void onEditar(ActionEvent evento) {
        if (!editar) {
            editar = true;
            bt_cadastrar.setText("Salvar");
            txt_dependente.setEditable(true);
            txt_telefone.setEditable(true);
            dp_nascimento.getEditor().setEditable(true);
            bt_alterar.setDisable(false);
        }
    }

    /**
     * Evento que é lançado quando o MenuItem excluir e pressionado.
     */
    @FXML
    private void onExcluir() {
        if (!cb_dependentes.getSelectionModel().isSelected(-1)) {
            DependenteModel dm = cb_dependentes.getSelectionModel().getSelectedItem();
            if (DialogFX.showConfirmation("Deseja realmente excluir este dependente ?", "Excluir dependente")) {
                if (DependentesDAO.executeUpdates(dm, DependentesDAO.DELETE)) {
                    cb_dependentes.getItems().remove(dm);
                    DialogFX.showMessage("Dependente Excluido com sucesso", "Sucesso!", DialogFX.SUCESS);
                } else {
                    DialogFX.showMessage("Houve um erro ao excluir o dependente", "ERRO!", DialogFX.ERRO);
                }
            }
        } else {
            DialogFX.showMessage("Por favor selecione antes um dependente!", "Dependente não selecionado", DialogFX.ATENCAO);
        }
    }

    /**
     * Evento que é lançado quando o botão cancelar e pressionado.
     *
     * @param evento
     */
    @FXML
    private void onCancelar(ActionEvent evento) {
        if (editar) {
            bt_cadastrar.setText("Cadastrar");
            editar = false;
            txt_dependente.setEditable(false);
            txt_telefone.setEditable(false);
            dp_nascimento.getEditor().setEditable(false);
            bt_alterar.setDisable(true);
            onSelectedPaciente();
        } else {
            /*Fecha a janela*/
            ((Node) evento.getSource()).getScene().getWindow().hide();
        }
        
    }

    /**
     * Método reiniciar os dados da tela.
     */
    @Override
    public void refresh() {
        txt_codigo.setText("");
        txt_dependente.setText("");
        txt_telefone.setText("");
        dp_nascimento.getEditor().setText("");
        cb_parent.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void onVerDados() {
        if (!cb_dependentes.getSelectionModel().isSelected(-1)) {
            if (!abriuVerDados) {
                try {
                    FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/PacienteDetalhe.fxml"));
                    this.palco = new Stage();
                    Parent root = carregar.load();
                    Scene cena = new Scene(root);
                    palco.setScene(cena);
                    palco.setTitle("Dados do Paciente");
                    palco.setResizable(false);
                    palco.setMaximized(false);
                    palco.initModality(Modality.APPLICATION_MODAL);
                    this.control = carregar.getController();
                    palco.show();
                    control.iniciarProcessos(cb_dependentes.getSelectionModel().getSelectedItem().getPacienteModel(), controller);
                    this.abriuVerDados = true;
                } catch (IOException ex) {
                    Logger.getLogger(MeusDependentesController.class.getName()).log(Level.SEVERE, null, ex);
                    Log.relatarExcecao(MeusDependentesController.class.getName(), ex);
                }
            } else {
                palco.show();
                control.iniciarProcessos(cb_dependentes.getSelectionModel().getSelectedItem().getPacienteModel(), controller);
            }
        } else {
            DialogFX.showMessage("Por favor selecione antes um dependente", "Nenhum dependente selecionado", DialogFX.ATENCAO);
        }
        
    }
    
    /**
     * Evento do botão alterar paciente.
     */
    @FXML
    private void onAlterarPaciente(){
        if(processo == null){
            processo = new ProcessosStage("/view/TrocaPaciente.fxml",new TrocaPacienteController());
            processo.addTitle("Alterar Paciente de Dependencia");
            processo.setModality(true);
            processo.show(null,cb_dependentes.getSelectionModel().getSelectedItem(),this);
        }else{
            processo.show(null,cb_dependentes.getSelectionModel().getSelectedItem(),this);
        }
    }
    
    /**
     * Método retorna o comboBox desta Classe.
     * @return 
     */
    public ComboBox<DependenteModel> getCbDependentes(){
        return this.cb_dependentes;
    }
    
}
