package com.xiangxue.ch04.createobj03;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FastPerson {
	private final Date birthDate;

	public FastPerson(Date birthDate) {
		this.birthDate = new Date(birthDate.getTime());
	}
	
	private static final Date Begin;
	//private static final Date End;
	
	static {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.set(1990, Calendar.JANUARY,1,0,0,0);
		Begin = cal.getTime();
		//.....
	}

	
	public boolean is90s() {
		//比较begin和end
		//.....
		return false;
	}

}
