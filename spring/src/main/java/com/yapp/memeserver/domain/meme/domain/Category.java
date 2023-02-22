package com.yapp.memeserver.domain.meme.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @NotNull(message = "이름은 필수로 입력되어야 합니다.")
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;

    @URL
    @Size(max = 2048)
    @Column(name = "ICON")
    private String icon;

    @Column(name = "PRIORITY")
    private Integer priority;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tagList = new ArrayList<>();


    // 연관관계 편의 메소드
    public void addTag(Tag tag) {
        tagList.add(tag);
        tag.setCategory(this);
    }

    @Builder
    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
        this.priority = 100;
    }

    public void updateCategory(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public void updatePriority(Integer priority) {
        this.priority = priority;
    }
}
