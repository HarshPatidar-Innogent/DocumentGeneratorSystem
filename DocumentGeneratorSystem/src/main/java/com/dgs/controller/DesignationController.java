package com.dgs.controller;

import com.dgs.DTO.DesignationDTO;
import com.dgs.exception.CustomException.DesignationException;
import com.dgs.service.iService.IDesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designation")
@CrossOrigin
public class DesignationController {

        @Autowired
        private IDesignationService designationService;

        @PostMapping("/add")
        public ResponseEntity<DesignationDTO> createDesignation(@RequestBody DesignationDTO designationDTO){
            try{
                designationDTO.setDesignationId(null);
                DesignationDTO createdDesignation = designationService.createDesignation(designationDTO);
                return ResponseEntity.status(HttpStatus.OK).body(createdDesignation);
            }catch (Exception e){
                throw new RuntimeException("Exception occurred in creating a designation");
            }
        }

        @GetMapping("/getAll")
        public ResponseEntity<List<DesignationDTO>>getall(){
            try{
                List<DesignationDTO> designation = designationService.getAll();
                return ResponseEntity.status(HttpStatus.OK).body(designation);
            }catch (Exception e){
                throw new RuntimeException("Exception occurred in fetching designations");
            }
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<DesignationDTO> update(@PathVariable Long id, @RequestBody DesignationDTO designationDTO) {
            try{
                DesignationDTO updatedDesignation = designationService.update(id, designationDTO);
                return ResponseEntity.ok(updatedDesignation);
            }catch (Exception e){
                throw new RuntimeException("Exception occurred in updating designation");
            }
        }

//        @DeleteMapping("/delete/{id}")
//        public ResponseEntity<DesignationDTO>delete(@PathVariable long id) {
//            designationService.delete(id);
//            return ResponseEntity.noContent().build();
//        }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<DesignationDTO> getByName(@PathVariable String name) {
        try{
            DesignationDTO designationDTO = designationService.getDesignationByName(name);
            return ResponseEntity.ok(designationDTO);
        }catch (Exception e){
            throw new RuntimeException("Exception occurred in fetching an designation by name");
        }
    }

    @GetMapping("/getDes/{id}")
    public ResponseEntity<DesignationDTO> getDesignationById(@PathVariable Long id){
        try{
            DesignationDTO designationDTO = designationService.getDesignationById(id);
            return ResponseEntity.ok(designationDTO);
        }catch (Exception e){
            throw new RuntimeException("Exception occurred in fetching an designation by id");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            Boolean result = designationService.delete(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception occurred in deleting an designation");
        }
    }
}
