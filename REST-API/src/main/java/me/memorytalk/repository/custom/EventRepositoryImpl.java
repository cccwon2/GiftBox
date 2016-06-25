package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Event;
import me.memorytalk.domain.QAttachment;
import me.memorytalk.domain.QEvent;
import me.memorytalk.dto.AdminEventDetailModel;
import me.memorytalk.dto.AdminEventModel;
import me.memorytalk.dto.EventDetailModel;
import me.memorytalk.dto.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class EventRepositoryImpl extends QueryDslRepositorySupport implements EventModelRepository {

    public EventRepositoryImpl() {
        super(Event.class);
    }

    public Page<EventModel> findEventModels(Pageable pageable) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qEvent.visible.isTrue());

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<EventModel> eventModels;

        if(total > pageable.getOffset()) {
            eventModels = pagedQuery.list(ConstructorExpression.create(EventModel.class,
                    qEvent.id,
                    qEvent.title,
                    qEvent.description,
                    qEvent.company,
                    qEvent.eventType,
                    qEvent.eventPage,
                    qEvent.homePage,
                    qEvent.startDate,
                    qEvent.endDate,
                    qEvent.publicationDate,
                    qEvent.registrationDate,
                    qEvent.premium,
                    qAttachment.width,
                    qAttachment.height,
                    qAttachment.url,
                    qAttachment.thumbnailS,
                    qAttachment.thumbnailM,
                    qAttachment.thumbnailL
            ));
        } else {
            eventModels = Collections.<EventModel>emptyList();
        }

        return new PageImpl<>(eventModels, pageable, total);
    }

    public EventDetailModel findEventModel(Long eventId) {

        QEvent qEvent = QEvent.event;
        JPQLQuery query = from(qEvent);
        query.where(qEvent.id.eq(eventId));

        EventDetailModel eventDetailModel = query.uniqueResult(ConstructorExpression.create(EventDetailModel.class,
                qEvent.id,
                qEvent.title,
                qEvent.description,
                qEvent.company,
                qEvent.eventType,
                qEvent.eventPage,
                qEvent.homePage,
                qEvent.startDate,
                qEvent.endDate,
                qEvent.publicationDate,
                qEvent.registrationDate,
                qEvent.premium
        ));

        return eventDetailModel;
    }

    public AdminEventDetailModel findAdminEventModel(Long eventId) {

        QEvent qEvent = QEvent.event;
        JPQLQuery query = from(qEvent);
        query.where(qEvent.id.eq(eventId));

        AdminEventDetailModel adminEventDetailModel = query.uniqueResult(ConstructorExpression.create(AdminEventDetailModel.class,
                qEvent.id,
                qEvent.title,
                qEvent.description,
                qEvent.company,
                qEvent.eventType,
                qEvent.eventPage,
                qEvent.homePage,
                qEvent.startDate,
                qEvent.endDate,
                qEvent.publicationDate,
                qEvent.registrationDate,
                qEvent.createdDate,
                qEvent.updatedDate,
                qEvent.premium,
                qEvent.visible
        ));

        return adminEventDetailModel;
    }

    public Page<AdminEventModel> findAdminEventModels(String visible, Pageable pageable) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));


        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(visible)) {
            whereBuilder.and(qEvent.visible.eq(Boolean.valueOf(visible)));
            query.where(whereBuilder);
        }

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminEventModel> adminEventModels;

        if(total > pageable.getOffset()) {
            adminEventModels = pagedQuery.list(ConstructorExpression.create(AdminEventModel.class,
                    qEvent.id,
                    qEvent.title,
                    qEvent.description,
                    qEvent.company,
                    qEvent.eventType,
                    qEvent.eventPage,
                    qEvent.homePage,
                    qEvent.startDate,
                    qEvent.endDate,
                    qEvent.publicationDate,
                    qEvent.registrationDate,
                    qEvent.createdDate,
                    qEvent.updatedDate,
                    qEvent.premium,
                    qEvent.visible,
                    qAttachment.width,
                    qAttachment.height,
                    qAttachment.url,
                    qAttachment.thumbnailS,
                    qAttachment.thumbnailM,
                    qAttachment.thumbnailL
            ));
        } else {
            adminEventModels = Collections.<AdminEventModel>emptyList();
        }

        return new PageImpl<>(adminEventModels, pageable, total);
    }
}
