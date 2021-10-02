package kr.co.bomz.cmn;

/**
 * 	인트형 아이디와 문자형 값으로 이루어진 기본 VO
 *  
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ValueVO {

	/**		고유 아이디		*/
	private int id;
	
	/**		이름			*/
	private String name;
	
	public ValueVO(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
