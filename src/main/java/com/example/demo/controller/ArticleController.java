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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.ArticleDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.FieldService;
import com.example.demo.service.PortionService;
import com.example.demo.service.StudyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
	private final ArticleService articleService;
	private final FieldService fieldService;
	private final StudyService studyService;
	private final PortionService portionService;
	
	// 記事一覧を表示する
	@GetMapping
	public String list(Model model) {
		model.addAttribute("articles", articleService.findAll());
		return "articles/list"; // list.htmlを返す
	}
	
	// 指定されたIDの記事の詳細を表示する
	@GetMapping("/{sid}/{fid}/{pid}/{aid}")
	public String detail(@PathVariable int sid,
			@PathVariable int fid, @PathVariable int pid,
			@PathVariable int aid, Model model) {
		model.addAttribute("article", articleService.findById(sid, fid, pid, aid));
		return "articles/detail"; // detail.htmlを返す
	}
	
	// 指定されたキーワードで検索し、結果を表示する
	@GetMapping("/search")
    public String search(@RequestParam String keyword,
                         @RequestParam(defaultValue = "atext") String type,
                         Model model) {
        List<ArticleDto> result = switch (type) {
            case "atitle" -> articleService.searchByAtitle(keyword);
            default -> articleService.searchByAtext(keyword);
        };
        model.addAttribute("articles", result);
        return "articles/list"; // 検索結果も list.html を再利用
    }
	
	@GetMapping("/new")
    public String createForm(Model model) {
		model.addAttribute("studyList", studyService.findAll());
		model.addAttribute("fieldList", fieldService.findAll());
		model.addAttribute("portionList", portionService.findAll());
        model.addAttribute("articleDto", new ArticleDto());
        return "articles/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute ArticleDto articleDto, BindingResult result, Model model) {
    	System.out.println("POST /articles called. Errors: " + result.hasErrors());
    	if (result.hasErrors()) {
        	model.addAttribute("studyList", studyService.findAll());
            model.addAttribute("fieldList", fieldService.findAll());
            model.addAttribute("portionList", portionService.findAll());
            return "articles/form";
        }
        articleService.save(articleDto);
        return "redirect:/articles";
    }

    @GetMapping("/{sid}/{fid}/{pid}/{aid}/edit")
    public String editForm(@PathVariable Integer sid, @PathVariable Integer fid,
                           @PathVariable Integer pid, @PathVariable Integer aid, Model model) {
        model.addAttribute("articleDto", articleService.findById(sid, fid, pid, aid));
        return "articles/form";
    }

    @PostMapping("/{sid}/{fid}/{pid}/{aid}")
    public String update(@PathVariable Integer sid, @PathVariable Integer fid,
                         @PathVariable Integer pid, @PathVariable Integer aid,
                         @Valid @ModelAttribute ArticleDto articleDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	 model.addAttribute("studyList", studyService.findAll());
             model.addAttribute("fieldList", fieldService.findAll());
             model.addAttribute("portionList", portionService.findAll());
            return "articles/form";
        }
        articleService.save(articleDto);
        return "redirect:/articles";
    }
}
