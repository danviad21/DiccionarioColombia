package com.odavid.projects.app.services;

import java.util.regex.Pattern;

public class ValidacionManualTexto {

	private static final String CARACTERES_INVALIDOS = "[0-9]+|[\\+\\*\\/&]";
	private static final String CONSONANTES_SEGUIDOS_INVALIDOS = "[b-df-hj-np-tv-z]{4,}";
	private static final String VOCALES_SEGUIDAS_INVALIDOS = "(a|e|i|o|u){4,}";
	private static final String CANTIDAD_VOCALES_SEGUIDAS_VALIDAS = "(a|e|i|o|u){3}";
	private static final String TRIPTONGO_VALIDO = "(u|i)(a|e|o)(u|i)";

	/*
	 * Método que se usa como advertencia en dado caso que no se haya cumplido algo
	 * de las expresiones regulares, se muestra en consola para evitar que el
	 * programa tome el mensaje como palabras a evaluar.
	 */
	public static final String validacionBasicaCadena(String palabra) {

		if (palabra == null) {
			return "cadena inválida";
		} else if (Pattern.compile(CARACTERES_INVALIDOS).matcher(palabra).find()) {
			return "Contiene Signos Inválidos";
		} else if (Pattern.compile(CONSONANTES_SEGUIDOS_INVALIDOS).matcher(palabra).find()) {
			return "Contiene consonantes seguidas inválidas";
		} else if (Pattern.compile(VOCALES_SEGUIDAS_INVALIDOS).matcher(palabra).find()) {
			return "Contiene vocales seguidas inválidas";
		} else {
			if (Pattern.compile(CANTIDAD_VOCALES_SEGUIDAS_VALIDAS).matcher(palabra).find()) {
				if (!Pattern.compile(TRIPTONGO_VALIDO).matcher(palabra).find()) {
					return "Triptongo invalido";
				}
			}
			return "Cumple";
		}
	}
}
