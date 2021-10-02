package kr.co.bomz.cmn.ui.event;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class CircleEvent extends ShapeEvent{

	private final Circle circle;
	
	public CircleEvent(Circle circle) throws NullPointerException, ScreenSizeException{
		super(circle);
		this.circle = circle;
	}
	
	public CircleEvent(Circle circle, double screenWidth, double screenHeight) throws NullPointerException, ScreenSizeException{
		super(circle, screenWidth, screenHeight);
		this.circle = circle;
	}
	
	@Override
	protected void eventDragResizeLeft(MouseEvent e) {
		double beforeMinX = super.getShapeMinX();
		double beforeMaxX = super.getShapeMaxX();
		
		double tmp = beforeMinX + e.getX() - super.pressMouseX;
		if( tmp < 0 )		tmp = 0;
		else					tmp = beforeMaxX - tmp <= MIN_W_SIZE ? beforeMaxX - MIN_W_SIZE : tmp;

		// 하단 범위를 벗어났는지 검사
		if( (super.getShapeMinY() + beforeMaxX - tmp + BLANK_BOTTOM_HEIGHT) > (super.screenHeight ) )
			// 뒤에 - 10을 안해주면 원이 범위를 벗어난다. 왜 그러는지는 모르겠지만 일단 잘 되니까 추가
			tmp = super.parsingPoint(tmp - (super.screenHeight - (super.getShapeMinY() + beforeMaxX - tmp + BLANK_BOTTOM_HEIGHT)) ) + 10;
		else
			tmp = super.parsingPoint(tmp);
		
		this.circle.setLayoutX( tmp );

		tmp = this.circle.getRadius() + (beforeMinX - super.getShapeMinX()) / 2;
		this.circle.setRadius( tmp );
		this.circle.setCenterX( tmp );
		this.circle.setCenterY( tmp );
		
	}
	
	@Override
	protected void eventDragResizeRight(MouseEvent e) {
		double tmp = this.pressShapeWidth + (e.getX() - this.pressMouseX);
		if( tmp < MIN_W_SIZE )	{
			tmp = MIN_W_SIZE;
		}else{
			if( super.screenWidth != ShapeEvent.NON_SIZE_VALUE && tmp + super.getShapeMinX() > super.screenWidth )
				tmp = super.screenWidth - super.getShapeMinX();
		}
		
		// 하단 범위를 벗어났는지 검사
		if( (super.getShapeMinY() + tmp + BLANK_BOTTOM_HEIGHT + 10) > (super.screenHeight ) )
			// 뒤에 - 10을 안해주면 원이 범위를 벗어난다. 왜 그러는지는 모르겠지만 일단 잘 되니까 추가
			tmp = super.parsingPoint(tmp + (super.screenHeight - (super.getShapeMinY() + tmp + BLANK_BOTTOM_HEIGHT)) ) - 10;
		else
			tmp = super.parsingPoint(tmp);
		
		tmp = tmp / 2;
		
		this.circle.setRadius( tmp );
		this.circle.setCenterX( tmp );
		this.circle.setCenterY( tmp );
	}

	@Override
	protected void eventDragResizeTop(MouseEvent e) {
		double beforeMinY = this.getShapeMinY();
		double beforeMaxY = this.getShapeMaxY();
		
		double tmp = beforeMinY + e.getY() - this.pressMouseY;
		if( tmp < 0 )		tmp = 0;
		else					tmp = beforeMaxY - tmp <= MIN_H_SIZE ? beforeMaxY - MIN_H_SIZE : tmp;
		
		// 오른쪽 범위를 벗어났는지 검사
		if( (super.getShapeMinX() + beforeMaxY - tmp) > (super.screenWidth ) )
			// 뒤에 - 10을 안해주면 원이 범위를 벗어난다. 왜 그러는지는 모르겠지만 일단 잘 되니까 추가
			tmp = super.parsingPoint(tmp - (super.screenWidth - (super.getShapeMinX() + beforeMaxY - tmp)) );
		else
			tmp = super.parsingPoint(tmp);
		
		this.circle.setLayoutY( tmp );

		tmp = this.circle.getRadius() + (beforeMinY - this.getShapeMinY()) / 2;
		this.circle.setRadius( tmp );
		this.circle.setCenterX( tmp );
		this.circle.setCenterY( tmp );
	}

	@Override
	protected void eventDragResizeBottom(MouseEvent e) {
		double tmp = this.pressShapeHeight + (e.getY() - this.pressMouseY);
		if( tmp < MIN_H_SIZE )	{
			tmp = MIN_H_SIZE;
		}else{
			if( super.screenHeight != ShapeEvent.NON_SIZE_VALUE && tmp + super.getShapeMinY() > super.screenHeight )
				tmp = super.screenHeight - super.getShapeMinY();
		}
		
		
		// 오른쪽 범위를 벗어났는지 검사
		if( (super.getShapeMinX() + tmp) > (super.screenWidth ) )
			// 뒤에 - 10을 안해주면 원이 범위를 벗어난다. 왜 그러는지는 모르겠지만 일단 잘 되니까 추가
			tmp = super.parsingPoint(tmp + (super.screenWidth - (super.getShapeMinX() + tmp)) );
		else
			tmp = super.parsingPoint(tmp);
		
		
		tmp = tmp / 2;
		
		this.circle.setRadius( tmp );
		this.circle.setCenterX( tmp );
		this.circle.setCenterY( tmp );
	}

}
