package com.infoecos.nlp.resource.language.en;

import com.infoecos.nlp.resource.language.Inflection;

public class InflectionEn extends Inflection {

	public static final int Gerund = 0x101;
	public static final int ThirdPersonSingular = 0x102;
	public static final int PresentParticiple = 0x104;
	public static final int Past = 0x108;
	public static final int PastParticiple = 0x110;

	public static final int Plural = 0x201;
	public static final int Nouned = 0x202;

	public static final int Possessive = 0x800;

	public static String getString(int inflection) {
		StringBuilder sb = new StringBuilder(Inflection.getString(inflection));

		if ((inflection & 0x100) > 0) {
			// verb
			if ((inflection & 0xFF & Gerund) > 0)
				sb.append(":Gerund");
			if ((inflection & 0xFF & ThirdPersonSingular) > 0)
				sb.append(":ThirdPersonSingular");
			if ((inflection & 0xFF & PresentParticiple) > 0)
				sb.append(":PresentParticiple");
			if ((inflection & 0xFF & Past) > 0)
				sb.append(":Past");
			if ((inflection & 0xFF & PastParticiple) > 0)
				sb.append(":PastParticiple");
		} else if ((inflection & 0x200) > 0) {
			// noun
			if ((inflection & 0xFF & Plural) > 0)
				sb.append(":Plural");
			if ((inflection & 0xFF & Nouned) > 0)
				sb.append(":Nouned");
		}
		if ((inflection & 0xFFFF & Possessive) > 0)
			sb.append(":Possessive");

		return sb.toString();
	}
}
