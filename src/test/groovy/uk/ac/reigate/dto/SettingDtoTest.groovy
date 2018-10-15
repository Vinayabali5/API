package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.system.Setting


public class SettingDtoTest {
    
    private Setting setting1
    
    private Setting setting2
    
    private List<Setting> settings
    
    @Before
    public void setup() {
        setting1 = new Setting(
                id: 1,
                setting:'S',
                value: '122',
                description: 'The suffix of the student email addresses to be used by the student email generation routines'
                );
        setting2 = new Setting(
                id: 2,
                setting:'F',
                value: '123',
                description: 'The suffix of the student email addresses to be used by the student email'
                );
        settings = Arrays.asList(setting1, setting2);
    }
    
    @Test
    public void testMapFromSettingEntity(){
        SettingDto settingTest = SettingDto.mapFromSettingEntity( setting1 )
        assertEquals( settingTest.id, setting1.id );
        assertEquals( settingTest.setting, setting1.setting);
        assertEquals( settingTest.value, setting1.value);
    }
    
    @Test
    public void testMapFromSettingsEntities(){
        List<SettingDto> settingsDtoTest = SettingDto.mapFromSettingsEntities( settings )
        assertEquals( settingsDtoTest[0].id, setting1.id );
        assertEquals( settingsDtoTest[0].setting, setting1.setting);
        assertEquals( settingsDtoTest[0].value, setting1.value);
        assertEquals( settingsDtoTest[1].id, setting2.id );
        assertEquals( settingsDtoTest[1].setting, setting2.setting);
        assertEquals( settingsDtoTest[1].value, setting2.value);
    }
    
    @Test
    public void testMapToSettingEntity(){
        SettingDto settingDto = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description);
        Setting setting = SettingDto.mapToSettingEntity( settingDto );
        assertEquals( setting.id, setting1.id );
        assertEquals( setting.setting, setting1.setting);
        assertEquals( setting.value, setting1.value);
    }
    
    @Test
    public void testEquals_Same() {
        SettingDto settingDto1 = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description)
        SettingDto settingDto2 = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description)
        assertEquals(settingDto1, settingDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SettingDto settingDto1 = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description)
        SettingDto settingDto2 = new SettingDto(setting2.id, setting2.setting, setting2.value, setting2.description)
        assertNotEquals(settingDto1, settingDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SettingDto settingDto1 = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description)
        SettingDto settingDto2 = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description)
        assertEquals(settingDto1.hashCode(), settingDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SettingDto settingDto1 = new SettingDto(setting1.id, setting1.setting, setting1.value, setting1.description)
        SettingDto settingDto2 = new SettingDto(setting2.id, setting2.setting, setting2.value, setting2.description)
        assertNotEquals(settingDto1.hashCode(), settingDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Setting() {
        SettingDto settingDto = new SettingDto(setting1)
        assertEquals( settingDto.setting, setting1.setting )
        assertEquals( settingDto.value, setting1.value )
        assertEquals( settingDto.description, setting1.description )
    }
}
