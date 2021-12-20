package application.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import application.model.KlotzTypeModel;
import application.model.SingleKlotzModel;
import application.model.TetrisGridModel;
import application.model.TetrisShapeModel;
import application.model.VectorDirectionsModel;

public class MovementController {
	private TetrisShapeModel shapeModel;
	private TetrisGridModel gridModel;
	
    private List<GameOverListenerInterface> gameOverListeners = new ArrayList<GameOverListenerInterface>();
    private List<LockShapeInterface> lockShapeListeners = new ArrayList<LockShapeInterface>();

	
	public MovementController(final TetrisShapeModel shapeModel, final TetrisGridModel gridModel) {
		this.shapeModel = shapeModel;
		this.gridModel = gridModel;
	}
	
	public void addGameOverListener(final GameOverListenerInterface toAdd) {
        gameOverListeners.add(toAdd);
    }

	public void addLockShapeListener(final LockShapeInterface toAdd) {
		lockShapeListeners.add(toAdd);
    }

	void setNewShapeModel(final TetrisShapeModel shapeModel) {
		this.shapeModel = shapeModel;
	}

	void moveLeft() {
		int offsetX = -1;
		int offsetY = 0;
		if (positionIsLegal(offsetX, offsetY)) {
			setShapeToNewPosition(offsetX, offsetY);
		}
	}

	void moveRight() {
		int offsetX = 1;
		int offsetY = 0;
		if (positionIsLegal(offsetX, offsetY)) {
			setShapeToNewPosition(offsetX, offsetY);
		}
	}

	void moveDown() {
		int offsetX = 0;
		int offsetY = 1;
		if (positionIsLegal(offsetX, offsetY)) {
			setShapeToNewPosition(offsetX, offsetY);
		} else {
			lockInGridAndMakeNewShape();
		}
	}
	
	void rotateRight() {
		//do not rotate OKlotzes
		if (shapeModel.getKlotzType()==KlotzTypeModel.OKLOTZ) {
			return;
		}
		
		for (SingleKlotzModel Klotz : shapeModel.getThreeKlotzVectorsRelativeToAnchorPoint()) {
			Klotz.setDirection(VectorDirectionsModel.rotateRight(Klotz.getDirection()));
		}
		
		boolean positionIsLegal = true;
		for (Point positionCoordinate : shapeModel.getFourKlotzCoordinates()) {
			if (singlePointPositionIsIllegal((int)positionCoordinate.getX(), (int)positionCoordinate.getY())) {
				positionIsLegal = false;
			}
		}
		
		if (positionIsLegal) {
			shapeModel.tellObserversIChanged();
		} else {
			rotateLeft();
		}
	}
	
	void rotateLeft() {
		//do not rotate OKlotzes
		if (shapeModel.getKlotzType()==KlotzTypeModel.OKLOTZ) {
			return;
		}
		for (SingleKlotzModel Klotz : shapeModel.getThreeKlotzVectorsRelativeToAnchorPoint()) {
			Klotz.setDirection(VectorDirectionsModel.rotateLeft(Klotz.getDirection()));
		}
		boolean positionIsLegal = true;
		for (Point positionCoordinate : shapeModel.getFourKlotzCoordinates()) {
			if (singlePointPositionIsIllegal((int)positionCoordinate.getX(), (int)positionCoordinate.getY())) {
				positionIsLegal = false;
			}
		}
		
		if (positionIsLegal) {
			shapeModel.tellObserversIChanged();
		} else {
			rotateRight();
		}
	}
	
	private boolean positionIsLegal(final int offsetX, final int offsetY) {
		for (Point klotzCoordinate: shapeModel.getFourKlotzCoordinates()) {
			int y = (int)klotzCoordinate.getY() + offsetY;
			int x = (int)klotzCoordinate.getX() + offsetX;

			if(singlePointPositionIsIllegal(x, y)) {
				return false;
			}
		}
		return true;
	}

	private boolean singlePointPositionIsIllegal(final int x, final int y) {
		if ( x < 0  || (x >= gridModel.getNumberOfColumns()) || (y >= gridModel.getNumberOfRows()) || (gridModel.getKlotzOfCell(y, x) != KlotzTypeModel.NOLOTZ) ) { 
			return true;
		}
		return false;		
	}
	
	private void setShapeToNewPosition(final int offsetX, final int offsetY) {
		shapeModel.setAnchorPoint((int)shapeModel.getAnchorPoint().getX() + offsetX, (int)shapeModel.getAnchorPoint().getY() + offsetY);
	}
	
	private void lockInGridAndMakeNewShape() {
		for (Point klotzCoordinate: shapeModel.getFourKlotzCoordinates()) {
			gridModel.setKlotzOfCell((int)klotzCoordinate.getY(), (int)klotzCoordinate.getX(), shapeModel.getKlotzType());
			if ((int)klotzCoordinate.getY()==0) {
				for (GameOverListenerInterface gameOverListener : gameOverListeners) {
		            gameOverListener.gameIsOver();
		    	}
			return;
			}
		}
		
		for (LockShapeInterface lockShapeListener : lockShapeListeners) {
            lockShapeListener.shapeReachedGroundAndLocked();
    	}
	}
}
