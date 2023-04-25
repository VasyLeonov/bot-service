package ru.vasily.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty("now")
    private Long now;

    @JsonProperty("now_dt")
    private String nowDate;

    @JsonProperty("info")
    private InfoDto info;

    @JsonProperty("fact")
    private FactDto fact;
}
