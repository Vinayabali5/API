package uk.ac.reigate.services

import static org.mockito.Mockito.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.system.Setting
import uk.ac.reigate.model.SearchResult
import uk.ac.reigate.repositories.system.SettingRepository


class SettingServiceTest {
    
    private SettingRepository settingRepository;
    
    private SettingService settingService;
    
    Setting setting1
    
    Setting setting2
    
    @Before
    public void setup() {
        this.settingRepository = mock(SettingRepository.class);
        this.settingService = new SettingService(settingRepository);
        
        setting1 = new Setting(id: 1, setting:'S', value: '122')
        setting2 = new Setting(id: 2, setting:'M', value: '200' )
        
        when(settingRepository.findAll()).thenReturn([setting1, setting2]);
        when(settingRepository.findOne(1)).thenReturn(setting1);
        when(settingRepository.findOne(null)).thenThrow(new IllegalArgumentException());
        when(settingRepository.save(any(Setting.class))).thenReturn(setting1);
    }
    
    
    @Test
    public void testFindSetting() {
        List<Setting> result = settingService.findAll();
        verify(settingRepository, times(1)).findAll()
        verifyNoMoreInteractions(settingRepository)
    }
    
    @Test
    public void testFindSettingById() {
        Setting result = settingService.findById(1);
        verify(settingRepository, times(1)).findOne(1)
        verifyNoMoreInteractions(settingRepository)
    }
    
    @Test
    public void testSaveNewSetting() {
        setting1.id = null
        settingService.save(setting1);
        verify(settingRepository, times(1)).save(setting1)
        verifyNoMoreInteractions(settingRepository)
    }
    
    @Test
    public void testSaveSetting() {
        settingService.save(setting1);
        verify(settingRepository, times(1)).save(setting1)
        verifyNoMoreInteractions(settingRepository)
    }
}

