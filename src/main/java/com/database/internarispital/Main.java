package com.database.internarispital;

import java.sql.SQLException;

import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.ViewManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application
{
	public static final String VERSION = "1.0.0";
	public static final String APP_NAME = "Patient Management";
	private Stage mStage;
	private DataBase mDataBase;
	
	public static void main( String[] args )
    {
		Main.launch(args);
    }
	
	@Override
	public void start(Stage stage) throws Exception
	{
		mStage = stage;
		mStage.setResizable(false);
		initHelpers();
		
		if(initDatabase())
		{
			configureManagers();
			ViewManager.showLoginView();
		}
		else
		{
			//DialogHelper.showErrorPopup("Could not connect to the database. See the log for more details.");
			Platform.exit();//Replace this with a pop-up error message
		}
	}
	
	@Override
	public void stop()
	{	
		//TODO
	}
	
	private boolean initDatabase()
	{
		boolean success = false;
		try
		{
			mDataBase = new DataBase();
			success = true;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return success;
	}
	
	private void initHelpers()
	{
		DialogHelper.setWindow(mStage);
	}
	
	private void configureManagers()
	{
		ViewManager.init(mStage, mDataBase);
	}
}
