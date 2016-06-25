package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Gift;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QGift;
import me.memorytalk.dto.AdminGiftModel;
import me.memorytalk.dto.GiftModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class GiftRepositoryImpl extends QueryDslRepositorySupport implements GiftModelRepository {

    public GiftRepositoryImpl() {
        super(Gift.class);
    }


    public List<GiftModel> findGiftModels(Long eventId) {

        QGift qGift = QGift.gift;
        QEvent qEvent = QEvent.event;
        JPQLQuery query = from(qGift);
        query.innerJoin(qGift.event, qEvent).on(qEvent.id.eq(eventId));

        List<GiftModel> giftModels = query.list(ConstructorExpression.create(GiftModel.class,
                qGift.product,
                qGift.count
        ));

        return giftModels;
    }

    public Long deleteByEventId(Long eventId) {

        QGift qGift = QGift.gift;

        return new JPADeleteClause(getEntityManager(), qGift).where(qGift.event.id.eq(eventId)).execute();
    }

    public AdminGiftModel findAdminGiftModel(Long id) {

        QGift qGift = QGift.gift;
        QEvent qEvent = QEvent.event;

        JPQLQuery query = from(qGift);
        query.innerJoin(qGift.event, qEvent);
        query.where(qGift.id.eq(id));

        AdminGiftModel adminGiftModel = query.uniqueResult(ConstructorExpression.create(AdminGiftModel.class,
                qGift.id,
                qGift.createdDate,
                qGift.product,
                qGift.count,
                qEvent.id,
                qEvent.title
        ));

        return adminGiftModel;
    }

    public Page<AdminGiftModel> findAdminGiftModels(String eventId, String product, Pageable pageable) {

        QGift qGift = QGift.gift;
        QEvent qEvent = QEvent.event;

        JPQLQuery query = from(qGift);
        query.innerJoin(qGift.event, qEvent);

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(eventId)) {
            whereBuilder.and(qEvent.id.eq(Long.valueOf(eventId)));
        } else if(!StringUtils.isEmpty(product)) {
            whereBuilder.and(qGift.product.contains(product));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminGiftModel> adminGiftModels;

        if(total > pageable.getOffset()) {
            adminGiftModels = pagedQuery.list(ConstructorExpression.create(AdminGiftModel.class,
                    qGift.id,
                    qGift.createdDate,
                    qGift.product,
                    qGift.count,
                    qEvent.id,
                    qEvent.title
            ));
        } else {
            adminGiftModels = Collections.<AdminGiftModel>emptyList();
        }

        return new PageImpl<>(adminGiftModels, pageable, total);
    }
}
