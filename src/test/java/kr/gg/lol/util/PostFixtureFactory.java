package kr.gg.lol.util;

import kr.gg.lol.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LocalDateRangeRandomizer;
import org.jeasy.random.randomizers.range.LocalDateTimeRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {
    public static Post create() {
        EasyRandomParameters parameter = getEasyRandomParameters();
        return new EasyRandom(parameter).nextObject(Post.class);
    }

    public static Post create(Long memberId, LocalDate createdDate) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter.randomize(createdDate(), new LocalDateRangeRandomizer(createdDate, createdDate));

        return new EasyRandom(parameter).nextObject(Post.class);
    }

    public static Post create(LocalDate start, LocalDate end) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter.randomize(createdDate(), new LocalDateRangeRandomizer(start, end));
        return new EasyRandom(parameter).nextObject(Post.class);
    }

    public static EasyRandom get(Long userId, LocalDateTime start, LocalDateTime end) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter
                .excludeField(named("id"))
                .stringLengthRange(10, 100)
                .randomize(userId(), () -> userId)
                .randomize(createdDate(), new LocalDateTimeRangeRandomizer(start, end));
        return new EasyRandom(parameter);
    }

    private static EasyRandomParameters getEasyRandomParameters() {
        return new EasyRandomParameters()
                .excludeField(named("id"))
                .stringLengthRange(1, 100)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100000L));
    }
    private static Predicate<Field> userId() {
        return named("userId").and(ofType(Long.class)).and(inClass(Post.class));
    }
    private static Predicate<Field> createdDate() {
        return named("createdAt").and(ofType(LocalDateTime.class)).and(inClass(Post.class));
    }
    private static Predicate<Field> title(){
        return named("title").and(ofType(String.class)).and(inClass(Post.class));
    }
    private static Predicate<Field> content(){
        return named("content").and(ofType(String.class)).and(inClass(Post.class));
    }

}
