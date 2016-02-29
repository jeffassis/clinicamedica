package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.bean.BairroModel;
import model.bean.CidadeModel;
import model.dao.BairroDAO;
import model.dao.CidadeDAO;
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
    @FXML
    private ComboBox<BairroModel> cb_bairro;
    @FXML
    private ComboBox<String> cb_sexo;
    ObservableList<String> listaSexo = FXCollections.observableArrayList("Feminino", "Masculino");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFormatter formatter = new MaskFormatter(txt_telefone);
        formatter.setMask(MaskFormatter.TEL_8DIG);
        /*Caso vc queria mostrar a mascara no TextField*/
        formatter.showMask();
        /*Adicionamos outros componentes*/
        formatter.addComponente(dp_cliente, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento, MaskFormatter.DATA_BARRA, true);

        /*Utilizando a nossa Classe converter CidadeModel*/
        this.cb_cidade.setConverter(new ConverterDados(ConverterDados.GET_CIDADE_NOME).getCidadeConverter());
        this.cb_bairro.setConverter(new ConverterDados(ConverterDados.GET_BAIRRO_NOME).getBairroConverter());

        cb_sexo.setItems(listaSexo);
    }

    /**
     * Executa as funções iniciais como preencher o comboBox da Cidade
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_cidade.getItems().clear();
        cb_bairro.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_cidade.setItems(CidadeDAO.executeQuery(null, CidadeDAO.QUERY_TODOS));
                cb_bairro.setItems(BairroDAO.executeQuery(null, BairroDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}
