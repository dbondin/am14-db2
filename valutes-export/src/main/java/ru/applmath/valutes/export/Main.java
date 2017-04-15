package ru.applmath.valutes.export;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		ValutesReader vr = new CbrValutesReader();
		Calendar c = GregorianCalendar.getInstance();
		c.set(2017, 04, 11, 0, 0, 0);
		List<Valute> lv = vr.getValutes(c.getTime());
		for(Valute v : lv) {
			System.out.println(">>" + v);
		}
		
		DatabaseWriter dbwr = new MongoDatabaseWriter();
		
		dbwr.writeToDatabase(lv);
	}
}
