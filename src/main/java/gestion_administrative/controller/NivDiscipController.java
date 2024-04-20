package gestion_administrative.controller;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gestion_administrative.entities.Discipline;
import gestion_administrative.entities.NivDiscip;
import gestion_administrative.entities.Niveau;
import gestion_administrative.helper.ExcelHelper;
import gestion_administrative.service.DisciplineService;
import gestion_administrative.service.NivDiscipService;
import gestion_administrative.service.NiveauService;

@RestController
@RequestMapping("/niv_discip")
public class NivDiscipController {

    @Autowired
    private NivDiscipService nivDiscipService;

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private NiveauService niveauService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/insert")
    public ResponseEntity<String> insertNivDiscip(@RequestBody String nivDiscipJson) throws IOException {
        // Convert JSON to ObjectNode to extract fields manually
        JsonNode rootNode = objectMapper.readTree(nivDiscipJson);
        String codeNivDiscip = rootNode.get("codeNivDiscip").asText();
        int nbreHeures = rootNode.get("nbreHeures").asInt(); // Adjusted to parse as integer
        int disciplineId = rootNode.get("discipline").asInt(); // Assuming discipline is an integer
        int niveauId = rootNode.get("niveau").asInt(); // Assuming niveau is an integer

        // Retrieve Discipline and Niveau objects by ID
        Discipline discipline = disciplineService.getById(disciplineId);
        Niveau niveau = niveauService.getById(niveauId);

        if (discipline == null || niveau == null) {
            return new ResponseEntity<>("Discipline or Niveau not found", HttpStatus.NOT_FOUND);
        }

        // Create a new NivDiscip object
        NivDiscip nivDiscip = new NivDiscip();
        nivDiscip.setCodeNivDiscip(codeNivDiscip);
        nivDiscip.setNbreHeures(nbreHeures);
        nivDiscip.setDiscipline(discipline); // Set the Discipline object
        nivDiscip.setNiveau(niveau); // Set the Niveau object

        // Save the NivDiscip
        nivDiscipService.save(nivDiscip);

        return new ResponseEntity<>("Niv_Discip inserted successfully", HttpStatus.CREATED);
    }



    @PutMapping("/update")
    public ResponseEntity<String> updateNivDiscip(@RequestBody String nivDiscipJson) throws JsonParseException, JsonMappingException, IOException {
        // Convert JSON to Niv_Discip object
        NivDiscip nivDiscip = objectMapper.readValue(nivDiscipJson, NivDiscip.class);

        // Update the Niv_Discip
        nivDiscipService.save(nivDiscip);

        return new ResponseEntity<>("Niv_Discip updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNivDiscip(@PathVariable int id) {
        NivDiscip nivDiscip = nivDiscipService.getById(id);
        if (nivDiscip != null) {
            nivDiscipService.delete(nivDiscip);
            return new ResponseEntity<>("Niv_Discip deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Niv_Discip not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getNivDiscipById(@PathVariable int id) {
        NivDiscip nivDiscip = nivDiscipService.getById(id);
        if (nivDiscip != null) {
            ObjectNode nivDiscipNode = objectMapper.createObjectNode();
            // Populate nivDiscipNode with Niv_Discip data as needed
            nivDiscipNode.put("idNivDiscip", nivDiscip.getIdNivDiscip());
            nivDiscipNode.put("codeNivDiscip", nivDiscip.getCodeNivDiscip());
            nivDiscipNode.put("nbreHeures", nivDiscip.getNbreHeures());
            nivDiscipNode.put("discipline", nivDiscip.getDiscipline().getIdDiscip()); // Assuming discipline has getId method
            nivDiscipNode.put("niveau", nivDiscip.getNiveau().getIdNiveau()); // Assuming niveau has getId method
            
            String jsonResult = nivDiscipNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllNivDiscips() {
        List<NivDiscip> nivDiscips = nivDiscipService.getAll();
        if (nivDiscips != null) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (NivDiscip nivDiscip : nivDiscips) {
                ObjectNode nivDiscipNode = objectMapper.createObjectNode();
                // Populate nivDiscipNode with Niv_Discip data as needed
                nivDiscipNode.put("idNivDiscip", nivDiscip.getIdNivDiscip());
                nivDiscipNode.put("codeNivDiscip", nivDiscip.getCodeNivDiscip());
                nivDiscipNode.put("nbreHeures", nivDiscip.getNbreHeures());
                nivDiscipNode.put("discipline", nivDiscip.getDiscipline().getIdDiscip()); // Assuming discipline has getId method
                nivDiscipNode.put("niveau", nivDiscip.getNiveau().getIdNiveau()); // Assuming niveau has getId method
                
                arrayNode.add(nivDiscipNode);
            }
            String jsonResult = arrayNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Niv_Discips found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/import")
    public ResponseEntity<String> importNivDiscips(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            List<NivDiscip> nivDiscips = ExcelHelper.excelToNivDiscip(file.getInputStream());
            nivDiscipService.saveAll(nivDiscips);
            return ResponseEntity.ok("NivDiscips imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing N: " + e.getMessage());
        }
    }
   
}
