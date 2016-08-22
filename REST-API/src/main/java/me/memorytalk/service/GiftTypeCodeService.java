package me.memorytalk.service;

import me.memorytalk.domain.GiftTypeCode;
import me.memorytalk.dto.AdminGiftTypeCodeModel;
import me.memorytalk.repository.GiftTypeCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class GiftTypeCodeService {

    @Autowired
    private GiftTypeCodeRepository giftTypeCodeRepository;

    protected Page<AdminGiftTypeCodeModel> getAdminGiftTypeCodes(Pageable pageable) {

        return giftTypeCodeRepository.findAdminGiftTypeCodeModels(pageable);
    }

    protected Boolean addAdminGiftTypeCode(String name) {

        GiftTypeCode giftTypeCode = giftTypeCodeRepository.findByName(name);
        Assert.isNull(giftTypeCode, "giftTypeCode already exist.");

        giftTypeCode = new GiftTypeCode();
        giftTypeCode.setName(name);
        giftTypeCode.setCreatedDate(new Date());
        giftTypeCodeRepository.save(giftTypeCode);

        return Boolean.TRUE;
    }

    protected Boolean editAdminGiftTypeCode(Long id, String name) {

        GiftTypeCode giftTypeCode = giftTypeCodeRepository.findById(id);
        Assert.notNull(giftTypeCode, "No giftTypeCode info.");

        giftTypeCode.setName(name);
        giftTypeCodeRepository.save(giftTypeCode);

        return Boolean.TRUE;
    }

    protected Boolean removeAdminGiftTypeCode(Long id) {

        GiftTypeCode giftTypeCode = giftTypeCodeRepository.findById(id);
        Assert.notNull(giftTypeCode, "No giftTypeCode info.");

        giftTypeCodeRepository.delete(giftTypeCode);

        return Boolean.TRUE;
    }
}
