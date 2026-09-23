package com.example.campusfinder.mapper;

import com.example.campusfinder.dto.ProgramDtos.ProgramRequest;
import com.example.campusfinder.dto.ProgramDtos.ProgramResponse;
import com.example.campusfinder.entities.Campus;
import com.example.campusfinder.entities.Program;
import org.springframework.stereotype.Component;

@Component
public class ProgramMapper {

    public Program toEntity(ProgramRequest request, Campus campus) {
        return Program.builder()
                .programName(request.getProgramName())
                .degreeType(request.getDegreeType())
                .duration(request.getDuration())
                .description(request.getDescription())
                .tuitionFee(request.getTuitionFee())
                .eligibilityCriteria(request.getEligibilityCriteria())
                .campus(campus)
                .build();
    }

    public void updateEntity(Program program, ProgramRequest request) {
        program.setProgramName(request.getProgramName());
        program.setDegreeType(request.getDegreeType());
        program.setDuration(request.getDuration());
        program.setDescription(request.getDescription());
        program.setTuitionFee(request.getTuitionFee());
        program.setEligibilityCriteria(request.getEligibilityCriteria());
    }

    public ProgramResponse toResponse(Program program) {
        return ProgramResponse.builder()
                .programName(program.getProgramName())
                .degreeType(program.getDegreeType())
                .duration(program.getDuration())
                .description(program.getDescription())
                .tuitionFee(program.getTuitionFee())
                .eligibilityCriteria(program.getEligibilityCriteria())
                .campusId(program.getCampus().getId())
                .campusName(program.getCampus().getCampusName())
                .build();
    }
}
