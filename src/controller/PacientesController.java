package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.bean.CidadeModel;
import model.dao.CidadeDAO;
import model.dao.MedicoDAO;
import util.ConverterDados;
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
    @FXML
    private ComboBox<CidadeModel> cb_cidade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFormatter formatter = new MaskFormatter(txt_telefone);
        formatter.setMask(MaskFormatter.TEL_8DIG);
        /*Caso vc queria mostrar a mascara no TextField*/
        formatter.showMask();

        /*Utilizando a nossa Classe converter CidadeModel*/
        this.cb_cidade.setConverter(new ConverterDados(ConverterDados.GET_CIDADE_NOME).getCidadeConverter());
    }

    /**
     * Executa as funções iniciais como preencher o comboBox da Cidade
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_cidade.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_cidade.setItems(CidadeDAO.executeQuery(null, CidadeDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}
