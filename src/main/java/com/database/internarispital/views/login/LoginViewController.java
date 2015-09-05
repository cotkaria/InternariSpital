package com.database.internarispital.views.login;

import java.net.URL;
import java.util.ResourceBundle;

import com.database.internarispital.exceptions.MissingInputException;
import com.database.internarispital.util.DialogHelper;
import com.database.internarispital.views.common.CommonHelper;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable
{
	@FXML
    private TextField userNameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button loginButon;
	
    private ILoginViewModel mViewModel;
    
    public void setViewModel(ILoginViewModel viewModel)
    {
    	mViewModel = viewModel;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		loginButon.setOnAction(event -> onLogin());
	}
	
	private void onLogin()
	{
		try
		{
			String userName = CommonHelper.getInputText(userNameTF, "Please provide an user name.");
			String password = CommonHelper.getInputText(passwordTF, "Please provide a password.");
			mViewModel.login(userName, password);
		}
		catch(MissingInputException ex)
		{
			DialogHelper.showErrorPopup(ex.getMessage());
		}
	}
}
