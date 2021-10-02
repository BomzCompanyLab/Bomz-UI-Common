package kr.co.bomz.cmn.module;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * 	FXMLLoader.load() 호출 시 리소스 값 <p>
 * 	자유로운 리소스 값 전달을 위해 사용
 * 
 * @author Bomz
 *
 * @param <V>		값 타입
 */
public class BomzResource extends ResourceBundle{
	
	private final Map<String, Object> resourceMap = new HashMap<String, Object>();
	
	public BomzResource(){}
	
	public BomzResource(String key, Object value){
		this.resourceMap.put(key, value);
	}

	public void addResource(String key, Object value){
		this.resourceMap.put(key, value);
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
