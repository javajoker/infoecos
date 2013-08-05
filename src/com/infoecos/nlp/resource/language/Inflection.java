package com.infoecos.nlp.resource.language;

public class Inflection {
	public static final int NA = 0;

	public static final int Stop = 1;
	public static final int Link = 2;
	public static final int LeftBracket = 3;
	public static final int RightBracket = 4;

	public static final int ComparativeDegree = 0x11;
	public static final int SuperlativeDegree = 0x12;

	public static final int Cardinal = 0x21;
	public static final int Ordinal = 0x22;

	/**
	 * Name of objects, man / woman / animal / ...
	 * 
	 * E.g., Rose, Jack
	 */
	public static final int Name = 0x41;

	public static String getString(int inflection) {
		if ((inflection & 0xFF00) > 0)
			return "";

		StringBuilder sb = new StringBuilder();

		if ((inflection & 0xF0) == 0) {
			switch (inflection) {
			case 1:
				sb.append(":Stop");
				break;
			case 2:
				sb.append(":Link");
				break;
			case 3:
				sb.append(":LeftBracket");
				break;
			case 4:
				sb.append(":RightBracket");
				break;
			}
		} else if ((inflection & 0x10) > 0) {
			// ad
			if ((inflection & 0x0F & ComparativeDegree) > 0)
				sb.append(":ComparativeDegree");
			else if ((inflection & 0x0F & SuperlativeDegree) > 0)
				sb.append(":SuperlativeDegree");
		} else if ((inflection & 0x20) > 0) {
			// number
			if ((inflection & 0x0F & Cardinal) > 0)
				sb.append(":Cardinal");
			else if ((inflection & 0x0F & Ordinal) > 0)
				sb.append(":Ordinal");
		} else if ((inflection & 0x40) > 0) {
			if ((inflection & Name) > 0)
				sb.append(":Name");
		}

		return sb.toString();
	}
}
