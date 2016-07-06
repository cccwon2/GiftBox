package me.memorytalk.repository.custom;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Banner;
import me.memorytalk.domain.QAttachment;
import me.memorytalk.domain.QBanner;
import me.memorytalk.dto.AdminBannerModel;
import me.memorytalk.dto.BannerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class BannerRepositoryImpl extends QueryDslRepositorySupport implements BannerModelRepository {

    public BannerRepositoryImpl() {
        super(Banner.class);
    }

    public Page<BannerModel> findBannerModels(Pageable pageable) {

        QBanner qBanner = QBanner.banner;
        QAttachment qAttachment = QAttachment.attachment;

        JPQLQuery query = from(qBanner);
        query.leftJoin(qBanner.attachments, qAttachment).on(qAttachment.sort.eq(1));
        query.where(qBanner.visible.isTrue());

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<BannerModel> bannerModels;

        if(total > pageable.getOffset()) {
            bannerModels = pagedQuery.list(ConstructorExpression.create(BannerModel.class,
                    qBanner.id,
                    qBanner.eventId,
                    qBanner.bannerPage,
                    qAttachment.width,
                    qAttachment.height,
                    qAttachment.url,
                    qAttachment.thumbnailS,
                    qAttachment.thumbnailM,
                    qAttachment.thumbnailL
            ));
        } else {
            bannerModels = Collections.<BannerModel>emptyList();
        }

        return new PageImpl<>(bannerModels, pageable, total);
    }

    public Page<AdminBannerModel> findAdminBannerModels(String visible, Pageable pageable) {

        QBanner qBanner = QBanner.banner;
        QAttachment qAttachment = QAttachment.attachment;

        JPQLQuery query = from(qBanner);
        query.leftJoin(qBanner.attachments, qAttachment).on(qAttachment.sort.eq(1));

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(!StringUtils.isEmpty(visible)) {
            whereBuilder.and(qBanner.visible.eq(Boolean.valueOf(visible)));
        }
        query.where(whereBuilder);

        long total = query.count();
        JPQLQuery pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<AdminBannerModel> adminBannerModels;

        if(total > pageable.getOffset()) {
            adminBannerModels = pagedQuery.list(ConstructorExpression.create(AdminBannerModel.class,
                    qBanner.id,
                    qBanner.eventId,
                    qBanner.bannerPage,
                    qBanner.visible,
                    qAttachment.width,
                    qAttachment.height,
                    qAttachment.url,
                    qAttachment.thumbnailS,
                    qAttachment.thumbnailM,
                    qAttachment.thumbnailL
            ));
        } else {
            adminBannerModels = Collections.<AdminBannerModel>emptyList();
        }

        return new PageImpl<>(adminBannerModels, pageable, total);
    }
}
