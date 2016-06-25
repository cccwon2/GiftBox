package me.memorytalk.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.ConstructorExpression;
import me.memorytalk.domain.Attachment;
import me.memorytalk.domain.QAttachment;
import me.memorytalk.domain.QEvent;
import me.memorytalk.dto.AttachmentModel;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.List;

public class AttachmentRepositoryImpl extends QueryDslRepositorySupport implements AttachmentModelRepository {

    public AttachmentRepositoryImpl() {
        super(Attachment.class);
    }

    public List<AttachmentModel> findAttachmentModels(Long eventId) {

        QAttachment qAttachment = QAttachment.attachment;
        QEvent qEvent = QEvent.event;
        JPQLQuery query = from(qAttachment);
        query.innerJoin(qAttachment.event, qEvent).on(qEvent.id.eq(eventId));

        List<AttachmentModel> attachmentModels = query.list(ConstructorExpression.create(AttachmentModel.class,
                qAttachment.width,
                qAttachment.height,
                qAttachment.url,
                qAttachment.thumbnailS,
                qAttachment.thumbnailM,
                qAttachment.thumbnailL
        ));

        return attachmentModels;
    }
}
