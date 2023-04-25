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
public class FactDto {

    @JsonProperty("temp")
    private Integer temp;

    @JsonProperty("feels_like")
    private Integer feelsLike;

    @JsonProperty("temp_water")
    private Integer tempWater;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("wind_speed")
    private Integer windSpeed;

    @JsonProperty("wind_gust")
    private Integer windGust;

    @JsonProperty("wind_dir")
    private String windDir;

    @JsonProperty("pressure_mm")
    private Integer pressureMm;

    @JsonProperty("pressure_pa")
    private Integer pressurePa;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("daytime")
    private String daytime;

    @JsonProperty("polar")
    private Boolean polar;

    @JsonProperty("season")
    private String season;

    @JsonProperty("obs_time")
    private Long obsTime;

    @JsonProperty("prec_type")
    private Integer precipitation;

}
