package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Image adm = new Image(getClass().getResourceAsStream("/img/folder16.png"));

    /*Variavel booleana para verificar se as Janelas já estão abertas*/
    private boolean abriuCadMedico, abriuCadPaciente, abriuCadFuncionario, abriuAgendamento,
            abriuMeusPacientes, abriuCadCidade, abriuCadBairro, abriuCadExame,
            abriuCadCategoria, abriuTabelaExame;

    /*Declaração do Stage para colocar as caracteristicas da nova Janela*/
    private Stage cadMedicoPalco, cadPacientePalco, cadFuncionarioPalco, AgendamentoPalco,
            MeusPacientesPalco, cadCidadePalco, cadBairroPalco, cadExamePalco,
            primaryStage, cadCategoriaPalco, tabelaExamePalco;

    /*Declaração que representa a class Controller*/
    private MedicosController medicosController;
    private PacientesController pacientesController;
    private FuncionarioController funcionarioController;
    private AgendamentoController agendamentoController;
    private MeusPacientesController meusPacientesController;
    private CidadeController cidadeController;
    private BairroController bairroController;
    private ExameController exameController;
    private CategoriasController categoriasController;
    private TabelaExameController tabelaExameController;

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
        this.root = new TreeItem<>("Administração", new ImageView(adm));
        /*Deixa o TreeItem já aberto!*/
        root.setExpanded(true);

        this.nodeA = new TreeItem<>("Cadastros", new ImageView(adm));
        this.nodeB = new TreeItem<>("Gerenciamento", new ImageView(adm));
        this.nodeC = new TreeItem<>("Relatórios", new ImageView(adm));
        /*Adicionando os filhos do root da TreeItem*/
        root.getChildren().addAll(nodeA, nodeB, nodeC);
        /*Deixa o TreeItem já aberto!*/
        nodeA.setExpanded(true);
        nodeB.setExpanded(true);

        this.nodeA1 = new TreeItem<>("Médicos", new ImageView(icon));
        this.nodeA2 = new TreeItem<>("Pacientes", new ImageView(icon));
        this.nodeA3 = new TreeItem<>("Funcionários", new ImageView(icon));
        this.nodeA4 = new TreeItem<>("Exame", new ImageView(icon));
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
                    case "Exame":
                        cadExame();
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
                /*Alteramos para que não seja maximizado e nem redimensionavel*/
                this.cadMedicoPalco.setResizable(false);
                this.cadMedicoPalco.setMaximized(false);
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
            this.medicosController.refresh();
            this.cadMedicoPalco.show();
            this.cadMedicoPalco.requestFocus();
            this.medicosController.carregarTabela();
        }
    }

    /**
     * Método que pega a referencia do Stage principal de Home E Ajusta o
     * SplitPane a cada Tela.
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
                split_superior.setDividerPosition(0, 0.16);
                split_lateral.setDividerPosition(0, 0.15);
            } else {
                split_superior.setDividerPosition(0, 0.20);
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
                this.cadPacientePalco.setResizable(false);
                this.cadPacientePalco.setMaximized(false);
                this.cadPacientePalco.initOwner(primaryStage);
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
            /*Deixamos a tela como se estivesse abrindo pela primeira vez*/
            this.pacientesController.refresh();
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
                this.MeusPacientesPalco.setResizable(false);
                this.MeusPacientesPalco.setMaximized(false);
                this.MeusPacientesPalco.initOwner(primaryStage);
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
                this.cadFuncionarioPalco.setResizable(false);
                this.cadFuncionarioPalco.setMaximized(false);
                this.cadFuncionarioPalco.initOwner(primaryStage);
                this.cadFuncionarioPalco.show();
                this.funcionarioController.carregarTabela();

                this.abriuCadFuncionario = true;

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class
                        .getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.funcionarioController.refresh();
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
                this.AgendamentoPalco.setResizable(false);
                this.AgendamentoPalco.setMaximized(false);
                this.AgendamentoPalco.initOwner(primaryStage);
                this.AgendamentoPalco.show();
                this.agendamentoController.iniciarProcessos();
                //this.agendamentoController.carregarTabela();
                this.abriuAgendamento = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.agendamentoController.refresh();
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
                this.cadCidadePalco.setResizable(false);
                this.cadCidadePalco.setMaximized(false);
                this.cadCidadePalco.initOwner(primaryStage);
                this.cadCidadePalco.show();
                this.cidadeController.carregarTabela();
                this.abriuCadCidade = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.cidadeController.refresh();
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
                this.cadBairroPalco.setResizable(false);
                this.cadBairroPalco.setMaximized(false);
                this.cadBairroPalco.initOwner(primaryStage);
                this.cadBairroPalco.show();
                this.bairroController.carregarTabela();
                this.bairroController.iniciarProcessos();
                this.abriuCadBairro = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(HomeController.class.getName(), ex);
            }
        } else {
            this.bairroController.refresh();
            this.cadBairroPalco.show();
            this.cadBairroPalco.requestFocus();
            this.bairroController.carregarTabela();
            this.bairroController.iniciarProcessos();
        }
    }

    @FXML
    private void cadExame() {
        if (!abriuCadExame) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Exame.fxml"));
            this.cadExamePalco = new Stage();
            try {
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.exameController = carregar.getController();
                this.cadExamePalco.setTitle("Cadastros de Exame");
                this.cadExamePalco.setScene(scene);
                this.cadExamePalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadExamePalco.setResizable(false);
                this.cadExamePalco.setMaximized(false);
                this.cadExamePalco.initOwner(primaryStage);
                this.cadExamePalco.show();
                this.exameController.carregarTabela();
                this.abriuCadExame = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.exameController.refresh();
            this.cadExamePalco.show();
            this.cadExamePalco.requestFocus();
            this.exameController.carregarTabela();
        }
    }

    @FXML
    private void cadCategoria() {
        if (!abriuCadCategoria) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Categorias.fxml"));
            try {
                this.cadCategoriaPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.categoriasController = carregar.getController();
                this.cadCategoriaPalco.setTitle("Cadastro de Categorias");
                this.cadCategoriaPalco.setScene(scene);
                this.cadCategoriaPalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.cadCategoriaPalco.setResizable(false);
                this.cadCategoriaPalco.setMaximized(false);
                this.cadCategoriaPalco.initOwner(primaryStage);
                this.cadCategoriaPalco.show();
                this.categoriasController.carregarTabela();
                this.abriuCadCategoria = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadCategoriaPalco.show();
            this.cadCategoriaPalco.requestFocus();
            this.categoriasController.carregarTabela();
        }
    }

    @FXML
    private void tabelaExame() {
        if (!abriuTabelaExame) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/TabelaExame.fxml"));
            try {
                this.tabelaExamePalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.tabelaExameController = carregar.getController();
                this.tabelaExamePalco.setTitle("Tabela de preço de Exames");
                this.tabelaExamePalco.setScene(scene);
                this.tabelaExamePalco.getIcons().add(new Image(getClass().getResourceAsStream("/img/medico_icon.png")));
                this.tabelaExamePalco.setResizable(false);
                this.tabelaExamePalco.setMaximized(false);
                this.tabelaExamePalco.initOwner(primaryStage);
                this.tabelaExamePalco.show();
                this.abriuTabelaExame = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.tabelaExamePalco.show();
            this.tabelaExamePalco.requestFocus();
        }
    }

}
