package com.soda.apiserver.restaurant.dao;

import com.soda.apiserver.common.paging.SelectCriteria;
import com.soda.apiserver.restaurant.dto.CategoryDto;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper{
    
    //카테고리 입력
    void insertCategory(CategoryDto categoryDto);

    //페이징 처리 된 카테고리 선택
    List<CategoryDto> selectCategoryListWithPaging(SelectCriteria selectCriteria);

    //카테고리 총 개수
    int selectCategoryTotal();

    CategoryDto selectCategoryById(String categoryId);
}
