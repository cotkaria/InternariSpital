package com.database.internarispital.util;

public interface TwoParamCallback<P1,P2,R> 
{
	R call(P1 p1, P2 p2);
	
}
