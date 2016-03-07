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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class TabelaExameController implements Initializable {

    private boolean abriuPrecoExame;

    private Stage precoExamePalco;

    private PrecoExameController precoExameController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void precoExame() {
        if (!abriuPrecoExame) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/PrecoExame.fxml"));
            try {
                this.precoExamePalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.precoExameController = carregar.getController();
                this.precoExamePalco.setTitle("Gerenciamento de Exames");
                this.precoExamePalco.setScene(scene);
                this.precoExamePalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.precoExamePalco.setResizable(false);
                this.precoExamePalco.setMaximized(false);
                this.precoExamePalco.initModality(Modality.APPLICATION_MODAL);
                this.precoExamePalco.show();
                this.precoExameController.carregarTabela();
                this.precoExameController.iniciarProcessos();
                this.abriuPrecoExame = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.precoExamePalco.show();
            this.precoExamePalco.requestFocus();
            this.precoExameController.carregarTabela();
            this.precoExameController.iniciarProcessos();
        }
    }

    @FXML
    private void onSave() {
        /* TODO aqui e a dificuldade pois eu queria chamar o metodo la da Class
        HomeController o Método e o precoExame
         */
    }
}
