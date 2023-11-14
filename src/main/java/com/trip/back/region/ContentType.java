package com.trip.back.region;

public enum ContentType {
	TOURIST(12, "관광지"),
	CULTURAL(14, "문화시설"),
	FESTIVAL(15, "축제공연행사"),
	LODGMENT(32, "숙박"),
	FOOD(39, "음식점");
	
	private Integer contentTypeId;
	private String contentTypeName;
	
	private ContentType(Integer contentTypeId, String contentTypeName) {
		this.contentTypeId = contentTypeId;
		this.contentTypeName = contentTypeName;
	}
	
	public Integer getContentTypeId() {
		return contentTypeId;
	}
	
	public String getContentTypeName() {
		return contentTypeName;
	}
	
	
}
