
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

public class ImagesApi {

	@Autowired
	private RestTemplate restTempalte = new RestTemplate();

	public Map<String, Object> findingImage(String word) {
		Map<String, Object> foundImage;
		List<String> srcImages = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "563492ad6f9170000100000165bf66e1016e4163b03ab06e5073d3c5");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<>(headers);
		foundImage = new HashMap<String, Object>();
		try {
			String response = this.restTempalte
					.exchange("https://api.pexels.com/v1/search?query=" + word+"&locale=es-Es", HttpMethod.GET, entity, String.class)
					.getBody();
			JSONParser parser = new JSONParser();

			try {
				JSONObject jsonObject = (JSONObject) parser.parse(response);
				JSONArray jsonPhotos = (JSONArray) jsonObject.get("photos");
				
				Iterator<?> it = jsonPhotos.iterator();
				
				while(it.hasNext()) {
					String valor = ((JSONObject)((JSONObject)it.next()).get("src")).get("medium").toString();
					srcImages.add(valor);
				}
				
				foundImage.put("images", srcImages);
								
			} catch (ParseException ex) {
				foundImage.put("error", "Error al realizar la peticion");
				return foundImage;
			}
		} catch (HttpStatusCodeException e) {
			foundImage.put("no_images", "No se encontro imagenes para la palabra buscada");
			return foundImage;
		}
		return foundImage;
	}
}
