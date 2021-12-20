package application.view;

import application.model.KlotzTypeModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;


public class TetrisGridView {
	private static double CELL_SIZE = 12;
	private static double BORDER_WIDTH = 2;
	private Canvas canvas;
	private int numberOfYGridLines, numberOfXGridLines;
	
	public TetrisGridView(final int rows, final int columns, final Canvas tetrisGridCanvas) {
		canvas = tetrisGridCanvas;
		numberOfYGridLines = rows + 1;
		numberOfXGridLines = columns + 1;
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        drawGridLines(graphicsContext);
	}

	public void setKlotz(final int rowIndex, final int columnIndex, final KlotzTypeModel klotzType) {
		Color color;
		switch (klotzType) {
			case IKLOTZ:
				color = Color.DODGERBLUE;
				break;
			case OKLOTZ:
				color = Color.VIOLET;
				break;
			case LKLOTZ:
				color = Color.CHARTREUSE;
				break;
			case JKLOTZ:
				color= Color.ORANGE;
				break;
			case ZKLOTZ:
				color = Color.YELLOW;
				break;
			case TKLOTZ:
				color = Color.TURQUOISE;
				break;
			case SKLOTZ:
				color = Color.SALMON;
				break;
			default:
				color = Color.DIMGREY;
				break;
			}
		if (klotzType != KlotzTypeModel.NOLOTZ) {
			drawKlotz(rowIndex, columnIndex, color);	
		} else {
			drawNoKlotz(rowIndex, columnIndex);
		}
	}
	
	private void drawKlotz(final int rowIndex, final int columnIndex, final Color color) {
		Lighting effect = new Lighting();
		double xStart = columnIndex * (CELL_SIZE + BORDER_WIDTH) + (0.5*BORDER_WIDTH);
		double yStart = rowIndex * (CELL_SIZE + BORDER_WIDTH) + (0.5*BORDER_WIDTH);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(color);
		gc.setEffect(effect);

		gc.fillRect(xStart, yStart, CELL_SIZE, CELL_SIZE);
	}
	
	private void drawNoKlotz(final int rowIndex, final int columnIndex) {
		double xStart = columnIndex * (CELL_SIZE + BORDER_WIDTH) + (0.5*BORDER_WIDTH);
		double yStart = rowIndex * (CELL_SIZE + BORDER_WIDTH) + (0.5*BORDER_WIDTH);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(48,48,48));
		gc.setEffect(null);

		gc.fillRect(xStart, yStart, CELL_SIZE, CELL_SIZE);
	}
	
	private void drawGridLines(final GraphicsContext gc) {
		gc.setLineWidth(BORDER_WIDTH);
		gc.setStroke(Color.DIMGREY);
		double xStart = 0;
		double yStart = 0;
		for (int i=0; i<numberOfYGridLines; i++) { 
	        gc.strokeLine(0, yStart, canvas.getWidth(), yStart);
	        yStart += CELL_SIZE + BORDER_WIDTH;
		}
		for (int j=0; j<numberOfXGridLines; j++) {
			gc.strokeLine(xStart, 0, xStart, canvas.getHeight());
	        xStart += CELL_SIZE + BORDER_WIDTH;
		}
	}

}
