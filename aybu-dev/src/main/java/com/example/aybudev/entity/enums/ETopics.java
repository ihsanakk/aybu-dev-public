package com.example.aybudev.entity.enums;

public enum ETopics {

    
    C_PLUS_PLUS("c++"),
    C("c"),
    C_SHARP("c#"),
    HTML("html"),
    JS("js"),
    JAVA("java");


    private final String topic;
    private ETopics(String topic){
        this.topic = topic;

    }
    
    public String getTopic() {
        return topic;
    }
}
