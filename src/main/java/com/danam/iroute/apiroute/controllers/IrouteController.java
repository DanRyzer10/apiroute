package com.danam.iroute.apiroute.controllers;

import com.danam.iroute.apiroute.services.IrouteServices;
import com.itextpdf.text.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/iroute")
public class IrouteController {

    @Autowired
    private IrouteServices irouteService;

    @PostMapping("commerce/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file){
        if(file==null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No se ha seleccionado un archivo");
        }
        var transactionResponse = irouteService.saveFile(file);
        if(transactionResponse==null){
            return ResponseEntity.status(503).body("No se ha podido guardar el archivo");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }
    @PostMapping("commerce/extract")

    public ResponseEntity<?> extractFromCommerce(@RequestParam("process_date") String processDate){
        if(processDate==null || processDate.isEmpty()){
            return ResponseEntity.badRequest().body("No se ha seleccionado una fecha");
        }
        var transactionResponse = irouteService.extractAllRegisterFromCommerce(processDate);
        if(transactionResponse==-1){
            return ResponseEntity.status(503).body("No se ha podido extraer los registros");
        }
        String message = "Se han extraido "+transactionResponse+" registros";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    @GetMapping("/quarantine")
    public ResponseEntity<?> getAllFromCommerceQuarantine(){
        byte[] transactionResponse = irouteService.getAllFromCommerceQuarantine();
        if(transactionResponse==null){
            return ResponseEntity.status(503).body("No se ha podido generar el reporte");
        }
        //convertir los bytes a un archivo pdf y enviarlo
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=report_data_quarantine.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(transactionResponse);
    }

}
