package gestion_administrative.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @PostMapping("/insert")
public ResponseEntity<String> insertDiscipline(@Valid @RequestBody Discipline discipline, BindingResult bindingResult) throws JsonParseException, JsonMappingException, IOException {
    // Check if there are validation errors
    if (bindingResult.hasErrors()) {
        // Construct error message
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.badRequest().body(errorMessage.toString());
    }

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
            List<Discipline> importedDisciplines = ExcelHelper.excelToDisciplines(file.getInputStream());
            List<Discipline> existingDisciplines = disciplineService.getAll();
    
            List<Discipline> newDisciplines = new ArrayList<>();
    
            // Check if each imported discipline already exists in the database
            for (Discipline importedDiscipline : importedDisciplines) {
                boolean disciplineExists = false;
                for (Discipline existingDiscipline : existingDisciplines) {
                    if (importedDiscipline.getCodeDiscip().equals(existingDiscipline.getCodeDiscip())) {
                        disciplineExists = true;
                        break;
                    }
                }
                // If discipline does not exist, add it to the list of new disciplines
                if (!disciplineExists) {
                    newDisciplines.add(importedDiscipline);
                }
            }
    
            // Save only the new disciplines
            disciplineService.saveAll(newDisciplines);
    
            return ResponseEntity.ok("Disciplines imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing disciplines");
        }
    }
    @GetMapping("/search")
public ResponseEntity<String> searchDisciplines(@RequestParam String query) {
    List<Discipline> searchResults = disciplineService.search(query);
    if (searchResults != null && !searchResults.isEmpty()) {
        // Convert search results to JSON and return them
        return ResponseEntity.ok(convertDisciplinesToJson(searchResults));
    } else {
        return ResponseEntity.notFound().build();
    }
}

private String convertDisciplinesToJson(List<Discipline> disciplines) {
    ArrayNode arrayNode = objectMapper.createArrayNode();
    for (Discipline discipline : disciplines) {
        ObjectNode disciplineNode = objectMapper.createObjectNode();
        disciplineNode.put("id", discipline.getIdDiscip());
        disciplineNode.put("codeDiscip", discipline.getCodeDiscip());
        disciplineNode.put("nomDiscip", discipline.getNomDiscip());
        arrayNode.add(disciplineNode);
    }
    return arrayNode.toString();
}

}
	
