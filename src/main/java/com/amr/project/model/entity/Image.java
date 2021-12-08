package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiIgnore
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] picture;

    private Boolean isMain = false;

    @JsonIgnore
    @OneToOne(mappedBy = "images", cascade = {CascadeType.PERSIST})
    private User user;

    @JsonIgnore
    @ManyToMany(mappedBy = "logo")
    private List<Review> reviews;

    @JsonIgnore
    @ManyToMany(mappedBy = "images")
    private List<Item> items;

    @JsonIgnore
    @ManyToMany(mappedBy = "logo")
    private List<Shop> shops;

}
