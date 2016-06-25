package me.memorytalk.service;

import me.memorytalk.domain.Tag;
import me.memorytalk.dto.AdminTagModel;
import me.memorytalk.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    protected Page<AdminTagModel> getAdminTags(String eventId, String tagName, Pageable pageable) {

        return tagRepository.findAdminTagModels(eventId, tagName, pageable);
    }

    protected AdminTagModel getAdminTag(Long id) {

        return tagRepository.findAdminTagModel(id);
    }

    protected Boolean editAdminTag(Long id, String name) {

        Tag tag = tagRepository.findById(id);
        Assert.notNull(tag, "No tag info.");
        tag.setName(name);
        tagRepository.save(tag);

        return Boolean.TRUE;
    }

    protected Boolean removeAdminTag(Long id) {

        Tag tag = tagRepository.findById(id);
        Assert.notNull(tag, "No tag info.");

        tagRepository.delete(tag);

        return Boolean.TRUE;
    }
}
