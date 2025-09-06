package com.punksoft.weblab1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    private final TestRecordRepository repository;

    public TestController(TestRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/records")
    public String listTestRecords(Model model) {
        model.addAttribute("records", repository.findAll());
        return "records";
    }

    @PostMapping("/records")
    public String addTestRecord(@RequestParam String name) {
        TestRecord student = new TestRecord();
        student.setName(name);
        repository.save(student);
        return "redirect:/records";
    }
}