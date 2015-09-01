package com.database.internarispital.util;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InputHelper 
{
	public static boolean isDoubleClick(MouseEvent event)
	{
		final boolean isDoubleClick = event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY);
		return isDoubleClick;
	}
}
