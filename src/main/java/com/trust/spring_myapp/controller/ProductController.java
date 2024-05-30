package com.trust.spring_myapp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trust.spring_myapp.dto.ProductRequest;
import com.trust.spring_myapp.entity.Product;
import com.trust.spring_myapp.entity.Shop;
import com.trust.spring_myapp.service.ProductService;
import com.trust.spring_myapp.service.ShopService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ShopService shopService;

	@GetMapping("/products/list")
	public String listProducts(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "products/list";
	}

	@GetMapping("/products/add")
	public String showAddProductForm(@RequestParam("shopId") Long shopId, Model model) {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setShopId(shopId);
		model.addAttribute("productRequest", productRequest);
		return "products/add";
	}

	/**
	 * 
	 * 商品登録
	 * @param productRequest
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/products/create")
	public String createProduct(@Validated @ModelAttribute ProductRequest productRequest, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "products/add";
		}

		Shop shop = shopService.findById(productRequest.getShopId());
		if (shop != null) {
			productService.createProduct(productRequest, shop);
		} else {
			model.addAttribute("validationError", List.of("Shop not found."));
			return "products/add";
		}

		return String.format("redirect:/shop/%d", productRequest.getShopId());
	}

	@GetMapping("/products/{id}")
	public String viewProduct(@PathVariable Long id, Model model, Principal principal) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		String currentUsername = principal.getName();
		boolean isOwner = product.getShop().getUser().getEmail().equals(currentUsername);

		model.addAttribute("isOwner", isOwner);
		return "products/view";
	}

	@GetMapping("/products/{id}/edit")
	public String showEditProductForm(@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		ProductRequest productRequest = new ProductRequest();
		productRequest.setId(product.getId()); // ここを追加
		productRequest.setName(product.getName());
		productRequest.setDescription(product.getDescription());
		productRequest.setPrice(product.getPrice());
		productRequest.setStock(product.getStock());
		productRequest.setShopId(product.getShop().getId());
		model.addAttribute("productRequest", productRequest);
		return "products/edit";
	}

	/**
	 * 
	 * 商品更新
	 * @param id
	 * @param productRequest
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/products/update/{id}")
	public String updateProduct(@PathVariable Long id, @Validated @ModelAttribute ProductRequest productRequest,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return "products/edit";
		}

		Shop shop = shopService.findById(productRequest.getShopId());
		productService.updateProduct(id, productRequest, shop);

		return String.format("redirect:/shop/%d", productRequest.getShopId());
	}

	/**
	 * 
	 * 商品削除
	 * @param id
	 * @return
	 */
	@GetMapping("/products/{id}/delete")
	public String deleteProduct(@PathVariable Long id) {
		Product product = productService.findById(id);
		productService.deleteProduct(id);
		return String.format("redirect:/shop/%d", product.getShop().getId());
	}

	/**
	 * 
	 * 購入ボタン
	 * @param id
	 * @return
	 */
	@PostMapping("/products/{id}/buy")
	public String buyProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		productService.decreaseStock(id);
		redirectAttributes.addFlashAttribute("purchaseMessage", "購入が完了しました");
		return "redirect:/products/" + id;
	}

	/**
	 * csvダウンロード
	 * @param response
	 * @throws IOException
	 */
	    @GetMapping("/products/{shopId}/download")
	    public ResponseEntity<byte[]> downloadCsv(@PathVariable Long shopId) {
	        List<Product> products = productService.findByShopId(shopId);

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8)) {
	            // BOMを追加
	            writer.write('\uFEFF');
	            writer.write("ID,Name,Description,Price,Stock\n");

	            for (Product product : products) {
	                writer.write(product.getId() + ",");
	                writer.write(escapeCsvField(product.getName()) + ",");
	                writer.write(escapeCsvField(product.getDescription()) + ",");
	                writer.write(product.getPrice() + ",");
	                writer.write(product.getStock() + "\n");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        byte[] csvBytes = byteArrayOutputStream.toByteArray();

	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv");
	        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

	        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
	    }

	    private String escapeCsvField(String field) {
	        if (field == null) {
	            return "";
	        }
	        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
	            field = field.replace("\"", "\"\"");
	            field = "\"" + field + "\"";
	        }
	        return field;
	    }
}