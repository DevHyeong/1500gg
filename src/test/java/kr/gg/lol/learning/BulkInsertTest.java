package kr.gg.lol.learning;

import kr.gg.lol.domain.post.entity.Post;
import kr.gg.lol.util.PostFixtureFactory;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@SpringBootTest
public class BulkInsertTest {
    @Autowired
    private PostJdbcRepository postJdbcRepository;
    @Test
    @Disabled
    void test() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                33L,
                LocalDateTime.of(1970, 1, 1, 0, 0),
                LocalDateTime.of(2022, 2, 1, 23, 0)
        );

        var stopWatch = new StopWatch();
        stopWatch.start();

        int _1만 = 10000;
        List<Post> posts = IntStream.range(0, _1만 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .collect(Collectors.toList());

        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postJdbcRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB 인서트 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
