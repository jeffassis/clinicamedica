package aplicacao;

import controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe Principal da Aplicação
 *
 * @author jeff-
 */
public class Launch extends Application {

    /**
     * Sobrecarga do método start para ser chamado o parametro Stage, utilizando
     * o Parent com o FXMLoader para carregar o FXML
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
        Parent root = carregar.load();
        Scene cena = new Scene(root);
        primaryStage.setTitle("TelaPrincipal");
        primaryStage.setScene(cena);
        HomeController controller = carregar.getController();
        /*Passamos a referencia da Stage principal*/
        controller.getTelaHome(primaryStage);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
        primaryStage.show();
    }

    /**
     * Método main para fazer a Chamada do argumento
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
