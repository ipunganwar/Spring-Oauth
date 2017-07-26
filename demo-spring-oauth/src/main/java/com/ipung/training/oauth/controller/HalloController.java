package com.ipung.training.oauth.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HalloController {

	@RequestMapping(value="/halo")
	public void halo(Model m){
		m.addAttribute("waktu", new Date());
	}
}
