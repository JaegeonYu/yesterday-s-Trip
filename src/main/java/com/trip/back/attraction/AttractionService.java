package com.trip.back.attraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trip.back.region.Sido;
import com.trip.back.region.SidoDto;
import com.trip.back.region.SidoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	private final AttractionMapper attractionRepository;
	private final SidoMapper sidoRepository;
	
	public List<AttractionResDto> selectByRegionAndContentType(Integer sido, Integer gugun, Long contentType) {
		return attractionRepository.selectBySidoAndGugunAndContentType(sido, gugun, contentType).stream()
				.map(entity -> {
					return AttractionResDto.builder().entity(entity).count(findReviewCountById(entity)).build();

				}).collect(Collectors.toList());
	}

	public List<AttractionResDto> selectByRegion(Integer sido, Integer gugun) {
		return attractionRepository.selectBySidoAndGugun(sido, gugun).stream().map(entity -> {
			return AttractionResDto.builder().entity(entity).count(findReviewCountById(entity)).build();

		}).collect(Collectors.toList());
	}

	public List<AttractionResDto> selectBySido(Integer sido) {
		return attractionRepository.selectBySido(sido).stream().map(entity -> {
			return AttractionResDto.builder().entity(entity).count(findReviewCountById(entity)).build();

		}).collect(Collectors.toList());
	}

	public List<AttractionResDto> selectBySidoAndContentType(Integer sido, Long contentType) {
		return attractionRepository.selectBySidoAndContentType(sido, contentType).stream().map(entity -> {
			return AttractionResDto.builder().entity(entity).count(findReviewCountById(entity)).build();

		}).collect(Collectors.toList());
	}

	public List<AttractionResDto> selectByContentType(Long contentType) {
		return attractionRepository.selectByContentType(contentType).stream().map(entity -> {
			return AttractionResDto.builder().entity(entity).count(findReviewCountById(entity)).build();

		}).collect(Collectors.toList());
	}

	public List<AttractionResDto> selectByTitle(String title) {
		return attractionRepository.selectByTitle(title).stream().map(entity -> {
			return AttractionResDto.builder().entity(entity).count(findReviewCountById(entity)).build();

		}).collect(Collectors.toList());
	}

	private Integer findReviewCountById(AttractionInfo info) {
		return attractionRepository.countReview(info.getContentId());
	}

	public AttractionBestDto selectBestBySido(Integer sido) {
		List<AttractionResDto> attractions = attractionRepository.selectBySidoBestScoreLimit3(sido);

		return getBestPath(attractions);
	}
	
	public List<SidoDto> selectBest3Region(){
		List<AttractionBestDto> regions = new ArrayList<AttractionBestDto>();
		List<Sido> sidos = sidoRepository.selectAll();
		
		for(Sido sido : sidos) {
			regions.add(selectBestBySido(sido.getSidoCode()));
		}
		Collections.sort(regions, new Comparator<AttractionBestDto>() {

			@Override
			public int compare(AttractionBestDto o1, AttractionBestDto o2) {
				return Double.compare(o1.getShortestDistance(), o2.getShortestDistance());
			}
		});
		
		List<SidoDto> bestSido = new ArrayList<SidoDto>();
		for(int i=0;i<3;i++) {
			bestSido.add(new SidoDto(new Sido(regions.get(i).getShortestPath().get(0).getSidoCode(), sidoRepository.selectByCode(regions.get(i).getShortestPath().get(0).getSidoCode()))));
		}
		
		return bestSido;
	}
	
	private double distance = Double.MAX_VALUE;
	private List<Integer> idxResult = new ArrayList<Integer>();
	
	private AttractionBestDto getBestPath(List<AttractionResDto> attractions) {
		distance = Double.MAX_VALUE;
		
		boolean vis[] = new boolean[3];
		perm(new Stack<Integer>(), vis, attractions);
	
		return new AttractionBestDto(distance, getResultPath(attractions));
	}
	 private List<AttractionResDto> getResultPath(List<AttractionResDto> attractions) {
		List<AttractionResDto> paths = new ArrayList<AttractionResDto>();
		paths.add(attractions.get(idxResult.get(0)));
		paths.add(attractions.get(idxResult.get(1)));
		paths.add(attractions.get(idxResult.get(2)));
		return paths;
	}

	private void perm(Stack<Integer> stk, boolean[] vis, List<AttractionResDto> attractions) {
		if(stk.size() == 3) {
			double nowDistance = getDistance(stk, attractions);
			if(distance > nowDistance) {
				distance = nowDistance;
				idxResult = new ArrayList<Integer>(stk);
			}
			return;
		}
		 
		for(int i=0;i<3;i++) {
			if(!vis[i]) {
				vis[i]=true;
				stk.push(i);
				perm(stk, vis, attractions);
				stk.pop();
				vis[i]=false;
			}
		}	
	}



	private double getDistance(Stack<Integer> stk, List<AttractionResDto> attractions) {
		AttractionResDto point1 = attractions.get(stk.get(0));
		AttractionResDto point2 = attractions.get(stk.get(1));
		AttractionResDto point3 = attractions.get(stk.get(2));
		double lengthFirst = haversine(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude());
		double lengthSecond = haversine(point2.getLatitude(), point2.getLongitude(), point3.getLatitude(), point3.getLongitude());
		
		return lengthFirst + lengthSecond;
	}



	private static double haversine(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구의 반경 (단위: km)

		// 위도 및 경도를 라디안으로 변환
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		// Haversine 공식 계산
		double dlat = lat2 - lat1;
		double dlon = lon2 - lon1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		// 최단 거리 계산
		return R * c;
	}
}
