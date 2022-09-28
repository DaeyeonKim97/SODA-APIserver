package com.soda.apiserver.restaurant.controller;

import com.soda.apiserver.common.paging.Pagenation;
import com.soda.apiserver.common.paging.ResponseDtoWithPaging;
import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.common.response.ResponseDto;
import com.soda.apiserver.restaurant.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categortService;

    public CategoryController(CategoryService categoryService){ this.categortService = categoryService;}

    @GetMapping("/categorys")
    public ResponseEntity<ResponseDto> selectCategoryListWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, String limit){

        System.out.println("call selectCategoryWithPaging");

        int totalCount = categortService.selectCategoryTotal();
//        int limit = Integer.parseInt(request_limit);
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, Integer.parseInt(limit), buttonAmount);

        //log.info("[ProductController] selectCriteria : " + selectCriteria);
        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(categortService.selectCategoryListWithPaging(selectCriteria));
        System.out.println("call select API");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }
    @GetMapping("category")
    public ResponseEntity<ResponseDto> selectRestarantWithId(@RequestParam String categoryId) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", categortService.selectCategoryById(categoryId)));
    }
}
