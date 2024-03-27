package com.trend.controller;

import com.trend.controller.form.ProductForm;
import com.trend.domain.*;
import com.trend.security.auth.AccountDetails;
import com.trend.security.auth.CurrentAccount;
import com.trend.service.ProductService;
import com.trend.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product/new")
    public String productForm(Model model) {
        model.addAttribute("productForm", new ProductForm());

        String productUUID = UUID.randomUUID().toString();
        model.addAttribute("productUUID", productUUID);

        return "productForm";
    }

    @PostMapping("/product/new")
    public String productSubmit(@Valid ProductForm productForm, BindingResult bindingResult, @CurrentAccount Account account, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "productForm";
        }
        productService.saveProduct(productForm, account.getAccountId());

        // TODO 나중에 성공 메세지 보내기
        //attributes.addFlashAttribute();
        return "redirect:/product-list";
    }

    @GetMapping("/category")
    @ResponseBody
    public List<Category> categoryList(@RequestParam(required = false) Integer categoryId) {
       return productService.getCategoryList(categoryId);
    }

    @PostMapping("/product-image")
    @ResponseBody
    public Map<String, String> uploadProductImage(List<MultipartFile> file, HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        productService.saveProductImage(file, uuid);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/product-list")
    public String mainList(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int pageSize,
                           @RequestParam(defaultValue = "") String keyword,
                           @RequestParam(required = false) String categoryKeyword,
                           Model model) {


        // ?keyword=바지&categoryKeyword=남성의류 이렇게 의도 하지 않게 들어오면 메인페이지로 리다이렉트
        if (!keyword.equals("") && categoryKeyword != null) {
            return "redirect:/product-list";
        }


        PageRequest pageRequest = new PageRequest(page, pageSize, keyword, categoryKeyword);

        List<Product> productList = productService.getMainProductList(pageRequest, categoryKeyword);
        model.addAttribute("productList", productList);

        PageResponse pageResponse = productService.pageHandler(pageRequest, categoryKeyword);
        model.addAttribute("pr", pageResponse);


        // 리스트 메인 페이지에 카테고리 추천 목록에 출력
        List<String> recommendCategory = productService.getMainRecommendCategory();
        model.addAttribute("mainCategories", recommendCategory);

        // 뷰에서 어떤 카테고리를 선택했는지 보여줌
        if (categoryKeyword != null) {
            model.addAttribute("categoryKeyword", categoryKeyword);
        }

        return "productList";
    }



    @GetMapping("/product-detail/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model) {
        Product product = productService.getProductDetail(productId);
        model.addAttribute("product", product);
        return "productDetail";
    }

    @PostMapping("/product-cart-del")
    @ResponseBody
    public Map<String, String> productCartDel(@RequestBody Map<String, Integer> productInfo, HttpSession session) {
        Integer productId = productInfo.get("productId");
        List<Integer> cart = (List<Integer>) session.getAttribute("cartProduct");

        if (cart != null) {
            if (cart.contains(productId)) {
                cart.remove(productId);
            }
        }
        return Map.of("RESULT", "SUCCESS");
    }

    @PostMapping("/product-cart-add")
    @ResponseBody
    public Map<String, String> productCartAdd(@RequestBody Map<String, Integer> productInfo, HttpSession session) {
        // 상품 ID와 수량을 받아옴
        Integer productId = productInfo.get("productId");

            // 세션에서 기존 카트 정보를 가져옴
            List<Integer> cart = (List<Integer>) session.getAttribute("cartProduct");
            if (cart == null) {
                cart = new ArrayList<>();
            }

            // 상품 ID를 카트에 추가
            cart.add(productId);

            // 수정된 카트 정보를 세션에 저장
            session.setAttribute("cartProduct", cart);


        return Map.of("RESULT", "SUCCESS");
    }


    @GetMapping("/product-cart")
    public String productCart(HttpSession session, Model model) {
        // 세션에서 카트 정보를 가져옴
        List<Integer> cart = (List<Integer>) session.getAttribute("cartProduct");

        if (cart != null && !cart.isEmpty()) {
            // 장바구니에 상품이 있을때
           List<Product> cartProductList = productService.getCartProductList(cart);
           model.addAttribute("cartProductList", cartProductList);
        } else {
            // 장바구니에 상품이 없을때
            model.addAttribute("emptyCart", true);
        }
        return "productCart";
    }


}
