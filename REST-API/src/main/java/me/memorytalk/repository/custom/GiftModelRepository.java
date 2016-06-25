package me.memorytalk.repository.custom;

import me.memorytalk.dto.GiftModel;

import java.util.List;

public interface GiftModelRepository {

    List<GiftModel> findGiftModels(Long eventId);
}
