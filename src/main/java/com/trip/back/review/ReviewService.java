package com.trip.back.review;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trip.back.image.ImageMapper;
import com.trip.back.image.ImageResultDto;
import com.trip.back.sentiment.SentimentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	private final ReviewMapper reviewRepository;
	private final ImageMapper imageRepository;

	@Value("${com.upload.path}")
	private String uploadPath;

	public void save(Review review, MultipartFile[] uploadImages) {
		// review save
		reviewRepository.insert(review);
		
		// image save
		if (uploadImages != null) {
			for (MultipartFile uploadImage : uploadImages) {
				if (uploadImage.getContentType().startsWith("image") == false)
					throw new RuntimeException();

				String originalName = uploadImage.getOriginalFilename();
				String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String folderPath = makeFolder();
				String uuid = UUID.randomUUID().toString();

				String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + fileName;
				Path savePath = Paths.get(saveName);

				try {
					uploadImage.transferTo(savePath);
					// imageRepository save
					
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}

	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		String folderPath = str.replace("/", File.separator);

		File uploadPatheFolder = new File(uploadPath, folderPath);

		if (uploadPatheFolder.exists() == false) {
			uploadPatheFolder.mkdirs();
		}

		return folderPath;
	}

}
