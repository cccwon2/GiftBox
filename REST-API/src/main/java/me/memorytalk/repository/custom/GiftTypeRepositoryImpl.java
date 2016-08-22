package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.GiftType;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QGiftType;
import me.memorytalk.domain.QGiftTypeCode;
import me.memorytalk.dto.AdminGiftTypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class GiftTypeRepositoryImpl extends QueryDslRepositorySupport implements GiftTypeModelRepository {

    public GiftTypeRepositoryImpl() {
        super(GiftType.class);
    }

    public List<Long> findGiftTypeCodeIds(Long eventId) {

        QGiftType qGiftType = QGiftType.giftType;
        QEvent qEvent = QEvent.event;
        QGiftTypeCode qGiftTypeCode = QGiftTypeCode.giftTypeCode;

        JPQLQuery query = from(qGiftType);
        query.innerJoin(qGiftType.event, qEvent).on(qEvent.id.eq(eventId));
        query.innerJoin(qGiftType.giftTypeCode, qGiftTypeCode);

        List<Long> giftTypeCodeIds = query.list(ConstructorExpression.create(Long.class,
                qGiftTypeCode.id
        ));

        return giftTypeCodeIds;
    }

    public List<String> findGiftTypeModels(Long eventId) {

        QGiftType qGiftType = QGiftType.giftType;
        QEvent qEvent = QEvent.event;
        QGiftTypeCode qGiftTypeCode = QGiftTypeCode.giftTypeCode;

        JPQLQuery query = from(qGiftType);
        query.innerJoin(qGiftType.event, qEvent).on(qEvent.id.eq(eventId));
        query.innerJoin(qGiftType.giftTypeCode, qGiftTypeCode);

        List<String> giftTypeModels = query.list(qGiftTypeCode.name);

        return giftTypeModels;
    }

    public Long deleteByEventId(Long eventId) {

        QGiftType qGiftType = QGiftType.giftType;

        return new JPADeleteClause(getEntityManager(), qGiftType).where(qGiftType.event.id.eq(eventId)).execute();
    }

    public AdminGiftTypeModel findAdminGiftTypeModel(Long id) {

        QGiftType qGiftType = QGiftType.giftType;
        QEvent qEvent = QEvent.event;
        QGiftTypeCode qGiftTypeCode = QGiftTypeCode.giftTypeCode;

        JPQLQuery query = from(qGiftType);
        query.innerJoin(qGiftType.event, qEvent);
        query.where(qGiftType.id.eq(id));
        query.innerJoin(qGiftType.giftTypeCode, qGiftTypeCode);

        AdminGiftTypeModel adminGiftTypeModel = query.uniqueResult(ConstructorExpression.create(AdminGiftTypeModel.class,
                qGiftType.id,
                qGiftType.createdDate,
                qGiftTypeCode.name,
                qEvent.id,
                qEvent.title
        ));

        return adminGiftTypeModel;
    }

    public Page<AdminGiftTypeModel> findAdminGiftTypeModels(String eventId, Pageable pageable) {

        QGiftType qGiftType = QGiftType.giftType;
        QEvent qEvent = QEvent.event;
        QGiftTypeCode qGiftTypeCode = QGiftTypeCode.giftTypeCode;

        JPQLQuery query = from(qGiftType);
        query.innerJoin(qGiftType.event, qEvent);
        query.innerJoin(qGiftType.giftTypeCode, qGiftTypeCode);

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(eventId)) {
            whereBuilder.and(qEvent.id.eq(Long.valueOf(eventId)));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminGiftTypeModel> adminGiftTypeModels;

        if(total > pageable.getOffset()) {
            adminGiftTypeModels = pagedQuery.list(ConstructorExpression.create(AdminGiftTypeModel.class,
                    qGiftType.id,
                    qGiftType.createdDate,
                    qGiftTypeCode.name,
                    qEvent.id,
                    qEvent.title
            ));
        } else {
            adminGiftTypeModels = Collections.<AdminGiftTypeModel>emptyList();
        }

        return new PageImpl<>(adminGiftTypeModels, pageable, total);
    }
}
