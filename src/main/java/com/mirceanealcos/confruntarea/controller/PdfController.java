package com.mirceanealcos.confruntarea.controller;

import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.pdf.UserPDFExporter;
import com.mirceanealcos.confruntarea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "/export/pdf")
public class PdfController {

    private final UserService userService;

    @Autowired
    public PdfController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public void exportUsersToPDF(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        List<User> users = userService.getAllUsersAsEntity();

        UserPDFExporter exporter = new UserPDFExporter(users);
        exporter.export(response);
    }
}
