package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateSerializer;

import java.util.Date;

@Data
public class AdminPopupModel {

    private Long id;

    private Long eventId;

    private String popupPage;

    @JsonSerialize(using = DateSerializer.class)
    private Date startDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date endDate;

    private boolean visible;

    public AdminPopupModel(Long id, Long eventId, String popupPage,
                           Date startDate, Date endDate, boolean visible) {
        this.id = id;
        this.eventId = eventId;
        this.popupPage = popupPage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visible = visible;
    }

}
