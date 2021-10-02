package kr.co.bomz.cmn.ui.event;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class RectangleEvent extends ShapeEvent{
	
	private final Rectangle rec;
	
	public RectangleEvent(Rectangle rec) throws NullPointerException, ScreenSizeException{
		super(rec);
		this.rec = rec;
	}
	
	public RectangleEvent(Rectangle rec, double screenWidth, double screenHeight) throws NullPointerException, ScreenSizeException{
		super(rec, screenWidth, screenHeight);
		this.rec = rec;
	}

	@Override
	protected void eventDragResizeLeft(MouseEvent e){
		double beforeMinX = this.getShapeMinX();
		double beforeMaxX = this.getShapeMaxX();
		
		double tmp = beforeMinX + e.getX() - this.pressMouseX;
		if( tmp < 0 )		tmp = 0;
		this.rec.setLayoutX( super.parsingPoint(beforeMaxX - tmp <= MIN_W_SIZE ? beforeMaxX - MIN_W_SIZE : tmp) );
		
		this.rec.setWidth( super.parsingPoint(this.rec.getWidth() + (beforeMinX - this.getShapeMinX())) );
	}
	
	@Override
	protected void eventDragResizeRight(MouseEvent e){
		double tmp = this.pressShapeWidth + (e.getX() - this.pressMouseX);
		if( tmp < MIN_W_SIZE )	{
			tmp = MIN_W_SIZE;
		}else{
			if( super.screenWidth != ShapeEvent.NON_SIZE_VALUE && tmp + super.getShapeMinX() > super.screenWidth )
				tmp = super.parsingPoint(super.screenWidth - super.getShapeMinX());
			else
				tmp = super.parsingPoint(tmp);
		}
		
		this.rec.setWidth( tmp );
	}
	
	@Override
	protected void eventDragResizeTop(MouseEvent e){
		
		double beforeMinY = this.getShapeMinY();
		double beforeMaxY = this.getShapeMaxY();
		
		double tmp = beforeMinY + e.getY() - this.pressMouseY;
		if( tmp < 0 )		tmp = 0;
		this.rec.setLayoutY( super.parsingPoint(beforeMaxY - tmp <= MIN_H_SIZE ? beforeMaxY - MIN_H_SIZE : tmp) );
		
		this.rec.setHeight( super.parsingPoint(this.rec.getHeight() + (beforeMinY - this.getShapeMinY())) );
	}
	
	@Override
	protected void eventDragResizeBottom(MouseEvent e){
		double tmp = this.pressShapeHeight + (e.getY() - this.pressMouseY);
		
		if( tmp < MIN_H_SIZE )	{
			tmp = MIN_H_SIZE;
		}else{
			if( super.screenHeight != ShapeEvent.NON_SIZE_VALUE && tmp + super.getShapeMinY() > super.screenHeight )
				tmp = super.parsingPoint(super.screenHeight - super.getShapeMinY());
			else
				tmp = super.parsingPoint(tmp);
		}
		
		this.rec.setHeight( tmp );
	}
}
