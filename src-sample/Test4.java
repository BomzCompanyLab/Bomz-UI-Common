package example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kr.co.bomz.cmn.ui.field.BomzTextField;
import kr.co.bomz.cmn.ui.field.KeyInputType;

public class Test4 extends Application {

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
	
		VBox pn = new VBox();
		pn.getChildren().add( this.makePn("��ü", new BomzTextField(KeyInputType.ALL) ));
		pn.getChildren().add( this.makePn("��ü 5�ڸ�", new BomzTextField(5, true, KeyInputType.ALL) ));
		
		pn.getChildren().add( this.makePn("��ü+�ĸ�", new BomzTextField(KeyInputType.ALL_AND_COMMA) ));
		pn.getChildren().add( this.makePn("��ü+�ĸ�+��ȣ",  new BomzTextField(KeyInputType.ALL_AND_COMMA_AND_ANGLEBRAKET) ));
		pn.getChildren().add( this.makePn("��ü+�ĸ�_���빮��", new BomzTextField(KeyInputType.ALL_AND_COMMA_AND_UPPER) ));
		pn.getChildren().add( this.makePn("��������", new BomzTextField(KeyInputType.ONLY_ENGLISH) ));
		pn.getChildren().add( this.makePn("��������", new BomzTextField(KeyInputType.ONLY_NUMBER) ));
		pn.getChildren().add( this.makePn("����+����", new BomzTextField(KeyInputType.ONLY_NUMBER_AND_ENGLISH) ));
		pn.getChildren().add( this.makePn("����+��", new BomzTextField(KeyInputType.ONLY_NUMBER_DOT) ));
		pn.getChildren().add( this.makePn("����+������", new BomzTextField(KeyInputType.ONLY_NUMBER_HYPHEN) ));
		
		stage.setScene( new Scene(pn) );
		stage.show();
		
	}

	private FlowPane makePn(String name, BomzTextField field){
		FlowPane pn = new FlowPane();
		pn.setAlignment(Pos.BASELINE_LEFT);
		pn.getChildren().add( new Label(name) );
		pn.getChildren().add(field);
		
		return pn;
	}
}
