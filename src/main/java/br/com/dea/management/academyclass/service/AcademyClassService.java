package br.com.dea.management.academyclass.service;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.dto.AcademyClassDto;
import br.com.dea.management.academyclass.dto.CreateAcademyClassDto;
import br.com.dea.management.academyclass.dto.UpdateAcademyClassDto;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.position.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AcademyClassService {

    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<AcademyClass> findAllAcademyClassPaginated(Integer page, Integer pageSize) {
        return this.academyClassRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("startDate").ascending()));
    }

    public AcademyClass findAcademyClassById(Long id) {
        return this.academyClassRepository.findById(id).orElseThrow(() -> new NotFoundException(AcademyClass.class, id));
    }

    public AcademyClass createAcademyClass(CreateAcademyClassDto createAcademyClassDto) {

        Employee instructor = this.employeeRepository.findById(createAcademyClassDto.getInstructor())
                .orElseThrow(() -> new NotFoundException(Employee.class, createAcademyClassDto.getInstructor()));

        AcademyClass academyClass = AcademyClass.builder()
                .startDate(createAcademyClassDto.getStartDate())
                .endDate(createAcademyClassDto.getEndDate())
                .classType(createAcademyClassDto.getClassType())
                .instructor(instructor)
                .build();

        return this.academyClassRepository.save(academyClass);

    }

    public AcademyClass updateAcademyClass(Long academyClassId, UpdateAcademyClassDto updateAcademyClassDto) {


        AcademyClass academyClass = this.findAcademyClassById(academyClassId);
        Employee instructor = this.employeeRepository.findById(updateAcademyClassDto.getInstructor())
                .orElseThrow(() -> new NotFoundException(Employee.class, updateAcademyClassDto.getInstructor()));


        academyClass.setStartDate(updateAcademyClassDto.getStartDate());
        academyClass.setEndDate(updateAcademyClassDto.getEndDate());
        academyClass.setClassType(updateAcademyClassDto.getClassType());
        academyClass.setInstructor(instructor);

        return this.academyClassRepository.save(academyClass);

    }

    public void deleteAcademyClass(Long academyClassId) {
        AcademyClass academyClass = this.findAcademyClassById(academyClassId);
        this.academyClassRepository.delete(academyClass);
    }

}
