package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.co.bomz.cmn.ui.button.BomzToggleButton;

public class Test2 extends Application {

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		BomzToggleButton bt = new BomzToggleButton();
		bt.setOn();
		bt.setButtonWidth(88);
		bt.setButtonHeight(60);
		
		BorderPane pn = new BorderPane();
		pn.setCenter( bt );
		
		
		stage.setScene( new Scene(pn) );
		stage.show();
	}

}
