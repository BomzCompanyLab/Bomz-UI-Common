package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.co.bomz.cmn.ui.popup.OkPopup;

public class Test3 extends Application {

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		BorderPane pn = new BorderPane();

		stage.setScene( new Scene(pn) );
		stage.show();
		
		OkPopup ok = new OkPopup(stage, "알람이야");
//		ok.setOkButtonStyle("-fx-pref-width:130;-fx-pref-height:50;-fx-font-size:10;");
		ok.show();
	}

}
