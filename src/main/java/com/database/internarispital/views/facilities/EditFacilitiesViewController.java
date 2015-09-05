package com.database.internarispital.views.facilities;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;
import com.database.internarispital.exceptions.InvalidInputException;
import com.database.internarispital.exceptions.MissingSelectionException;
import com.database.internarispital.util.DialogHelper;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

public class EditFacilitiesViewController implements Initializable
{
    @FXML
    private Button addSectionButton;

    @FXML
    private Button addWardButton;
    
    @FXML
    private Button addBedButton;
    
    @FXML
    private Button removeSectionButton;

    @FXML
    private Button removeWardButton;

    @FXML
    private Button removeBedButton;
    
    @FXML
    private ComboBox<Section> sectionsCB;

    @FXML
    private ComboBox<Ward> wardsCB;

    @FXML
    private ComboBox<Bed> bedsCB;

    @FXML
    private TextField sectionNameTF;
    
    @FXML
    private TextField wardNumberTF;
    
    @FXML
    private TextField bedNumberTF;
    
    private IEditFacilitiesViewModel mViewModel;
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		addSectionButton.setOnAction(event -> addSection());
		addWardButton.setOnAction(event -> addWard());
		addBedButton.setOnAction(event -> addBed());
		
		removeSectionButton.setOnAction(event -> removeSection());
		removeWardButton.setOnAction(event -> removeWard());
		removeBedButton.setOnAction(event -> removeBed());
		
		sectionsCB.setOnAction(event -> onSectionSelected());
		wardsCB.setOnAction(event -> onWardSelected());
	}
	
	private void addSection()
	{
		String name = sectionNameTF.getText();
		if(name.isEmpty() == false)
		{
			mViewModel.addSection(name);
			sectionNameTF.clear();
		}
		else
		{
			DialogHelper.showErrorPopup("Please insert a valid section name");
		}
	}
	private void addWard()
	{
		try
		{
			int wardNumber = getTextAsInt(wardNumberTF);
			wardNumberTF.clear();
			mViewModel.addWard(getSelectedItem(sectionsCB), wardNumber);
		}
		catch (InvalidInputException e)
		{
			DialogHelper.showErrorPopup("Please insert a valid ward number. Digits only.");
		}
		catch (MissingSelectionException e)
		{
			//nothing to do
		}
	}
	
	private void addBed()
	{
		try
		{
			int bedNumber = getTextAsInt(bedNumberTF);
			bedNumberTF.clear();
			mViewModel.addBed(getSelectedItem(wardsCB), bedNumber);
		}
		catch (InvalidInputException e)
		{
			DialogHelper.showErrorPopup("Please insert a valid bed number. Digits only.");
		}
		catch (MissingSelectionException e)
		{
			//nothing to do
		}
	}
	
	private void removeSection()
	{
		try
		{
			mViewModel.removeSection(getSelectedItem(sectionsCB));
		}
		catch(MissingSelectionException ex)
		{
			DialogHelper.showErrorPopup("Please select a section to remove.");
		}
	}
	private void removeWard()
	{
		try
		{
			mViewModel.removeWard(getSelectedItem(wardsCB));
		}
		catch(MissingSelectionException ex)
		{
			DialogHelper.showErrorPopup("Please select a ward to remove.");
		}
	}
	private void removeBed()
	{
		try
		{
			mViewModel.removeBed(getSelectedItem(bedsCB));
		}
		catch(MissingSelectionException ex)
		{
			DialogHelper.showErrorPopup("Please select a bed to remove.");
		}
	}

	public void setViewModel(IEditFacilitiesViewModel viewModel)
	{
		mViewModel = viewModel;
	}

	public void setSections(ObservableList<Section> sections)
    {
		clearAndSetItems(sectionsCB, sections);
		selectFirstItem(sectionsCB);
		onSectionSelected();
    }
	
	public void setWards(ObservableList<Ward> wards)
    {
		clearAndSetItems(wardsCB, wards);
		selectFirstItem(wardsCB);
		onWardSelected();
    }
	
    public void setBeds(ObservableList<Bed> beds)
    {
		clearAndSetItems(bedsCB, beds);
		selectFirstItem(bedsCB);
    }
    
	private void onSectionSelected()
	{
		SingleSelectionModel<Section> selectionModel = sectionsCB.getSelectionModel();
		if(selectionModel.isEmpty() == false)
		{
			mViewModel.onSectionSelected(selectionModel.getSelectedItem());
		}
		else
		{
			sectionsCB.getItems().clear();
		}
	}
	
	private void onWardSelected()
	{
		SingleSelectionModel<Ward> selectionModel = wardsCB.getSelectionModel();
		if(selectionModel.isEmpty() == false)
		{
			mViewModel.onWardSelected(selectionModel.getSelectedItem());
		}
		else
		{
			bedsCB.getItems().clear();
		}
	}
	
	public int getSelectedWardId()
	{
		SelectionModel<Ward> selectionModel = wardsCB.getSelectionModel();
		if(!selectionModel.isEmpty())
		{
			return selectionModel.getSelectedItem().wardIdProperty().getValue();
		}
		return -1;
	}
	
	private <T> T getSelectedItem(ComboBox<T> cb) throws MissingSelectionException
	{
		T selectedItem = null;
		SingleSelectionModel<T> selectionModel = cb.getSelectionModel();
		if(selectionModel.isEmpty() == false)
		{
			selectedItem = selectionModel.getSelectedItem();
		}
		else
		{
			throw new MissingSelectionException("No selection is made in " + cb.toString());
		}
		return selectedItem;
	}
	
	private int getTextAsInt(TextField tf) throws InvalidInputException
	{
		int intValue = 0;
		try
		{
			intValue = Integer.parseInt(tf.getText());
		}
		catch(NumberFormatException ex)
		{
			throw new InvalidInputException(ex.getMessage());
		}
		return intValue;
	}
	
	private <T> void clearAndSetItems(ComboBox<T> cb, ObservableList<T> items)
	{
		cb.getItems().clear();
		cb.setItems(items);
	}
	
	private <T> void selectFirstItem(ComboBox<T> cb)
	{
		ObservableList<T> items = cb.getItems();
		if(items.isEmpty() == false)
		{
			cb.getSelectionModel().select(0);
		}
	}
}
