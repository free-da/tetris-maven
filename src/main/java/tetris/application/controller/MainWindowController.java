package application.controller;

import application.model.TetrisGridModel;
import application.model.TetrisShapeModel;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainWindowController implements GameOverListenerInterface, LockShapeInterface {
	private static final int PLAYING_GRID_SHAPE_ROW_POSITION = 0;
	private static final int NEXT_GRID_SHAPE_ROW_POSITION = 6;
	private TetrisGridController gameBoardGridController;
	private TetrisGridController nextGridController;
	private TetrisGridModel gameboardGridModel;
	private MovementController movementController;
	private Stage stage;
	private Scene scene;
	static double time;
	static AnimationTimer timer;

	@FXML
	private Canvas gameboardCanvas;
	@FXML
	private Canvas nextUpCanvas;
	@FXML
	public Label pointsLabel;
	
	public void initialize() {
		gameboardGridModel = new TetrisGridModel(31, 15);
		gameBoardGridController = new TetrisGridController(gameboardCanvas, gameboardGridModel);
		TetrisShapeModel playingShape = gameBoardGridController.newTetrisShape(PLAYING_GRID_SHAPE_ROW_POSITION);
		
		movementController = new MovementController(playingShape, gameboardGridModel);
		movementController.addGameOverListener(this);
		movementController.addLockShapeListener(this);
		
		TetrisGridModel nextGridModel = new TetrisGridModel(14, 7);
		nextGridController = new TetrisGridController(nextUpCanvas, nextGridModel);
		putNewShapeInNextGrid();
		
		pointsLabel.textProperty().bind(gameboardGridModel.getScoreCountProperty());	
		startAnimationTimer();
	}

	public void setSceneAndSetupListeners(final Scene scene, final Stage stage) {
		this.scene = scene;
		this.stage = stage;
		new InputEventController(scene, movementController);
	}
	
	@Override
	public void gameIsOver() {
		gameOver();
	}

	@Override
	public void shapeReachedGroundAndLocked() {
		gameBoardGridController.meltRowsAndIncrementScoreIfNecessary();
		gameBoardGridController.incrementScoreForLockedShape();
		putNextShapeInStartPosition();
		putNewShapeInNextGrid();
	}

	private void newGame() {
		initialize();
		setSceneAndSetupListeners(scene, stage);
	}
	
	private void startAnimationTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                time += 0.015;

                if (time >= 0.5) {
                	movementController.moveDown();
                    time = 0;
                }
            }
        };
        timer.start();
	}
	
	private static void stopAnimationTimer() {
		timer.stop();
	}
	
	private void gameOver() {
		stopAnimationTimer();
		
		//game over screen
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        
        VBox dialogVbox = new VBox(20);
        dialogVbox.setStyle("-fx-background-color: #262626;");
        dialogVbox.setAlignment(Pos.CENTER);
        
        Button quitButton = new Button("Quit");
        Button newGameButton = new Button("New Game");
        quitButton.setOnAction(event -> {System.exit(0); dialog.close();});
        newGameButton.setOnAction(event -> {newGame(); dialog.close();});
        quitButton.setAlignment(Pos.CENTER);
        newGameButton.setAlignment(Pos.CENTER);       
        Text gameOver = new Text("Game Over");
        gameOver.setFill(Color.YELLOW);
        gameOver.setStroke(Color.AQUAMARINE);
        gameOver.setStyle("-fx-font-size: 24;");
        dialogVbox.getChildren().add(gameOver);
        dialogVbox.getChildren().add(newGameButton);
        dialogVbox.getChildren().add(quitButton);
        
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	
	private void putNextShapeInStartPosition() {
		TetrisShapeModel playingShape = gameBoardGridController.newTetrisShape(PLAYING_GRID_SHAPE_ROW_POSITION, nextGridController.newShape.getKlotzType());
		movementController.setNewShapeModel(playingShape);
	}

	private void putNewShapeInNextGrid() {
		nextGridController.newTetrisShape(NEXT_GRID_SHAPE_ROW_POSITION);
	}
}
