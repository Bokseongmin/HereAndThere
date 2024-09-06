package com.mz.hat.support.utill;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mz.hat.vo.TouristAttrVo;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonLoader {
    private final ObjectMapper objectMapper;

    public JsonLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<TouristAttrVo> load_tourist_attr() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/json/tour_list.json");

        try (InputStream inputStream = resource.getInputStream()) {
            List<TouristAttrVo> touristAttractions = new ArrayList<>();

            Map<String, Object> jsonData = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});

            List<Map<String, Object>> records = (List<Map<String, Object>>) jsonData.get("records");

            for (Map<String, Object> record : records) {
                String name = (String) record.get("관광지명");
                String content = (String) record.get("관광지소개");
                String category = (String) record.get("관광지구분");
                String road_address = (String) record.get("소재지도로명주소");
                String parcel_address = (String) record.get("소재지지번주소");
                String lat = (String) record.get("위도");
                String lng = (String) record.get("경도");
                String tel = (String) record.get("관리기관전화번호");
                String assigned_date = (String) record.get("지정일자");

                TouristAttrVo touristAttrVo = new TouristAttrVo();
                touristAttrVo.setName(name);
                touristAttrVo.setContent(content);
                touristAttrVo.setRoad_address(road_address);
                touristAttrVo.setParcel_address(parcel_address);
                touristAttrVo.setLat(lat);
                touristAttrVo.setLng(lng);
                touristAttrVo.setTel(tel);
                touristAttrVo.setAssigned_date(convert_string_to_localdatetime(assigned_date));

                touristAttractions.add(touristAttrVo);
            }

            return touristAttractions;
        }
    }

    private LocalDateTime convert_string_to_localdatetime(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.atStartOfDay();
    }
}
