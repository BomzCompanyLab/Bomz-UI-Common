package kr.co.bomz.cmn.ui.event;

public class ScreenSizeException extends RuntimeException{

	private static final long serialVersionUID = -766161379149566633L;

	public ScreenSizeException(double value){
		super("screen size " + value + " error. [-1 or 10~");
	}
}
