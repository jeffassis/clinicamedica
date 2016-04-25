package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.bean.PacienteModel;
import util.ProcessosStage;

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
    private Stage primaryStage;
    /*Declaração dos TreeItem*/
    private TreeItem<String> root, nodeA, nodeB, nodeC,
            nodeA1, nodeA2, nodeA3, nodeA4, nodeA5, nodeA6, nodeA7,
            nodeB1, nodeB2, nodeB3, nodeB4, nodeB5;
    /*Declaração dos nossos SplitPanes*/
    @FXML
    private SplitPane split_superior, split_lateral;
    /*Nova forma de Chamar as Telas*/
    ProcessosStage telaMedico, telaPacientes, telaMeusPacientes, telaFuncionarios
            , telaAgendamento, telaCidade, telaBairro, telaExame, telaCategoria
            , telaDeExames, telaDependentes, telaMensalidades, telaMeusDependentes, telaGerarMensalidades;

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
        this.nodeA7 = new TreeItem<>("Dependentes", new ImageView(icon));
        /*Adicionando os filhos do nodeA*/
        nodeA.getChildren().addAll(nodeA1, nodeA2, nodeA3, nodeA4, nodeA5, nodeA6, nodeA7);

        this.nodeB1 = new TreeItem<>("Meus Pacientes", new ImageView(icon));
        this.nodeB2 = new TreeItem<>("Agendamentos", new ImageView(icon));
        this.nodeB3 = new TreeItem<>("Tabela de Exames", new ImageView(icon));
        this.nodeB4 = new TreeItem<>("Meus Dependentes", new ImageView(icon));
        this.nodeB5 = new TreeItem<>("Mensalidades", new ImageView(icon));
        /*Adicionando os filhos do nodeB*/
        nodeB.getChildren().addAll(nodeB1, nodeB4, nodeB3, nodeB2, nodeB5);
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
                    case "Dependentes":
                        cadDependentes();
                        break;
                    case "Meus Dependentes":
                        meusDependentes();
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
                    case "Tabela de Exames":
                        tabelaExame();
                        break;
                    case "Mensalidades":
                        mensalidade();
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
        if (this.telaMedico == null) {
            this.telaMedico = new ProcessosStage("/view/Medicos.fxml", new MedicosController());
            telaMedico.addTitle("Cadastro de Médicos");
            telaMedico.addIcon("/img/medico_icon.png");
            telaMedico.addPrimaryStage(primaryStage);
            telaMedico.show(null, null, null);
        } else {
            telaMedico.show(null, null, null);
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
        if (this.telaPacientes == null) {
            this.telaPacientes = new ProcessosStage("/view/Pacientes.fxml", new PacientesController());
            telaPacientes.addTitle("Cadastro de Pacientes");
            telaPacientes.addIcon("/img/medico_icon.png");
            telaPacientes.addPrimaryStage(primaryStage);
            telaPacientes.setEditar(editar);
            telaPacientes.show(tabela, null, null);
        } else {
            telaPacientes.setEditar(editar);
            telaPacientes.show(tabela, null, null);
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
        if (telaMeusPacientes == null) {
            this.telaMeusPacientes = new ProcessosStage("/view/MeusPacientes.fxml", new MeusPacientesController());
            telaMeusPacientes.addTitle("Meus Pacientes");
            telaMeusPacientes.addIcon("/img/medico_icon.png");
            telaMeusPacientes.addPrimaryStage(primaryStage);
            telaMeusPacientes.show(null, this, null);
        } else {
            telaMeusPacientes.show(null, this, null);
        }
    }

    @FXML
    private void cadFuncionario() {
        if (telaFuncionarios == null) {
            this.telaFuncionarios = new ProcessosStage("/view/Funcionario.fxml", new FuncionarioController());
            telaFuncionarios.addTitle("Cadastro de Funcionários");
            telaFuncionarios.addIcon("/img/medico_icon.png");
            telaFuncionarios.addPrimaryStage(primaryStage);
            telaFuncionarios.show(null, null, null);
        } else {
            telaFuncionarios.show(null, null, null);
        }
    }

    @FXML
    private void agendamento() {
        if (this.telaAgendamento == null) {
            this.telaAgendamento = new ProcessosStage("/view/Agendamento.fxml", new AgendamentoController());
            telaAgendamento.addTitle("Agendamento");
            telaAgendamento.addIcon("/img/medico_icon.png");
            telaAgendamento.addPrimaryStage(primaryStage);
            telaAgendamento.show(null, null, null);
            /*Não segue o padrão das outras Classes, o iniciarProcessos nesse caso tem q acontecer depois do show*/
            ((AgendamentoController) telaAgendamento.getController()).iniciandoProcessos();
        } else {
            telaAgendamento.show(null, null, null);
            ((AgendamentoController) telaAgendamento.getController()).iniciandoProcessos();
        }
    }

    @FXML
    private void cadCidade() {
        if (telaCidade == null) {
            this.telaCidade = new ProcessosStage("/view/Cidade.fxml", new CidadeController());
            telaCidade.addTitle("Cadastro de Cidades");
            telaCidade.addIcon("/img/medico_icon.png");
            telaCidade.addPrimaryStage(primaryStage);
            telaCidade.show(null, null, null);
        } else {
            telaCidade.show(null, null, null);
        }
    }

    @FXML
    private void cadBairro() {
        if (telaBairro == null) {
            this.telaBairro = new ProcessosStage("/view/Bairro.fxml", new BairroController());
            telaBairro.addTitle("Cadastro de Cidades");
            telaBairro.addIcon("/img/medico_icon.png");
            telaBairro.addPrimaryStage(primaryStage);
            telaBairro.show(null, null, null);
        } else {
            telaBairro.show(null, null, null);
        }
    }

    @FXML
    private void cadExame() {
        if (telaExame == null) {
            this.telaExame = new ProcessosStage("/view/Exame.fxml", new ExameController());
            telaExame.addTitle("Cadastros de Exame");
            telaExame.addIcon("/img/medico_icon.png");
            telaExame.addPrimaryStage(primaryStage);
            telaExame.show(null, null, null);
        } else {
            telaExame.show(null, null, null);
        }
    }

    @FXML
    private void cadCategoria() {
        if (telaCategoria == null) {
            this.telaCategoria = new ProcessosStage("/view/Categorias.fxml", new CategoriasController());
            telaCategoria.addTitle("Cadastro de Categorias");
            telaCategoria.addIcon("/img/medico_icon.png");
            telaCategoria.addPrimaryStage(primaryStage);
            telaCategoria.show(null, null, null);
        } else {
            telaCategoria.show(null, null, null);
        }
    }

    @FXML
    private void tabelaExame() {
        if (telaDeExames == null) {
            this.telaDeExames = new ProcessosStage("/view/TabelaExame.fxml", new TabelaExameController());
            telaDeExames.addTitle("Tabela de preço de Exames");
            telaDeExames.addIcon("/img/medico_icon.png");
            telaDeExames.addPrimaryStage(primaryStage);
            telaDeExames.show(null, null, null);
        } else {
            telaDeExames.show(null, null, null);
        }
    }

    /**
     * Método que exibir a Tela de Cadastro de Dependentes.
     */
    @FXML
    public void cadDependentes() {
        if (telaDependentes == null) {
            this.telaDependentes = new ProcessosStage("/view/Dependentes.fxml", new DependentesController());
            telaDependentes.addTitle("Cadastro de Dependentes");
            telaDependentes.addIcon("/img/paciente64.png");
            telaDependentes.addPrimaryStage(primaryStage);
            telaDependentes.show(null, null, null);
        } else {
            telaDependentes.show(null, null, null);
        }
    }

    /**
     * Método para chamar a GUI de Mensalidades
     */
    @FXML
    private void mensalidade() {
        if (telaMensalidades == null) {
            this.telaMensalidades = new ProcessosStage("/view/Mensalidade.fxml", new MensalidadeController());
            telaMensalidades.addTitle("Pagamento de Mensalidades");
            telaMensalidades.addIcon("/img/paciente64.png");
            telaMensalidades.addPrimaryStage(primaryStage);
            telaMensalidades.show(null, null, null);
        } else {
            telaMensalidades.show(null, null, null);
        }
    }

    /**
     * Método que chama a Tela meus Dependentes.
     */
    @FXML
    private void meusDependentes() {
        if (telaMeusDependentes == null) {
            this.telaMeusDependentes = new ProcessosStage("/view/MeusDependentes.fxml", new MeusDependentesController());
            telaMeusDependentes.addTitle("Meus Dependentes");
            telaMeusDependentes.addIcon("/img/users_icon.png");
            telaMeusDependentes.addPrimaryStage(primaryStage);
            telaMeusDependentes.show(null, null, null);
        } else {
            telaMeusDependentes.show(null, null, null);
        }
    }

    /**
     * Método para chamar a GUI de gerarMensalidade
     */
    @FXML
    public void gerarMensalidade() {
        if (telaGerarMensalidades == null) {
            this.telaGerarMensalidades = new ProcessosStage("/view/GerarMensalidade.fxml", new GerarMensalidadeController());
            telaGerarMensalidades.addTitle("Gerar Mensalidade");
            telaGerarMensalidades.addIcon("/img/paciente64.png");
            telaGerarMensalidades.addPrimaryStage(primaryStage);
            telaGerarMensalidades.show(null, null, null);
        } else {
            telaGerarMensalidades.show(null, null, null);
        }
    }
}
