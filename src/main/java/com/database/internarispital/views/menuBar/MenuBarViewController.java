package com.database.internarispital.views.menuBar;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.database.internarispital.NavigationManager;
import com.database.internarispital.NavigationManager.Views;
import com.database.internarispital.entities.accounts.AccountTypes;
import com.database.internarispital.views.ViewManager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarViewController implements Initializable
{
	@FXML
	private MenuBar menuBar;
	    
	@FXML
	private MenuItem mainLobby;

	@FXML
	private Menu patientsLobby;

	@FXML
	private MenuItem browsePatients;
	    
	@FXML
	private MenuItem hospitalizePatients;

	@FXML
	private Menu doctorsLobby;

	@FXML
	private MenuItem browseDoctors;
	    
	@FXML
	private MenuItem diagnosticationView;
	    
	@FXML
	private Menu adminLobby;
	    
	@FXML
	private MenuItem editsPatientsView;
	    
	@FXML
	private MenuItem editsDoctorsView;
	    
	@FXML
	private MenuItem editsFacilitiesView;

	@FXML
	private Menu loginNameMenu;
	
	@FXML
	private MenuItem logOutButton;
    
    private IMenuBarViewModel mViewModel;
    private AccountTypes mAccountType = AccountTypes.Patient;
    
    private Map<NavigationManager.Views, MenuItem> mMenuItemsToViewsMap;
    private List<Menu> mMenus;
    

	public void setViewModel(IMenuBarViewModel viewModel)
	{
		mViewModel = viewModel;
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		mainLobby.setOnAction(event -> ViewManager.showMainView());
		
		browsePatients.setOnAction(event -> ViewManager.showBrowsePatientsView());
		hospitalizePatients.setOnAction(event -> ViewManager.showHospitalizePatientsView());
		
		browseDoctors.setOnAction(event -> ViewManager.showBrowseDoctorsView());
		diagnosticationView.setOnAction(event -> ViewManager.showConsultationView());
		
		editsPatientsView.setOnAction(event -> ViewManager.showEditPatientsView());
		editsDoctorsView.setOnAction(event -> ViewManager.showEditDoctorsView());
		editsFacilitiesView.setOnAction(event -> ViewManager.showEditFacilitiesView());
		
		logOutButton.setOnAction(event -> mViewModel.logout());
		
		initMenus();
		initMenuItemsToViewsMapping();
		onAccountTypeChanged();
	}
	
	private void initMenus()
	{
		mMenus = Arrays.asList(patientsLobby, doctorsLobby, adminLobby);
	}
	
	private void initMenuItemsToViewsMapping()
	{
		mMenuItemsToViewsMap = new HashMap<NavigationManager.Views, MenuItem>();
		mMenuItemsToViewsMap.put(Views.MAIN_VIEW, 					mainLobby);
		mMenuItemsToViewsMap.put(Views.PATIENTS_BROWSE_VIEW, 		browsePatients);
		mMenuItemsToViewsMap.put(Views.PATIENTS_HOSPITALIZE_VIEW, 	hospitalizePatients);
		mMenuItemsToViewsMap.put(Views.PATIENTS_EDIT_VIEW, 			editsPatientsView);
		mMenuItemsToViewsMap.put(Views.DOCTORS_BROWSE_VIEW, 			browseDoctors);
		mMenuItemsToViewsMap.put(Views.DOCTORS_HOSPITALIZE_VIEW, 	diagnosticationView);
		mMenuItemsToViewsMap.put(Views.DOCTORS_EDIT_VIEW, 			editsDoctorsView);
		mMenuItemsToViewsMap.put(Views.FACILITIES_EDIT_VIEW, 		editsFacilitiesView);
	}
	
	public void setAccountType(AccountTypes accountType)
	{
		if((accountType != null) && (mAccountType != accountType))
		{
			mAccountType = accountType;
			onAccountTypeChanged();
		}
	}
	
	public MenuBar getMenuBar()
	{
		return menuBar;
	}
	
	public void showNavigationBar(boolean show)
	{
		menuBar.setVisible(show);
	}
	
	public void setLoginName(String loginName)
	{
		loginNameMenu.setText("Logged in as: " + loginName);
	}
	
	private void onAccountTypeChanged()
	{
		updateMenuItemsVisiblity();
		updateMenusVisiblity();
	}
	private void updateMenuItemsVisiblity()
	{
		for(Map.Entry<NavigationManager.Views, MenuItem> entry : mMenuItemsToViewsMap.entrySet())
		{
			NavigationManager.Views view = entry.getKey();
			boolean isEnabled = NavigationManager.VIEW_LEVELS.get(view).contains(mAccountType);
			entry.getValue().setVisible(isEnabled);
		}
	}
	private void updateMenusVisiblity()
	{
		for(Menu menu: mMenus)
		{
			ObservableList<MenuItem> menuItems = menu.getItems();
			boolean isAnyVisible = false;
			for(MenuItem menuItem: menuItems)
			{
				if(menuItem.isVisible())
				{
					isAnyVisible = true;
					break;
				}
			}
			menu.setVisible(isAnyVisible);
		}
	}
}
