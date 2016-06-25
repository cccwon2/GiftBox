package me.memorytalk.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Gift;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QGift;
import me.memorytalk.dto.GiftModel;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

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
}
