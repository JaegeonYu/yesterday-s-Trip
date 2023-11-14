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
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trip.back.attraction.AttractionInfo;
import com.trip.back.attraction.AttractionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/parse")
@RequiredArgsConstructor
@Slf4j
public class AttractionParseController {
	@Value("${api.data.key}")
	private String apiKey;
	
	private final AttractionMapper attractionRepository;
	
	@GetMapping("/attraction/{contentType}")
	public ResponseEntity<String> region(@PathVariable String contentType) throws IOException{
		 int[] areaCodes = {1, 2, 3, 4, 5, 6, 7, 8, 31, 32};
		 log.info("check parse");
		 try {for (int areaCode : areaCodes) {
	            StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/areaBasedList1");
	            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "="+apiKey);
	     
	            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	            urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode(contentType, "UTF-8"));
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
//	            System.out.println(sb.toString());
	            
	            JsonParser parser = new JsonParser();
	            JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();

	            JsonArray arr = obj.get("response").getAsJsonObject()
	                    .get("body").getAsJsonObject()
	                    .get("items").getAsJsonObject()
	                    .get("item").getAsJsonArray();
	            
	            
	            
	            for (JsonElement jsonElement : arr) {
	            	
	                JsonObject temp = jsonElement.getAsJsonObject();
	           
//	                log.info("error {}" , temp.get("mapy").getAsDouble());
//  	                log.info("error {}" , temp.get("mapx").getAsDouble());
//  	                log.info("error {}" , temp.get("addr1").getAsString());
//	                try{
//	                	if(temp.get("mapx").getAsString().contains("E")  || temp.get("mapy").getAsString().contains("E")) {
//	                		log.info(temp.get("mapx").getAsString());
//	                		continue;
//	                	}
//	
//	                }catch(Exception e) {
//	                	e.printStackTrace();
//	                	continue;
//	                }
	                try {
	                attractionRepository.insert(
	                		AttractionInfo.builder()
	                		.contentId(temp.get("contentid").getAsLong())
	                		.contentTypeId(temp.get("contenttypeid").getAsLong())
	                		.sidoCode(temp.get("areacode").getAsInt())
	                		.gugunCode(temp.get("sigungucode").getAsInt())
	                		.title(temp.get("title").getAsString())
	                		.tel(temp.get("tel").getAsString())
	                		.address(temp.get("addr1").getAsString())
	                		.zipcode(temp.get("zipcode").getAsString())
	                		.imageUrl(temp.get("firstimage").getAsString())
	                		.longitude(temp.get("mapx").getAsDouble())
	                		.latitude(temp.get("mapy").getAsDouble())
	                		.mlevel(temp.get("mlevel").getAsString())
	                		.build());
	                }catch(Exception e) {
	                	e.printStackTrace();
	                	log.info("{}",temp.get("contentid").getAsLong());
	                	continue;
	                }
	            }
		
	        }
         	
         }catch(Exception e) {
         	e.printStackTrace();
         }
	        
	        return ResponseEntity.ok("ok");
	}
}
