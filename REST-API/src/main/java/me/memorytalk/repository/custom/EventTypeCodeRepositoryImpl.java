package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.EventTypeCode;
import me.memorytalk.domain.QEventTypeCode;
import me.memorytalk.dto.AdminEventTypeCodeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class EventTypeCodeRepositoryImpl extends QueryDslRepositorySupport implements EventTypeCodeModelRepository {

    public EventTypeCodeRepositoryImpl() {
        super(EventTypeCode.class);
    }

    public List<AdminEventTypeCodeModel> findAdminEventTypeCodeModels(String sort) {

        QEventTypeCode qEventTypeCode = QEventTypeCode.eventTypeCode;
        JPQLQuery query = from(qEventTypeCode);

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(sort)) {
            whereBuilder.and(qEventTypeCode.sort.contains(sort));
        }
        query.where(whereBuilder);

        List<AdminEventTypeCodeModel> adminEventTypeCodeModels = query.list(ConstructorExpression.create(AdminEventTypeCodeModel.class,
                qEventTypeCode.id,
                qEventTypeCode.name,
                qEventTypeCode.color,
                qEventTypeCode.sort
        ));

        return adminEventTypeCodeModels;
    }

    public Page<AdminEventTypeCodeModel> findAdminEventTypeCodeModels(String sort, Pageable pageable) {

        QEventTypeCode qEventTypeCode = QEventTypeCode.eventTypeCode;

        JPQLQuery query = from(qEventTypeCode);

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(sort)) {
            whereBuilder.and(qEventTypeCode.sort.contains(sort));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminEventTypeCodeModel> adminEventTypeCodeModels;

        if(total > pageable.getOffset()) {
            adminEventTypeCodeModels = pagedQuery.list(ConstructorExpression.create(AdminEventTypeCodeModel.class,
                    qEventTypeCode.id,
                    qEventTypeCode.name,
                    qEventTypeCode.color,
                    qEventTypeCode.sort
            ));
        } else {
            adminEventTypeCodeModels = Collections.<AdminEventTypeCodeModel>emptyList();
        }

        return new PageImpl<>(adminEventTypeCodeModels, pageable, total);
    }
}
