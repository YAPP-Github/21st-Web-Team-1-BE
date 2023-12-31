package com.yapp.memeserver.domain.meme.domain;

public enum MemeStatus {
    ACTIVE("활성화"),
    DELETED("삭제"),
    DRAFT("임시저장");

    private String name;

    MemeStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
