package com.odavid.projects.app.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class BusquedadLocal {

	private Set<String> diccionarioLocal;

	public BusquedadLocal() {
		this.diccionarioLocal = new HashSet<>();

	}

	public void reemplazaLetras(Set<String> coincidencias, Set<String> diccionario, String p_palabra) {
		String palabra = p_palabra;
		if (palabra.contains("v")) {
			 palabra = palabra.replace('v', 'b');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}
		if (palabra.contains("b")) {
			palabra = palabra.replace('b', 'v');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (palabra.contains("n")) {
			palabra = palabra.replace('n', 'm');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}
		
		if (palabra.contains("m")) {
			palabra = palabra.replace('m', 'n');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (palabra.contains("s")) {
			palabra  = palabra.replace('s', 'z');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (palabra.contains("z")) {
			palabra = palabra.replace('z', 's');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (palabra.contains("y")) {
			palabra = palabra.replace('y', 'i');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (palabra.contains("i")) {
			palabra = palabra.replace('i', 'y');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (Pattern.compile("^(a|e|i|o|u)+[a-z]*?").matcher(palabra).matches()) {
			palabra = "h" + palabra;
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}

		if (palabra.contains("h")) {
			palabra = palabra.replace('h', ' ');
			if (diccionario.contains(palabra)) {
				coincidencias.add(palabra);
			}
			palabra = p_palabra;
		}
	}

	public Map<String, Object> busquedadPalabra(String palabra) {

		Map<String, Object> resultado = new HashMap<>();
		ManejoArchivos archivos = new ManejoArchivos(this.diccionarioLocal);
		archivos.lecturaArchivo();
		LinkedHashSet<String> diccionarioTemp = new LinkedHashSet<>();
		LinkedHashSet<String> coincidencias = new LinkedHashSet<>();

		if (ValidacionManualTexto.validacionBasicaCadena(palabra).compareTo("Cumple") == 0) {

			for (String cadena : this.diccionarioLocal) {
				if (cadena.length() <= palabra.length() + 2 && cadena.length() >= palabra.length() - 2) {
					diccionarioTemp.add(cadena);
				}
				System.out.print(diccionarioTemp.contains("humano"));
			}
			reemplazaLetras(coincidencias, diccionarioTemp, palabra);

			for (String cadena : diccionarioTemp) {
				if (cadena.length() == palabra.length()) {
					int contador = 0;
					char[] cadena1 = cadena.toCharArray();
					char[] palabra1 = palabra.toCharArray();

					for (int i = 0; i < cadena1.length; i++) {
						if (cadena1[i] != palabra1[i]) {
							contador++;
						}
					}

					if (contador < 2) {
						coincidencias.add(cadena);
					}
				}
			}

			if (!diccionarioTemp.isEmpty()) {
				resultado.put("coincidencias", coincidencias);
			} else {
				resultado.put("nohay", "No existen la palabra en el diccionario");
			}
		} else {
			resultado.put("incorrecto", ValidacionManualTexto.validacionBasicaCadena(palabra));
		}
		return resultado;
	}
}
