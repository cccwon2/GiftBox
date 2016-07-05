package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Popup;
import me.memorytalk.domain.QPopup;
import me.memorytalk.dto.AdminPopupModel;
import me.memorytalk.dto.PopupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class PopupRepositoryImpl extends QueryDslRepositorySupport implements PopupModelRepository {

    public PopupRepositoryImpl() {
        super(Popup.class);
    }

    public Page<PopupModel> findPopupModels(Pageable pageable) {

        QPopup qPopup = QPopup.popup;
        JPQLQuery query = from(qPopup);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<PopupModel> popupModels;

        if(total > pageable.getOffset()) {
            popupModels = pagedQuery.list(ConstructorExpression.create(PopupModel.class,
                    qPopup.id,
                    qPopup.eventId,
                    qPopup.popupPage,
                    qPopup.startDate,
                    qPopup.endDate
            ));
        } else {
            popupModels = Collections.<PopupModel>emptyList();
        }

        return new PageImpl<>(popupModels, pageable, total);
    }

    public Page<AdminPopupModel> findAdminPopupModels(String visible, Pageable pageable) {

        QPopup qPopup = QPopup.popup;
        JPQLQuery query = from(qPopup);
        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(visible)) {
            whereBuilder.and(qPopup.visible.eq(Boolean.valueOf(visible)));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminPopupModel> adminPopupModels;

        if(total > pageable.getOffset()) {
            adminPopupModels = pagedQuery.list(ConstructorExpression.create(AdminPopupModel.class,
                    qPopup.id,
                    qPopup.eventId,
                    qPopup.popupPage,
                    qPopup.startDate,
                    qPopup.endDate,
                    qPopup.visible
            ));
        } else {
            adminPopupModels = Collections.<AdminPopupModel>emptyList();
        }

        return new PageImpl<>(adminPopupModels, pageable, total);
    }
}
