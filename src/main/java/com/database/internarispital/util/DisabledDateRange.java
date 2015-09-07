package com.database.internarispital.util;

import java.time.LocalDate;

public class DisabledDateRange
{
	    private final LocalDate mInitialDate;
	    private final LocalDate mEndDate;

	    public DisabledDateRange(LocalDate initialDate, LocalDate endDate){
	        mInitialDate=initialDate;
	        mEndDate = endDate;
	    }

	    public LocalDate getInitialDate() { return mInitialDate; }
	    public LocalDate getEndDate() { return mEndDate; }
	}
