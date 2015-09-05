package com.database.internarispital.views.facilities;

import com.database.internarispital.entities.facilities.Bed;
import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.entities.facilities.Ward;

public interface IEditFacilitiesViewModel
{
	void onSectionSelected(Section section);
	void onWardSelected(Ward ward);
	
	void addSection(String name);
	void addWard(Section section, int wardNumber);
	void addBed(Ward ward, int bedNumber);
	
	void removeSection(Section section);
	void removeWard(Ward ward);
	void removeBed(Bed bed);
}
