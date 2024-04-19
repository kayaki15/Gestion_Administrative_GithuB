package employee_management.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import employee_management.entities.Etablissement;
import employee_management.helper.ExcelHelper;
import employee_management.service.EtablissementService;

@RestController
@RequestMapping("/etablissements")
public class EtablissementController {

    @Autowired
    private EtablissementService etablissementService;
    
    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/insert")
    public ResponseEntity<String> insertEtablissement(@RequestBody String etablissementJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Etablissement
        Etablissement etablissement = objectMapper.readValue(etablissementJson, Etablissement.class);

        // Insérer l'établissement
        etablissementService.save(etablissement);

        return new ResponseEntity<>("Etablissement inserted successfully", HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateEtablissement(@RequestBody String etablissementJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Etablissement
        Etablissement etablissement = objectMapper.readValue(etablissementJson, Etablissement.class);
                
        // modifier l'établissement
        etablissementService.save(etablissement);

        return new ResponseEntity<>("Etablissement updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEtablissement(@PathVariable int id) {
        Etablissement etablissement = etablissementService.getById(id);
        if (etablissement != null) {
            etablissementService.delete(etablissement);
            return new ResponseEntity<>("Etablissement deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Etablissement not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEtablissementById(@PathVariable int id) {
        Etablissement etablissement = etablissementService.getById(id);
        if (etablissement != null) {
        	
            ObjectNode etablissementNode = objectMapper.createObjectNode();
            etablissementNode.put("idEtabli", etablissement.getIdEtabli());
            etablissementNode.put("codeEtab", etablissement.getCodeEtab());
            etablissementNode.put("nomEtab", etablissement.getNomEtab());
            
            
            String jsonResult = etablissementNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> getAllEtablissements() {
        List<Etablissement> etablissements = etablissementService.getAll();
        
        if (etablissements != null) {
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for (Etablissement etablissement : etablissements) {
                ObjectNode etablissementNode = objectMapper.createObjectNode();
                etablissementNode.put("idEtabli", etablissement.getIdEtabli());
                etablissementNode.put("codeEtab", etablissement.getCodeEtab());
                etablissementNode.put("nomEtab", etablissement.getNomEtab());
                arrayNode.add(etablissementNode);
            }

            String jsonResult = arrayNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            // Gérer le cas où la liste des établissements est null
            return new ResponseEntity<>("No etablissements found", HttpStatus.NOT_FOUND);
        }
    }
        @PostMapping("/import")
        public ResponseEntity<String> importEtablissements(@RequestParam("file") MultipartFile file) {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload a file");
            }

            try {
                List<Etablissement> etablissements = ExcelHelper.excelToEtablissements(file.getInputStream());
                etablissementService.saveAll(etablissements);
                return ResponseEntity.ok("Etablissements imported successfully");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing Etablissements");
            }
        }

	
}
