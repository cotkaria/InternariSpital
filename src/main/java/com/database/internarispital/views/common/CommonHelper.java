package com.database.internarispital.views.common;

import java.text.DecimalFormat;

import com.database.internarispital.exceptions.MissingInputException;
import com.database.internarispital.exceptions.MissingSelectionException;

import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

public class CommonHelper
{
    public static String getInputText(TextField tf, String errorMessage) throws MissingInputException
    {
    	String text = tf.getText();
    	if(text == null || text.isEmpty())
	    {
			throw new MissingInputException(errorMessage);
	    }
    	return text;
    }
    
    public static String getComboBoxSelection(ComboBox<String> cb, String errorMessage) throws MissingSelectionException
    {
    	String selection = "";
    	SingleSelectionModel<String> model = cb.getSelectionModel();
    	if(model.isEmpty() == false)
    	{
    		selection = model.getSelectedItem();
    	}
    	if(selection == null || selection.isEmpty())
	    {
			throw new MissingSelectionException(errorMessage);
	    }
    	return selection;
    }
    
	public static String getTextOrException(String text, String errorMessage) throws MissingInputException
	{
		if(text.isEmpty())
		{
			throw new MissingInputException(errorMessage);
		}
		return text;
	}
	
	public static double convertToPercentage(double value)
	{
		return (roundToOneDecimal(value) * 100);
	}
	
	public static double roundToOneDecimal(double value)
	{
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(value));
	}
}
