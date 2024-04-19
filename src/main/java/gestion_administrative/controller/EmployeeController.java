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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import employee_management.entities.Employee;
import employee_management.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/insert")
    public ResponseEntity<String> insertEmployee(@RequestBody String employeeJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Employee
        Employee employee = objectMapper.readValue(employeeJson, Employee.class);

        // Insérer l'employé
        employeeService.save(employee);

        return new ResponseEntity<>("Employee inserted successfully", HttpStatus.CREATED);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody String employeeJson) throws JsonParseException, JsonMappingException, IOException {
        // Convertir le JSON en objet Employee
        Employee employee = objectMapper.readValue(employeeJson, Employee.class);
                
        // modifier l'employé
        employeeService.save(employee);

        return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            employeeService.delete(employee);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
        	
            ObjectNode employeeNode = objectMapper.createObjectNode();
            employeeNode.put("id", employee.getIdEmploye());
            employeeNode.put("nom", employee.getNom());
            employeeNode.put("prenom", employee.getPrenom());
            employeeNode.put("departement",employee.getDepartement() );
            employeeNode.put("grade", employee.getGrade());
            String jsonResult = employeeNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> getAllEmployees() {
        List<Employee> employees = employeeService.getAll();
        
        if (employees != null) {
            ArrayNode arrayNode = objectMapper.createArrayNode();

            for (Employee employee : employees) {
                ObjectNode employeeNode = objectMapper.createObjectNode();
                employeeNode.put("id", employee.getIdEmploye());
                employeeNode.put("nom", employee.getNom());
                employeeNode.put("prenom", employee.getPrenom());
                employeeNode.put("departement", employee.getDepartement());
                employeeNode.put("grade", employee.getGrade());
                arrayNode.add(employeeNode);
            }

            String jsonResult = arrayNode.toString();
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } else {
            // Gérer le cas où la liste des employés est null
            return new ResponseEntity<>("No employees found", HttpStatus.NOT_FOUND);
        }
    }

	
}
