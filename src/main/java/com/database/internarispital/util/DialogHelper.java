package com.database.internarispital.util;

import org.controlsfx.dialog.Dialogs;
import javafx.stage.Window;

public class DialogHelper
{
	private static Window mWindow = null;
	
	public static void setWindow(Window window)
	{
		mWindow = window;
	}
	
	public static void showErrorPopup(String message)
	{
		Dialogs.create().owner(mWindow).title("Error").message(message).showError();
	}
}
