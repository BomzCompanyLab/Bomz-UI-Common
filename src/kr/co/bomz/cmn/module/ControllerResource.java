package kr.co.bomz.cmn.module;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.stage.Stage;

/**
 * 
 * 	FXMLLoader.load() 호출 시 리소스 값
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 * @param <V>		값 타입
 */
public class ControllerResource extends ResourceBundle{
	
	private final Map<String, Object> resourceMap = new HashMap<String, Object>();
	
	public ControllerResource(ControllerStage controllerStage){
		this.resourceMap.put(ControllerStage.RESOURCE_ID, controllerStage);
	}
	
	public ControllerResource(ControllerStage controllerStage, String key, Object value){
		this(controllerStage);
		this.resourceMap.put(key, value);
	}

	public void addResource(String key, Object value){
		this.resourceMap.put(key, value);
	}
	
	public void setStage(Stage stage){
		Object obj = this.resourceMap.get(ControllerStage.RESOURCE_ID);
		if( obj == null )		return;
		
		if( obj instanceof ControllerStage )		((ControllerStage)obj).setStage(stage);
	}
	
	@Override
	public Enumeration<String> getKeys() {
		return new ControllerResourceEnumeration(
				this.resourceMap.keySet().toArray(new String[this.resourceMap.size()])
			);
	}

	@Override
	protected Object handleGetObject(String key) {
		return this.resourceMap.get(key);
	}
	
	/**		리소스 getKeys() 호출 시 리턴 용		*/
	class ControllerResourceEnumeration implements Enumeration<String>{

		private final String[] keys;
		private int index = 0;
		private final int size;
		
		private ControllerResourceEnumeration(String[] keys){
			this.keys = keys;
			this.size = keys.length;
		}
		
		@Override
		public boolean hasMoreElements() {
			return this.index < this.size;
		}

		@Override
		public String nextElement() {
			return this.keys[this.index++];
		}
		
	}
}
