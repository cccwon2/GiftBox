package me.memorytalk.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.GiftTypeCode;
import me.memorytalk.domain.QGiftTypeCode;
import me.memorytalk.dto.AdminGiftTypeCodeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.Collections;
import java.util.List;

public class GiftTypeCodeRepositoryImpl extends QueryDslRepositorySupport implements GiftTypeCodeModelRepository {

    public GiftTypeCodeRepositoryImpl() {
        super(GiftTypeCode.class);
    }

    public Page<AdminGiftTypeCodeModel> findAdminGiftTypeCodeModels(Pageable pageable) {

        QGiftTypeCode qGiftTypeCode = QGiftTypeCode.giftTypeCode;

        JPQLQuery query = from(qGiftTypeCode);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminGiftTypeCodeModel> adminGiftTypeCodeModels;

        if(total > pageable.getOffset()) {
            adminGiftTypeCodeModels = pagedQuery.list(ConstructorExpression.create(AdminGiftTypeCodeModel.class,
                    qGiftTypeCode.id,
                    qGiftTypeCode.name
            ));
        } else {
            adminGiftTypeCodeModels = Collections.<AdminGiftTypeCodeModel>emptyList();
        }

        return new PageImpl<>(adminGiftTypeCodeModels, pageable, total);
    }
}
