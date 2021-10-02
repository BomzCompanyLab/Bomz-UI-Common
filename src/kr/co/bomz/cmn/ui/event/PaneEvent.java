package kr.co.bomz.cmn.ui.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * 
 * 	Pane ��ü�� �巡�� �� ũ�� ���� �̺�Ʈ ó��
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class PaneEvent {

	private static final int RESIZE_ZONE = 15;
	
	protected static final double MIN_W_SIZE = 140;
	protected static final double MIN_H_SIZE = 50;
	
	/**		ȭ�� �ϴ� ���� ó���� ���� ���		*/
	protected static final double BLANK_BOTTOM_HEIGHT = 80;
	
	/**		��ũ�� ���� �Ǵ� ���� ����ġ�� ������� ���� ��� ��		*/
	protected static final int NON_SIZE_VALUE = -1;
	
	/**	ũ�� ���� �� ���콺 Ŭ�� ��ġ		*/
	private enum RESIZE_POINT {
			N_POINT,		// ���
			S_POINT,		// �ϴ�
			W_POINT,	// ����
			E_POINT,		// ������
			
			NW_POINT,	// ��ܿ���
			NE_POINT,	// ��ܿ�����
			SW_POINT,	// �ϴܿ���
			SE_POINT		// �ϴܿ�����
		};
		
	private RESIZE_POINT resizePointType;
	
	/**		ȭ�� �ִ� ����		*/
	protected double screenWidth;
	/**		ȭ�� �ִ� ����		*/
	protected double screenHeight;
	
	
	/**		�巡�� ���� �� ���콺 X ��ǥ		*/
	protected double pressMouseX;
	/**		�巡�� ���� �� ���콺 Y ��ǥ		*/
	protected double pressMouseY;
	
	/**		�巡�� ���� �� ����		*/
	protected double pressShapeWidth;
	/**		�巡�� ���� �� ����		*/
	protected double pressShapeHeight;
	
	/**
	 * 		true �� ��� shape ũ�⺯��
	 * 		false �� ��� shape ��ġ�̵�
	 */
	private boolean resizeShape;
	
	/**		��ü �巡�� ��� ����		*/
	private boolean drag = true;
	/**		��ü ũ�� ���� ��� ����		*/
	private boolean resize = true;
	
	/**		��ü Ŭ�� �� ȣ��Ǵ� �ڵ鷯		*/
	private EventHandler<MouseEvent> pressHandler;
	
	/**		��ü �̵� �� ũ�� ���� �� ȣ��Ǵ� �ڵ鷯		*/
	private EventHandler<MouseEvent> releaseHandler;
		
	/**		ȭ�鿡 ���� �� �� ǥ�ø� ���� ��ü		*/
	protected final StackPane rootPane;
	
	public PaneEvent(double screenWidth, double screenHeight, boolean mouseEvent) throws NullPointerException, ScreenSizeException{
		
		this.setScreenWidth(screenWidth);
		this.setScreenHeight(screenHeight);
		
		this.rootPane = new StackPane();
		this.rootPane.setPrefWidth(screenWidth);
		this.rootPane.setPrefHeight(screenHeight);
		this.rootPane.getStylesheets().add(PaneEvent.class.getResource("PaneEvent.css").toExternalForm());
		
		
		this.initSubPane();
		
		// ���콺�� �̿��� Ŭ��, �巡��, ũ�� ���� �̺�Ʈ ��� ����
		this.initEvent(mouseEvent);
	}
		
	/**		ȭ��� ǥ�õǴ� ���� ����		*/
	abstract protected void initSubPane(); 
	
	
	public Pane getRootPane(){
		return this.rootPane;
	}
	
	/**		ȭ�� �ִ� ���� ����		*/
	public void setScreenWidth(double screenWidth) throws ScreenSizeException{
		if( screenWidth != -1 && screenWidth < 10 )		throw new ScreenSizeException(screenWidth);
		
		this.screenWidth = screenWidth;
	}
	
	/**		ȭ�� �ִ� ���� ����		*/
	public void setScreenHeight(double screenHeight) throws ScreenSizeException{
		if( screenHeight != -1 && screenHeight < 10 )		throw new ScreenSizeException(screenHeight);
		
		this.screenHeight = screenHeight;
	}
	
	/**	���콺 �̺�Ʈ ���		*/
	private void initEvent(boolean mouseEvent){
				
		// ���콺 Ŭ�� �� �̺�Ʈ
		this.rootPane.setOnMousePressed(e->{
			if( this.pressHandler != null )		this.pressHandler.handle(e);
			
			if( mouseEvent )		this.eventPressed(e);	
		});
		
		// ���콺 �巡�� �̺�Ʈ
		if( mouseEvent )		
			this.rootPane.setOnMouseDragged(e->{	this.eventDrag(e);		});
		
		// ���콺 Ŭ�� ���� �� �̺�Ʈ
		this.rootPane.setOnMouseReleased(e->{
			// ��ϵ� �ڵ鷯�� ���� ��� ȣ��
			if( this.releaseHandler != null )		this.releaseHandler.handle(e);
		});
	}
	
	/**		ù Ŭ�� �̺�Ʈ		*/
	private void eventPressed(MouseEvent e){
		this.pressMouseX = e.getX();
		this.pressMouseY = e.getY();
		
		this.pressShapeWidth = this.rootPane.getBoundsInParent().getWidth();
		this.pressShapeHeight = this.rootPane.getBoundsInParent().getHeight();
		
		this.checkDragType(e);
	}
	
	/**		�巡�� �̺�Ʈ		*/
	private void eventDrag(MouseEvent e){
		if( this.resizeShape ){
			// ũ�� ���� �巡��
			this.eventDragResize(e);

		}else{
			// ��ġ �̵� �巡��
			this.eventDragMove(e);
		}
		
	}

	/**		��ġ �̵� �巡�� ó��		*/
	private void eventDragMove(MouseEvent e){
		
		if( !this.drag )		return;
		
		// ȭ�� ����, �������� ����� ���ϰ� ó��
		double tmp = this.parsingPoint(this.getShapeMinX() + e.getX() - this.pressMouseX);
		if( tmp < 0 )			tmp = 0;
		else if( this.screenWidth != NON_SIZE_VALUE && tmp + this.rootPane.getBoundsInParent().getWidth() > this.screenWidth )
			tmp = this.parsingPoint(this.screenWidth - this.rootPane.getBoundsInParent().getWidth());
		
		this.rootPane.setLayoutX( tmp );
		
		// ȭ�� ��, �Ʒ����� ����� ���ϰ� ó��
		tmp = this.parsingPoint(this.getShapeMinY() + e.getY() - this.pressMouseY);
		if( tmp < 0 )			tmp = 0;
		else if( this.screenHeight != NON_SIZE_VALUE && tmp + this.rootPane.getBoundsInParent().getHeight() > (this.screenHeight - BLANK_BOTTOM_HEIGHT) )
			tmp = this.parsingPoint(this.screenHeight - this.rootPane.getBoundsInParent().getHeight() - BLANK_BOTTOM_HEIGHT);
		
		this.rootPane.setLayoutY( tmp );
	}
	
	/**		ũ�� ���� �巡�� ó��			*/
	private void eventDragResize(MouseEvent e){
		
		if( !this.resize )		return;
		
		switch( this.resizePointType ){
		case W_POINT :		// ����
			this.eventDragResizeLeft(e);			break;
			
		case E_POINT :			// ������
			this.eventDragResizeRight(e);		break;
			
		case N_POINT :		// ���
			this.eventDragResizeTop(e);		break;
			
		case S_POINT :			// �ϴ�
			this.eventDragResizeBottom(e);	break;
			
		case NW_POINT :		// ���� ���
			this.eventDragResizeLeft(e);			
			this.eventDragResizeTop(e);
			break;
			
		case SW_POINT : 	// ���� �ϴ�
			this.eventDragResizeLeft(e);
			this.eventDragResizeBottom(e);
			break;
			
		case NE_POINT :		// ������ ���
			this.eventDragResizeRight(e);		
			this.eventDragResizeTop(e);
			break;
			
		case SE_POINT : 		// ������ �ϴ�
			this.eventDragResizeRight(e);
			this.eventDragResizeBottom(e);
			break;
		}
		
		

	}
	
	
	/**		�巡�׿� ���� �Ʒ��� ũ�� �ø��� ó��		*/
	private void eventDragResizeBottom(MouseEvent e){
		double tmp = this.pressShapeHeight + (e.getY() - this.pressMouseY);
		
		if( tmp < MIN_H_SIZE )	{
			tmp = MIN_H_SIZE;
		}else{
			if( this.screenHeight != PaneEvent.NON_SIZE_VALUE && tmp + this.getShapeMinY() > this.screenHeight )
				tmp = this.parsingPoint(this.screenHeight - this.getShapeMinY());
			else
				tmp = this.parsingPoint(tmp);
		}
		
		this.rootPane.setPrefHeight( tmp );
	}
	
	/**		�巡�׿� ���� ���� ũ�� �ø��� ó��		*/
	private void eventDragResizeTop(MouseEvent e){
		double beforeMinY = this.getShapeMinY();
		double beforeMaxY = this.getShapeMaxY();
		
		double tmp = beforeMinY + e.getY() - this.pressMouseY;
		if( tmp < 0 )		tmp = 0;
		this.rootPane.setLayoutY( this.parsingPoint(beforeMaxY - tmp <= MIN_H_SIZE ? beforeMaxY - MIN_H_SIZE : tmp) );
		
		this.rootPane.setPrefHeight( this.parsingPoint(this.rootPane.getPrefHeight() + (beforeMinY - this.getShapeMinY())) );
	}
		
	/**		�巡�׿� ���� ������ ũ�� �ø��� ó��		*/
	private void eventDragResizeRight(MouseEvent e){
		double tmp = this.pressShapeWidth + (e.getX() - this.pressMouseX);
		if( tmp < MIN_W_SIZE )	{
			tmp = MIN_W_SIZE;
		}else{
			if( this.screenWidth != PaneEvent.NON_SIZE_VALUE && tmp + this.getShapeMinX() > this.screenWidth )
				tmp = this.parsingPoint(this.screenWidth - this.getShapeMinX());
			else
				tmp = this.parsingPoint(tmp);
		}
		
		this.rootPane.setPrefWidth( tmp );
	}
	
	/**		�巡�׿� ���� ���� ũ�� �ø��� ó��		*/
	private void eventDragResizeLeft(MouseEvent e){
		double beforeMinX = this.getShapeMinX();
		double beforeMaxX = this.getShapeMaxX();
		
		double tmp = beforeMinX + e.getX() - this.pressMouseX;
		if( tmp < 0 )		tmp = 0;
		this.rootPane.setLayoutX( this.parsingPoint(beforeMaxX - tmp <= MIN_W_SIZE ? beforeMaxX - MIN_W_SIZE : tmp) );
		
		this.rootPane.setPrefWidth( this.parsingPoint(this.rootPane.getPrefWidth() + (beforeMinX - this.getShapeMinX())) );
	}
	
	/**		���� ��ġ�� ���� ��ġ�̵����� ũ�⺯������ �˻��Ѵ�		*/
	private void checkDragType(MouseEvent e){
		double w = this.rootPane.getBoundsInParent().getWidth();
		double h = this.rootPane.getBoundsInParent().getHeight();
		
		// ���콺 Ŭ�� ��ġ �м�
		boolean left = RESIZE_ZONE >= this.pressMouseX && 0 - RESIZE_ZONE <= this.pressMouseX;
		boolean top = RESIZE_ZONE >= this.pressMouseY && 0 - RESIZE_ZONE <= this.pressMouseY;
		boolean right = w + RESIZE_ZONE >= this.pressMouseX && w - RESIZE_ZONE <= this.pressMouseX;
		boolean bottom = h + RESIZE_ZONE >= this.pressMouseY && h - RESIZE_ZONE <= this.pressMouseY;
				
		this.resizeShape = left || top || right || bottom;		// ũ�� ���� �������� ���� �Ǵ�
		
		if( !this.resizeShape )		return;		// ũ�� ������ �ƴ� ��� ���̻� ó�� ����
		
		// ũ�� ������ ��� Ÿ�� ����
		if( top && left )				this.resizePointType = RESIZE_POINT.NW_POINT;
		else if( top && right )		this.resizePointType = RESIZE_POINT.NE_POINT;
		else if( bottom && left )	this.resizePointType = RESIZE_POINT.SW_POINT;
		else if( bottom && right )	this.resizePointType = RESIZE_POINT.SE_POINT;
		else if( top )						this.resizePointType = RESIZE_POINT.N_POINT;
		else if( bottom )				this.resizePointType = RESIZE_POINT.S_POINT;
		else if( left )						this.resizePointType = RESIZE_POINT.W_POINT;
		else									this.resizePointType = RESIZE_POINT.E_POINT;
	}
	
	/** Shape �� ��ġ�� ���� ������ �����Ͽ� �̵���ų �� �ֵ��� �Ѵ�		*/
	protected double parsingPoint(double recPoint){
		return ( ((int)recPoint) / 10) * 10;
	}
	
	/** Shape �� ���� X ��ǥ		*/
	protected double getShapeMinX(){
		return this.rootPane.getBoundsInParent().getMinX();
	}
	
	/**	Shape �� ���� Y ��ǥ		*/
	protected double getShapeMinY(){
		return this.rootPane.getBoundsInParent().getMinY();
	}
	
	/** Shape �� ���� X ��ǥ		*/
	protected double getShapeMaxX(){
		return this.rootPane.getBoundsInParent().getMaxX();
	}
	
	/**	Shape �� ���� Y ��ǥ		*/
	protected double getShapeMaxY(){
		return this.rootPane.getBoundsInParent().getMaxY();
	}
	
//	public Rectangle getRectangle(){
//		return this.rootPane;
//	}

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
	
	public void setLayoutX(double x){
		this.rootPane.setLayoutX(x);
	}
	
	public void setLayoutY(double y){
		this.rootPane.setLayoutY(y);
	}
	
	public void setUserData(Object userData){
		this.rootPane.setUserData(userData);
	}
}
