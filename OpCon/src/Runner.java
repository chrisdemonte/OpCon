import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Runner extends Application {
	//data management classes. experiment holds all the experimental data. notable methods getGameList(), . prob calculalator take
	public Experiment ex;
	public ProbabilityCalculator pc;
	
	public int instructionsCounter = 0;
	public int gameCounter = 0;
	public int trialCounter = 0;
	public Random rand = new Random();
	
	public int width = (int) Screen.getPrimary().getVisualBounds().getWidth();
	public int height = (int) Screen.getPrimary().getVisualBounds().getHeight();
	public int elementHeights = (height/20);
	public int normalFont = (int)(Math.sqrt(width/30.0) + Math.sqrt(height/30.0));
	public int bigFont = 25;
	public int smallFont = 18;

	public Label centerLabel;
	
	public BorderPane borderpane;
	public HBox bottomContainer;
	public VBox centerContainer;
	public HBox sliderLabelContainer;
	public Slider slider;
	public Label trialCounterLabel;
	
	public Button bottomButton;
	public Button nextGameButton;
	public Button finalButton;
	
	public ImageView bulbOn;
	public ImageView bulbOff;
	public ImageView bgImage;
	
	public int sceneMode = 0;
	public int resultDuration = 2;
	public int temp = 0;
	
	

	public void start(Stage primaryStage) throws Exception {
	
		pc = new ProbabilityCalculator();
		
		sliderLabelContainer = new HBox();
		
		
		bulbOn = new ImageView(new Image(new FileInputStream("resources/light-bulb-on.png")));
		bulbOn.setFitWidth((600.0 * 0.576));
		bulbOn.setFitHeight(800.0 * 0.5);
		bulbOff = new ImageView(new Image(new FileInputStream("resources/light-bulb-off.png")));
		bulbOff.setFitWidth(600.0 * 0.45);
		bulbOff.setFitHeight(800.0 * 0.5);
		
		bgImage = new ImageView(new Image(new FileInputStream("resources/greybg.jpg")));
		bgImage.setRotate(180.0);
		bgImage.setFitWidth(width);
		bgImage.setFitHeight(height + 102);
		

		primaryStage.setScene(dataEntryScene(primaryStage));
		primaryStage.setTitle("Free Operant Contigiency");
		primaryStage.setFullScreen(true);
		primaryStage.setMinHeight(800);
		primaryStage.setMinWidth(600);
		primaryStage.show();
		
	}
	
	
	public Scene dataEntryScene (Stage mainStage) {
		
		Pane root = new Pane();
		root.setPrefSize(width, height);
		Label bg = new Label();
		bg.setGraphic(bgImage);
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		root.getChildren().addAll(bg, container);
		

		//01************** leftElements holds all the elements on the left (labels)**************************01
		VBox leftElements = new VBox(15);
		leftElements.setAlignment(Pos.BASELINE_RIGHT);
		leftElements.setMinSize(width * .4 , height * .5);
		leftElements.setPadding(new Insets(height * .3, 0.0, 0.0, 0.0));
		
		Label userLabel = new Label("User ID:");
		userLabel.setFont(Font.font("Verdana", normalFont));
		userLabel.setMinHeight(elementHeights);
		
		Label numGamesLabel = new Label("Number of Games:");
		numGamesLabel.setFont(Font.font("Verdana", normalFont));
		numGamesLabel.setMinHeight(elementHeights);
		
		leftElements.getChildren().addAll(userLabel, numGamesLabel);
		
		//01************** leftElements ************************************************************************01
		
		// 02************** rightElements holds all the elements on the right (textfields and submit button) *******************02
		VBox rightElements = new VBox(15);
		rightElements.setAlignment(Pos.BASELINE_LEFT);
		rightElements.setMinSize(width * .6 , height * .5);
		rightElements.setPadding(new Insets(height * .3, 0.0, 0.0, width * .01));
		TextField userText = new TextField();
		userText.setMinHeight(elementHeights);
		userText.setMaxWidth(width * .3);
		userText.setMinWidth(200);
		userText.setFont(Font.font("Verdana", normalFont));
		userText.setOnMouseClicked(a->{
			userText.setStyle(null);
		});
		
		TextField numGamesText = new TextField();
		numGamesText.setMinHeight(elementHeights);
		numGamesText.setMaxWidth(width * .3);
		numGamesText.setMinWidth(200);
		numGamesText.setFont(Font.font("Verdana", normalFont));
		numGamesText.setOnMouseClicked(a->{
			numGamesText.setStyle(null);
		});
		Button submit1 = new Button("Submit");
		submit1.setMinSize( width * .2, elementHeights);
		submit1.setFont(Font.font("Verdana", smallFont));
		submit1.setOnAction(e->{
			if (userText.getText().equals("")) {
				userText.setStyle("-fx-text-box-border: red;");
			};
			int temp = 0;
		
			try {
				temp = Integer.parseInt(numGamesText.getText());
			}
			catch (NumberFormatException ex) {
				numGamesText.setStyle("-fx-text-box-border: red;");
				temp = 0;
			}
			if (temp < 1) {
				numGamesText.setStyle("-fx-border-color: red");
			}
			if (temp > 0 && !userText.getText().equals("")) {
				ex = new Experiment(userText.getText(), temp);
				root.getChildren().clear();
				root.getChildren().add(dataEntryPart2(mainStage));
			}
			
		
			
		});
		
		rightElements.getChildren().addAll(userText, numGamesText, submit1);
		
		//02************** rightElements ***************************************************************************************02
		
		mainStage.heightProperty().addListener(e->{
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			leftElements.setPadding(new Insets(height * .3, 0.0, 0.0, 0.0));
			rightElements.setPadding(new Insets(height * .3, 0.0, 0.0, width * .01));
			elementHeights = (int) mainStage.getHeight() / 20;
			submit1.setMinSize(mainStage.getWidth() * .2, elementHeights);
			numGamesText.setMinHeight(elementHeights);
			userText.setMinHeight(elementHeights);
			numGamesLabel.setMinHeight(elementHeights);
			userLabel.setMinHeight(elementHeights);
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			numGamesLabel.setFont(Font.font("Verdana", normalFont));
			userLabel.setFont(Font.font("Verdana", normalFont));
			userText.setFont(Font.font("Verdana", normalFont));
			numGamesText.setFont(Font.font("Verdana", normalFont));
			submit1.setFont(	Font.font("Verdana", normalFont));
			
		});
		mainStage.widthProperty().addListener(e ->{
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			container.setPrefWidth(mainStage.getWidth());
			leftElements.setMinSize(mainStage.getWidth() * .4, mainStage.getHeight() * .5);
			leftElements.setPadding(new Insets(height * .3, 0.0, 0.0, 0.0));
			rightElements.setMinSize(mainStage.getWidth() * .6, mainStage.getHeight() * .5);
			rightElements.setPadding(new Insets(height * .3, 0.0, 0.0, width * .01));
			userText.setMaxWidth(mainStage.getWidth() * .3);
			numGamesText.setMaxWidth(mainStage.getWidth() * .3);
			elementHeights = (int) mainStage.getHeight() / 20;
			submit1.setMinSize( mainStage.getWidth() * .2, elementHeights);
			numGamesText.setMinHeight(elementHeights);
			userText.setMinHeight(elementHeights);
			numGamesLabel.setMinHeight(elementHeights);
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			numGamesLabel.setFont(Font.font("Verdana", normalFont));
			userLabel.setFont(Font.font("Verdana", normalFont));
			userText.setFont(Font.font("Verdana", normalFont));
			numGamesText.setFont(Font.font("Verdana", normalFont));
			submit1.setFont(	Font.font("Verdana", normalFont) );

	
			
		});
				
		container.getChildren().addAll(leftElements, rightElements);
		Scene scene = new Scene(root, width, height);
			
		return scene;
	}
	
	public Pane dataEntryPart2 (Stage mainStage) {
		
		Pane root = new Pane();
		root.setPrefSize(width, height);
		Label bg = new Label();
		bg.setGraphic(bgImage);
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		
		root.getChildren().addAll(bg, container);
		
		
		//01************** leftElements holds all the elements on the left (labels)****************************01
		VBox leftElements = new VBox(15);
		leftElements.setAlignment(Pos.BASELINE_RIGHT);
		leftElements.setMinSize(width * .4 , height * .5);
		leftElements.setPadding(new Insets(height * .25 - height/275, 0.0, 0.0, 0.0));

		Label spacer = new Label(" ");
		spacer.setMinHeight(elementHeights);
		Label numTrialsLabel = new Label("Number of Trials:");
		numTrialsLabel.setFont(Font.font("Verdana", normalFont));
		numTrialsLabel.setMinSize( width/4, elementHeights);
		numTrialsLabel.setMaxWidth(width * .3);
		numTrialsLabel.setAlignment(Pos.BASELINE_RIGHT);
		numTrialsLabel.setMinWidth(200);
		
		Label trialLengthLabel = new Label("Trial Length(ms):");
		trialLengthLabel.setFont(Font.font("Verdana", normalFont));
		trialLengthLabel.setMinSize( width/4, elementHeights);
		trialLengthLabel.setMaxWidth(width * .3);
		trialLengthLabel.setAlignment(Pos.BASELINE_RIGHT);
		trialLengthLabel.setMinWidth(200);
		
		Label restLengthLabel = new Label("Inter-Trial Interval(ms):");
		restLengthLabel.setFont(Font.font("Verdana", normalFont));
		restLengthLabel.setMinSize( width/4, elementHeights);
		restLengthLabel.setMaxWidth(width * .3);
		restLengthLabel.setAlignment(Pos.BASELINE_RIGHT);
		restLengthLabel.setMinWidth(200);
		
		Label probCodeLabel = new Label("Probability Code:");
		probCodeLabel.setFont(Font.font("Verdana", normalFont));
		probCodeLabel.setMinSize( width/4, elementHeights);
		probCodeLabel.setMaxWidth(width * .3);
		probCodeLabel.setAlignment(Pos.BASELINE_RIGHT);
		probCodeLabel.setMinWidth(200);
		
		leftElements.getChildren().addAll(spacer, numTrialsLabel, trialLengthLabel, restLengthLabel, probCodeLabel);
		
		//01************** leftElements *************************************************************************01
		
		// 02************** rightElements holds all the elements on the right (textfields and submit button) *******************02
		VBox rightElements = new VBox(15);
		rightElements.setAlignment(Pos.BASELINE_LEFT);
		rightElements.setMinSize(width * .6 , height * .5);
		rightElements.setPadding(new Insets(height * .25, 0.0, 0.0, width* .01));
		
		Label gameEntryLabel = new Label ("Game " + (gameCounter + 1));
		gameEntryLabel.setFont(Font.font("Verdana", FontWeight.BOLD, normalFont));
		gameEntryLabel.setMinSize( width/4, elementHeights);
		gameEntryLabel.setMaxWidth(width * .3);
		gameEntryLabel.setMinWidth(200);
		
		TextField numTrialsEntry = new TextField();
		numTrialsEntry.setFont(Font.font("Verdana", normalFont));
		numTrialsEntry.setMinSize( width/4, elementHeights);
		numTrialsEntry.setMaxWidth(width * .3);
		numTrialsEntry.setMinWidth(200);
		numTrialsEntry.setOnMouseClicked(a->{
			numTrialsEntry.setStyle(null);
		});
		
		TextField trialLengthEntry= new TextField();
		trialLengthEntry.setFont(Font.font("Verdana", normalFont));
		trialLengthEntry.setMinSize( width/4, elementHeights);
		trialLengthEntry.setMaxWidth(width * .3);
		trialLengthEntry.setMinWidth(200);
		trialLengthEntry.setOnMouseClicked(a->{
			trialLengthEntry.setStyle(null);
		});
		
		TextField restLengthEntry = new TextField();
		restLengthEntry.setFont(Font.font("Verdana", normalFont));
		restLengthEntry.setMinSize( width/4, elementHeights);
		restLengthEntry.setMaxWidth(width * .3);
		restLengthEntry.setMinWidth(200);
		restLengthEntry.setOnMouseClicked(a->{
			restLengthEntry.setStyle(null);
		});
		
		TextField probCodeEntry = new TextField();
		probCodeEntry.setFont(Font.font("Verdana", normalFont));
		probCodeEntry.setMinSize( width/4, elementHeights);
		probCodeEntry.setMaxWidth(width * .3);
		probCodeEntry.setMinWidth(200);
		probCodeEntry.setOnMouseClicked(a->{
			probCodeEntry.setStyle(null);
		});
		
		Button submit2 = new Button ("Submit");
		submit2.setFont(	Font.font("Verdana", normalFont));
		submit2.setMinSize( width/4, elementHeights);
		submit2.setOnAction(e->{
			//***
			int tempT = 0;
			try {
				tempT = Integer.parseInt(numTrialsEntry.getText());
			}
			catch (NumberFormatException ex) {
				numTrialsEntry.setStyle("-fx-text-box-border: red;");
				tempT = 0;
				
			}
			if (tempT < 1) {
				numTrialsEntry.setStyle("-fx-text-box-border: red;");
			}
			//***
			int tempL = 0;
			try {
				tempL = Integer.parseInt(trialLengthEntry.getText());
			}
			catch (NumberFormatException ex) {
				trialLengthEntry.setStyle("-fx-text-box-border: red;");
				tempL = 0;
				
			}
			if (tempL < 1000) {
				trialLengthEntry.setStyle("-fx-text-box-border: red;");
			}
			//****
			int tempR = 0;
			try {
				tempR = Integer.parseInt(restLengthEntry.getText());
			}
			catch (NumberFormatException ex) {
				restLengthEntry.setStyle("-fx-text-box-border: red;");
				tempR = 0;
				
			}
			if (tempR < 1000) {
				restLengthEntry.setStyle("-fx-text-box-border: red;");
			}
			//****
			int tempP = 0;
			try {
				tempP = Integer.parseInt(probCodeEntry.getText());
				
			}
			catch (NumberFormatException ex) {
				probCodeEntry.setStyle("-fx-text-box-border: red;");
				tempP = 0;
				
			}
			if (tempP < 1 || tempP > 9) {
				probCodeEntry.setStyle("-fx-text-box-border: red;");
				tempP = 0;
			}
			if (tempT > 0 && tempL >= 1000 && tempR >= 1000 && tempP > 0) {
				
				Game game = new Game(tempT, tempL, tempR, tempP);
				ex.getGameList().add(gameCounter, game); 
				gameCounter++;
				gameEntryLabel.setText("Game " + (gameCounter + 1));
				numTrialsEntry.clear();
				trialLengthEntry.clear();
				restLengthEntry.clear();
				probCodeEntry.clear();
				if (ex.getNumberOfGames() <= gameCounter) {
					gameCounter = 0;
					root.getChildren().clear();
					root.getChildren().add(instructionsPane(mainStage));
				}
			}
			
		});
		Button backButton = new Button("Back");
		backButton.setFont(	Font.font("Verdana", normalFont));
		backButton.setMinSize( width/4, elementHeights);
		backButton.setOnAction(e->{
			if (gameCounter == 0) {
				mainStage.setScene(dataEntryScene(mainStage));
			}
			else {
				gameCounter--;
				gameEntryLabel.setText("Game " + (gameCounter + 1));
				numTrialsEntry.clear();
				trialLengthEntry.clear();
				restLengthEntry.clear();
				probCodeEntry.clear();
			}
			
		});
		
		rightElements.getChildren().addAll(gameEntryLabel, numTrialsEntry, trialLengthEntry, restLengthEntry, probCodeEntry, submit2, backButton);
		
		// 02************** rightElements*********************************************************************************02
		
		mainStage.heightProperty().addListener(e->{
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			leftElements.setMinSize(mainStage.getWidth() * .4, mainStage.getHeight() * .5);
			leftElements.setPadding(new Insets(height * .25 - height/275, 0.0, 0.0, 0.0));
			rightElements.setMinSize(mainStage.getWidth() * .6, mainStage.getHeight() * .5);
			rightElements.setPadding(new Insets(height * .25, 0.0, 0.0, width* .01));
			elementHeights = (int) mainStage.getHeight() / 20;
			submit2.setMinSize(mainStage.getWidth() * .2, elementHeights);
			backButton.setMinSize(mainStage.getWidth() * .2, elementHeights);
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			spacer.setMinHeight(elementHeights);
			gameEntryLabel.setFont(Font.font("Verdana",FontWeight.BOLD, normalFont));
			numTrialsLabel.setMinHeight(elementHeights);
			numTrialsLabel.setFont(Font.font("Verdana", normalFont));
			trialLengthLabel.setMinHeight(elementHeights);
			trialLengthLabel.setFont(Font.font("Verdana", normalFont));
			restLengthLabel.setMinHeight(elementHeights);
			restLengthLabel.setFont(Font.font("Verdana", normalFont));
			probCodeLabel.setMinHeight(elementHeights);
			probCodeLabel.setFont(Font.font("Verdana", normalFont));
			
			numTrialsEntry.setMinHeight(elementHeights);
			numTrialsEntry.setFont(Font.font("Verdana", normalFont));
			trialLengthEntry.setMinHeight(elementHeights);
			numTrialsEntry.setFont(Font.font("Verdana", normalFont));
			restLengthEntry.setMinHeight(elementHeights);
			restLengthEntry.setFont(Font.font("Verdana", normalFont));
			probCodeEntry.setMinHeight(elementHeights);
			probCodeEntry.setFont(Font.font("Verdana", normalFont));
			
			submit2.setFont(	Font.font("Verdana", normalFont));
			backButton.setFont(	Font.font("Verdana", normalFont));
		});
		mainStage.widthProperty().addListener(e ->{
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			elementHeights = (int) mainStage.getHeight() / 20;
			container.setPrefWidth(mainStage.getWidth());
			leftElements.setMinSize(mainStage.getWidth() * .4, mainStage.getHeight() * .5);
			leftElements.setPadding(new Insets(height * .25 - height/275, 0.0, 0.0, 0.0));
			rightElements.setMinSize(mainStage.getWidth() * .6, mainStage.getHeight() * .5);
			rightElements.setPadding(new Insets(height * .25, 0.0, 0.0, width* .01));
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			gameEntryLabel.setFont(Font.font("Verdana",FontWeight.BOLD, normalFont));
			numTrialsLabel.setMaxWidth(mainStage.getWidth() * .3);
			numTrialsLabel.setFont(Font.font("Verdana", normalFont));
			trialLengthLabel.setMaxWidth(mainStage.getWidth() * .3);
			trialLengthLabel.setFont(Font.font("Verdana", normalFont));
			restLengthLabel.setMaxWidth(mainStage.getWidth() * .3);
			restLengthLabel.setFont(Font.font("Verdana", normalFont));
			probCodeLabel.setMaxWidth(mainStage.getWidth() * .3);
			probCodeLabel.setFont(Font.font("Verdana", normalFont));
			submit2.setMinSize( mainStage.getWidth() * .2, elementHeights);
			numTrialsEntry.setMaxWidth(mainStage.getWidth() * .3);
			numTrialsEntry.setFont(Font.font("Verdana", normalFont));
			trialLengthEntry.setMaxWidth(mainStage.getWidth() * .3);
			trialLengthEntry.setFont(Font.font("Verdana", normalFont));
			restLengthEntry.setMaxWidth(mainStage.getWidth() * .3);
			restLengthEntry.setFont(Font.font("Verdana", normalFont));
			probCodeEntry.setMaxWidth(mainStage.getWidth() * .3);
			probCodeEntry.setFont(Font.font("Verdana", normalFont));
			
			submit2.setFont(Font.font("Verdana", normalFont));
			backButton.setMinSize(mainStage.getWidth() * .2, elementHeights);
			backButton.setFont(	Font.font("Verdana", normalFont));

	
			
		});
		
		container.getChildren().addAll(leftElements, rightElements);
		return root;
	}
	
	public Pane instructionsPane (Stage mainStage) {
		
		Pane root = new Pane();
		root.setPrefSize(width, height);
		Label bg = new Label();
		bg.setGraphic(bgImage);
		
		VBox container = new VBox();
		container.setPrefSize(width, height);

		//01************** leftElements holds all the elements on the left (labels)****************************01
		HBox textContainer = new HBox();
		textContainer.setPadding(new Insets (50, 50, 50, 50));
		textContainer.setAlignment(Pos.CENTER);
		textContainer.setMaxWidth(width);
		
		Label instructionsLabel = new Label(ex.getInstructions()[0]);
		instructionsLabel.setMaxWidth(width-100);
		instructionsLabel.setWrapText(true);
		instructionsLabel.setFont(Font.font("Verdana", normalFont));
		textContainer.getChildren().add(instructionsLabel);
		
		HBox lightContainer = new HBox();
		lightContainer.setAlignment(Pos.CENTER);
		lightContainer.setMaxHeight(height/2);
		lightContainer.setMaxWidth(width);
		Label pic = new Label();
		pic.setPadding(new Insets(height/10, 0 , 0 , 0));

		lightContainer.getChildren().add(pic);
		container.getChildren().addAll(textContainer, lightContainer);
		//01************** leftElements holds all the elements on the left (labels)****************************01
		
		//02************** leftElements holds all the elements on the left (labels)****************************02
		HBox buttonBox = new HBox();
		buttonBox.setMinWidth(width);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setLayoutY(height - height/12);
		buttonBox.setPadding(new Insets(0.0, 0.0, 60.0, 0.0));
		HBox leftBox = new HBox();
		leftBox.setAlignment(Pos.BASELINE_LEFT);
		leftBox.setMinWidth(width/4);
		HBox rightBox = new HBox();
		rightBox.setAlignment(Pos.BASELINE_RIGHT);
		rightBox.setMinWidth(width/4);
		HBox centerBox = new HBox();
		centerBox.setAlignment(Pos.BASELINE_CENTER);
		centerBox.setMinWidth(width/4);

		Button forward = new Button ("Next");
		Button previous = new Button ("Back");
		Button carryOn = new Button("Click");

		rightBox.getChildren().add(forward);
		leftBox.getChildren().add(previous);
		centerBox.getChildren().add(carryOn);
		buttonBox.getChildren().addAll(leftBox, centerBox, rightBox);
		
		root.getChildren().addAll(bg, container, buttonBox);
		
		//02************** leftElements holds all the elements on the left (labels)****************************02
		

		
		forward.setFont(Font.font("Verdana", smallFont));
		forward.setMinSize(width/6, elementHeights);
		forward.setOnAction(e->{
			if (instructionsCounter == 0) {
				instructionsCounter++;
				previous.setVisible(true);
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
				
			}
			else if (instructionsCounter == 1) {
				instructionsCounter++;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
			}
			else if (instructionsCounter == 2) {
				instructionsCounter++;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
				forward.setVisible(false);
				previous.setVisible(false);
				pic.setGraphic(bulbOff);
				carryOn.setStyle(null);
				carryOn.setVisible(true);
				
			}		
			else if (instructionsCounter == 3) {
				instructionsCounter++;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
				pic.setGraphic(null);
				carryOn.setVisible(false);
				forward.setText("Start Experiment");
				
			}
			else if (instructionsCounter == 4) {
				root.getChildren().clear();
				root.getChildren().add(trialScene(mainStage));
			}
		});
		
		previous.setFont(Font.font("Verdana", smallFont));
		previous.setMinSize(width/6, elementHeights);
		previous.setVisible(false);
		previous.setOnAction(e->{
			if (instructionsCounter == 1) {
				instructionsCounter--;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
				leftBox.getChildren().clear();
			}
			if (instructionsCounter == 2) {
				instructionsCounter--;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
			}
			else if (instructionsCounter == 3) {
				instructionsCounter--;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
				carryOn.setVisible(false);
				pic.setGraphic(null);
				
			}
			else if (instructionsCounter == 4) {
				instructionsCounter--;
				instructionsLabel.setText(ex.getInstructions()[instructionsCounter]);
				forward.setText("Forward");
				pic.setGraphic(bulbOff);
				forward.setVisible(false);
				previous.setVisible(false);
				carryOn.setStyle(null);
				carryOn.setVisible(true);
			}
		});
		
		carryOn.setFont(Font.font("Verdana", normalFont));
		carryOn.setMinSize(width/6, elementHeights);
		carryOn.setVisible(false);
		carryOn.setOnAction(e->{
			pic.setGraphic(bulbOn);
			forward.setVisible(true);
			previous.setVisible(true);
			carryOn.setStyle("-fx-background-color: GREEN; -fx-text-fill: WHITE");
		});
		mainStage.heightProperty().addListener(e->{
			instructionsLabel.setFont(Font.font("Verdana", normalFont));
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			elementHeights = (int) mainStage.getHeight() / 20;
			forward.setMinSize( mainStage.getWidth() * .2, elementHeights);
			previous.setMinSize( mainStage.getWidth() * .2, elementHeights);
			carryOn.setMinSize( mainStage.getWidth() * .2, elementHeights);
			textContainer.setMaxWidth(width);
			lightContainer.setMaxHeight(height/2);
			buttonBox.setLayoutY(height - height/10);
			buttonBox.setMinWidth(width);
			leftBox.setMinWidth(width/4);
			rightBox.setMinWidth(width/4);
			centerBox.setMinWidth(width/4);
			lightContainer.setMaxHeight(height/8);
			lightContainer.setMaxWidth(width);
		});
		mainStage.widthProperty().addListener(e->{
			instructionsLabel.setFont(Font.font("Verdana", normalFont));
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			forward.setMinSize( mainStage.getWidth() * .2, elementHeights);
			previous.setMinSize( mainStage.getWidth() * .2, elementHeights);
			carryOn.setMinSize( mainStage.getWidth() * .2, elementHeights);
			textContainer.setMaxWidth(width);
			lightContainer.setMaxHeight(height/8);
			buttonBox.setLayoutY(height - height/10);
			buttonBox.setMinWidth(width);
			leftBox.setMinWidth(width/4);
			rightBox.setMinWidth(width/4);
			centerBox.setMinWidth(width/4);
			lightContainer.setMaxHeight(height/2);
			lightContainer.setMaxWidth(width);
		});

		return root;
	}
	
	public Pane trialScene (Stage mainStage) {
		
		Pane root1 = new Pane();
		borderpane = new BorderPane();
		Label bg = new Label();
		bg.setGraphic(bgImage);
		borderpane.setMaxSize(width, height);
		borderpane.setMinSize(width, height);
		root1.getChildren().addAll(bg, borderpane);
		
		centerContainer = new VBox(2);
		centerContainer.setAlignment(Pos.CENTER);
		centerContainer.setMaxWidth(width);
		centerLabel = new Label(""+ ex.getGameList().get(gameCounter).getTrialLength());
		centerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, normalFont*2));
		centerContainer.getChildren().add(centerLabel);
		
		trialCounterLabel = new Label ("Trial : " + (trialCounter + 1));
		trialCounterLabel.setFont(Font.font("Verdana", normalFont));
		borderpane.setTop(trialCounterLabel);
		
		bottomContainer = new HBox(2);
		bottomContainer.setAlignment(Pos.CENTER);
		bottomContainer.setPadding(new Insets (0.0, 0.0, height/20, 0.0));
		
		slider = new Slider(-100.0, 100.0, 0.0);
		slider.setMaxWidth(mainStage.getWidth() * .875);
		slider.setMinHeight(20.0);
		
		Label label1 = new Label("Total Prevent");
		Tooltip tooltip1 = new Tooltip();
		tooltip1.setText("Each button press prevents the light from coming on, the light comes on consistently without pressing the button.");
		label1.setTooltip(tooltip1);
		label1.setAlignment(Pos.BASELINE_LEFT);
		Label label2 = new Label("No Control");
		Tooltip tooltip2 = new Tooltip();
		tooltip2.setText("Pressing or not pressing the button has no impact on whether the light comes on or not.");
		label2.setTooltip(tooltip2);
		label2.setAlignment(Pos.BASELINE_CENTER);
		Label label3 = new Label("Total Control");
		Tooltip tooltip3 = new Tooltip();
		tooltip3.setText("The light comes on every time the button is pressed and never comes on if the button is not pressed.");
		label3.setTooltip(tooltip3);
		label3.setAlignment(Pos.BASELINE_RIGHT);
		label1.setFont(Font.font("Verdana", normalFont));
		label1.minWidth(width * .291);
		label1.setMaxWidth(width * .291);
		label2.setFont(Font.font("Verdana", normalFont));
		label2.minWidth(width * .291);
		label2.setMaxWidth(width * .291);
		label3.setFont(Font.font("Verdana", normalFont));
		label3.minWidth(width * .291);
		label3.setMaxWidth(width * .291);
		
		finalButton = new Button ("End Experiment");
		finalButton.setFont(Font.font("Verdana", normalFont));
		finalButton.setMinSize(width/6, elementHeights);
		finalButton.setOnAction(action->{
			ex.getGameList().get(gameCounter).setSliderValue(slider.getValue());
			ex.finalCalc();
			saveToFile();
		});
		nextGameButton = new Button("Next Game");
		nextGameButton.setFont(Font.font("Verdana", normalFont));
		nextGameButton.setMinSize(width/6, elementHeights);
		nextGameButton.setOnAction(action->{
			
			ex.getGameList().get(gameCounter).setSliderValue(slider.getValue());
			centerContainer.getChildren().clear();
			centerContainer.getChildren().add(centerLabel);
			centerLabel.setText("");
			
			gameCounter++;
			trialCounter = 0;
			sceneMode = 0;
			
			trialCounterLabel.setText("Trial : " + (trialCounter + 1));
			centerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
			centerLabel.setText("" + ex.getGameList().get(gameCounter).getTrialLength());
			
			bottomContainer.getChildren().clear();
			bottomContainer.getChildren().add(bottomButton);
			
			runTimer(ex.getGameList().get(gameCounter).getTrialLength(), mainStage);
			
		});
		sliderLabelContainer.setAlignment(Pos.CENTER);
		sliderLabelContainer.setSpacing(width * .291);
		sliderLabelContainer.setMaxWidth(width * .875);
		sliderLabelContainer.getChildren().addAll(label1, label2, label3);
		bottomButton = new Button("Click");
		bottomButton.setFont(Font.font("Verdana", normalFont));
		bottomButton.setMinSize(width/6, elementHeights);
		bottomButton.setOnAction(e->{
			ex.getGameList().get(gameCounter).getTrialList().get(trialCounter).setClicked();
			bottomButton.setStyle("-fx-background-color: GREEN; -fx-text-fill: WHITE");
		});
		bottomContainer.getChildren().add(bottomButton);
		
		borderpane.setCenter(centerContainer);
		borderpane.setBottom(bottomContainer);
		
		mainStage.heightProperty().addListener(e->{
			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			elementHeights = (int) mainStage.getHeight() / 20;
			borderpane.setMaxSize(width, height);
			borderpane.setMinSize(width, height);
			centerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, normalFont * 2));
			bottomButton.setFont(Font.font("Verdana", normalFont));
			bottomButton.setMinSize(width/6, elementHeights);
			sliderLabelContainer.setMaxWidth(width * .875);
			sliderLabelContainer.setSpacing(width * .291);
			slider.setMaxWidth(mainStage.getWidth() * .875);
			slider.setMinHeight(20.0);
			centerContainer.setMaxWidth(width);
			bottomContainer.setPadding(new Insets (0.0, 0.0, height/20, 0.0));
			label1.setFont(Font.font("Verdana", normalFont));
			label1.minWidth(width * .291);
			label1.setMaxWidth(width * .291);
			label2.setFont(Font.font("Verdana", normalFont));
			label2.minWidth(width * .291);
			label2.setMaxWidth(width * .291);
			label3.setFont(Font.font("Verdana", normalFont));
			label3.minWidth(width * .291);
			label3.setMaxWidth(width * .291);
			finalButton.setFont(Font.font("Verdana", normalFont));
			finalButton.setMinSize(width/6, elementHeights);
			nextGameButton.setFont(Font.font("Verdana", normalFont));
			nextGameButton.setMinSize(width/6, elementHeights);

		});
		mainStage.widthProperty().addListener(e->{

			height = (int) mainStage.getHeight();
			width = (int) mainStage.getWidth();
			normalFont = (int) (Math.sqrt(mainStage.getWidth()/10.0) + Math.sqrt(mainStage.getHeight()/10.0));
			borderpane.setMaxSize(width, height);
			borderpane.setMinSize(width, height);
			centerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, normalFont * 2));
			bottomButton.setFont(Font.font("Verdana", normalFont));
			bottomButton.setMinSize(width/6, elementHeights);
			slider.setMaxWidth(mainStage.getWidth() * .875);
			slider.setMinHeight(20.0);
			sliderLabelContainer.setMaxWidth(width * .875);
			sliderLabelContainer.setSpacing(width * .291);
			centerContainer.setMaxWidth(width);
			bottomContainer.setPadding(new Insets (0.0, 0.0, height/20, 0.0));
			label1.setFont(Font.font("Verdana", normalFont));
			label1.minWidth(width * .291);
			label1.setMaxWidth(width * .291);
			label2.setFont(Font.font("Verdana", normalFont));
			label2.minWidth(width * .291);
			label2.setMaxWidth(width * .291);
			label3.setFont(Font.font("Verdana", normalFont));
			label3.minWidth(width * .291);
			label3.setMaxWidth(width * .291);
			finalButton.setFont(Font.font("Verdana", normalFont));
			finalButton.setMinSize(width/6, elementHeights);
			nextGameButton.setFont(Font.font("Verdana", normalFont));
			nextGameButton.setMinSize(width/6, elementHeights);

		});

		
		runTimer(ex.getGameList().get(gameCounter).getTrialLength(), mainStage);
		
		return root1;
	}
	public void saveToFile () {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save Experiment");
			fc.setInitialFileName("opcon_" + ex.getID());
			fc.getExtensionFilters().addAll(
				    new FileChooser.ExtensionFilter(".txt", "*.txt")
				);
			File file = fc.showSaveDialog(null);
			if (file != null) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(ex.toString());
				bw.close();
			}
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runTimer (int duration, Stage mainStage) {
		temp = duration;
		Timeline timer = new Timeline();
		timer.setCycleCount(temp);
		timer.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						temp--;
						if (sceneMode == 0) {
							centerLabel.setText("" + temp);
							trialCounterLabel.setText("Trial : " + (trialCounter + 1));
						}

						if (sceneMode == 2) {
							centerLabel.setText("The next trial will start in " + temp + " seconds.");
						}
						
						if (temp <= 0) {
							timer.stop();	
						
							if (sceneMode ==0) {
								
								sceneMode = 1;
								bottomContainer.getChildren().clear();
								bottomButton.setStyle(null);
								centerLabel.setText("");
								
								if (ex.getGameList().get(gameCounter).getTrialList().get(trialCounter).getClicked() == 0) {
									if (rand.nextInt(100)< pc.probNoResponse(ex.getGameList().get(gameCounter).getProbCode())) {
										ex.getGameList().get(gameCounter).getTrialList().get(trialCounter).setLight();
									}
								}
								if (ex.getGameList().get(gameCounter).getTrialList().get(trialCounter).getClicked() == 1) {
									if (rand.nextInt(100) < pc.probResponse(ex.getGameList().get(gameCounter).getProbCode())) {
										ex.getGameList().get(gameCounter).getTrialList().get(trialCounter).setLight();
									}
								}
								if (ex.getGameList().get(gameCounter).getTrialList().get(trialCounter).getLight() == 1) {
									centerLabel.setGraphic(bulbOn);
								}
								else {
									centerLabel.setGraphic(bulbOff);
								}
								
								
								runTimer(resultDuration, mainStage);
								
							}
							else if (sceneMode == 1) {
								
								sceneMode = 2;
								centerLabel.setGraphic(null);
								if (trialCounter < ex.getGameList().get(gameCounter).getTrialList().size() - 1 ) {
									
									centerLabel.setText("The next trial will start in " + ex.getGameList().get(gameCounter).getRestLength() + " seconds.");
									centerLabel.setFont(Font.font("Verdana", normalFont));
									
									
									runTimer(ex.getGameList().get(gameCounter).getRestLength(), mainStage);
								}
								else if (trialCounter >= ex.getGameList().get(gameCounter).getTrialList().size() - 1 && gameCounter < ex.getGameList().size() - 1){
									
									trialCounterLabel.setText("");
									centerLabel.setText("");
									centerContainer.getChildren().addAll(sliderLabelContainer, slider);
									trialCounter = 0;
								
									bottomContainer.getChildren().add(nextGameButton);
								}
								else {
									trialCounterLabel.setText("");
									centerLabel.setText("");
									centerContainer.getChildren().addAll(sliderLabelContainer, slider);
									
									bottomContainer.getChildren().clear();
									

									bottomContainer.getChildren().add(finalButton);
								}
							}
							else if (sceneMode == 2) {
								
								sceneMode = 0;
								centerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
								centerLabel.setText("" + ex.getGameList().get(gameCounter).getTrialLength());
								bottomContainer.getChildren().clear();
								bottomContainer.getChildren().add(bottomButton);
								trialCounter++;
								runTimer(ex.getGameList().get(gameCounter).getTrialLength(), mainStage);
							}
						}

					}
			}));
		timer.playFromStart();
	}

	public static void main(String[] args) {
		
		Application.launch(args);

	}
}