package com.ecolink.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecolink.spring.dto.DTOConverter;
import com.ecolink.spring.dto.MissionDTO;
import com.ecolink.spring.entity.Mission;
import com.ecolink.spring.service.MissionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/missions")
@RestController
public class MissionController {

    public final MissionService missionService;
    private final DTOConverter dtoConverter;

    @GetMapping
    public ResponseEntity<?> getAllMissions(){

        List<Mission> missions = missionService.getAllMissions(); 
        if(missions.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        List<MissionDTO> dtoMission = missions.stream().map(dtoConverter::convertMissionToDto)
        .collect(Collectors.toList());

        return ResponseEntity.ok(dtoMission);
    }

}
