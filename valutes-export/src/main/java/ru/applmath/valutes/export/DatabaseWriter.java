package ru.applmath.valutes.export;

import java.util.List;

public interface DatabaseWriter {
	void writeToDatabase(List<Valute> v);
}
