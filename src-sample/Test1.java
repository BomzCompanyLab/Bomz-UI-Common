package example;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.co.bomz.cmn.ui.transfer.TransferSelect;
import kr.co.bomz.cmn.ui.transfer.TransferSelectItem;

public class Test1 extends Application {

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		TransferSelect<Integer> selectPn = new TransferSelect<Integer>();
		
		List<TransferSelectItem<Integer>> notUseList = new ArrayList<TransferSelectItem<Integer>>();
		notUseList.add(new Test1Item(1, "¾È³ç"));
		notUseList.add(new Test1Item(2, "ÇÏ¼¼"));
		notUseList.add(new Test1Item(3, "¿ä"));
		
		selectPn.setNotUseItemList(notUseList);
		
		List<TransferSelectItem<Integer>> useList = new ArrayList<TransferSelectItem<Integer>>();
		useList.add(new Test1Item(4, "±è¹ä"));
		useList.add(new Test1Item(5, "Ãµ±¹ÇÏ¼¼¿ä"));
		useList.add(new Test1Item(6, "¸ÀÀÖ³×¿ä"));
		
		selectPn.setUseItemList(useList);
		
		BorderPane pn = new BorderPane();
		pn.setCenter( selectPn );
		
		
		stage.setScene( new Scene(pn) );
		stage.show();
	}

}
