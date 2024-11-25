package com.notificationapi.notificationapi.controller;

import com.notificationapi.notificationapi.service.ReporteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/reporte")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/gen")
    public ResponseEntity<?> generarReporte() {
        log.info("Generando reporte");
        if (reporteService.generarReporte("C:\\Users\\User\\Desktop\\reportePersonas5.xlsx")){
            return new ResponseEntity<>("Reporte generado exitosamente", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Reporte NO generado exitosamente", HttpStatus.BAD_REQUEST);
        }

    }
}
