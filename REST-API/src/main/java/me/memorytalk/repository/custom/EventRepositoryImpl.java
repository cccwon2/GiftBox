package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Event;
import me.memorytalk.domain.QAttachment;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QEventType;
import me.memorytalk.dto.AdminEventDetailModel;
import me.memorytalk.dto.AdminEventModel;
import me.memorytalk.dto.EventDetailModel;
import me.memorytalk.dto.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.*;

public class EventRepositoryImpl extends QueryDslRepositorySupport implements EventModelRepository {

    public EventRepositoryImpl() {
        super(Event.class);
    }

    public List<EventModel> findPremiumEventModels(Date now, String sort, List<String> onGoings, List<Long> forms) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;
        QEventType qEventType = QEventType.eventType;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qEvent.visible.isTrue(), qEvent.premium.isTrue());

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(onGoings.size() > 0 && onGoings.size() < 3) {
            if ("progress".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.loe(now));
                whereBuilder.and(qEvent.endDate.goe(now));
            } else if ("end".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.endDate.lt(now));
            } else if ("oncoming".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.gt(now));
            }
            if (onGoings.size() == 2) {
                if ("progress".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.loe(now));
                    whereBuilder.and(qEvent.endDate.goe(now));
                } else if ("end".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.endDate.lt(now));
                } else if ("oncoming".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.gt(now));
                }
            }
        }
        if(forms.size() > 0) {
            whereBuilder.and(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                    .where(qEventType.eventTypeCode.id.eq(forms.get(0))).exists());
            for(int i=1; i<forms.size(); i++) {
                whereBuilder.or(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                        .where(qEventType.eventTypeCode.id.eq(forms.get(i))).exists());
            }
        }
        if("close".equals(sort)) {
            whereBuilder.and(qEvent.endDate.goe(now));
            query.where(whereBuilder);
            query.orderBy(qEvent.endDate.asc());
        } else {
            query.where(whereBuilder);
            query.orderBy(qEvent.createdDate.desc());
        }

        List<EventModel> eventModels = query.list(ConstructorExpression.create(EventModel.class,
                qEvent.id,
                qEvent.title,
                qEvent.company,
                qEvent.prizePage,
                qEvent.startDate,
                qEvent.endDate,
                qEvent.premium,
                qAttachment.width,
                qAttachment.height,
                qAttachment.url,
                qAttachment.thumbnailS,
                qAttachment.thumbnailM,
                qAttachment.thumbnailL
        ));

        return eventModels;
    }

    public long countPremiumEventModels(Date now, String sort, List<String> onGoings, List<Long> forms) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;
        QEventType qEventType = QEventType.eventType;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qEvent.visible.isTrue(), qEvent.premium.isTrue());

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(onGoings.size() > 0 && onGoings.size() < 3) {
            if ("progress".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.loe(now));
                whereBuilder.and(qEvent.endDate.goe(now));
            } else if ("end".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.endDate.lt(now));
            } else if ("oncoming".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.gt(now));
            }
            if (onGoings.size() == 2) {
                if ("progress".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.loe(now));
                    whereBuilder.and(qEvent.endDate.goe(now));
                } else if ("end".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.endDate.lt(now));
                } else if ("oncoming".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.gt(now));
                }
            }
        }
        if(forms.size() > 0) {
            whereBuilder.and(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                    .where(qEventType.eventTypeCode.id.eq(forms.get(0))).exists());
            for(int i=1; i<forms.size(); i++) {
                whereBuilder.or(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                        .where(qEventType.eventTypeCode.id.eq(forms.get(i))).exists());
            }
        }
        if("close".equals(sort)) {
            whereBuilder.and(qEvent.endDate.goe(now));
        }
        query.where(whereBuilder);

        return query.count();
    }

    public Page<EventModel> findPremiumEventModels(Pageable pageable) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qEvent.visible.isTrue(), qEvent.premium.isTrue());

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<EventModel> eventModels;

        if(total > pageable.getOffset()) {
            eventModels = pagedQuery.list(ConstructorExpression.create(EventModel.class,
                    qEvent.id,
                    qEvent.title,
                    qEvent.company,
                    qEvent.prizePage,
                    qEvent.startDate,
                    qEvent.endDate,
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

    public long countEventModels(Date now, String sort, List<String> onGoings, List<Long> forms) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;
        QEventType qEventType = QEventType.eventType;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qEvent.visible.isTrue(), qEvent.premium.isFalse());

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(onGoings.size() > 0 && onGoings.size() < 3) {
            if ("progress".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.loe(now));
                whereBuilder.and(qEvent.endDate.goe(now));
            } else if ("end".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.endDate.lt(now));
            } else if ("oncoming".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.gt(now));
            }
            if (onGoings.size() == 2) {
                if ("progress".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.loe(now));
                    whereBuilder.and(qEvent.endDate.goe(now));
                } else if ("end".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.endDate.lt(now));
                } else if ("oncoming".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.gt(now));
                }
            }
        }
        if(forms.size() > 0) {
            whereBuilder.and(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                    .where(qEventType.eventTypeCode.id.eq(forms.get(0))).exists());
            for(int i=1; i<forms.size(); i++) {
                whereBuilder.or(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                        .where(qEventType.eventTypeCode.id.eq(forms.get(i))).exists());
            }
        }
        if("close".equals(sort)) {
            whereBuilder.and(qEvent.endDate.goe(now));
        }
        query.where(whereBuilder);

        return query.count();
    }

    public Page<EventModel> findEventModels(Date now, String sort, List<String> onGoings, List<Long> forms, Pageable pageable) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;
        QEventType qEventType = QEventType.eventType;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qEvent.visible.isTrue(), qEvent.premium.isFalse());

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(onGoings.size() > 0 && onGoings.size() < 3) {
            if ("progress".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.loe(now));
                whereBuilder.and(qEvent.endDate.goe(now));
            } else if ("end".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.endDate.lt(now));
            } else if ("oncoming".equals(onGoings.get(0))) {
                whereBuilder.and(qEvent.startDate.gt(now));
            }
            if (onGoings.size() == 2) {
                if ("progress".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.loe(now));
                    whereBuilder.and(qEvent.endDate.goe(now));
                } else if ("end".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.endDate.lt(now));
                } else if ("oncoming".equals(onGoings.get(1))) {
                    whereBuilder.or(qEvent.startDate.gt(now));
                }
            }
        }
        if(forms.size() > 0) {
            whereBuilder.and(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                    .where(qEventType.eventTypeCode.id.eq(forms.get(0))).exists());
            for(int i=1; i<forms.size(); i++) {
                whereBuilder.or(new JPASubQuery().from(qEvent.eventTypes, qEventType)
                        .where(qEventType.eventTypeCode.id.eq(forms.get(i))).exists());
            }
        }
        if("close".equals(sort)) {
            whereBuilder.and(qEvent.endDate.goe(now));
            query.where(whereBuilder);
            query.orderBy(qEvent.endDate.asc());
        } else {
            query.where(whereBuilder);
            query.orderBy(qEvent.createdDate.desc());
        }

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<EventModel> eventModels;

        if(total > pageable.getOffset()) {
            eventModels = pagedQuery.list(ConstructorExpression.create(EventModel.class,
                    qEvent.id,
                    qEvent.title,
                    qEvent.company,
                    qEvent.prizePage,
                    qEvent.startDate,
                    qEvent.endDate,
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
                qEvent.eventTarget,
                qEvent.eventPage,
                qEvent.prizePage,
                qEvent.startDate,
                qEvent.endDate,
                qEvent.publicationDate,
                qEvent.createdDate,
                qEvent.publicationContent1,
                qEvent.publicationContent2,
                qEvent.publicationType,
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
                qEvent.eventTarget,
                qEvent.eventPage,
                qEvent.prizePage,
                qEvent.startDate,
                qEvent.endDate,
                qEvent.publicationDate,
                qEvent.createdDate,
                qEvent.publicationContent1,
                qEvent.publicationContent2,
                qEvent.publicationType,
                qEvent.premium,
                qEvent.visible
        ));

        return adminEventDetailModel;
    }

    public Page<AdminEventModel> findAdminEventModels(String eventId, String eventTitle, String premium, String visible, Pageable pageable) {

        QEvent qEvent = QEvent.event;
        QAttachment qAttachment = QAttachment.attachment;

        JPQLQuery query = from(qEvent);
        query.leftJoin(qEvent.attachments, qAttachment).on(qAttachment.sort.eq(1));

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(eventId)) {
            whereBuilder.and(qEvent.id.eq(Long.valueOf(eventId)));
        } else if(!StringUtils.isEmpty(eventTitle)) {
            whereBuilder.and(qEvent.title.contains(eventTitle));
        }
        if(!StringUtils.isEmpty(premium)) {
            whereBuilder.and(qEvent.premium.eq(Boolean.valueOf(premium)));
        }
        if(!StringUtils.isEmpty(visible)) {
            whereBuilder.and(qEvent.visible.eq(Boolean.valueOf(visible)));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminEventModel> adminEventModels;

        if(total > pageable.getOffset()) {
            adminEventModels = pagedQuery.list(ConstructorExpression.create(AdminEventModel.class,
                    qEvent.id,
                    qEvent.title,
                    qEvent.description,
                    qEvent.company,
                    qEvent.eventTarget,
                    qEvent.eventPage,
                    qEvent.prizePage,
                    qEvent.startDate,
                    qEvent.endDate,
                    qEvent.publicationDate,
                    qEvent.createdDate,
                    qEvent.publicationContent1,
                    qEvent.publicationContent2,
                    qEvent.publicationType,
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
