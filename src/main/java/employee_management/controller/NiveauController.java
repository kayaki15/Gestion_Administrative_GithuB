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
import employee_management.entities.Niveau;
import employee_management.helper.ExcelHelper;
import employee_management.service.NiveauService;



@RestController
@RequestMapping("/niveau")
public class NiveauController {

    @Autowired
    private NiveauService niveauService;
    
    @Autowired
    private ObjectMapper objectMapper;


//    @PostMapping("/insert")
//    public ResponseEntity<String> insertNiveau(@RequestBody String niveauJson) throws JsonParseException, JsonMappingException, IOException {
//        // Convertir le JSON en objet Niveau
//        Niveau niveau = objectMapper.readValue(niveauJson, Niveau.class);
//
//        // Insérer l'employé
//        niveauService.save(niveau);
//
//        return new ResponseEntity<>("Niveau inserted successfully", HttpStatus.CREATED);
//    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertNiveau(@RequestBody String niveauJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Niveau
        Niveau niveau = objectMapper.readValue(niveauJson, Niveau.class);

        // Check if a record with the same code exists
        if (niveauService.existsByCodeNiv(niveau.getCodeNiv())) {
            return new ResponseEntity<>("Error: Niveau with the same code already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if a record with the same name exists
        if (niveauService.existsByCodeNiv(niveau.getNomNiv())) {
            return new ResponseEntity<>("Error: Niveau with the same name already exists", HttpStatus.BAD_REQUEST);
        }

        // Insert the Niveau
        niveauService.save(niveau);

        return new ResponseEntity<>("Niveau inserted successfully", HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateNiveau(@RequestBody String niveauJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Niveau
        Niveau niveau = objectMapper.readValue(niveauJson, Niveau.class);
                
        // modifier l'employé
        niveauService.save(niveau);

        return new ResponseEntity<>("Niveau updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNiveau(@PathVariable int id) {
        Niveau niveau = niveauService.getById(id);
        if (niveau != null) {
            niveauService.delete(niveau);
            return new ResponseEntity<>("Niveau deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Niveau not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getNiveauById(@PathVariable int id) {
        Niveau niveau = niveauService.getById(id);
        if (niveau != null) {
        	
            ObjectNode niveauNode = objectMapper.createObjectNode();
            niveauNode.put("id", niveau.getIdNiveau());
            niveauNode.put("codeNiv", niveau.getCodeNiv());
            niveauNode.put("nomNiv", niveau.getNomNiv());
            String jsonResult = niveauNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> getAllNiveaus() {
        List<Niveau> niveaus = niveauService.getAll();
        
        if (niveaus != null) {
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for (Niveau niveau : niveaus) {
                ObjectNode niveauNode = objectMapper.createObjectNode();
                niveauNode.put("id", niveau.getIdNiveau());
                niveauNode.put("codeNiv", niveau.getCodeNiv());
                niveauNode.put("nomNiv", niveau.getNomNiv());
                arrayNode.add(niveauNode);
            }

            String jsonResult = arrayNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            // Gérer le cas où la liste des employés est null
            return new ResponseEntity<>("No niveau found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> importNiveaus(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            List<Niveau> niveaus = ExcelHelper.excelToNiveaux(file.getInputStream());
            niveauService.saveAll(niveaus);
            return ResponseEntity.ok("Niveaus imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing Niveaus: " + e.getMessage());
        }
    }

}
