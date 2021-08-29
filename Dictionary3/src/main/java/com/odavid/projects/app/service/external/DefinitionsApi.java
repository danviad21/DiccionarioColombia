/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odavid.projects.app.service.external;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author danvi
 */
public class DefinitionsApi {

	@Autowired
	private RestTemplate restTempalte = new RestTemplate();

	public Map<String, Object> findingWord(String word) {
		word = word.split(" ")[0];
		Map<String, Object> foundWord = new HashMap<String, Object>();
		List<String> definitions = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			String response = this.restTempalte.exchange("https://api.dictionaryapi.dev/api/v2/entries/es/" + word,
					HttpMethod.GET, entity, String.class).getBody();
			JSONParser parser = new JSONParser();

			try {
				JSONArray jsonArray = (JSONArray) parser.parse(response);
				foundWord.put("rightWord", ((JSONObject) jsonArray.get(0)).get("word").toString());
				JSONArray jsonMeanings = (JSONArray) ((JSONObject) jsonArray.get(0)).get("meanings");
				JSONArray jsonDefinitions = (JSONArray) ((JSONObject) jsonMeanings.get(0)).get("definitions");
				Iterator<?> iterator = jsonDefinitions.iterator();
				while (iterator.hasNext()) {
					String definition = ((JSONObject) iterator.next()).get("definition").toString();
					definitions.add(definition);
				}

				foundWord.put("definitions", definitions);
			} catch (ParseException ex) {
				foundWord.put("error", "Error al realizar la peticion");
				return foundWord;
			}
		} catch (HttpStatusCodeException e) {
			foundWord.put("no_definition", "No se encontro definicion para la palabra buscada");
			return foundWord;
		}
		return foundWord;
	}

	// oxford api
	public List<String> definitionsWords(String word) {
		List<String> definitions = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("app_id", "a46d63f2");
		headers.set("app_key", "fa41d5ea3f42f341abe7dcd8bcd66cb8");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		word = word.split(" ")[0];
		System.out.println(word);
		try {

			String response = this.restTempalte.exchange("https://od-api.oxforddictionaries.com/api/v2/entries/es/"
					+ word + "?fields=definitions&strictMatch=true", HttpMethod.GET, entity, String.class).getBody();
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(response);
				JSONArray jsonResults = (JSONArray) jsonObject.get("results");
				JSONArray jsonLexicalEntries = (JSONArray) ((JSONObject) jsonResults.get(0)).get("lexicalEntries");
				JSONArray jsonEntries = (JSONArray) ((JSONObject) jsonLexicalEntries.get(0)).get("entries");
				JSONArray jsonSenses = (JSONArray) ((JSONObject) jsonEntries.get(0)).get("senses");
				JSONArray jsonDefinitions = (JSONArray) ((JSONObject) jsonSenses.get(0)).get("definitions");

				Iterator<?> iterator = jsonDefinitions.iterator();
				while (iterator.hasNext()) {
					definitions.add(iterator.next().toString());
				}

			} catch (ParseException ex) {
				definitions.add("No existe resultados para la palabra teclada.");
				return definitions;
			}
		} catch (HttpStatusCodeException e) {
			definitions.add("No existe resultados para la palabra teclada.");
			return definitions;
		}
		return definitions;
	}

}
