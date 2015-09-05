package com.database.internarispital.views.facilities;

import com.database.internarispital.DataBase;
import com.database.internarispital.entities.facilities.Bed;
import com.database.internarispital.entities.facilities.Section;
import com.database.internarispital.entities.facilities.Ward;

public class EditFacilitiesViewModel implements IEditFacilitiesViewModel
{
	private EditFacilitiesViewController mViewController;
	DataBase mDataBase;
	
	public EditFacilitiesViewModel(EditFacilitiesViewController viewController, DataBase dataBase)
	{
		mViewController = viewController;
		mDataBase = dataBase;
		configureViewController();
	}
	
	private void configureViewController()
	{
		mViewController.setViewModel(this);
		refreshSections();
	}
	
	public void onSectionSelected(Section section)
	{
		refreshWards(section.sectionIdProperty().getValue());
	}
	
	public void onWardSelected(Ward ward)
	{
		refreshBeds(ward.wardIdProperty().getValue());
	}

	@Override
	public void addSection(String name)
	{
		mDataBase.insertSection(name);
		refreshSections();
	}

	@Override
	public void addWard(Section section, int wardNumber)
	{
		mDataBase.insertWard(section, wardNumber);
		refreshWards(section.sectionIdProperty().getValue());
	}
	
	@Override
	public void addBed(Ward ward, int bedNumber)
	{
		mDataBase.insertBed(ward, bedNumber);
		refreshBeds(ward.wardIdProperty().getValue());
	}

	@Override
	public void removeSection(Section section)
	{
		mDataBase.removeSection(section);
		refreshSections();
	}

	@Override
	public void removeWard(Ward ward)
	{
		mDataBase.removeWard(ward);
		refreshWards(ward.sectionIdProperty().getValue());
	}

	@Override
	public void removeBed(Bed bed)
	{
		mDataBase.removeBed(bed);
		refreshBeds(bed.wardIdProperty().getValue());
	}
	
	private void refreshSections()
	{
		mViewController.setSections(mDataBase.getSections());
	}
	
	private void refreshWards(int sectionId)
	{
		mViewController.setWards(mDataBase.getWards(sectionId));
	}
	
	private void refreshBeds(int wardId)
	{
		mViewController.setBeds(mDataBase.getBeds(wardId));
	}
}
