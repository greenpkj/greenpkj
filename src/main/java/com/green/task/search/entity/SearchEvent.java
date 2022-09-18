package com.green.task.search.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "search_event")
@Getter
@EqualsAndHashCode(of = "id")
public class SearchEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static SearchEvent toEntity(String keyword) {
        SearchEvent history = new SearchEvent();
        history.keyword = keyword;
        return history;
    }
}
