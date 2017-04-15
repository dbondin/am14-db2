package ru.applmath.valutes.export;

public class Valute {

	private String id;

	private String charCode;

	private String name;

	private double value; // TODO: Do NOT use floating point value for MONEY !!!

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[" + id + "; " + charCode + "; " + name + "; " + value + "]";
	}
}
