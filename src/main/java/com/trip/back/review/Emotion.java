package com.trip.back.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emotion {
	POSITIVE("positive"),
	NEUTRAL("neutral"),
	NEGATIVE("negative");

    private final String emotion;

    public static Emotion of(String sentiment){
        for(Emotion temp : Emotion.values()){
            if(sentiment.equals(temp.emotion))return temp;
        }
        throw new RuntimeException();
    }
}
