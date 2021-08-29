package com.odavid.projects.app.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ManejoArchivos {

	private Resource resource;
	private final Set<String> diccionarioLocal;
	private String pathBase;

	public ManejoArchivos(Set<String> diccionario) {
		this.pathBase = "diccionario/";
		this.diccionarioLocal = diccionario;
	}
	
	public void lecturaArchivo() {

		this.resource = new ClassPathResource("diccionario");
		File[] files = null;
		try {
			files = new File(this.resource.getURI()).listFiles();
			for (File f : files) {
				this.resource = new ClassPathResource(this.pathBase + f.getName());
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
				String line = null;
				while ((line = br.readLine()) != null) {
					this.diccionarioLocal.add(line);
				}
				br.close();
			}

		} catch (IOException e1) {
			System.out.println("ocurrio algo");
		}
	}

}
