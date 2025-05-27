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

import com.example.demo.model.dto.StudyDto;
import com.example.demo.service.StudyService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudiesController {

	private final StudyService studyService;

	// 一覧表示
    @GetMapping
    public String list(Model model) {
        List<StudyDto> studies = studyService.findAll();
        model.addAttribute("studies", studies);
        return "studies/list";
    }

    // 詳細表示
    @GetMapping("/{sid}")
    public String detail(@PathVariable Integer sid, Model model) {
        StudyDto study = studyService.findById(sid);
        model.addAttribute("study", study);
        return "studies/detail";
    }
    
    // 登録フォーム表示
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("studyDto", new StudyDto());
        return "studies/form";
    }

    // 登録実行
    @PostMapping
    public String create(@Valid @ModelAttribute StudyDto studyDto, BindingResult result) {
        if (result.hasErrors()) {
            return "studies/form";
        }
        studyService.save(studyDto);
        return "redirect:/studies";
    }

    // 編集フォーム表示
    @GetMapping("/{sid}/edit")
    public String editForm(@PathVariable Integer sid, Model model) {
        model.addAttribute("studyDto", studyService.findById(sid));
        return "studies/form";
    }

    // 更新実行
    @PostMapping("/{sid}")
    public String update(@PathVariable Integer sid, @Valid @ModelAttribute StudyDto studyDto, BindingResult result) {
        if (result.hasErrors()) {
            return "studies/form";
        }
        studyService.save(studyDto); // saveで新旧区別なく保存
        return "redirect:/studies";
    }

    // 削除実行
    @PostMapping("/{sid}/delete")
    public String delete(@PathVariable Integer sid) {
    	studyService.deleteById(sid);
        return "redirect:/studies";
    }
	
}
