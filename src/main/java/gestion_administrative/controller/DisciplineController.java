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
import gestion_administrative.helper.ExcelHelper;
import gestion_administrative.service.DisciplineService;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;
    
    @Autowired
    private ObjectMapper objectMapper;


//    @PostMapping("/insert")
//    public ResponseEntity<String> insertDiscipline(@RequestBody String disciplineJson) throws JsonParseException, JsonMappingException, IOException {
//        // Convertir le JSON en objet Discipline
//        Discipline discipline = objectMapper.readValue(disciplineJson, Discipline.class);
//
//        // Insérer l'discipline
//        disciplineService.save(discipline);
//
//        return new ResponseEntity<>("Discipline inserted successfully", HttpStatus.CREATED);
//    }
    @PostMapping("/insert")
    public ResponseEntity<String> insertDiscipline(@RequestBody String disciplineJson) throws JsonParseException, JsonMappingException, IOException {
        // Convert the JSON to a Discipline object
        Discipline discipline = objectMapper.readValue(disciplineJson, Discipline.class);

        // Check if a discipline with the same code and name already exists
        Discipline existingDiscipline = disciplineService.getByCodeAndName(discipline.getCodeDiscip(), discipline.getNomDiscip());
        if (existingDiscipline != null) {
            // Discipline with the same code and name already exists
            return new ResponseEntity<>("The discipline already exists in the database.", HttpStatus.BAD_REQUEST);
        }

        // Insert the discipline
        disciplineService.save(discipline);

        return new ResponseEntity<>("Discipline inserted successfully", HttpStatus.CREATED);
    }



//    @PutMapping("/update")
//    public ResponseEntity<String> updateDiscipline(@RequestBody String disciplineJson) throws JsonParseException, JsonMappingException, IOException {
//        // Convertir le JSON en objet Discipline
//        Discipline discipline = objectMapper.readValue(disciplineJson, Discipline.class);
//                
//        // modifier l'discipline
//        disciplineService.save(discipline);
//
//        return new ResponseEntity<>("Discipline updated successfully", HttpStatus.OK);
//    }
//    
//    @PutMapping("/update")
//    public ResponseEntity<String> updateDiscipline(@RequestBody String disciplineJson) throws JsonParseException, JsonMappingException, IOException {
//        // Convert JSON to Discipline object
//        Discipline discipline = objectMapper.readValue(disciplineJson, Discipline.class);
//        
//        // Update the discipline
//        discipline = disciplineService.save(discipline);
//
//        // Return the updated discipline as JSON
//        ObjectNode disciplineNode = objectMapper.createObjectNode();
//        disciplineNode.put("id", discipline.getIdDiscip());
//        disciplineNode.put("codeDiscip", discipline.getCodeDiscip());
//        disciplineNode.put("nomDiscip", discipline.getNomDiscip());
//
//        return new ResponseEntity<>(disciplineNode.toString(), HttpStatus.OK);
//    }

    
    @PutMapping("/update")
    public ResponseEntity<String> updateDiscipline(@RequestBody JsonNode requestBody) {
        // Extract discipline ID from the request body
        int disciplineId = requestBody.get("id").asInt();

        // Retrieve existing discipline from the database
        Discipline existingDiscipline = disciplineService.getById(disciplineId);

        if (existingDiscipline == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discipline not found");
        }

        // Update discipline fields from the request body
        existingDiscipline.setCodeDiscip(requestBody.get("codeDiscip").asText());
        existingDiscipline.setNomDiscip(requestBody.get("nomDiscip").asText());

        // Save the updated discipline
        Discipline updatedDiscipline = disciplineService.update(existingDiscipline);

        // Prepare response JSON
        ObjectNode responseJson = objectMapper.createObjectNode();
        responseJson.put("id", updatedDiscipline.getIdDiscip());
        responseJson.put("codeDiscip", updatedDiscipline.getCodeDiscip());
        responseJson.put("nomDiscip", updatedDiscipline.getNomDiscip());

        // Return the updated discipline as JSON
        return ResponseEntity.ok(responseJson.toString());
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDiscipline(@PathVariable int id) {
        Discipline discipline = disciplineService.getById(id);
        if (discipline != null) {
            disciplineService.delete(discipline);
            return new ResponseEntity<>("Discipline deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Discipline not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getDisciplineById(@PathVariable int id) {
        Discipline discipline = disciplineService.getById(id);
        if (discipline != null) {
        	
            ObjectNode disciplineNode = objectMapper.createObjectNode();
            disciplineNode.put("id", discipline.getIdDiscip());
            disciplineNode.put("codeDiscip", discipline.getCodeDiscip());
            disciplineNode.put("nomDiscip", discipline.getNomDiscip());
            
            //Add others
            String jsonResult = disciplineNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> getAllDisciplines() {
        List<Discipline> disciplines = disciplineService.getAll();
        
        if (disciplines != null) {
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for (Discipline discipline : disciplines) {
                ObjectNode disciplineNode = objectMapper.createObjectNode();
                disciplineNode.put("id", discipline.getIdDiscip());
                disciplineNode.put("codeDiscip", discipline.getCodeDiscip());
                disciplineNode.put("nomDiscip", discipline.getNomDiscip());
  
                arrayNode.add(disciplineNode);
            }

            String jsonResult = arrayNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            // Gérer le cas où la liste des disciplines est null
            return new ResponseEntity<>("No disciplines found", HttpStatus.NOT_FOUND);
        }
    }

    
    @PostMapping("/import")
    public ResponseEntity<String> importDisciplines(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            List<Discipline> discipline = ExcelHelper.excelToDisciplines(file.getInputStream());
            disciplineService.saveAll(discipline);
            return ResponseEntity.ok("Disciplines imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing disciplines");
        }
    }
    
    

}
	
