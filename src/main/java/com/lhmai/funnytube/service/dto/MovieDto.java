package com.lhmai.funnytube.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private String id;
    private String embeddedUrl;
    private String description;
    private String title;
    private String sharedBy;
}
