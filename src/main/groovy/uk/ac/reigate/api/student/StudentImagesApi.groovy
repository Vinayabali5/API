package uk.ac.reigate.api.student

import java.nio.file.Files

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import uk.ac.reigate.exceptions.NoImageFoundException

@RestController
class StudentImagesApi {
    
    private static final String DIRECTORY = "//home//michael//Development//Source//cis//student-images";
    
    private static final String NO_IMAGE = "no-image.png";
    
    private class PdfFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".pdf");
        }
    }
    
    private File[] getFileListing() {
        File dir = new File(DIRECTORY); // current directory
        
        FilenameFilter filter = new PdfFileFilter()
        
        return dir.listFiles();
    }
    
    @RequestMapping("/testing/list")
    protected ResponseEntity<Map<String, Object>> listFiles(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        File[] files = getFileListing();
        map.put("dir", files);
        return new ResponseEntity<?>(map, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/testing", method = RequestMethod.GET, params = "filename")
    protected ResponseEntity<byte[]> loadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String filename) throws Exception {
        // Change below to proper logger, if you have one configured.
        System.out.println("Trying to find file: " + filename + " from directory: " + DIRECTORY + ".");
        
        File imgFile = new File(DIRECTORY + "//" + filename);
        String contentType = Files.probeContentType(imgFile.toPath());
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(imgFile.toPath())
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(bytes.length);
        headers.setContentDispositionFormData("filename", filename);
        
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/students/{studentId}/image", method = RequestMethod.GET)
    protected ResponseEntity<byte[]> loadStudentImage(HttpServletRequest request, HttpServletResponse response, @PathVariable String studentId) throws Exception, NoImageFoundException {
        // Change below to proper logger, if you have one configured.
        String filename
        File imgFile = new File(DIRECTORY + "//" + studentId + ".jpg");
        if (!imgFile.exists()) {
            imgFile = new File(DIRECTORY + "//" + NO_IMAGE);
            if (!imgFile.exists()) {
                throw new NoImageFoundException(404, "No default image was found!")
            }
            filename = NO_IMAGE
        } else {
            filename = studentId + ".jpg"
        }
        
        String contentType = Files.probeContentType(imgFile.toPath());
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(imgFile.toPath())
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(bytes.length);
        headers.setContentDispositionFormData("filename", filename);
        
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
    
}
