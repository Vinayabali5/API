package uk.ac.reigate.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import groovy.transform.EqualsAndHashCode;
import uk.ac.reigate.domain.system.Setting

/**
 *
 * JSON serializable DTO containing Setting data
 *
 */
@JsonSerialize
@EqualsAndHashCode(includeFields=true)
public class SettingDto implements Serializable {
    
    @JsonProperty
    private Integer id;
    
    @JsonProperty
    private String setting;
    
    @JsonProperty
    private String value;
    
    @JsonProperty
    private String description;
    
    /**
     * Default No Args constructor
     */
    public SettingDto() {
    }
    
    /**
     * Constructor to create a SettingDto object
     *
     * @param id the Id for the Setting
     * @param setting the setting for the Setting
     * @param value the value for the Setting
     */
    public SettingDto(Integer id, String setting, String value, String description) {
        this.id = id;
        this.setting = setting;
        this.value = value;
        this.description = description;
    }
    
    /**
     * Constructor to create a SettingDto object from a Setting object
     *
     * @param setting the Setting object to use for construction
     */
    SettingDto(Setting setting) {
        this.id = setting.id;
        this.setting = setting.setting;
        this.value = setting.value;
        this.description = setting.description;
    }
    
    @Override
    public String toString() {
        return "SettingDto [id=" + id + ", setting=" + setting + ", value=" + value + ", description=" + description +"]";
    }
    
    public static SettingDto mapFromSettingEntity(Setting setting) {
        return new SettingDto(setting);
    }
    
    public static List<SettingDto> mapFromSettingsEntities(List<Setting> settings) {
        List<SettingDto> output = settings.collect { setting ->  new SettingDto(setting) };
        return output
    }
    
    public static Setting mapToSettingEntity(SettingDto settingDto) {
        return new Setting(settingDto.id, settingDto.setting, settingDto.value, settingDto.description)
    }
}
