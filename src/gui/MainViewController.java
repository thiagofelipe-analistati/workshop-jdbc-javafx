package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.service.DepartamentoService;
import model.service.VendedorService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItenVendedor;
	@FXML
	private MenuItem menuItenDepartamento;
	@FXML
	private MenuItem menuItenAbout;
	
	@FXML
	public void onMenuItemVendedor() {
		LoadView("/gui/VendedorList.fxml", (VendedorListController controller) -> {
			controller.setVendedorService(new VendedorService());
			controller.updateTableView();
		});
	}
	@FXML
	public void onMenuItemDepartamento() {
		LoadView("/gui/DepartamentoList.fxml", (DepartamentoListController controller) -> {
			controller.setDepartamentoService(new DepartamentoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAbout() {
		LoadView("/gui/about.fxml", x->{});
	}
	
	
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	//synchronized garante que n�o seja interrompido nada.
	// incluss�o do acao de inicia�liza��o
	private synchronized <T> void LoadView(String absoltename, Consumer<T> acaoInicializacao ) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoltename));
		VBox newVBox = loader.load();
		
		// referencia para a cena principal
		Scene mainScene = Main.getMainScene();
		//referencia para Vbox principal 
		VBox mainVBOx =(VBox) ((ScrollPane) mainScene.getRoot()).getContent();
		
		Node mainMenu = mainVBOx.getChildren().get(0);
		mainVBOx.getChildren().clear();
		mainVBOx.getChildren().add(mainMenu);
		mainVBOx.getChildren().addAll(newVBox.getChildren());
		// fun��o de inicializa��o.
		T controller = loader.getController();
		acaoInicializacao.accept(controller);
		
		} catch (IOException e) {
			
			Alerts.showAlert("IOException", "Erro na leitura da tela ", e.getMessage(), AlertType.ERROR);
			
		}
	}


}