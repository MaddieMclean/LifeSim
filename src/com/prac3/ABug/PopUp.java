package com.prac3.ABug;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopUp extends Application	// PopUp class intended to support text popups and editing of the map and objects
{
	private BorderPane border = new BorderPane();
	
	PopUp(List<String> text)	
	// constructor for text based windows and displaying information, the list format was to allow for 
	// all animals to be displayed clearly and conveniently without having to concatenate strings
	{
		infoDump(text);	// adds text to borderpane
		Stage newStage = new Stage();
		start(newStage);	// starts new window instance to display text
	}
	
	PopUp(AWorld world, String info)	// constructor for editing, takes a copy of the world object to make changes to then submit to reload the simulation
	{
		switch (info)
		{
		case "objects":
			editObjects(world);
			break;
		case "map":
			editMap(world);
			break;

		default:
			break;
		}
		
		Stage newStage = new Stage();
		start(newStage); // starts new window instance to display edit options
	}

	public void start(Stage newStage) // crates and makes visible popup window to the user
	{
		newStage.setScene
        (
            new Scene
            (
                new Group
                (
                    border
        		)
    		)
		);
		
		newStage.show();
	}
	
	private void infoDump(List<String> text)	// takes list of text and iterates through to display in a VBox - each line is arranged vertically
	{
		VBox textBox = new VBox();
		
		for (int i = 0; i < text.size(); i++)
		{
			Text line = new Text(text.get(i));
			textBox.getChildren().add(line);
		}
		border.getChildren().add(textBox);
	}
	
	private void editObjects(AWorld world)	// takes world information and allows you to edit or delete objects
	{
		VBox textBox = new VBox();
		Text line = new Text("Under Construction");
		textBox.getChildren().add(line);
		border.getChildren().addAll(textBox);
	}
	
	private void editMap(AWorld world) // creates options menu for changing the dimensions of the map
	{
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		Text warning = new Text("Warning: Resets Sim");
		
		Label rLabel = new Label("Rows:");
		TextField rows = new TextField(Integer.toString(world.rows));
		
		Label cLabel = new Label("Columns:");
		TextField cols = new TextField(Integer.toString(world.cols));
		
		Button submit = new Button("Submit");
		
		grid.getChildren().addAll(warning, rLabel, rows, cLabel, cols, submit);
		
		GridPane.setConstraints(rLabel, 0, 0);
		GridPane.setConstraints(rows, 1, 0);
		GridPane.setConstraints(cLabel, 0, 1);
		GridPane.setConstraints(cols, 1, 1);
		GridPane.setConstraints(warning, 0, 2);
		GridPane.setConstraints(submit, 0, 3);
		
		border.getChildren().addAll(grid);		
	}
	
}
