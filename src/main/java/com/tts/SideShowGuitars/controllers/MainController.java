package com.tts.SideShowGuitars.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.SideShowGuitars.model.Product;
import com.tts.SideShowGuitars.services.ProductService;

import lombok.Data;

@Data
@Controller
@ControllerAdvice
public class MainController {
	@Autowired
	ProductService productService;

	public void addNew() {
		List<Product> allProducts = productService.findAll();
		if (allProducts.isEmpty()) {
			List<Product> newProducts = new ArrayList<Product>();
			newProducts.add(new Product(1, (float) 399.99, "SideShow", "White Bass", "Jazz style", "Bass",
					"images/whitebass.jpg"));
			newProducts.add(new Product(1, (float) 399.99, "SideShow", "Green Bass", "Precision style", "Bass",
					"images/greenbass.jpg"));
			newProducts.add(new Product(1, (float) 399.99, "SideShow", "Blue Guitar", "Telecaster style", "Guitar",
					"images/blueguitar.jpg"));
			newProducts.add(new Product(1, (float) 399.99, "SideShow", "Black Guitar", "Telecaster style", "Guitar",
					"images/blackguitar.jpg"));
			newProducts.add(new Product(1, (float) 399.99, "SideShow", "White Guitar", "Telecaster style", "Guitar",
					"images/whiteguitar.jpg"));
			newProducts.add(new Product(1, (float) 399.99, "SideShow", "Red Guitar", "Stratocaster style", "Guitar",
					"images/redguitar.jpg"));

			for (Product product : newProducts) {
				productService.save(product);
				System.out.println(product);
			}
		} else {
			System.out.println("You don't need more items!");
		}
	}

	@GetMapping("/")
	public String main() {
		if ((productService.findAll().isEmpty())) {
			addNew();
		}
		return "main";
	}

	@ModelAttribute("products")
	public List<Product> products() {
		return productService.findAll();
	}

	@ModelAttribute("categories")
	public List<String> categories() {
		return productService.findDistinctCategories();
	}

	@ModelAttribute("brands")
	public List<String> brands() {
		return productService.findDistinctBrands();
	}

	@GetMapping("/filter")
	public String filter(@RequestParam(required = false) String category, @RequestParam(required = false) String brand,
			Model model) {
		List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
		model.addAttribute("products", filtered);
		return "main";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}
}
