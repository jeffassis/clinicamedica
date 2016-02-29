package aplicacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tela Principal");
        primaryStage.setScene(scene);
        /*Deixa já maximizado*/
        //primaryStage.setMaximized(true);
        /*deixa em tela cheia*/
        //primaryStage.setFullScreen(true);
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
