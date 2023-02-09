package com.ffucks.ipfilter.controllers;

import com.ffucks.ipfilter.dto.IpFilterDto;
import com.ffucks.ipfilter.models.ErrorMessageModel;
import com.ffucks.ipfilter.models.IpFilterModel;
import com.ffucks.ipfilter.services.IpFilterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class IpFilterController {

    private final IpFilterService ipFilterService;

    public IpFilterController(IpFilterService ipFilterService) {
        this.ipFilterService = ipFilterService;
    }


    @GetMapping("/rule")
    public ResponseEntity<Object> findAllFilters(@RequestParam String sourceIp, @RequestParam String destinationIp) {
        IpFilterModel ipFilterModel = ipFilterService.findByIp(sourceIp, destinationIp);
        if (ipFilterModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageModel(HttpStatus.NOT_FOUND.value(), "Rule not found"));
        }
        return "ALLOW".equalsIgnoreCase(ipFilterModel.getRule())
                ? ResponseEntity.status(HttpStatus.OK).body(ipFilterModel)
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body(ipFilterModel);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<IpFilterModel>> findAllFilters() {
        return ResponseEntity.status(HttpStatus.OK).body(ipFilterService.getAllFilters());
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> addIpFilter(@RequestBody IpFilterDto ipFilterDto) {
        if (ipFilterDto.getSourceIp().equals(ipFilterDto.getDestinationIp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageModel(HttpStatus.BAD_REQUEST.value(), "Source and Destination IP can not be the same"));
        }
        if (!ipFilterService.verifyIp(ipFilterDto.getSourceIp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageModel(HttpStatus.BAD_REQUEST.value(), "Invalid Source IP"));
        }
        if (!ipFilterService.verifyIp(ipFilterDto.getDestinationIp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageModel(HttpStatus.BAD_REQUEST.value(), "Invalid Destination IP"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ipFilterService.saveIpFilter(ipFilterService.converter(ipFilterDto)));
    }

    @DeleteMapping("/filter")
    public ResponseEntity<Object> deleteIpFilter(@RequestBody IpFilterDto ipFilterDto) {
        if (ipFilterService.findByIp(ipFilterDto.getSourceIp(), ipFilterDto.getDestinationIp()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageModel(HttpStatus.NOT_FOUND.value(), "Data not found"));
        }
        ipFilterService.delete(ipFilterDto.getSourceIp(), ipFilterDto.getDestinationIp());
        return ResponseEntity.status(HttpStatus.OK).body("Filter deleted successfully");
    }

}


