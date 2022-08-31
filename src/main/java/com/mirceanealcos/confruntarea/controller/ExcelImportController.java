package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.excel.exporter.ChampionExcelExporter;
import com.mirceanealcos.confruntarea.excel.importer.ImportChampionExcelUtil;
import com.mirceanealcos.confruntarea.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "import/excel")
public class ExcelImportController {

    private final ExcelService excelService;

    @Autowired
    public ExcelImportController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping(path = "/champions")
    public ResponseEntity<String> importChampions(@RequestParam("file")MultipartFile file, HttpServletResponse response) {
        String message = "";
        if(ImportChampionExcelUtil.hasExcelFormat(file)) {
            try {
                List<Champion> failedChampions = excelService.saveChampions(file);
                if(failedChampions.isEmpty()) {
                    message = "Successfully imported all rows from " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }
                ChampionExcelExporter excelExporter = new ChampionExcelExporter(failedChampions);
                response.setContentType("application/octet-stream");
                excelExporter.export(response);
                message = "Failed to add " + failedChampions.size() + " row(s).";
                return new ResponseEntity<>(message, HttpStatus.OK);
            }catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
