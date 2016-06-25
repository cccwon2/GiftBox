package me.memorytalk.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import me.memorytalk.domain.QEvent;
import me.memorytalk.domain.QTag;
import me.memorytalk.domain.Tag;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.List;

public class TagRepositoryImpl extends QueryDslRepositorySupport implements TagModelRepository {

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    public List<String> findTags(Long eventId) {

        QTag qTag = QTag.tag;
        QEvent qEvent = QEvent.event;
        JPQLQuery query = from(qTag);
        query.innerJoin(qTag.event, qEvent).on(qEvent.id.eq(eventId));

        return query.list(qTag.name);
    }
}
