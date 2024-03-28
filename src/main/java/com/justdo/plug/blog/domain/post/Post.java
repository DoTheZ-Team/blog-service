package com.justdo.plug.blog.domain.post;

import com.justdo.plug.blog.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int like_count;

    @Column(nullable = false)
    private boolean temporary_state;

    @Column(nullable = false)
    private boolean state;

    @Column(insertable=false, updatable=false)
    private LocalDateTime created_at;

    @Column(insertable=false, updatable=false)
    private LocalDateTime updated_at;

    @Column(nullable = false, updatable = false)
    private long blog_id;

    @Column(nullable = false, updatable = false)
    private long member_id;
    /* TODO: 테이블 완성 되면 Mapping 해주기 */
    /*
    @ManyToOne
    @JoinColumn(name="member_id", nullable = false, updatable = false)
    private Member member;


    @ManyToOne
    @JoinColumn(name="blog_id", nullable = false, updatable = false)
    private Blog blog;
    */

    @Builder
    public Post( String title, String content, boolean temporary_state, boolean state, long member_id, long blog_id){
        this.title = title;
        this.content = content;
        this.temporary_state = temporary_state;
        this.state = state;
        /*
        this.created_at = created_at;
        this.updated_at = updated_at;
        */

        this.member_id = member_id;
        this.blog_id = blog_id;
    }

}
