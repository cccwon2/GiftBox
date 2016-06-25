package me.memorytalk.service;

import me.memorytalk.domain.Gift;
import me.memorytalk.dto.AdminGiftModel;
import me.memorytalk.repository.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GiftService {

    @Autowired
    private GiftRepository giftRepository;

    protected Page<AdminGiftModel> getAdminGifts(String eventId, String product, Pageable pageable) {

        return giftRepository.findAdminGiftModels(eventId, product, pageable);
    }

    protected AdminGiftModel getAdminGift(Long id) {

        return giftRepository.findAdminGiftModel(id);
    }

    protected Boolean editAdminGift(Long id, String product, int count) {

        Gift gift = giftRepository.findById(id);
        Assert.notNull(gift, "No gift info.");
        gift.setProduct(product);
        gift.setCount(count);
        giftRepository.save(gift);

        return Boolean.TRUE;
    }

    protected Boolean removeAdminGift(Long id) {

        Gift gift = giftRepository.findById(id);
        Assert.notNull(gift, "No gift info.");

        giftRepository.delete(gift);

        return Boolean.TRUE;
    }
}
