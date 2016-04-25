package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.bean.ValorExameModel;
import model.dao.ValorExameDAO;
import util.DialogFX;
import util.Funcionalidades;
import util.Log;
import util.ProcessosStage;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class TabelaExameController extends Funcionalidades implements Initializable {

	@FXML
	private TableView<ValorExameModel> tabela_valor_exame;
	@FXML
	private TableColumn<ValorExameModel, Integer> codigoColuna;
	@FXML
	private TableColumn<ValorExameModel, String> exameColuna;
	@FXML
	private TableColumn<ValorExameModel, String> categoriaColuna;
	@FXML
	private TableColumn<ValorExameModel, Double> valor_exameColuna;
	@FXML
	private TableColumn<ValorExameModel, Double> valor_categoriaColuna;
	@FXML
	private Button bt_excluir;
	private ProcessosStage telaPrecoExame;
	/* Instancia da Class Model */
	ValorExameModel valorExameModel;
	/* Variavel para ser enviada com o valor da ação da GUI preçoExame */
	int flag = 1;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		/**
		 * Popular TableView
		 */
		this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
		this.exameColuna.setCellValueFactory(cellData -> cellData.getValue().getExameModel().getDescricaoProperty());
		this.categoriaColuna
				.setCellValueFactory(cellData -> cellData.getValue().getCategoriaModel().getDescricaoProperty());
		this.valor_exameColuna.setCellValueFactory(cellData -> cellData.getValue().getValor_exameProperty().asObject());
		this.valor_categoriaColuna
				.setCellValueFactory(cellData -> cellData.getValue().getValor_categoriaProperty().asObject());

		/*
		 * Botao editar so fica ativado quando tem alguma coisa selecionada na
		 * TableView
		 */
		bt_excluir.disableProperty().bind(tabela_valor_exame.getSelectionModel().selectedItemProperty().isNull());
	}

	/**
	 * Metodo para carregar o TableView da GUI com Thread
	 */
	@Override
	public void carregarTabela() {
		Task task = new Task() {
			@Override
			protected Object call() throws Exception {
				return ValorExameDAO.executeQuery(null, ValorExameDAO.QUERY_TODOS);
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				tabela_valor_exame.setItems((ObservableList<ValorExameModel>) getValue());
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Sobrecargar do método preçoExame para ser utilizado quando for editar
	 *
	 * @param editar
	 */
	private void precoExame(boolean editar) {
		if (this.telaPrecoExame == null) {
			this.telaPrecoExame = new ProcessosStage("/view/PrecoExame.fxml", new PrecoExameController());
			telaPrecoExame.addIcon("/img/medico_icon.png");
			telaPrecoExame.setModality(true);
			telaPrecoExame.setEditar(editar);
			telaPrecoExame.addTitle("Gerenciamento de Exames");
			telaPrecoExame.show(tabela_valor_exame, null, null);
		} else {
			telaPrecoExame.show(tabela_valor_exame, null, null);
		}

	}

	/**
	 * Método precoExame com a anotação para quando for chamado adicionar
	 */
	@FXML
	private void precoExame() {
		/* se for chamado por aqui significa q não é para editar */
		precoExame(false);
		flag = 1;
	}

	/**
	 * Método com evento do mouse na tabela para ativar o editar
	 *
	 * @param evento
	 */
	@FXML
	private void onMouseClick(MouseEvent evento) {
		if (tabela_valor_exame.getSelectionModel().getSelectedIndex() != -1) {
			if (evento.getClickCount() == 2) {
				/* Informamos que queremos editar */
				this.precoExame(true);
				flag = 2;
			}
		} else {
			/*
			 * Para evitar no caso da Tabela vazia ao clicar nela aperecer a msg
			 * para selecionar um exame.
			 */
			int totalItens = tabela_valor_exame.getItems().size();
			if (totalItens > 0) {
				DialogFX.showMessage("Por favor selecione um Exame!", "Atenção", DialogFX.ATENCAO);
			}
		}
	}

	/**
	 * Botão que faz a ação da exclusão na TableView
	 */
	@FXML
	private void onDelete() {
		if (DialogFX.showConfirmation("Deseja excluir um exame?")) {
			this.valorExameModel = tabela_valor_exame.getItems()
					.get(tabela_valor_exame.getSelectionModel().getSelectedIndex());
			if (ValorExameDAO.executeUpdates(valorExameModel, ValorExameDAO.DELETE)) {
				tabela_valor_exame.getItems().remove(tabela_valor_exame.getSelectionModel().getSelectedIndex());
				DialogFX.showMessage("Excluido com sucesso", "Sucesso", DialogFX.SUCESS);
				tabela_valor_exame.getSelectionModel().clearSelection();
			} else {
				DialogFX.showMessage("Não foi possivel excluir dados", "ERRO", DialogFX.ERRO);
			}
		}
	}

	/**
	 * Botão para limpar a TableView e atualizar a tabela
	 */
	@FXML
	private void onAtualizar() {
		tabela_valor_exame.getSelectionModel().clearSelection();
		carregarTabela();
	}
}
