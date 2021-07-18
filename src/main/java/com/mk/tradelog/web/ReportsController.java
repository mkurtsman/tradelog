package com.mk.tradelog.web;

import com.mk.tradelog.model.reports.simplereport.Account;
import com.mk.tradelog.model.reports.simplereport.Strategy;
import com.mk.tradelog.service.event.UpdateDataService;
import com.mk.tradelog.service.files.FileService;
import com.mk.tradelog.service.reportservice.web.SimpleReportModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final SimpleReportModelService modelService;
    private final FileService fileService;
    private final UpdateDataService updateDataService;


    @GetMapping("/simplereport")
    public String simpleReport(Model model,
                               @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate dateFrom,
                               @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate dateTo,
                               @RequestParam
                                        Strategy strategy,
                               @RequestParam
                                       Account account) {

        Map<String, Object> modelAttributes = modelService.getReportModel(dateFrom, dateTo, strategy, account);
        model.addAllAttributes(modelAttributes);
        return "simplereport";
    }

    @PostMapping("/uploadFile")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)  {

        try {
            fileService.writeFile(file.getOriginalFilename(), file.getBytes());
            updateDataService.update();
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("message", "Uploaded  failed" + file.getOriginalFilename() + "!");
            e.printStackTrace();
        }


        return "redirect:/";

    }



}
