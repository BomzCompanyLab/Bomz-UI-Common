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
		pn.getChildren().add( this.makePn("전체", new BomzTextField(KeyInputType.ALL) ));
		pn.getChildren().add( this.makePn("전체 5자리", new BomzTextField(5, true, KeyInputType.ALL) ));
		
		pn.getChildren().add( this.makePn("전체+컴마", new BomzTextField(KeyInputType.ALL_AND_COMMA) ));
		pn.getChildren().add( this.makePn("전체+컴마+괄호",  new BomzTextField(KeyInputType.ALL_AND_COMMA_AND_ANGLEBRAKET) ));
		pn.getChildren().add( this.makePn("전체+컴마_영대문자", new BomzTextField(KeyInputType.ALL_AND_COMMA_AND_UPPER) ));
		pn.getChildren().add( this.makePn("오직영어", new BomzTextField(KeyInputType.ONLY_ENGLISH) ));
		pn.getChildren().add( this.makePn("오직숫자", new BomzTextField(KeyInputType.ONLY_NUMBER) ));
		pn.getChildren().add( this.makePn("숫자+영어", new BomzTextField(KeyInputType.ONLY_NUMBER_AND_ENGLISH) ));
		pn.getChildren().add( this.makePn("숫자+점", new BomzTextField(KeyInputType.ONLY_NUMBER_DOT) ));
		pn.getChildren().add( this.makePn("숫자+하이픈", new BomzTextField(KeyInputType.ONLY_NUMBER_HYPHEN) ));
		
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
