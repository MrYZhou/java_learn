package com.lar.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Configuration
public class TimeFormatConvert {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // 序列化为时间字符串给前端,和前端时间字符串反序列化为日期对象
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(TimeFormat.DateTime)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(TimeFormat.Date)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TimeFormat.Time)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(TimeFormat.DateTime)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(TimeFormat.Date)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TimeFormat.Time)));

        javaTimeModule.addSerializer(Date.class, new DateSerializer());
        javaTimeModule.addDeserializer(Date.class, new DateDeserializer());

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    public interface TimeFormat {
        String DateTime = "yyyy-MM-dd HH:mm:ss";
        String Date = "yyyy-MM-dd";
        String Time = "HH:mm:ss";
    }

    public static class DateSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date date, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
            if (this.hasJsonFormatAnnotation(date, gen)) return;

            SimpleDateFormat formatter = new SimpleDateFormat(TimeFormat.DateTime);
            formatter.setLenient(true);
            formatter.setTimeZone(TimeZone.getDefault());
            String formattedDate = formatter.format(date);
            gen.writeString(formattedDate);
        }

        private Boolean hasJsonFormatAnnotation(Date value, JsonGenerator gen) {
            // 获取value来源的类
            Class<?> aClass = gen.getCurrentValue().getClass();
            // 获取字段名
            String currentName = gen.getOutputContext().getCurrentName();
            try {
                // 获取字段
                Field declaredField = aClass.getDeclaredField(currentName);
                // 是否被@JsonFormat修饰
                boolean annotationPresent = declaredField.isAnnotationPresent(JsonFormat.class);

                if (annotationPresent) {
                    String pattern = declaredField.getAnnotation(JsonFormat.class).pattern();
                    if (pattern != null && !"".equals(pattern)) {
                        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                        formatter.setTimeZone(TimeZone.getDefault());
                        gen.writeString(formatter.format(value));
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static class DateDeserializer extends JsonDeserializer<Date> {
        @SneakyThrows
        @Override
        public Date deserialize(JsonParser p, DeserializationContext deserializationContext) {
            if (p.getText() == null || "".equals(p.getText().trim())) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(TimeFormat.DateTime);
            formatter.setLenient(true);
            formatter.setTimeZone(TimeZone.getDefault());
            if (p.getText().length() <= 10) {
                formatter = new SimpleDateFormat(TimeFormat.Date);
                if (p.getText().charAt(4) != '-') {
                    formatter = new SimpleDateFormat(TimeFormat.Time);
                }
            }
            return formatter.parse(p.getText());
        }
    }

}
