package kr.co.bomz.cmn.module;

/**
 * 	��� ���ۿ� �ʿ��� ȯ�� ���� ����
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ModuleProperties {

	/**		Ű���� ��� ����		*/
	private static volatile boolean keyboardLock = true;
	
	public static boolean isKeyboardLock(){
		return keyboardLock;
	}
	
	public static void setKeyboardLock(boolean lock){
		keyboardLock = lock;
	}
}
