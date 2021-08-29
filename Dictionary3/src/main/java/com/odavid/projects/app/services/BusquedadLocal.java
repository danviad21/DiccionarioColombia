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

	public String reemplazaLetras(Set<String> diccionario, String palabra) {
		if (palabra.contains("*v*")) {
			if (diccionario.contains(palabra = palabra.replace('v', 'b'))) {
				return palabra;
			}
		} else if (palabra.contains("b")) {
			if (diccionario.contains(palabra = palabra.replace('b', 'v'))) {
				return palabra;
			}
		} else if (palabra.contains("n")) {
			if (diccionario.contains(palabra = palabra.replace('n', 'm'))) {
				return palabra;
			}
		} else if (palabra.contains("m")) {
			if (diccionario.contains(palabra = palabra.replace('m', 'n'))) {
				return palabra;
			}
		} else if (palabra.contains("s")) {
			if (diccionario.contains(palabra = palabra.replace('s', 'z'))) {
				return palabra;
			}
		} else if (palabra.contains("z")) {
			if (diccionario.contains(palabra = palabra.replace('z', 's'))) {
				return palabra;
			}
		} else if (palabra.contains("y")) {
			if (diccionario.contains(palabra = palabra.replace('y', 'i'))) {
				return palabra;
			}
		} else if (palabra.contains("i")) {
			if (diccionario.contains(palabra = palabra.replace('i', 'y'))) {
				return palabra;
			}
		} else if (Pattern.compile("^(a|e|i|o|u)+[a-z]*?").matcher(palabra).matches()) {
			if (diccionario.contains(palabra = "h" + "palabra")) {
				return palabra;
			}
		} else if (palabra.contains("h")) {
			if (diccionario.contains(palabra = palabra.replace('h', ' '))) {
				return palabra;
			}
		}
		return null;

	}

	public Map<String, Object> busquedadPalabra(String palabra) {

		Map<String, Object> resultado = new HashMap<>();
		String palabraNueva;
		ManejoArchivos archivos = new ManejoArchivos(this.diccionarioLocal);
		archivos.lecturaArchivo();
		LinkedHashSet<String> diccionarioTemp = new LinkedHashSet<>();
		LinkedHashSet<String> coincidencias = new LinkedHashSet<>();

		if (ValidacionManualTexto.validacionBasicaCadena(palabra).compareTo("Cumple") == 0) {

			for (String cadena : this.diccionarioLocal) {
				if (cadena.length() <= palabra.length() + 2 && cadena.length() >= palabra.length() - 2) {
					diccionarioTemp.add(cadena);
				}
			}

			palabraNueva = reemplazaLetras(coincidencias, palabra);
			if (palabraNueva != null) {
				coincidencias.add(palabraNueva);
			}

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
				resultado.put("nohay", "No Hay Sugerencias");
			}
		} else {
			resultado.put("incorrecto", ValidacionManualTexto.validacionBasicaCadena(palabra));
		}
		return resultado;
	}
}
