package com.dolkuntarim.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	@RequestMapping("/product")
	public String viewProductPage(Model model) {
		List<Product> listProducts = service.listAllProductsByUser();
		model.addAttribute("listProducts", listProducts);
		return "product";
	}
	@GetMapping("/products")
	public List<Product> getProducts()
	{
		return  service.listAll();
	}

	@RequestMapping("/")
	public String viewHomePage()
	{
		return "index";
	}


	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product);
		
		return "redirect:/product";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_product");
		
		Product product = service.get(id);
		mav.addObject("product", product);
		
		return mav;
	}	
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		service.delete(id);
		
		return "redirect:/product";
	}
}
