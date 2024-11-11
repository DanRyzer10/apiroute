package com.danam.iroute.apiroute.services;

import com.danam.iroute.apiroute.repositories.IRouteRepository;
import com.danam.iroute.apiroute.utils.ReportWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class IrouteServices {
    private final IRouteRepository irouteRepository;

    public IrouteServices(){
        this.irouteRepository = new IRouteRepository();
    }
    //metodo post para guardar los datos desde un archivo csv
    public String saveFile(MultipartFile file){
        return irouteRepository.saveFile(file);
    }

    public int extractAllRegisterFromCommerce(String processDate){
        return irouteRepository.extractAllRegisterByValidation(processDate);
    }

    public byte[]  getAllFromCommerceQuarantine(){
        List<Map<String,String>> dataToReport = irouteRepository.getAllFromCommerceQuarantine();
        ReportWriter reportWriter = new ReportWriter(dataToReport);
        return reportWriter.createReport();
    }

}
