package com.yapp.memeserver.domain.meme.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Size(min = 1, max = 50)
    private String name;

    private Integer priority;

    @Builder
    public Category(String name) {
        this.name = name;
        this.priority = 100;
    }
    public void updateCategory(String name) {
        this.name = name;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }
}
