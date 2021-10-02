package kr.co.bomz.cmn.module;

/**
 * 	모듈 동작에 필요한 환경 설정 정보
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ModuleProperties {

	/**		키보드 잠금 여부		*/
	private static volatile boolean keyboardLock = true;
	
	public static boolean isKeyboardLock(){
		return keyboardLock;
	}
	
	public static void setKeyboardLock(boolean lock){
		keyboardLock = lock;
	}
}
