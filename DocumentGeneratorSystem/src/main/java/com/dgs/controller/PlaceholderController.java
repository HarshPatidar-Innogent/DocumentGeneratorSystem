package com.dgs.controller;

import com.dgs.DTO.PlaceholderDTO;
import com.dgs.exception.CustomException.PlaceholderException;
import com.dgs.service.iService.IPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/placeholder")
@CrossOrigin
public class PlaceholderController {
    @Autowired
    private IPlaceholderService placeholderService;

    @GetMapping("/template/{id}")
    public List<PlaceholderDTO> getAllPlaceholderOfTemplate(@PathVariable Long id) {
        try {
            return placeholderService.getAllPlaceholderOfTemplate(id);
        }catch (Exception e){
            throw new PlaceholderException("Exception occurred in fetching placeholder by templateId");
        }
    }

}
