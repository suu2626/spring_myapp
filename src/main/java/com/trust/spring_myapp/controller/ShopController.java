package com.trust.spring_myapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.trust.spring_myapp.dto.ShopRequest;
import com.trust.spring_myapp.dto.ShopUpdateRequest;
import com.trust.spring_myapp.entity.Product;
import com.trust.spring_myapp.entity.Shop;
import com.trust.spring_myapp.entity.User;
import com.trust.spring_myapp.service.ShopService;
import com.trust.spring_myapp.service.UserService;

/**
 * ショップ情報 Controller
 */
@Controller
public class ShopController {

    @Autowired
    ShopService shopService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/shop/list")
    public String displayList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<Shop> shoplist = shopService.searchAll();
        model.addAttribute("shoplist", shoplist);
        model.addAttribute("loggedInUserId", user.getId());
        return "shop/list";
    }

    @GetMapping(value = "/shop/add")
    public String displayAdd(Model model) {
        model.addAttribute("shopRequest", new ShopRequest());
        return "shop/add";
    }

    @PostMapping("/shop/create")
    public String createShop(@Validated @ModelAttribute ShopRequest shopRequest, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "shop/add";
        }

        String username = authentication.getName();
        User user = userService.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user != null) {
            shopService.createShop(shopRequest, user);
        }

        return "redirect:/shop/list";
    }

    @GetMapping("/shop/{id}")
    public String displayView(@PathVariable Long id, Model model, Principal principal) {
        Shop shop = shopService.findById(id);
        String currentUsername = principal.getName();
        boolean isOwner = shop.getUser().getEmail().equals(currentUsername);
        if (shop != null) {
            model.addAttribute("shopData", shop);
            model.addAttribute("isOwner", isOwner);
            List<Product> products = shop.getProducts(); // ショップに関連する商品を取得
            model.addAttribute("products", products);
        }
        return "shop/view";
    }

    @GetMapping("/shop/{id}/edit")
    public String displayEdit(@PathVariable Long id, Model model) {
        Shop shop = shopService.findById(id);
        if (shop != null) {
            ShopUpdateRequest shopUpdateRequest = new ShopUpdateRequest();
            shopUpdateRequest.setId(shop.getId());
            shopUpdateRequest.setName(shop.getName());
            shopUpdateRequest.setDescription(shop.getDescription());
            model.addAttribute("shopUpdateRequest", shopUpdateRequest);
        }
        return "shop/edit";
    }

    @PostMapping("/shop/update")
    public String update(@Validated @ModelAttribute ShopUpdateRequest shopUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "shop/edit";
        }

        shopService.update(shopUpdateRequest);
        return String.format("redirect:/shop/%d", shopUpdateRequest.getId());
    }
  
    /**
     * 
     * ショップの削除
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/shop/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        shopService.delete(id);
        return "redirect:/shop/list";
    }
  
    @GetMapping("/shop/mypage")
    public String displayMyPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Shop> shops = shopService.findByUser(user);
        model.addAttribute("shops", shops);
        model.addAttribute("username", user.getName());  // ユーザーの名前を表示するように修正
        return "shop/mypage";
    }
}
