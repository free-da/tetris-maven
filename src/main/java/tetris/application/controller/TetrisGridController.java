package application.controller;

import application.model.TetrisShapeModel;

import java.awt.Point;
import application.model.KlotzTypeModel;
import application.model.TetrisGridModel;
import application.view.TetrisGridView;
import javafx.scene.canvas.Canvas;

public class TetrisGridController implements TetrisShapeChangedListenerInterface {
	private static final int MELTED_ROW_SCORE = 1000;
	private static final int LOCKED_SHAPE_SCORE = 50;
	private TetrisGridModel tetrisGridModel;
	private TetrisGridView tetrisGridView;
	TetrisShapeModel newShape;
	
	public TetrisGridController(final Canvas gameboardCanvas, final TetrisGridModel tetrisGridModel) {
		this.tetrisGridModel = tetrisGridModel;
		tetrisGridView = new TetrisGridView(tetrisGridModel.getNumberOfRows(), tetrisGridModel.getNumberOfColumns(), gameboardCanvas);
		fillGridWithKlotzes();
	}

	TetrisShapeModel newTetrisShape(final int rowIndex) {
		int columnIndex = tetrisGridModel.getNumberOfColumns() / 2;
		newShape = new TetrisShapeModel(rowIndex, columnIndex, tetrisGridModel);
		newShape.addListener(this);
		return newShape;
	}
	
	TetrisShapeModel newTetrisShape(final int rowIndex, final KlotzTypeModel klotzType) {
		int columnIndex = tetrisGridModel.getNumberOfColumns() / 2;
		newShape = new TetrisShapeModel(rowIndex, columnIndex, tetrisGridModel, klotzType);
		newShape.addListener(this);
		return newShape;
	}

	void meltRowsAndIncrementScoreIfNecessary() {
		while (checkFirstFullRows() > -1) {
			clearFullRow(checkFirstFullRows());
			incrementScoreCount(MELTED_ROW_SCORE);
		}		
	}
	
	void incrementScoreForLockedShape() {
		incrementScoreCount(LOCKED_SHAPE_SCORE);
	}
	

	private void refreshGrid() {
		for(int columns=0; columns<=tetrisGridModel.getNumberOfColumns(); columns++) {
			for(int rows=0; rows<tetrisGridModel.getNumberOfRows(); rows++) {
				tetrisGridView.setKlotz(rows, columns, KlotzTypeModel.NOLOTZ);
			}
		}
		fillGridWithKlotzes();
	}

	private void fillGridWithKlotzes() {
		for(int columns=0; columns<tetrisGridModel.getNumberOfColumns(); columns++) {
			for(int rows=0; rows<tetrisGridModel.getNumberOfRows(); rows++) {
				if (tetrisGridModel.getKlotzOfCell(rows, columns) != KlotzTypeModel.NOLOTZ) {
					KlotzTypeModel klotz = tetrisGridModel.getKlotzOfCell(rows, columns) ;
					tetrisGridView.setKlotz(rows, columns, klotz);
				}
			}
		}
		if (newShape != null) {
			Point[] coordinates = newShape.getFourKlotzCoordinates();
			for(Point coordinate:coordinates) {
				KlotzTypeModel klotz = newShape.getKlotzType() ;
				tetrisGridView.setKlotz((int)coordinate.getY(), (int)coordinate.getX(), klotz);
			}
		}
	}
	
	private int checkFirstFullRows() {
		boolean rowIsFull = false;
		for(int y = 0; y < tetrisGridModel.getNumberOfRows(); y++) {
			for (int x = 0; x < tetrisGridModel.getNumberOfColumns(); x++) {
				if (tetrisGridModel.getKlotzOfCell(y, x) == KlotzTypeModel.NOLOTZ) {
					rowIsFull = false;
					break;
				} else {
					rowIsFull = true;
				}
			}
			if (rowIsFull) {
				return y;
			}
		}
		return -1;
	}
	
	private void clearFullRow(final int rowIndex) {
		//move down all rows above first full row
		for (int y = rowIndex; y > 0; y--) {
			for (int x = 0; x < tetrisGridModel.getNumberOfColumns(); x++) {
				tetrisGridModel.setKlotzOfCell(y, x, tetrisGridModel.getKlotzOfCell(y-1, x));
			}
		}
		//initialize new 0th row with NoKotz-Types
		for (int x = 0; x < tetrisGridModel.getNumberOfColumns(); x++) {
			tetrisGridModel.setKlotzOfCell(0, x, KlotzTypeModel.NOLOTZ);
		}
	}
	
	private void incrementScoreCount(final int increment) {
		int newScore = Integer.parseInt(tetrisGridModel.getScoreCount());
		newScore += increment;
		tetrisGridModel.setScoreCount(Integer.toString(newScore));
	}

	@Override
	public void tetrisShapeChanged() {
		refreshGrid();
	}
	
}
