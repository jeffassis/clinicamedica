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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Log;

/**
 * Class controller da Tela Principal
 *
 * @author jeff-
 */
public class HomeController implements Initializable {

    /*Declarando a TreeView*/
    @FXML
    private TreeView<String> treeView;
    /*Colocando uma imagem de pasta na Itens da TreeView*/
    Image icon = new Image(getClass().getResourceAsStream("/img/folder 16x16.png"));

    /*Variavel booleana para verificar se as Janelas já estão abertas*/
    private boolean abriuCadMedico, abriuCadPaciente, abriuCadFuncionario, abriuAgendamento, abriuMeusPacientes, abriuCadCidade, abriuCadBairro;
    /*Declaração do Stage para colocar as caracteristicas da nova Janela*/
    private Stage cadMedicoPalco, cadPacientePalco, cadFuncionarioPalco, AgendamentoPalco, MeusPacientesPalco, cadCidadePalco, cadBairroPalco;
    /*Declaração que representa a class Controller*/
    private MedicosController medicosController;
    private PacientesController pacientesController;
    private FuncionarioController funcionarioController;
    private AgendamentoController agendamentoController;
    private MeusPacientesController meusPacientesController;
    private CidadeController cidadeController;
    private BairroController bairroController;
    /*Declaração dos TreeItem*/
    private TreeItem<String> root, nodeA, nodeB, nodeC, nodeA1, nodeA2, nodeA3, nodeA4, nodeA5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Declarando a TreeView com root Pai*/
        this.root = new TreeItem<>("Administração", new ImageView(icon));
        /*Deixa o TreeItem já aberto!*/
        root.setExpanded(true);

        this.nodeA = new TreeItem<>("Cadastros", new ImageView(icon));
        this.nodeB = new TreeItem<>("Agendamento", new ImageView(icon));
        this.nodeC = new TreeItem<>("Meus Pacientes", new ImageView(icon));
        /*Adicionando os filhos do root da TreeItem*/
        root.getChildren().addAll(nodeA, nodeB, nodeC);
        /*Deixa o TreeItem já aberto!*/
        nodeA.setExpanded(true);

        this.nodeA1 = new TreeItem<>("Médicos", new ImageView(icon));
        this.nodeA2 = new TreeItem<>("Pacientes", new ImageView(icon));
        this.nodeA3 = new TreeItem<>("Funcionários", new ImageView(icon));
        this.nodeA4 = new TreeItem<>("Cidades", new ImageView(icon));
        this.nodeA5 = new TreeItem<>("Bairro", new ImageView(icon));
        /*Adicionando os filhos do nodeA*/
        nodeA.getChildren().addAll(nodeA1, nodeA2, nodeA3, nodeA4, nodeA5);
        /*Adicionando o Node Pai a TreeView*/
        treeView.setRoot(root);
    }

    /**
     * Método que da ação com mouse na TreeView
     *
     * @param mouseEvent
     */
    @FXML
    public void mouseTreeView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            /*verificação para evitar exceção*/
            if (item.getValue() != null) {
                switch (item.getValue()) {
                    case "Médicos":
                        cadMedico();
                        break;
                    case "Pacientes":
                        cadPaciente();
                        break;
                    case "Funcionários":
                        cadFuncionario();
                        break;
                    case "Agendamento":
                        agendamento();
                        break;
                    case "Cidades":
                        cadCidade();
                        break;
                    case "Bairro":
                        cadBairro();
                        break;
                    case "Meus Pacientes":
                        meusPacientes();
                        break;
                    default:
                        break;
                }
            }
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
                this.cadMedicoPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadMedicoPalco.show();
                this.medicosController.carregarTabela();
                /*informamos que a tela já foi aberta uma vez*/
                this.abriuCadMedico = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
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
                this.cadPacientePalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadPacientePalco.show();
                this.pacientesController.iniciarProcessos();
                this.abriuCadPaciente = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.cadPacientePalco.show();
            this.pacientesController.iniciarProcessos();
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
                this.cadFuncionarioPalco.setTitle("Cadastro de Funcionários");
                this.cadFuncionarioPalco.setScene(scene);
                this.cadFuncionarioPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadFuncionarioPalco.show();
                this.funcionarioController.carregarTabela();

                this.abriuCadFuncionario = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.cadFuncionarioPalco.show();
            this.cadFuncionarioPalco.requestFocus();
            this.funcionarioController.carregarTabela();
        }
    }

    @FXML
    private void agendamento() {
        if (!abriuAgendamento) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Agendamento.fxml"));
            try {
                this.AgendamentoPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.agendamentoController = carregar.getController();
                this.AgendamentoPalco.setTitle("Agendamento");
                this.AgendamentoPalco.setScene(scene);
                this.AgendamentoPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.AgendamentoPalco.show();
                this.agendamentoController.iniciarProcessos();
                this.abriuAgendamento = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.AgendamentoPalco.show();
            this.AgendamentoPalco.requestFocus();
            this.agendamentoController.iniciarProcessos();
        }
    }

    @FXML
    private void meusPacientes() {
        if (!abriuMeusPacientes) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/MeusPacientes.fxml"));
            try {
                this.MeusPacientesPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.meusPacientesController = carregar.getController();
                this.MeusPacientesPalco.setTitle("Meus Pacientes");
                this.MeusPacientesPalco.setScene(scene);
                this.MeusPacientesPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.MeusPacientesPalco.show();
                this.meusPacientesController.carregarTabela();
                this.abriuMeusPacientes = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.MeusPacientesPalco.show();
            this.meusPacientesController.carregarTabela();
            this.MeusPacientesPalco.requestFocus();
        }
    }

    @FXML
    private void cadCidade() {
        if (!abriuCadCidade) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Cidade.fxml"));
            try {
                this.cadCidadePalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.cidadeController = carregar.getController();
                this.cadCidadePalco.setTitle("Cadastro de Cidades");
                this.cadCidadePalco.setScene(scene);
                this.cadCidadePalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadCidadePalco.show();
                this.cidadeController.carregarTabela();
                this.abriuCadCidade = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.cadCidadePalco.show();
            this.cadCidadePalco.requestFocus();
            this.cidadeController.carregarTabela();
        }
    }

    @FXML
    private void cadBairro() {
        if (!abriuCadBairro) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Bairro.fxml"));
            try {
                this.cadBairroPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.bairroController = carregar.getController();
                this.cadBairroPalco.setTitle("Cadastro de Bairros");
                this.cadBairroPalco.setScene(scene);
                this.cadBairroPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadBairroPalco.show();
                this.bairroController.carregarTabela();
                this.bairroController.iniciarProcessos();
                this.abriuCadBairro = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.cadBairroPalco.show();
            this.cadBairroPalco.requestFocus();
            this.bairroController.carregarTabela();
            this.bairroController.iniciarProcessos();

        }
    }

}
