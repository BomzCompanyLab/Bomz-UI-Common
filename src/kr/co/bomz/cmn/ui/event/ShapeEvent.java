package kr.co.bomz.cmn.ui.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 * 
 * 	Shape ��ü�� �巡�� �� ũ�� ���� �̺�Ʈ ó��
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
	
	/**		ȭ�� �ϴ� ���� ó���� ���� ���		*/
	protected static final double BLANK_BOTTOM_HEIGHT = 41;
	
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
	
	private final Shape shape;
	
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
	private void initEvent(){
		
		// ���콺 Ŭ�� �� �̺�Ʈ
		this.shape.setOnMousePressed(e->{
			if( this.pressHandler != null )		this.pressHandler.handle(e);
			
			this.eventPressed(e);	
		});
		
		// ���콺 �巡�� �̺�Ʈ
		this.shape.setOnMouseDragged(e->{	this.eventDrag(e);		});
		
		// ���콺 Ŭ�� ���� �� �̺�Ʈ
		this.shape.setOnMouseReleased(e->{
			// ��ϵ� �ڵ鷯�� ���� ��� ȣ��
			if( this.releaseHandler != null )		this.releaseHandler.handle(e);
		});
	}
	
	/**		ù Ŭ�� �̺�Ʈ		*/
	private void eventPressed(MouseEvent e){
		this.pressMouseX = e.getX();
		this.pressMouseY = e.getY();
		
		this.pressShapeWidth = this.shape.getBoundsInParent().getWidth();
		this.pressShapeHeight = this.shape.getBoundsInParent().getHeight();
		
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
		else if( this.screenWidth != NON_SIZE_VALUE && tmp + this.shape.getBoundsInParent().getWidth() > this.screenWidth )
			tmp = this.parsingPoint(this.screenWidth - this.shape.getBoundsInParent().getWidth());
		
		this.shape.setLayoutX( tmp );
		
		// ȭ�� ��, �Ʒ����� ����� ���ϰ� ó��
		tmp = this.parsingPoint(this.getShapeMinY() + e.getY() - this.pressMouseY);
		if( tmp < 0 )			tmp = 0;
		else if( this.screenHeight != NON_SIZE_VALUE && tmp + this.shape.getBoundsInParent().getHeight() > (this.screenHeight - BLANK_BOTTOM_HEIGHT) )
			tmp = this.parsingPoint(this.screenHeight - this.shape.getBoundsInParent().getHeight() - BLANK_BOTTOM_HEIGHT);
		
		this.shape.setLayoutY( tmp );
		
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
	abstract protected void eventDragResizeBottom(MouseEvent e);
	
	/**		�巡�׿� ���� ���� ũ�� �ø��� ó��		*/
	abstract protected void eventDragResizeTop(MouseEvent e);
		
	/**		�巡�׿� ���� ������ ũ�� �ø��� ó��		*/
	abstract protected void eventDragResizeRight(MouseEvent e);
	
	/**		�巡�׿� ���� ���� ũ�� �ø��� ó��		*/
	abstract protected void eventDragResizeLeft(MouseEvent e);
	
	/**		���� ��ġ�� ���� ��ġ�̵����� ũ�⺯������ �˻��Ѵ�		*/
	private void checkDragType(MouseEvent e){
		double w = this.shape.getBoundsInParent().getWidth();
		double h = this.shape.getBoundsInParent().getHeight();
		
		// ���콺 Ŭ�� ��ġ �м�
		boolean left = RESIZE_ZONE >= this.pressMouseX && 0 - RESIZE_ZONE <= this.pressMouseX;
		boolean top = RESIZE_ZONE >= this.pressMouseY && 0 - RESIZE_ZONE <= this.pressMouseY;
		boolean right = w + RESIZE_ZONE >= this.pressMouseX && w - RESIZE_ZONE <= this.pressMouseX;
		boolean bottom = h + RESIZE_ZONE >= this.pressMouseY && h - RESIZE_ZONE <= this.pressMouseY;
				
		this.resizeShape = left || top || right || bottom;		// ũ�� ���� �������� ���� �Ǵ�
		
		if( !this.resizeShape )		return;		// ũ�� ������ �ƴ� ��� ���̻� ó�� ����
		
		// ũ�� ������ ��� Ÿ�� ����
		if( top && left )					this.resizePointType = RESIZE_POINT.NW_POINT;
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
		return this.shape.getBoundsInParent().getMinX();
	}
	
	/**	Shape �� ���� Y ��ǥ		*/
	protected double getShapeMinY(){
		return this.shape.getBoundsInParent().getMinY();
	}
	
	/** Shape �� ���� X ��ǥ		*/
	protected double getShapeMaxX(){
		return this.shape.getBoundsInParent().getMaxX();
	}
	
	/**	Shape �� ���� Y ��ǥ		*/
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
