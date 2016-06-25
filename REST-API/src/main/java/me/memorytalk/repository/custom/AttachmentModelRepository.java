package me.memorytalk.repository.custom;

import me.memorytalk.dto.AttachmentModel;

import java.util.List;

public interface AttachmentModelRepository {

    List<AttachmentModel> findAttachmentModels(Long eventId);
}
