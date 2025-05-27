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

import com.example.demo.model.dto.PortionDto;
import com.example.demo.service.PortionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/portions")
@RequiredArgsConstructor
public class PortionsController {

    private final PortionService portionService;

    @GetMapping
    public String list(Model model) {
        List<PortionDto> portions = portionService.findAll();
        model.addAttribute("portions", portions);
        return "portions/list";
    }

    @GetMapping("/{sid}/{fid}/{pid}")
    public String detail(@PathVariable Integer sid, @PathVariable Integer fid, @PathVariable Integer pid, Model model) {
        PortionDto portion = portionService.findById(sid, fid, pid);
        model.addAttribute("portion", portion);
        return "portions/detail";
    }
    
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("portionDto", new PortionDto());
        return "portions/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute PortionDto portionDto, BindingResult result) {
        if (result.hasErrors()) {
            return "portions/form";
        }
        portionService.save(portionDto);
        return "redirect:/portions";
    }

    @GetMapping("/{sid}/{fid}/{pid}/edit")
    public String editForm(@PathVariable Integer sid, @PathVariable Integer fid,
                           @PathVariable Integer pid, Model model) {
        model.addAttribute("portionDto", portionService.findById(sid, fid, pid));
        return "portions/form";
    }

    @PostMapping("/{sid}/{fid}/{pid}")
    public String update(@PathVariable Integer sid, @PathVariable Integer fid,
                         @PathVariable Integer pid,
                         @Valid @ModelAttribute PortionDto portionDto, BindingResult result) {
        if (result.hasErrors()) {
            return "portions/form";
        }
        portionService.save(portionDto);
        return "redirect:/portions";
    }
}