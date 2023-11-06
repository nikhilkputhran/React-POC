package com.poc.blog.blogapis.models;



import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private String content;

    @ManyToOne
    private Post post;

}
