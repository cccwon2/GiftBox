package me.memorytalk.service;

import me.memorytalk.domain.GiftType;
import me.memorytalk.domain.GiftTypeCode;
import me.memorytalk.dto.AdminGiftTypeModel;
import me.memorytalk.repository.GiftTypeCodeRepository;
import me.memorytalk.repository.GiftTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GiftTypeService {

    @Autowired
    private GiftTypeRepository giftTypeRepository;

    @Autowired
    private GiftTypeCodeRepository giftTypeCodeRepository;

    protected Page<AdminGiftTypeModel> getAdminEventTypes(String eventId, Pageable pageable) {

        return giftTypeRepository.findAdminGiftTypeModels(eventId, pageable);
    }

    protected AdminGiftTypeModel getAdminGiftType(Long id) {

        return giftTypeRepository.findAdminGiftTypeModel(id);
    }

    protected Boolean editAdminGiftType(Long id, Long giftTypeCodeId) {

        GiftType giftType = giftTypeRepository.findById(id);
        Assert.notNull(giftType, "No giftType info.");

        GiftTypeCode giftTypeCode = giftTypeCodeRepository.findById(giftTypeCodeId);
        Assert.notNull(giftTypeCode, "No giftTypeCode info.");

        giftType.setGiftTypeCode(giftTypeCode);
        giftTypeRepository.save(giftType);

        return Boolean.TRUE;
    }

    protected Boolean removeAdminGiftType(Long id) {

        GiftType giftType = giftTypeRepository.findById(id);
        Assert.notNull(giftType, "No giftType info.");

        giftTypeRepository.delete(giftType);

        return Boolean.TRUE;
    }
}
