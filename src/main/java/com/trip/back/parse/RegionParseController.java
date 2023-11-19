package com.trip.back.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trip.back.attraction.AttractionInfo;
import com.trip.back.attraction.AttractionMapper;
import com.trip.back.region.Gugun;
import com.trip.back.region.GugunMapper;
import com.trip.back.sentiment.SentimentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/parse")
@RequiredArgsConstructor
@Slf4j
public class RegionParseController {
	@Value("${api.data.key}")
	private String apiKey;

	private final GugunMapper gugunReposiotry;

	@GetMapping("/region")
	public ResponseEntity<String> region() throws IOException {
//		int[] areaCodes = { 1, 2, 3, 4, 5, 6, 7, 8, 31, 32 };
		int [] areaCodes = {39};
		log.info("check parse");
		for (int areaCode : areaCodes) {
			StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/areaCode1");
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + apiKey);

			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
			urlBuilder.append(
					"&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "="
					+ URLEncoder.encode(String.valueOf(areaCode), "UTF-8"));
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			
			log.info(sb.toString());
			
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();

			JsonArray arr = obj.get("response").getAsJsonObject().get("body").getAsJsonObject().get("items")
					.getAsJsonObject().get("item").getAsJsonArray();

			for (JsonElement jsonElement : arr) {

				JsonObject temp = jsonElement.getAsJsonObject();

				gugunReposiotry.insert(Gugun.builder().gugunCode(temp.get("code").getAsInt())
						.name(temp.get("name").getAsString()).sidoCode(areaCode).build());
			}

		}

		return ResponseEntity.ok("ok");
	}
}
