package kr.co.bomz.cmn;

/**
 * 	��Ʈ�� ���̵�� ������ ������ �̷���� �⺻ VO
 *  
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ValueVO {

	/**		���� ���̵�		*/
	private int id;
	
	/**		�̸�			*/
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
