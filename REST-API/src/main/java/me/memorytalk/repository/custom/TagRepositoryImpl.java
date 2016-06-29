package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QTag;
import me.memorytalk.domain.Tag;
import me.memorytalk.dto.AdminTagModel;
import me.memorytalk.dto.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class TagRepositoryImpl extends QueryDslRepositorySupport implements TagModelRepository {

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    public List<TagModel> findTagModels(Long eventId) {

        QTag qTag = QTag.tag;
        QEvent qEvent = QEvent.event;
        JPQLQuery query = from(qTag);
        query.innerJoin(qTag.event, qEvent).on(qEvent.id.eq(eventId));

        List<TagModel> tagModels = query.list(ConstructorExpression.create(TagModel.class,
                qTag.name,
                qTag.color
        ));

        return tagModels;
    }

    public Long deleteByEventId(Long eventId) {

        QTag qTag = QTag.tag;

        return new JPADeleteClause(getEntityManager(), qTag).where(qTag.event.id.eq(eventId)).execute();
    }

    public AdminTagModel findAdminTagModel(Long id) {

        QTag qTag = QTag.tag;
        QEvent qEvent = QEvent.event;

        JPQLQuery query = from(qTag);
        query.innerJoin(qTag.event, qEvent);
        query.where(qTag.id.eq(id));

        AdminTagModel adminTagModel = query.uniqueResult(ConstructorExpression.create(AdminTagModel.class,
                qTag.id,
                qTag.createdDate,
                qTag.name,
                qTag.color,
                qEvent.id,
                qEvent.title
        ));

        return adminTagModel;
    }

    public Page<AdminTagModel> findAdminTagModels(String eventId, String tagName, Pageable pageable) {

        QTag qTag = QTag.tag;
        QEvent qEvent = QEvent.event;

        JPQLQuery query = from(qTag);
        query.innerJoin(qTag.event, qEvent);

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(eventId)) {
            whereBuilder.and(qEvent.id.eq(Long.valueOf(eventId)));
        } else if(!StringUtils.isEmpty(tagName)) {
            whereBuilder.and(qTag.name.contains(tagName));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminTagModel> adminTagModels;

        if(total > pageable.getOffset()) {
            adminTagModels = pagedQuery.list(ConstructorExpression.create(AdminTagModel.class,
                    qTag.id,
                    qTag.createdDate,
                    qTag.name,
                    qTag.color,
                    qEvent.id,
                    qEvent.title
            ));
        } else {
            adminTagModels = Collections.<AdminTagModel>emptyList();
        }

        return new PageImpl<>(adminTagModels, pageable, total);
    }
}
