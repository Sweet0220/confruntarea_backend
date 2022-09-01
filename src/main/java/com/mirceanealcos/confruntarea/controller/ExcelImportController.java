package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.excel.exporter.ChampionExcelExporter;
import com.mirceanealcos.confruntarea.excel.importer.ImportChampionExcelUtil;
import com.mirceanealcos.confruntarea.excel.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "import/excel")
@Slf4j
public class ExcelImportController {

    private final ExcelService excelService;
    private List<Champion> failedChampions = new ArrayList<>();

    @Autowired
    public ExcelImportController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping(path = "/champions")
    public ResponseEntity<String> importChampions(@RequestParam("file")MultipartFile file) {
        String message = "";
        if(ImportChampionExcelUtil.hasExcelFormat(file)) {
            try {
                List<Champion> allChampions = excelService.getChampionListFromExcelFile(file);
                int success = excelService.saveChampions(file, failedChampions);
                log.info(failedChampions.toString());
                if(success == allChampions.size()) {
                    message = "Successfully imported all rows from " + file.getOriginalFilename() + "!";
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }
                if(success == 0) {
                    message = "No champions were imported..";
                    return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
                }
                message = "Successfully added " + success + " champions out of " + allChampions.size();
                return new ResponseEntity<>(message, HttpStatus.PARTIAL_CONTENT);
            }catch (Exception e) {
                return new ResponseEntity<>("Invalid file..", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/champions/failed")
    public void exportFailedChampions(HttpServletResponse response) {
        response.setContentType("application/octet-stream");

        ChampionExcelExporter excelExporter = new ChampionExcelExporter(failedChampions);

        try {
            excelExporter.export(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
