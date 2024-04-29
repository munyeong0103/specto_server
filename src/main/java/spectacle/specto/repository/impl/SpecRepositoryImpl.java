package spectacle.specto.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.res.SpecRes;

import java.util.List;
import java.util.Objects;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static spectacle.specto.domain.QSpec.spec;
import static spectacle.specto.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class SpecRepositoryImpl implements SpecRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SpecRes> selectSpecList(Category category, String sortType, Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = switch (Objects.requireNonNullElse(sortType, "")) {
            case "oldest" -> spec.startDate.asc();
            case "reviews" -> review.id.count().desc();
            default -> spec.startDate.desc();  // 최근 등록순
        };

        List<SpecRes> specResList = jpaQueryFactory.selectFrom(spec)
                .leftJoin(review).on(spec.id.eq(review.spec.id))
                .where(category == null ? null : spec.category.eq(category))
                .orderBy(orderSpecifier)
                .groupBy(spec.id)
                .transform(groupBy(spec.id)
                        .list(Projections.constructor(
                                SpecRes.class,
                                spec,
                                review.id.count().as("reviewCnt")
                        )));

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), specResList.size());
        List<SpecRes> resultList = specResList.subList(start, end);
        return new PageImpl<>(resultList, pageable, specResList.size());
    }

}
