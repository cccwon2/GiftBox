package me.memorytalk.repository.custom;

import java.util.List;

public interface TagModelRepository {

    List<String> findTags(Long eventId);
}
