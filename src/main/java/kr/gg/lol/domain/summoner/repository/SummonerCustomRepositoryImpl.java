package kr.gg.lol.domain.summoner.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.gg.lol.domain.summoner.entity.QSummoner;
import kr.gg.lol.domain.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static kr.gg.lol.domain.summoner.entity.QSummoner.summoner;

@Repository
@RequiredArgsConstructor
public class SummonerCustomRepositoryImpl implements SummonerCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Summoner> findByName(String name) {
        return jpaQueryFactory.selectFrom(summoner)
                .where(replaceAll(summoner.name).toLowerCase().eq(name.toLowerCase().replaceAll(" ", "")))
                .stream().findFirst();
    }

    public StringTemplate replaceAll(StringPath stringPath){
        return Expressions.stringTemplate("replace({0}, ' ', '')", stringPath);
    }

}
