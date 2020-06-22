package com.jhyeon.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//해당 인터페이스에서 CRUD 담당
public interface PostsRepository extends JpaRepository<Posts, Long> { //JpaRepository<Entity 클래스, PK타입>
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

    //만약 복잡한 조건의 쿼리 조회가 필요하다면 querydsl 프레임워크 추가!
}
