package com.database.internarispital.views.facilities;

import com.database.internarispital.entities.Bed;
import com.database.internarispital.entities.Section;
import com.database.internarispital.entities.Ward;

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
