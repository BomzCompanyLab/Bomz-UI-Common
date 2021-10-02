package kr.co.bomz.cmn.ui.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * 
 * 	Shape 객체의 드래그 및 크기 변경 이벤트 처리
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class ShapeEvent {

	private static final int RESIZE_ZONE = 15;
	
	protected static final double MIN_H_SIZE = 50;
	protected static final double MIN_W_SIZE = 140;
	
	/**		화면 하단 높이 처리를 위해 사용		*/
	protected static final double BLANK_BOTTOM_HEIGHT = 41;
	
	/**		스크린 넓이 또는 높이 설정치를 사용하지 않을 경우 값		*/
	protected static final int NON_SIZE_VALUE = -1;
	
	/**	크기 변경 시 마우스 클릭 위치		*/
	private enum RESIZE_POINT {
			N_POINT,		// 상단
			S_POINT,		// 하단
			W_POINT,	// 왼쪽
			E_POINT,		// 오른쪽
			
			NW_POINT,	// 상단왼쪽
			NE_POINT,	// 상단오른쪽
			SW_POINT,	// 하단왼쪽
			SE_POINT		// 하단오른쪽
		};
		
	private RESIZE_POINT resizePointType;
	
	private final Shape shape;
	
	/**		화면 최대 넓이		*/
	protected double screenWidth;
	/**		화면 최대 높이		*/
	protected double screenHeight;
	
	
	/**		드래그 시작 시 마우스 X 좌표		*/
	protected double pressMouseX;
	/**		드래그 시작 시 마우스 Y 좌표		*/
	protected double pressMouseY;
	
	/**		드래그 시작 시 넓이		*/
	protected double pressShapeWidth;
	/**		드래그 시작 시 높이		*/
	protected double pressShapeHeight;
	
	/**
	 * 		true 일 경우 shape 크기변경
	 * 		false 일 경우 shape 위치이동
	 */
	private boolean resizeShape;
	
	/**		객체 드래그 허용 여부		*/
	private boolean drag = true;
	/**		객체 크기 변경 허용 여부		*/
	private boolean resize = true;
	
	/**		객체 클릭 시 호출되는 핸들러		*/
	private EventHandler<MouseEvent> pressHandler;
	
	/**		객체 이동 및 크기 변경 시 호출되는 핸들러		*/
	private EventHandler<MouseEvent> releaseHandler;
		
	protected ShapeEvent(Shape shape) throws NullPointerException, ScreenSizeException{
		this(shape, -1, -1);
	}
	
	protected ShapeEvent(Shape shape, double screenWidth, double screenHeight) throws NullPointerException, ScreenSizeException{
		this.shape = shape;
		
		this.setScreenWidth(screenWidth);
		this.setScreenHeight(screenHeight);
		
		if( this.shape == null )		throw new NullPointerException("shape parameter is null");
		
		this.initEvent();
	}
	
	/**		화면 최대 넓이 설정		*/
	public void setScreenWidth(double screenWidth) throws ScreenSizeException{
		if( screenWidth != -1 && screenWidth < 10 )		throw new ScreenSizeException(screenWidth);
		
		this.screenWidth = screenWidth;
	}
	
	/**		화면 최대 높이 설정		*/
	public void setScreenHeight(double screenHeight) throws ScreenSizeException{
		if( screenHeight != -1 && screenHeight < 10 )		throw new ScreenSizeException(screenHeight);
		
		this.screenHeight = screenHeight;
	}
	
	/**	마우스 이벤트 등록		*/
	private void initEvent(){
		
		// 마우스 클릭 시 이벤트
		this.shape.setOnMousePressed(e->{
			if( this.pressHandler != null )		this.pressHandler.handle(e);
			
			this.eventPressed(e);	
		});
		
		// 마우스 드래그 이벤트
		this.shape.setOnMouseDragged(e->{	this.eventDrag(e);		});
		
		// 마우스 클릭 해제 시 이벤트
		this.shape.setOnMouseReleased(e->{
			// 등록된 핸들러가 있을 경우 호출
			if( this.releaseHandler != null )		this.releaseHandler.handle(e);
		});
	}
	
	/**		첫 클릭 이벤트		*/
	private void eventPressed(MouseEvent e){
		this.pressMouseX = e.getX();
		this.pressMouseY = e.getY();
		
		this.pressShapeWidth = this.shape.getBoundsInParent().getWidth();
		this.pressShapeHeight = this.shape.getBoundsInParent().getHeight();
		
		this.checkDragType(e);
	}
	
	/**		드래그 이벤트		*/
	private void eventDrag(MouseEvent e){
		
		if( this.resizeShape ){
			// 크기 변경 드래그
			this.eventDragResize(e);

		}else{
			// 위치 이동 드래그
			this.eventDragMove(e);
		}
		
	}

	/**		위치 이동 드래그 처리		*/
	private void eventDragMove(MouseEvent e){
		
		if( !this.drag )		return;
		
		// 화면 왼쪽, 오른쪽을 벗어나지 못하게 처리
		double tmp = this.parsingPoint(this.getShapeMinX() + e.getX() - this.pressMouseX);
		if( tmp < 0 )			tmp = 0;
		else if( this.screenWidth != NON_SIZE_VALUE && tmp + this.shape.getBoundsInParent().getWidth() > this.screenWidth )
			tmp = this.parsingPoint(this.screenWidth - this.shape.getBoundsInParent().getWidth());
		
		this.shape.setLayoutX( tmp );
		
		// 화면 위, 아래쪽을 벗어나지 못하게 처리
		tmp = this.parsingPoint(this.getShapeMinY() + e.getY() - this.pressMouseY);
		if( tmp < 0 )			tmp = 0;
		else if( this.screenHeight != NON_SIZE_VALUE && tmp + this.shape.getBoundsInParent().getHeight() > (this.screenHeight - BLANK_BOTTOM_HEIGHT) )
			tmp = this.parsingPoint(this.screenHeight - this.shape.getBoundsInParent().getHeight() - BLANK_BOTTOM_HEIGHT);
		
		this.shape.setLayoutY( tmp );
		
	}
	
	/**		크기 변경 드래그 처리			*/
	private void eventDragResize(MouseEvent e){
		
		if( !this.resize )		return;
		
		switch( this.resizePointType ){
		case W_POINT :		// 왼쪽
			this.eventDragResizeLeft(e);			break;
			
		case E_POINT :			// 오른쪽
			this.eventDragResizeRight(e);		break;
			
		case N_POINT :		// 상단
			this.eventDragResizeTop(e);		break;
			
		case S_POINT :			// 하단
			this.eventDragResizeBottom(e);	break;
			
		case NW_POINT :		// 왼쪽 상단
			this.eventDragResizeLeft(e);			
			this.eventDragResizeTop(e);
			break;
			
		case SW_POINT : 	// 왼쪽 하단
			this.eventDragResizeLeft(e);
			this.eventDragResizeBottom(e);
			break;
			
		case NE_POINT :		// 오른쪽 상단
			this.eventDragResizeRight(e);		
			this.eventDragResizeTop(e);
			break;
			
		case SE_POINT : 		// 오른쪽 하단
			this.eventDragResizeRight(e);
			this.eventDragResizeBottom(e);
			break;
		}
		
		

	}
	
	
	/**		드래그에 따른 아래쪽 크기 늘리기 처리		*/
	abstract protected void eventDragResizeBottom(MouseEvent e);
	
	/**		드래그에 따른 위쪽 크기 늘리기 처리		*/
	abstract protected void eventDragResizeTop(MouseEvent e);
		
	/**		드래그에 따른 오른쪽 크기 늘리기 처리		*/
	abstract protected void eventDragResizeRight(MouseEvent e);
	
	/**		드래그에 따른 왼쪽 크기 늘리기 처리		*/
	abstract protected void eventDragResizeLeft(MouseEvent e);
	
	/**		시작 위치에 따라 위치이동인지 크기변경인지 검사한다		*/
	private void checkDragType(MouseEvent e){
		double w = this.shape.getBoundsInParent().getWidth();
		double h = this.shape.getBoundsInParent().getHeight();
		
		// 마우스 클릭 위치 분석
		boolean left = RESIZE_ZONE >= this.pressMouseX && 0 - RESIZE_ZONE <= this.pressMouseX;
		boolean top = RESIZE_ZONE >= this.pressMouseY && 0 - RESIZE_ZONE <= this.pressMouseY;
		boolean right = w + RESIZE_ZONE >= this.pressMouseX && w - RESIZE_ZONE <= this.pressMouseX;
		boolean bottom = h + RESIZE_ZONE >= this.pressMouseY && h - RESIZE_ZONE <= this.pressMouseY;
				
		this.resizeShape = left || top || right || bottom;		// 크기 변경 시작인지 여부 판단
		
		if( !this.resizeShape )		return;		// 크기 변경이 아닐 경우 더이상 처리 없음
		
		// 크기 변경일 경우 타입 설정
		if( top && left )					this.resizePointType = RESIZE_POINT.NW_POINT;
		else if( top && right )		this.resizePointType = RESIZE_POINT.NE_POINT;
		else if( bottom && left )	this.resizePointType = RESIZE_POINT.SW_POINT;
		else if( bottom && right )	this.resizePointType = RESIZE_POINT.SE_POINT;
		else if( top )						this.resizePointType = RESIZE_POINT.N_POINT;
		else if( bottom )				this.resizePointType = RESIZE_POINT.S_POINT;
		else if( left )						this.resizePointType = RESIZE_POINT.W_POINT;
		else									this.resizePointType = RESIZE_POINT.E_POINT;
	}
	
	/** Shape 의 위치를 일정 단위로 구분하여 이동시킬 수 있도록 한다		*/
	protected double parsingPoint(double recPoint){
		return ( ((int)recPoint) / 10) * 10;
	}
	
	/** Shape 의 시작 X 좌표		*/
	protected double getShapeMinX(){
		return this.shape.getBoundsInParent().getMinX();
	}
	
	/**	Shape 의 시작 Y 좌표		*/
	protected double getShapeMinY(){
		return this.shape.getBoundsInParent().getMinY();
	}
	
	/** Shape 의 종료 X 좌표		*/
	protected double getShapeMaxX(){
		return this.shape.getBoundsInParent().getMaxX();
	}
	
	/**	Shape 의 종료 Y 좌표		*/
	protected double getShapeMaxY(){
		return this.shape.getBoundsInParent().getMaxY();
	}
	
	public Shape getShape(){
		return this.shape;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public EventHandler<MouseEvent> getPressHandler() {
		return pressHandler;
	}

	public void setPressHandler(EventHandler<MouseEvent> pressHandler) {
		this.pressHandler = pressHandler;
	}

	public EventHandler<MouseEvent> getReleaseHandler() {
		return releaseHandler;
	}

	public void setReleaseHandler(EventHandler<MouseEvent> releaseHandler) {
		this.releaseHandler = releaseHandler;
	}
	
}
