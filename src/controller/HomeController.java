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
import javafx.stage.Stage;

/**
 * Class controller da Tela Principal
 *
 * @author jeff-
 */
public class HomeController implements Initializable {

    /*Variavel booleana para verificar se as Janelas já estão abertas*/
    private boolean abriuCadMedico, abriuCadPaciente;
    /*Declaração do Stage para colocar as caracteristicas da nova Janela*/
    private Stage cadMedicoPalco, cadPacientePalco;
    /*Declaração que representa a class Controller*/
    private MedicosController medicosController;
    private PacientesController pacientesController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadPacientePalco.requestFocus();
            this.cadPacientePalco.show();
        }
    }

}
