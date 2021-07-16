package com.autentic.jpastreamershowcase.api;

import com.autentic.jpastreamershowcase.entity.Department;
import com.autentic.jpastreamershowcase.entity.Department$;
import com.autentic.jpastreamershowcase.entity.Municipality;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private final JPAStreamer jpaStreamer;

    @GetMapping
    public Collection<Department> getDepartments() {
        StreamConfiguration<Department> configuration = StreamConfiguration.of(Department.class)
                .joining(Department$.municipalities);

        return jpaStreamer.stream(configuration)
                .collect(Collectors.toUnmodifiableSet());
    }

    @GetMapping(value = "/{code}")
    public Department getDepartmentByCode(@PathVariable final Short code) {
        StreamConfiguration<Department> configuration = StreamConfiguration.of(Department.class)
                .joining(Department$.municipalities);

        return jpaStreamer.stream(configuration)
                .filter(Department$.code.equal(code))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found"));
    }
    @GetMapping(value = "/{departmentCode}/municipalities")
    public Collection<Municipality> getMunicipalitiesForDepartment(@PathVariable final Short departmentCode) {
        StreamConfiguration<Department> configuration = StreamConfiguration.of(Department.class)
                .joining(Department$.municipalities);

        return jpaStreamer.stream(configuration)
                .filter(Department$.code.equal(departmentCode))
                .findFirst()
                .map(Department::getMunicipalities)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
    }
}
