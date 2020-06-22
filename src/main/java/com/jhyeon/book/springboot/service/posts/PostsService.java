package com.jhyeon.book.springboot.service.posts;

import com.jhyeon.book.springboot.domain.posts.Posts;
import com.jhyeon.book.springboot.domain.posts.PostsRepository;
import com.jhyeon.book.springboot.web.dto.PostsListResponseDto;
import com.jhyeon.book.springboot.web.dto.PostsResponseDto;
import com.jhyeon.book.springboot.web.dto.PostsSaveRequestDto;
import com.jhyeon.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent()); //데이터 쿼리를 날리는 부분이 없다(JPA 영속성 컨텍스트)

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts  = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Object findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // map(posts -> new PostsListResponseDto(posts)) 와 동일
                .collect(Collectors.toList());
    }
}
