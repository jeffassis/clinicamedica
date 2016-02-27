package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.MaskFormatter;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class PacientesController implements Initializable {

    @FXML
    private DatePicker dp_nascimento, dp_cliente;
    @FXML
    private TextField txt_matricula, txt_nome, txt_endereco, txt_telefone;
    @FXML
    private TextField txt_cep, txt_documento, txt_tipo, txt_email, txt_observacoes;
    @FXML
    private Button bt_incluir, bt_gravar, bt_excluir, bt_localizar, bt_imprimir, bt_cancelar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MaskFormatter formatter = new MaskFormatter(txt_telefone);
        formatter.setMask(MaskFormatter.TEL_8DIG);
        /*Caso vc queria mostrar a mascara no TextField*/
        formatter.showMask();
    }

}
