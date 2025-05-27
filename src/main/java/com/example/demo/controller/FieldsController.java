package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.FieldDto;
import com.example.demo.service.FieldService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/fields")
@RequiredArgsConstructor
public class FieldsController {

    private final FieldService fieldService;

    @GetMapping
    public String list(Model model) {
        List<FieldDto> fields = fieldService.findAll();
        model.addAttribute("fields", fields);
        return "fields/list";
    }

    @GetMapping("/{sid}/{fid}")
    public String detail(@PathVariable Integer sid, @PathVariable Integer fid, Model model) {
        FieldDto field = fieldService.findById(sid, fid);
        model.addAttribute("field", field);
        return "fields/detail";
    }
    
    // 登録フォーム表示
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("fieldDto", new FieldDto());
        return "fields/form";
    }

    // 登録実行
    @PostMapping
    public String create(@Valid @ModelAttribute FieldDto fieldDto, BindingResult result) {
        if (result.hasErrors()) {
            return "fields/form";
        }
        fieldService.save(fieldDto);
        return "redirect:/fields";
    }

    // 編集フォーム表示
    @GetMapping("/{sid}/{fid}/edit")
    public String editForm(@PathVariable Integer sid, @PathVariable Integer fid, Model model) {
        model.addAttribute("fieldDto", fieldService.findById(sid, fid));
        return "fields/form";
    }

    // 更新実行
    @PostMapping("/{sid}/{fid}")
    public String update(@PathVariable Integer sid, @PathVariable Integer fid,
                         @Valid @ModelAttribute FieldDto fieldDto, BindingResult result) {
        if (result.hasErrors()) {
            return "fields/form";
        }
        fieldService.save(fieldDto);
        return "redirect:/fields";
    }
}