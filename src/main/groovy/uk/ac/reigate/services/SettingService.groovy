package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.system.Setting
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.system.SettingRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class SettingService implements ICoreDataService<Setting, Integer>{
    
    @Autowired
    SettingRepository settingRepository
    
    /**
     * Default NoArgs constructor
     */
    SettingService() {}
    
    /**
     * Autowired Constructor
     *
     * @param settingRepository
     */
    SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }
    
    /**
     * Find an individual setting using the settings ID fields
     *
     * @param id the ID fields to search for
     * @return the Setting object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Setting findById(Integer id) {
        return settingRepository.findOne(id);
    }
    
    /**
     * Find an individual setting using the setting field
     * 
     * @param setting The setting field to search for
     * @return the Setting object that matches the setting supplied, or null if not found
     */
    @PreAuthorize("@securityChecker.checkWriter(authentication)")
    @Transactional(readOnly = true)
    Setting findSettingBySetting(String setting) {
        return settingRepository.findBySetting(setting);
    }
    
    /**
     * Find a single page of Setting objects
     * @return a SearchResult set with the list of Settings
     */
    @Override
    @Transactional(readOnly = true)
    List<Setting> findAll() {
        return settingRepository.findAll();
    }
    
    /**
     * @param id
     * @param setting
     * @param value
     * @param description
     * @return
     */
    @Transactional
    public Setting saveSetting(Integer id, String setting, String value, String description) {
        ValidationUtils.assertNotBlank(setting, "setting cannot be blank");
        ValidationUtils.assertNotNull(value, "value is mandatory");
        
        Setting settingg = null;
        
        if (id != null) {
            settingg = findById(id);
            
            settingg.setSetting(setting);
            settingg.setValue(value);
            settingg.setDescription(description)
            
            save(settingg);
        } else {
            settingg = save(new Setting(setting, value, description));
        }
        
        return settingg;
    }
    
    /**
     * This service method is used to save a complete Setting object in the database
     *
     * @param setting the new Setting object to be saved
     * @return the saved version of the Setting object
     */
    @Override
    @Transactional
    public Setting save(Setting setting) {
        return settingRepository.save(setting)
    }
    
    /**
     * This service method is used to update an Setting object in the database from a partial or complete Setting object.
     *
     * @param setting the partial or complete Setting object to be saved
     * @return the saved version of the Setting object
     */
    @Transactional
    public Setting updateSetting(Setting setting) {
        Setting settingToSave = findById(setting.id)
        settingToSave.setting = setting.setting != null ? setting.setting : settingToSave.setting
        settingToSave.value = setting.value != null ? setting.value : settingToSave.value
        settingToSave.description = setting.description != null ? setting.description : settingToSave.description
        return save(settingToSave)
    }
    
    /**
     * Saves a list of Setting objects to the database
     *
     * @param settings a list of Settings to be saved to the database
     * @return the list of save Setting objects
     */
    @Transactional
    public List<Setting> saveSettings(List<Setting> settings) {
        return settings.collect { setting -> save(setting) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Setting should not be deleted.
     */
    @Override
    public void delete(Setting obj) {
        throw new InvalidOperationException("Setting should not be deleted")
    }
    
    /**
     * This method is a shorthand version of the findSettingBySetting(setting) method.
     */
    public Setting getSetting(String setting) {
        return findSettingBySetting(setting)
    }
}
