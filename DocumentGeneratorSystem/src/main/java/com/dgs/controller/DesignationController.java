package com.dgs.controller;

import com.dgs.DTO.DesignationDTO;
import com.dgs.service.iService.IDesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("designation")
public class DesignationController {

        @Autowired
        private IDesignationService designationService;

        @PostMapping("add")
        public ResponseEntity<DesignationDTO> createDesignation(@RequestBody DesignationDTO designationDTO){
            designationDTO.setDesignationId(null);

            DesignationDTO createdDesignation = designationService.createDesignation(designationDTO);
            return ResponseEntity.ok(createdDesignation);
        }

        @GetMapping("getAll")
        public ResponseEntity<List<DesignationDTO>>getall(){
            List<DesignationDTO> designation = designationService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(designation);
        }
        @PutMapping("/update/{id}")
        public ResponseEntity<DesignationDTO> update(@PathVariable Long id, @RequestBody DesignationDTO designationDTO) {
            DesignationDTO updatedDesignation = designationService.update(id, designationDTO);
            return ResponseEntity.ok(updatedDesignation);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<DesignationDTO>delete(@PathVariable long id) {
            designationService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
