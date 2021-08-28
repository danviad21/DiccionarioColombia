package com.odavid.projects.app.model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.stream.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ManejoArchivos {

	private Resource resource;


	public void lecturaArchivo() {

		try {

			this.resource = new ClassPathResource("saludo.txt");

			BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			StringBuffer message = new StringBuffer();
			String line = null;

			while ((line = br.readLine()) != null) {
				message.append(line);
			}

			/*
			 * String defaultString=message.toString(); String result=defaultString;
			 * System.out.println(result);
			 */
		} catch (IOException e) {

			System.out.println("ocurrio algo");
		}
	}

	public static void main(String[] args) {
		ManejoArchivos manejo = new ManejoArchivos();
		manejo.lecturaArchivo();
	}

}
