package uk.ac.reigate.services.exam

import jcifs.smb.NtlmPasswordAuthentication
import jcifs.smb.SmbFile
import jcifs.smb.SmbFileOutputStream

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.stereotype.Service

import uk.ac.reigate.domain.exam.StudentOptionEntry
import uk.ac.reigate.domain.system.Setting
import uk.ac.reigate.exceptions.EdiCreationException
import uk.ac.reigate.repositories.exam.EdiDataRepository
import uk.ac.reigate.services.SettingService
import uk.ac.reigate.services.system.ServiceUserAccountService



@Service
class EdiDataGeneratorService {
    
    private static final Logger LOGGER = Logger.getLogger(EdiDataGeneratorService.class);
    
    @Autowired
    JdbcTemplate jdbcTemplate
    
    @Autowired
    EdiDataRepository ediDataRepository
    
    @Autowired
    SettingService settingService
    
    @Autowired
    ServiceUserAccountService serviceUserAccountService
    
    /**
     * Default No Args constructor
     */
    EdiDataGeneratorService() {}
    
    List<StudentOptionEntry> generateEntries() {
        List<StudentOptionEntry> studentOptionEntries = ediDataRepository.createStudentOptionEntry();
        return studentOptionEntries;
    }
    
    /**
     * This method is used to trigger the SQL stored procedure Exams.CreateEdiFileData 
     * 	
     * @param year a string that represent the year of the exam series
     * @param series a string that represents the exam series 
     * @param examBoardId the Exam Board ID 
     * @throws EdiCreationException if anything goes wrong the EdiCreationException is thrown
     */
    void generateEdiFile(String year, String series, Integer examBoardId) throws EdiCreationException {
        LOGGER.info("II - Generating EDI file for Exam Board ID $examBoardId. Exam Series: $year - $series");
        try {
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("Exams").withProcedureName("CreateEdiFileData");
            SqlParameterSource functionParameters = new MapSqlParameterSource().addValue("year", year).addValue("series", series).addValue("exam_board_id", examBoardId);
            Map<String, Object> out = simpleJdbcCall.execute(functionParameters);
        } catch (UncategorizedSQLException  ex) {
            String message = ex.cause.getMessage();
            throw new EdiCreationException(message);
        } catch (Exception ex) {
            throw new EdiCreationException();
        }
    }
    
    /**
     * This is the old method of generating an EDI file and should not be used anymore 
     * 
     * Generate the EDI file and set ediStatus to 4 - processed
     * 
     * @param year
     * @param series
     * @param examBoardId
     */
    @Deprecated
    Boolean generateEdiFileOriginal(String year, String series, Integer examBoardId) {
        // Get data to be stored within the EDI file
        List<String> ediFileData = ediDataRepository.getCreateEdiFileData(year, series, examBoardId);
        
        Boolean successful = false
        
        Setting setting = settingService.findSettingBySetting('ExamsExportFolder');
        if (setting != null) {
            // Generate the EDI file
            def filename
            def ediFile
            
            /*
             // Local Generation 
             filename = '/home/michael/Development/Testing/EDI/' + ediFileData.get(0).trim()
             ediFile = new File(filename);
             //			ediFile.text = '';
             for (int i = 1; i < ediFileData.size(); i++) {
             ediFile << ediFileData.get(i) + '\r\n'
             }
             */
            filename = setting.value[setting.value.size()-1] == '/' ? setting.value + ediFileData.get(0) : setting.value + '/' + ediFileData.get(0)
            
            // Add smb: to filename if it is not present
            if (filename[0..3] != 'smb:') {
                filename = 'smb:' + filename
            }
            
            // Network Generation
            NtlmPasswordAuthentication auth = serviceUserAccountService.getNtlmPasswordAuthentication()
            
            try {
                ediFile = new SmbFile(filename, auth);
                SmbFileOutputStream sfos = new SmbFileOutputStream(ediFile);
                
                // SMB Version
                for (int i = 1; i < ediFileData.size(); i++) {
                    byte[] line = ediFileData.get(i) + '\r\n'
                    sfos.write(line);
                }
                
                successful = true;
                LOGGER.info("II EDI file '$filename' successfully created.");
            } catch (Exception e) {
                successful = false;
                LOGGER.error("EE Unable to create file. Cause: " + e.getMessage());
            }
            
            
        }
        
        if (successful) {
            ediDataRepository.setEdiStatusToSubmitted(year, series, examBoardId);
        }
        
        return successful;
        
    }
}
