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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gestion_administrative.entities.Structure;
import gestion_administrative.helper.ExcelHelper;
import gestion_administrative.service.StructureService;

@RestController
@RequestMapping("/structure")
public class StructureController {

    @Autowired
    private StructureService structureService;
    
    @Autowired
    private ObjectMapper objectMapper;


//    @PostMapping("/insert")
//    public ResponseEntity<String> insertStructure(@RequestBody String structureJson) throws JsonParseException, JsonMappingException, IOException {
//        // Convertir le JSON en objet Structure
//        Structure structure = objectMapper.readValue(structureJson, Structure.class);
//
//        // Insérer l'employé
//        structureService.save(structure);
//
//        return new ResponseEntity<>("Structure inserted successfully", HttpStatus.CREATED);
//    }
    @PostMapping("/insert")
    public ResponseEntity<String> insertStructure(@RequestBody Structure structure) {
        // Check if the structure already exists
        if (structureService.existsByCodeStr(structure.getCodeStr())) {
            return ResponseEntity.badRequest().body("Structure with code " + structure.getCodeStr() + " already exists.");
        }

        // Save the structure
        structureService.save(structure);
        return new ResponseEntity<>("Structure inserted successfully", HttpStatus.CREATED);
    }

   

    @PutMapping("/update")
    public ResponseEntity<String> updateStructure(@RequestBody String structureJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Structure
        Structure structure = objectMapper.readValue(structureJson, Structure.class);
                
        // modifier l'employé
        structureService.save(structure);

        return new ResponseEntity<>("Structure updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStructure(@PathVariable int id) {
        Structure structure = structureService.getById(id);
        if (structure != null) {
            structureService.delete(structure);
            return new ResponseEntity<>("Structure deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Structure not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getStructureById(@PathVariable int id) {
        Structure structure = structureService.getById(id);
        if (structure != null) {
        	
            ObjectNode structureNode = objectMapper.createObjectNode();
            structureNode.put("id", structure.getIdStr());
            structureNode.put("codeStr", structure.getCodeStr());
            structureNode.put("nbrClasse", structure.getNbrClasse());;
            String jsonResult = structureNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> getAllStructures() {
        List<Structure> structures = structureService.getAll();
        
        if (structures != null) {
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for (Structure structure : structures) {
                ObjectNode structureNode = objectMapper.createObjectNode();
                structureNode.put("id", structure.getIdStr());
                structureNode.put("codeStr", structure.getCodeStr());
                structureNode.put("nbrClasse", structure.getNbrClasse());
                arrayNode.add(structureNode);
            }

            String jsonResult = arrayNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            // Gérer le cas où la liste des employés est null
            return new ResponseEntity<>("No structure found", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/import")
    public ResponseEntity<String> importStructures(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            List<Structure> structures = ExcelHelper.excelToStructures(file.getInputStream());
            structureService.saveAll(structures);
            return ResponseEntity.ok("Structures imported successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing Structures: " + e.getMessage());
        }
    }
	
}
