package kr.co.bomz.cmn.module;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * 	FXMLLoader.load() ȣ�� �� ���ҽ� �� <p>
 * 	�����ο� ���ҽ� �� ������ ���� ���
 * 
 * @author Bomz
 *
 * @param <V>		�� Ÿ��
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
	
	/**		���ҽ� getKeys() ȣ�� �� ���� ��		*/
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
