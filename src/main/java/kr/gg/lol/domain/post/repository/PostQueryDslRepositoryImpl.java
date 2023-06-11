package kr.gg.lol.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.gg.lol.domain.post.dto.PostDto;
import kr.gg.lol.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.gg.lol.domain.post.entity.QPost.post;
import static kr.gg.lol.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<PostDto> findAllByLessThanIdAndOrderByIdDesc(Long id, Integer size) {
        return jpaQueryFactory
                .select(Projections.fields(PostDto.class,
                        post.id,
                        user.nickname,
                        post.userId,
                        post.title,
                        post.content,
                        post.createdAt))
                .from(post)
                .leftJoin(user).on(post.userId.eq(user.id))
                .fetchJoin()
                .where(post.id.lt(id))
                .orderBy(post.id.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<PostDto> findAllOrderByIdDesc(Integer size) {
        return jpaQueryFactory
                .select(Projections.fields(PostDto.class,
                        post.id,
                        user.nickname,
                        post.userId,
                        post.title,
                        post.content,
                        post.createdAt))
                .from(post)
                .leftJoin(user).on(post.userId.eq(user.id))
                .fetchJoin()
                .orderBy(post.id.desc())
                .limit(size)
                .fetch();
    }
}
