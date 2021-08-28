/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odavid.projects.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.odavid.projects.app.service.external.DefinitionsApi;
import com.odavid.projects.app.service.external.ImagesApi;

/**
 *
 * @author danvi
 */
@Controller
public class DiccionarioController {

	private DefinitionsApi definitionApi;
	private ImagesApi imagesApi;

	@GetMapping("/")
	public String home(Model model) {
		return "index.html";
	}

	@GetMapping("/search/{word}")
	public String searchWord(@PathVariable("word") String word, RedirectAttributes redirectAttributes) {
		this.definitionApi = new DefinitionsApi();
		Map<String, Object> results = this.definitionApi.findingWord(word);

		if (results.get("error") != null) {
			System.out.println("error en servidor");
		} else if (results.get("no_definition") != null) {
			System.out.println("no hay definiciones");
		} else {
			System.out.println("todo bien");
			Map<String, Object> imagesResult;
			String definition = "";

			List<String> listDefinitions = (List<String>) results.get("definitions");

			for (String definitionTemp : listDefinitions) {
				definition += definitionTemp + "   ";
			}
			redirectAttributes.addFlashAttribute("word", results.get("rightWord"));
			redirectAttributes.addFlashAttribute("definitions", definition);

			this.imagesApi = new ImagesApi();
			imagesResult = this.imagesApi.findingImage(word);

			if (imagesResult.get("error") != null) {
				System.out.println("error");
			} else if (imagesResult.get("no_definitios") != null) {
				System.out.println("error");
			} else {

				@SuppressWarnings("unchecked")
				List<String> listImages = (List<String>) imagesResult.get("images");	
				redirectAttributes.addFlashAttribute("images", listImages);
			}
		}

		return "redirect:/";
	}
}