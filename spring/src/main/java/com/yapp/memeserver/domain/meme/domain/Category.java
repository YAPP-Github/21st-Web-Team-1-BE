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

    @Column(name = "PRIORITY")
    private Integer priority;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_CATEGORY_ID", updatable = false)
    private MainCategory mainCategory;


    // 연관관계 편의 메소드
    public void addTag(Tag tag) {
        tagList.add(tag);
        tag.setCategory(this);
    }

    protected void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

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
