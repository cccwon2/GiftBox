package me.memorytalk.common.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        if(date == null) return;

        String dateToString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dt = DateTime.parse(dateToString, dtf).toDateTime(DateTimeZone.UTC);
        String dateString = dt.toString("yyyy-MM-dd HH:mm:ss");

        jsonGenerator.writeString(dateString);
    }

}
