package com.trip.back.review;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trip.back.image.ImageMapper;
import com.trip.back.image.ImageResultDto;
import com.trip.back.image.S3Uploader;
import com.trip.back.sentiment.SentimentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	private final ReviewMapper reviewRepository;
	private final ImageMapper imageRepository;
	private final S3Uploader s3Upload;

	public void save(Review review, MultipartFile[] uploadImages) throws IOException {
		// review save
		reviewRepository.insert(review);
		
		// image save
		if (uploadImages != null) {
			for (MultipartFile uploadImage : uploadImages) {
				if (uploadImage.getContentType().startsWith("image") == false)
					throw new RuntimeException();
				String uuid = UUID.randomUUID().toString();
				
				String url = s3Upload.upload(uploadImage, uuid).get();
				imageRepository.insert(ImageResultDto.builder().uuid(uuid)
						.reviewId(review.getId())
						.fileURL(url)
						.build());
				
//				 	s3Upload.upload(uploadImage, uuid).ifPresent(completeUrl->
//				 	imageRepository.insert(ImageResultDto.builder().uuid(uuid)
//							.reviewId(review.getId())
//							.fileURL(completeUrl)
//							.build())
//				 );
				 	

			}
		}
	}
	
	public List<ReviewSelectDto> selectByContentId(Long contentId){
		List<ReviewSelectDto> results = reviewRepository.selectById(contentId);
		
		for(ReviewSelectDto result : results) {
			List<ImageResultDto> images = imageRepository.selectByReviewId(result.getId());
			for(ImageResultDto image : images) {
				result.getImageURLs().add(image.getFileURL());
			}
		}
		
		return results;
	}


}
