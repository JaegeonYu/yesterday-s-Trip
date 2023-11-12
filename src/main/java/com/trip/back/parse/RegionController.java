package com.trip.back.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/region")

public class RegionController {
	@Value("${api.key}")
	String apiKey;
	
	@GetMapping
	public ResponseEntity<String> region() throws IOException{
		 int[] areaCodes = {1, 2, 3, 4, 5, 6, 7, 8, 31, 32};

	        for (int areaCode : areaCodes) {
	            StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList");
	            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "="+apiKey);
	     
	            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(areaCode), "UTF-8"));
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
	            System.out.println(sb.toString());

	            JsonParser parser = new JsonParser();
	            JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();

	            JsonArray arr = obj.get("response").getAsJsonObject()
	                    .get("body").getAsJsonObject()
	                    .get("items").getAsJsonObject()
	                    .get("item").getAsJsonArray();

	            for (JsonElement jsonElement : arr) {
//	                JsonObject temp = jsonElement.getAsJsonObject();
//	                City city = cityRepository.findById(Long.parseLong(String.valueOf(i))).orElseThrow(NullPointerException::new);
//
//	                touristRepository.save(Tourist.builder()
//	                        .name(temp.get("title").getAsString())
//	                        .cityStr(temp.get("addr1") == null? "-":temp.get("addr1").getAsString().split(" ")[0])
//	                        .load(temp.get("addr1") == null? "-":temp.get("addr1").getAsString())
//	                        .zipcode(temp.get("addr1") == null? "-":temp.get("addr1").getAsString().split(" ")[temp.get("addr1").getAsString().split(" ").length - 1])
//	                        .cityObj(city)
//	                        .build());
	            }
		
	}
	        return ResponseEntity.ok("ok");
	}
}
