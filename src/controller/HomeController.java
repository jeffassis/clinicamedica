package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Class controller da Tela Principal
 *
 * @author jeff-
 */
public class HomeController implements Initializable {

    /*Testando a TreeView*/
    @FXML
    private TreeView<String> treeView;

    Image icon = new Image(getClass().getResourceAsStream("/img/folder 16x16.png"));

    /*Variavel booleana para verificar se as Janelas já estão abertas*/
    private boolean abriuCadMedico, abriuCadPaciente, abriuCadFuncionario;
    /*Declaração do Stage para colocar as caracteristicas da nova Janela*/
    private Stage cadMedicoPalco, cadPacientePalco, cadFuncionarioPalco;
    /*Declaração que representa a class Controller*/
    private MedicosController medicosController;
    private PacientesController pacientesController;
    private FuncionarioController funcionarioController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Testando a TreeView
         */
        /*Declarando a TreeView com root Pai*/
        TreeItem<String> root = new TreeItem<>("Administração", new ImageView(icon));
        /*Deixa o TreeItem já aberto!*/
        root.setExpanded(true);

        TreeItem<String> nodeA = new TreeItem<>("Cadastros", new ImageView(icon));
        TreeItem<String> nodeB = new TreeItem<>("Edit", new ImageView(icon));
        TreeItem<String> nodeC = new TreeItem<>("Help", new ImageView(icon));
        root.getChildren().addAll(nodeA, nodeB, nodeC);
        nodeA.setExpanded(true);

        TreeItem<String> nodeA1 = new TreeItem<>("Médicos", new ImageView(icon));
        TreeItem<String> nodeA2 = new TreeItem<>("Pacientes", new ImageView(icon));
        TreeItem<String> nodeA3 = new TreeItem<>("Funcionários", new ImageView(icon));
        nodeA.getChildren().addAll(nodeA1, nodeA2, nodeA3);

        treeView.setRoot(root);
    }

    /**
     * Método que da ação com mouse na TreeView
     */
    public void mouseTreeView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            System.out.println(item.getValue());
        }
    }

    /**
     * Método para chamar a GUI de Cadastro de Médicos
     */
    @FXML
    private void cadMedico() {
        /*Verifica se a Janela já foi aberta*/
        if (!abriuCadMedico) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Medicos.fxml"));
            try {
                this.cadMedicoPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.medicosController = carregar.getController();

                this.cadMedicoPalco.setTitle("Cadastro de Médicos");
                this.cadMedicoPalco.setScene(scene);
                this.cadMedicoPalco.show();
                this.medicosController.carregarTabela();
                /*informamos que a tela já foi aberta uma vez*/
                this.abriuCadMedico = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadMedicoPalco.show();
            this.cadMedicoPalco.requestFocus();
            this.medicosController.carregarTabela();
        }
    }

    /**
     * Método para chamar a GUI de Cadastro de Pacientes
     */
    @FXML
    private void cadPaciente() {
        if (!abriuCadPaciente) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Pacientes.fxml"));
            try {
                this.cadPacientePalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.pacientesController = carregar.getController();
                this.cadPacientePalco.setTitle("Cadastro de Pacientes");
                this.cadPacientePalco.setScene(scene);
                this.cadPacientePalco.show();

                this.abriuCadPaciente = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadPacientePalco.requestFocus();
            this.cadPacientePalco.show();
            this.cadPacientePalco.requestFocus();
        }
    }

    @FXML
    private void cadFuncionario() {
        if (!abriuCadFuncionario) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Funcionario.fxml"));
            try {
                this.cadFuncionarioPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.funcionarioController = carregar.getController();
                this.cadFuncionarioPalco.setTitle("Cadastro de Funcionarios");
                this.cadFuncionarioPalco.setScene(scene);
                this.cadFuncionarioPalco.show();
                this.funcionarioController.carregarTabela();

                this.abriuCadFuncionario = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadFuncionarioPalco.show();
            this.cadFuncionarioPalco.requestFocus();
            this.funcionarioController.carregarTabela();
        }
    }

}
