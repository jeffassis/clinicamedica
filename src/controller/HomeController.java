package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.bean.PacienteModel;
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
    private boolean abriuCadMedico, abriuCadPaciente, abriuCadFuncionario, abriuAgendamento,
            abriuMeusPacientes, abriuCadCidade, abriuCadBairro, abriuCadProcedimento, abriuGerarProcedimento;
    /*Declaração do Stage para colocar as caracteristicas da nova Janela*/
    private Stage cadMedicoPalco, cadPacientePalco, cadFuncionarioPalco, AgendamentoPalco,
            MeusPacientesPalco, cadCidadePalco, cadBairroPalco, cadProcedimentoPalco,
            gerarProcedimentoPalco, primaryStage;
    /*Declaração que representa a class Controller*/
    private MedicosController medicosController;
    private PacientesController pacientesController;
    private FuncionarioController funcionarioController;
    private AgendamentoController agendamentoController;
    private MeusPacientesController meusPacientesController;
    private CidadeController cidadeController;
    private BairroController bairroController;
    private ProcedimentosController procedimentosController;
    private GerarProcedimentoController gerarProcedimentoController;
    /*Declaração dos TreeItem*/
    private TreeItem<String> root, nodeA, nodeB, nodeC,
            nodeA1, nodeA2, nodeA3, nodeA4, nodeA5, nodeA6,
            nodeB1, nodeB2;
    /*Declaração dos nossos SplitPanes*/
    @FXML
    private SplitPane split_superior, split_lateral;

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
        this.nodeB = new TreeItem<>("Gerenciamento", new ImageView(icon));
        this.nodeC = new TreeItem<>("Relatórios", new ImageView(icon));
        /*Adicionando os filhos do root da TreeItem*/
        root.getChildren().addAll(nodeA, nodeB, nodeC);
        /*Deixa o TreeItem já aberto!*/
        nodeA.setExpanded(true);
        nodeB.setExpanded(true);

        this.nodeA1 = new TreeItem<>("Médicos", new ImageView(icon));
        this.nodeA2 = new TreeItem<>("Pacientes", new ImageView(icon));
        this.nodeA3 = new TreeItem<>("Funcionários", new ImageView(icon));
        this.nodeA4 = new TreeItem<>("Procedimentos", new ImageView(icon));
        this.nodeA5 = new TreeItem<>("Cidades", new ImageView(icon));
        this.nodeA6 = new TreeItem<>("Bairro", new ImageView(icon));
        /*Adicionando os filhos do nodeA*/
        nodeA.getChildren().addAll(nodeA1, nodeA2, nodeA3, nodeA4, nodeA5, nodeA6);

        this.nodeB1 = new TreeItem<>("Meus Pacientes", new ImageView(icon));
        this.nodeB2 = new TreeItem<>("Agendamentos", new ImageView(icon));
        /*Adicionando os filhos do nodeB*/
        nodeB.getChildren().addAll(nodeB1, nodeB2);
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
                        /*Passamos null já que não vamos editar os dados*/
                        cadPaciente(false, null);
                        break;
                    case "Funcionários":
                        cadFuncionario();
                        break;
                    case "Procedimentos":
                        cadProcedimento();
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
                    case "Agendamentos":
                        agendamento();
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
                /*Informamos de onde ele veio que no caso é a tela Home, assim ele fica
                dependende dela, se ela minimizar a todas depedendes dela tbm vão*/
                this.cadMedicoPalco.initOwner(primaryStage);
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
     * Método que pega a referencia do Stage principal de Home E Ajusta o SplitPane a cada Tela.
     *
     * @param primaryStage
     */
    public void getTelaHome(Stage primaryStage) {
        this.primaryStage = primaryStage;
        /*Adicionamos um evento que é chamado toda a vez que a tela é maximizada ou minimizada
        como é um evento observavel utilizando o MaximizedProperty*/
        this.primaryStage.maximizedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            /*Se o novo valor for true, significa que está maximizado*/
            if (newValue) {
                /*Se vc não entender muito bem o Divider position, abre o Scene Builder
                e olha no SplitPanes que lá tem o Dividir Position ai vc vai ter a ideia*/
                split_superior.setDividerPosition(0,0.14);
                split_lateral.setDividerPosition(0, 0.15);
            }else{
                split_superior.setDividerPosition(0,0.20);
                split_lateral.setDividerPosition(0, 0.23);
            }
        });
    }

    /**
     * Método para chamar a GUI de Cadastro de Pacientes
     *
     * @param editar - Informar se a tela está sendo aberta para editar dados ao
     * não.
     * @param tabela - Passar uma tabela de pacienteModel, caso não for editar
     * passar null.
     */
    public void cadPaciente(boolean editar, TableView<PacienteModel> tabela) {
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
                this.pacientesController.iniciarProcessos(editar, tabela);
                this.abriuCadPaciente = true;
                /*Pegamos a referencia dessa classe*/
                this.pacientesController.getHomeController(this);

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.cadPacientePalco.show();
            this.pacientesController.iniciarProcessos(editar, tabela);
            this.cadPacientePalco.requestFocus();
        }
    }

    /**
     * Método para chamar a GUI de Cadastro de Pacientes
     */
    @FXML
    private void cadPaciente() {
        /*Se modificamos o método, o fxml não encontra mais ele. Por isso fiz dessa forma amigo*/
        cadPaciente(false, null);
    }

    /**
     * Método que chama a Tela Meus pacientes.
     */
    @FXML
    public void meusPacientes() {
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
                /*Pegamos a referencia da HomeController*/
                this.meusPacientesController.pegarHomeReferencia(this);
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
                //this.agendamentoController.carregarTabela();
                this.abriuAgendamento = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.AgendamentoPalco.show();
            this.AgendamentoPalco.requestFocus();
            this.agendamentoController.iniciarProcessos();
            //this.agendamentoController.carregarTabela();
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

    @FXML
    private void cadProcedimento() {
        if (!abriuCadProcedimento) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Procedimentos.fxml"));
            this.cadProcedimentoPalco = new Stage();
            try {
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.procedimentosController = carregar.getController();
                this.cadProcedimentoPalco.setTitle("Cadastros de Procedimentos");
                this.cadProcedimentoPalco.setScene(scene);
                this.cadProcedimentoPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadProcedimentoPalco.show();
                this.procedimentosController.carregarTabela();
                this.abriuCadProcedimento = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadProcedimentoPalco.show();
            this.cadProcedimentoPalco.requestFocus();
            this.procedimentosController.carregarTabela();
        }
    }

    @FXML
    private void gerarProcedimento() {
        if (!abriuGerarProcedimento) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/GerarProcedimento.fxml"));
            try {
                this.gerarProcedimentoPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.gerarProcedimentoController = carregar.getController();
                this.gerarProcedimentoPalco.setTitle("Gerenciamento de Procedimentos");
                this.gerarProcedimentoPalco.setScene(scene);
                this.gerarProcedimentoPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.gerarProcedimentoPalco.show();

                this.abriuGerarProcedimento = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.gerarProcedimentoPalco.show();
            this.gerarProcedimentoPalco.requestFocus();
        }
    }

}
