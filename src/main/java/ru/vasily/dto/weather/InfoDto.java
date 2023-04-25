package ru.vasily.dto.weather;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoDto {

    @JsonProperty("lat")
    private Integer lat;

    @JsonProperty("lon")
    private Integer lon;

    @JsonProperty("def_pressure_mm")
    private Integer defPressureMm;

    @JsonProperty("def_pressure_pa")
    private Integer defPressurePa;

    @JsonProperty("url")
    private String url;
}
