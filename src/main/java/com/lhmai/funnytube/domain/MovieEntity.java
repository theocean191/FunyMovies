package com.lhmai.funnytube.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "tube_movie_id")
    private String tubeMovieId;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "embedded_url", columnDefinition = "TEXT")
    private String embeddedUrl;

    @Column(name = "shared_by")
    private String sharedBy;
}
