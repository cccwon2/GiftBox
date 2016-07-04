package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.EventType;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QEventType;
import me.memorytalk.domain.QEventTypeCode;
import me.memorytalk.dto.AdminEventTypeModel;
import me.memorytalk.dto.EventTypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class EventTypeRepositoryImpl extends QueryDslRepositorySupport implements EventTypeModelRepository {

    public EventTypeRepositoryImpl() {
        super(EventType.class);
    }

    public List<Long> findEventTypeCodeIds(Long eventId) {

        QEventType qEventType = QEventType.eventType;
        QEvent qEvent = QEvent.event;
        QEventTypeCode qEventTypeCode = QEventTypeCode.eventTypeCode;

        JPQLQuery query = from(qEventType);
        query.innerJoin(qEventType.event, qEvent).on(qEvent.id.eq(eventId));
        query.innerJoin(qEventType.eventTypeCode, qEventTypeCode);

        List<Long> eventTypeCodeIds = query.list(ConstructorExpression.create(Long.class,
                qEventTypeCode.id
        ));

        return eventTypeCodeIds;
    }

    public List<EventTypeModel> findEventTypeModels(Long eventId) {

        QEventType qEventType = QEventType.eventType;
        QEvent qEvent = QEvent.event;
        QEventTypeCode qEventTypeCode = QEventTypeCode.eventTypeCode;

        JPQLQuery query = from(qEventType);
        query.innerJoin(qEventType.event, qEvent).on(qEvent.id.eq(eventId));
        query.innerJoin(qEventType.eventTypeCode, qEventTypeCode);

        List<EventTypeModel> eventTypeModels = query.list(ConstructorExpression.create(EventTypeModel.class,
                qEventTypeCode.name,
                qEventTypeCode.color,
                qEventTypeCode.sort
        ));

        return eventTypeModels;
    }

    public Long deleteByEventId(Long eventId) {

        QEventType qEventType = QEventType.eventType;

        return new JPADeleteClause(getEntityManager(), qEventType).where(qEventType.event.id.eq(eventId)).execute();
    }

    public AdminEventTypeModel findAdminEventTypeModel(Long id) {

        QEventType qEventType = QEventType.eventType;
        QEvent qEvent = QEvent.event;
        QEventTypeCode qEventTypeCode = QEventTypeCode.eventTypeCode;

        JPQLQuery query = from(qEventType);
        query.innerJoin(qEventType.event, qEvent);
        query.where(qEventType.id.eq(id));
        query.innerJoin(qEventType.eventTypeCode, qEventTypeCode);

        AdminEventTypeModel adminEventTypeModel = query.uniqueResult(ConstructorExpression.create(AdminEventTypeModel.class,
                qEventType.id,
                qEventType.createdDate,
                qEventTypeCode.name,
                qEventTypeCode.color,
                qEventTypeCode.sort,
                qEvent.id,
                qEvent.title
        ));

        return adminEventTypeModel;
    }

    public Page<AdminEventTypeModel> findAdminEventTypeModels(String eventId, String sort, Pageable pageable) {

        QEventType qEventType = QEventType.eventType;
        QEvent qEvent = QEvent.event;
        QEventTypeCode qEventTypeCode = QEventTypeCode.eventTypeCode;

        JPQLQuery query = from(qEventType);
        query.innerJoin(qEventType.event, qEvent);
        query.innerJoin(qEventType.eventTypeCode, qEventTypeCode);

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(eventId)) {
            whereBuilder.and(qEvent.id.eq(Long.valueOf(eventId)));
        } else if(!StringUtils.isEmpty(sort)) {
            whereBuilder.and(qEventTypeCode.sort.contains(sort));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminEventTypeModel> adminEventTypeModels;

        if(total > pageable.getOffset()) {
            adminEventTypeModels = pagedQuery.list(ConstructorExpression.create(AdminEventTypeModel.class,
                    qEventType.id,
                    qEventType.createdDate,
                    qEventTypeCode.name,
                    qEventTypeCode.color,
                    qEventTypeCode.sort,
                    qEvent.id,
                    qEvent.title
            ));
        } else {
            adminEventTypeModels = Collections.<AdminEventTypeModel>emptyList();
        }

        return new PageImpl<>(adminEventTypeModels, pageable, total);
    }
}
