package fr.thetiptop.app.controller;

import fr.thetiptop.app.dto.GainDistributionDto;
import fr.thetiptop.app.repository.GainRepository;
import fr.thetiptop.app.service.GainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gains")
public class GainController {
    private GainService gainService;

    public GainController(GainService gainService) {
        this.gainService = gainService;
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GainDistributionDto>> getGainsStats() {
        List<GainDistributionDto> gainDistributionDto = gainService.findCurrentDistributionPercentage();
        return new ResponseEntity<List<GainDistributionDto>>(gainDistributionDto, HttpStatus.OK);
    }

}