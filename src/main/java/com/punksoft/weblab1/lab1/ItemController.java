package com.punksoft.weblab1.lab1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/records")
    public String listTestRecords(Model model) {
        model.addAttribute("records", repository.findAll());
        return "records";
    }

    @PostMapping("/records")
    public String addTestRecord(@RequestParam String name) {
        Item student = new Item();
        student.setName(name);
        repository.save(student);
        return "redirect:/records";
    }
}