package com.database.internarispital;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.database.internarispital.entities.accounts.AccountTypes;

public class NavigationManager
{
	public enum Views
	{
		MAIN_VIEW,
		PATIENTS_BROWSE_VIEW,
		PATIENTS_HOSPITALIZE_VIEW,
		PATIENTS_EDIT_VIEW,
		DOCTORS_BROWSE_VIEW,
		DOCTORS_HOSPITALIZE_VIEW,
		DOCTORS_EDIT_VIEW,
		FACILITIES_EDIT_VIEW
	}
	
	public static final Map<Views, List<AccountTypes>> VIEW_LEVELS;
	static
	{
		VIEW_LEVELS = new HashMap<Views, List<AccountTypes>>();
		VIEW_LEVELS.put(Views.MAIN_VIEW,  					
				Arrays.asList(AccountTypes.Visitor, AccountTypes.Patient, AccountTypes.Doctor, AccountTypes.Admin));
		VIEW_LEVELS.put(Views.PATIENTS_BROWSE_VIEW, 		
				Arrays.asList(AccountTypes.Visitor, AccountTypes.Patient, AccountTypes.Doctor, AccountTypes.Admin));
		VIEW_LEVELS.put(Views.PATIENTS_HOSPITALIZE_VIEW, 	Arrays.asList(AccountTypes.Doctor));
		VIEW_LEVELS.put(Views.PATIENTS_EDIT_VIEW, 			Arrays.asList(AccountTypes.Admin));
		VIEW_LEVELS.put(Views.DOCTORS_BROWSE_VIEW, 			Arrays.asList(AccountTypes.Doctor, AccountTypes.Admin));
		VIEW_LEVELS.put(Views.DOCTORS_HOSPITALIZE_VIEW, 	Arrays.asList(AccountTypes.Doctor));
		VIEW_LEVELS.put(Views.DOCTORS_EDIT_VIEW, 			Arrays.asList(AccountTypes.Admin));
		VIEW_LEVELS.put(Views.FACILITIES_EDIT_VIEW, 		Arrays.asList(AccountTypes.Admin));
	}
}
