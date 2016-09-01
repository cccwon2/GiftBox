package me.memorytalk.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.PushNotification;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QPushNotification;
import me.memorytalk.dto.AdminPushNotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.Collections;
import java.util.List;

public class PushNotificationRepositoryImpl extends QueryDslRepositorySupport implements PushNotificationModelRepository {

    public PushNotificationRepositoryImpl() {
        super(PushNotification.class);
    }

    public Page<AdminPushNotificationModel> findAdminPushNotificationModels(Pageable pageable) {

        QPushNotification qPushNotification = QPushNotification.pushNotification;
        QEvent qEvent = QEvent.event;

        JPQLQuery query = from(qPushNotification);
        query.leftJoin(qPushNotification.event, qEvent);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminPushNotificationModel> adminPushNotificationModels;

        if(total > pageable.getOffset()) {
            adminPushNotificationModels = pagedQuery.list(ConstructorExpression.create(AdminPushNotificationModel.class,
                    qPushNotification.id,
                    qPushNotification.title,
                    qPushNotification.body,
                    qEvent.id,
                    qPushNotification.createdDate
            ));
        } else {
            adminPushNotificationModels = Collections.<AdminPushNotificationModel>emptyList();
        }

        return new PageImpl<>(adminPushNotificationModels, pageable, total);
    }
}
