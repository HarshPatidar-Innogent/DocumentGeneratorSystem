package com.dgs.controller;

import com.dgs.mapper.MapperConfig;
import com.dgs.service.iService.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private MapperConfig mapperConfig;

}
