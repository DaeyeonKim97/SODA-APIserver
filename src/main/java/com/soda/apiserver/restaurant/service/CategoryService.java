package com.soda.apiserver.restaurant.service;

import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.restaurant.dao.CategoryMapper;
import com.soda.apiserver.restaurant.dto.CategoryDto;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import jdk.jfr.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper){this.categoryMapper=categoryMapper;}

    public List<CategoryDto> selectCategoryListWithPaging(SelectCriteria selectCriteria){

        List<CategoryDto> categoryList = categoryMapper.selectCategoryListWithPaging(selectCriteria);

        return categoryList;
    }

    public int selectCategoryTotal() {
//        log.info("[ProductService] selectProductTotal Start ===================================");
        int result = categoryMapper.selectCategoryTotal();

//        log.info("[ProductService] selectProductTotal End ===================================");
        return result;
    }
    public CategoryDto selectCategoryById(String categoryId) {
        CategoryDto categoryDto = categoryMapper.selectCategoryById(categoryId);
        return categoryDto;
    }

}
