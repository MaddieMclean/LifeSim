package com.prac3.ABug;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GridsCanvas extends Application {	// Responsible for the GUI and the graphical representation of the simulation
	
	private GraphicsContext gc;
	public AWorld world;
	private Timeline timeline = new Timeline();
	public String filePath = "config.lifesim";
	private Configuration config;
	
	private boolean showMap = true;

	public static int width = 450, height = 450;	// Dimensions of graphics canvas
	public static int rows = 15, cols = 15;	// Number of row and columns the area is divided into
	private int rowWid = width / (cols);	// Calculates the width of each row for the purposes of drawing the graphically displayed grid
	private int rowHt = height / (rows);	// Calculates the height of each row for the purposes of drawing the graphically displayed grid
	private int aniCount = 0, plantCount = 0, blockCount = 0;	// Counters for Unique Identities (UID)
	
	
	
	public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
    	
    	world = new AWorld(rows, cols);	// Creates default starting world before configurations are applied
    	
        primaryStage.setTitle("Life Sim");
        
        BorderPane border = new BorderPane();
        VBox menu = menuBar();
                    
        Canvas canvas = new Canvas(width, height); 
        gc = canvas.getGraphicsContext2D();
        
        drawGrid();	// Creates graphical grid
        
        config = new Configuration(filePath);	// Loads default filepath for saving
        
        border.setTop(menu);	// Adds MenuBar to the main window
        border.setCenter(canvas);	// Adds the canvas to the main window 
	        
        primaryStage.setScene
        (
            new Scene
            (
                new Group
                (
                    border
        		)
    		)
		);
                
        timeline();	// Prepares animation
        
        primaryStage.show(); // Makes main window visible to the user
        
    }

	private void drawGrid()	// Divides the window into even grid squares and draws the grid onto the canvas
	{
		gc.setFill(Color.CORNSILK);
		gc.fillRect(0, 0, width, height);
		
	    for (int i = 0; i < rows; i++)	// Draws the grid on screen as a series of rectangles going across then down the screen
	    {
	    	for (int j = 0; j < cols; j++)
	    	{
	        	gc.strokeRect(j * rowWid, i * rowHt, rowWid, rowHt);
	    	}
	    }   
    
	}
	
	private void drawObj(int x, int y, Color colour)	// Function used to colour in the shapes representing the various objects in the sim
	{
		gc.setFill(colour);
		gc.fillOval((x * rowWid) + rowWid/4, (y * rowHt) + rowHt/4, rowWid/2, rowHt/2);
	}
	
	private void timeline()	// Main game loop, runs an infinite animation that repeatedly redraws the grid and updates the objects every 0.5 seconds
	{
		KeyFrame k = new KeyFrame(
				Duration.seconds(0.5),
				new EventHandler<ActionEvent>()	// Event handler that contains the main game loop
				{
					public void handle(ActionEvent e)
					{									
						world.clearGrid();	// Wipes previous background grid				
						world.drawGrid();	// Creates new background grid with updates positi
						drawGrid();	// Draws rectangles for visible grid
						
						for (int i = 0; i < AWorld.blockList.size(); i++) // Loops through obstacles and draws them on the map
						{							
							int x = AWorld.blockList.get(i).x;						
							int y = AWorld.blockList.get(i).y;
							Color colour = AWorld.blockList.get(i).getColour();	
							if(showMap)	// Check for toggle as to whether or not to show map animations
							{
								drawObj(x, y, colour);
							}
						}
						
						for(int i = 0; i < AWorld.lifeList.size(); i++)	// Loops through lifeforms and draws living ones onthe map
						{
							if(!AWorld.lifeList.get(i).dead)	// Checks if currently dead
							{
								int x = AWorld.lifeList.get(i).x;						
								int y = AWorld.lifeList.get(i).y;
								Color colour = AWorld.lifeList.get(i).getColour();								
								if(showMap)	// Check for toggle as to whether or not to show map animations
								{
									drawObj(x, y, colour);
								}
							}
						}
						
						
					}        			
				}
			);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(k);	
	}
	
	private VBox menuBar()	// Main menu container, holds dropdown menus
    {
    	VBox menu = new VBox();
    	
    	MenuBar mainMenu = new MenuBar();
    	
        Menu file = file();        
        Menu view = view();        
        Menu edit = edit();        
        Menu sim = sim();        
        Menu help = help();
        
        mainMenu.getMenus().addAll(file, view, edit, sim, help);
        
        menu.getChildren().add(mainMenu);
        
		return menu;    	
    }
	
	private Menu file()	//File menu contains all to load, save or create new configurations
	{
		JFileChooser fc = new JFileChooser();
		Menu file = new Menu("File");
        
        MenuItem newConfig = new MenuItem("New Configuration");
        MenuItem openConfig = new MenuItem("Open Configuration File");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save As");
        MenuItem exit = new MenuItem("Exit");
        
        newConfig.setOnAction(new EventHandler<ActionEvent>() {	// Refreshes world object back to defaults 
            @Override public void handle(ActionEvent e) {
            	world = new AWorld(rows, cols);
            }
        });
        openConfig.setOnAction(new EventHandler<ActionEvent>() {	// Opens file browser to load previously saved configs
            @Override public void handle(ActionEvent e) {
            	int checkVal = fc.showOpenDialog(fc);            	
            	if(checkVal == JFileChooser.APPROVE_OPTION)
            	{
            		File loadPath = fc.getSelectedFile();
            		config = new Configuration(loadPath.getAbsolutePath());	// Loads configs based on filepath
                	world = new AWorld(rows, cols, config.properties);	// Recreates world based on information in the save file
            	}
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {	// Saves to current filepath
            @Override public void handle(ActionEvent e) {
                config.save(world, filePath);
            }
        });
        saveAs.setOnAction(new EventHandler<ActionEvent>() {	// Opens file browser to allow saving to a specific filepath
            @Override public void handle(ActionEvent e) {
            	int checkVal = fc.showSaveDialog(fc);            	
            	if(checkVal == JFileChooser.APPROVE_OPTION)
            	{
            		File savePath = fc.getSelectedFile();
            		filePath = savePath.getAbsolutePath();
                	config.save(world, filePath);
            	}
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {	// Exits program
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });
        
        file.getItems().addAll(newConfig, openConfig, save, saveAs, exit);
        
        return file;
	}
	
	private Menu view()	// View menu calls PopUp class to open a new window to display and edit information about map configuration or an overview of lifeforms
	{
		Menu view = new Menu("View");
        
        MenuItem dispConfig = new MenuItem("Display Config");
        MenuItem editMap = new MenuItem("Edit Map");
        MenuItem lifeInfo = new MenuItem("Life Info");
        MenuItem mapInfo = new MenuItem("Map Info");
       
        view.getItems().addAll(dispConfig, editMap, lifeInfo, mapInfo);
        
        return view;
	}
	
	private Menu edit()	// Edit menu calls PopUp class to open a new window to allow editing of lifeforms. Contains new object menu where lifeforms can be added to the simulation
	{		
		Menu edit = new Menu("Edit");
        MenuItem editObjects = new MenuItem("Edit/ Delete Objects");
        Menu newObject = newObject();
        
        edit.getItems().addAll(editObjects, newObject);
        
        return edit;
	}
	
	private Menu sim()	// Contains all controls for starting, stopping and resetting the simulation
	{
		Menu sim = new Menu("Simulation");
        MenuItem play = new MenuItem("Play");
        MenuItem pause = new MenuItem("Pause");
        MenuItem restart = new MenuItem("Restart Sim");
        MenuItem reset = new MenuItem("Reset Sim");
        MenuItem showSim = new MenuItem("Toggle Sim");
        
        play.setOnAction(new EventHandler<ActionEvent>() {	// Starts sim
            @Override public void handle(ActionEvent e) {
            	timeline.play();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {	// Pauses sim
            @Override public void handle(ActionEvent e) {
            	timeline.pause();
            }
        });
        restart.setOnAction(new EventHandler<ActionEvent>() {	// Restarts sim from the beginning
            @Override public void handle(ActionEvent e) {
            	timeline.pause();
            	world.reset();	// Resets all objects in the world to their starting positions and state
            	timeline.play();
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {	// Stops the sim and resets without starting it
            @Override public void handle(ActionEvent e) {
            	timeline.stop();
            	world.reset();
            }
        });
        showSim.setOnAction(new EventHandler<ActionEvent>() {	// Toggles whether or not each turn of the sim is displayed
            @Override public void handle(ActionEvent e) {
            	timeline.stop();
            	if(showMap)
				{
					showMap = false;
				}
            	else if(!showMap)
				{
					showMap = true;
				}
            	timeline.play();
            }
        });
        
        sim.getItems().addAll(play, pause, restart, reset, showSim);
        
        return sim;
	}
	
	private Menu help()	// Displays text in a separate PopUp window relating to the author and the project
	{
		List<String> app = new ArrayList<String>();
		app.add("This application was created for coursework and contains a (mostly) working life sim");
		List<String> auth = new ArrayList<String>();
		auth.add("This app was createdby Mathew Mclean");
		
		
		Menu help = new Menu("Help");
        MenuItem appInfo = new MenuItem("Application Info");
        MenuItem authInfo = new MenuItem("Author Info");
        
        appInfo.setOnAction(new EventHandler<ActionEvent>() {	// Calls an instance of the PopUp class to display information about the app
            @Override public void handle(ActionEvent e) {
            	new PopUp(app);
            }
        });
        
        authInfo.setOnAction(new EventHandler<ActionEvent>() {	// Calls an instance of the PopUp class to display information about the author
            @Override public void handle(ActionEvent e) {
            	new PopUp(auth);
            }
        });
        
        help.getItems().addAll(appInfo, authInfo);
        
        return help;
	}
	
	private Menu newObject()	// Lists as a dropdown submenu all the possible variations on lifeforms that can be added to the sim
	{		
		Menu newObject = new Menu ("New Object");		
        MenuItem addBug = new MenuItem("Add Bug");
        MenuItem addGiraffe = new MenuItem("Add Giraffe");
        MenuItem addBush = new MenuItem("Add Bush");
        MenuItem addBushP = new MenuItem("Add Bush(P)");
        MenuItem addTree = new MenuItem("Add Tree");
        MenuItem addBlock = new MenuItem("Add Block");
        
        addBug.setOnAction(new EventHandler<ActionEvent>() {	// Creates a new bug
            @Override public void handle(ActionEvent e) {
            	aniCount++;
            	world.addObject(aniCount, "Bug");
            }
        });
        
        addGiraffe.setOnAction(new EventHandler<ActionEvent>() {	// Creates a new Giraffe
            @Override public void handle(ActionEvent e) {
            	aniCount++;
            	world.addObject(aniCount, "Giraffe");
            }
        });
        
        addBush.setOnAction(new EventHandler<ActionEvent>() {	// Creates a new bush
            @Override public void handle(ActionEvent e) {
            	plantCount++;
            	world.addObject(plantCount, "Bush");
            }
        });
        
        addBushP.setOnAction(new EventHandler<ActionEvent>() {	// Creates a new poisoned bush
            @Override public void handle(ActionEvent e) {
            	plantCount++;
            	world.addObject(plantCount, "BushP");
            }
        });
        
        addTree.setOnAction(new EventHandler<ActionEvent>() {	// Creates a new tree
            @Override public void handle(ActionEvent e) {
            	plantCount++;
            	world.addObject(plantCount, "Tree");
            }
        });
        
        addBlock.setOnAction(new EventHandler<ActionEvent>() {	// Creates a new block
            @Override public void handle(ActionEvent e) {
            	blockCount++;
            	world.addObject(blockCount, "Block");
            }
        });
        
        newObject.getItems().addAll(addBug, addGiraffe, addBush, addBushP, addTree, addBlock);
        
		return newObject;
		
	}

}

