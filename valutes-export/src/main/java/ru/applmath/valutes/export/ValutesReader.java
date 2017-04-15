package ru.applmath.valutes.export;

import java.util.Date;
import java.util.List;

public interface ValutesReader {
	List<Valute> getValutes(final Date d);
}
