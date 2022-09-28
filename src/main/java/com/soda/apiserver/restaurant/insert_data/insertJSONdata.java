package com.soda.apiserver.restaurant.insert_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soda.apiserver.restaurant.dao.CategoryMapper;
import com.soda.apiserver.restaurant.dao.MenuMapper;
import com.soda.apiserver.restaurant.dao.OldReviewMapper;
import com.soda.apiserver.restaurant.dao.RestaurantMapper;
import com.soda.apiserver.restaurant.dto.CategoryDto;
import com.soda.apiserver.restaurant.dto.MenuDto;
import com.soda.apiserver.restaurant.dto.OldReviewDto;
import com.soda.apiserver.restaurant.dto.RestaurantDto;
import jdk.jfr.Category;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.util.*;

@SpringBootTest
public class insertJSONdata {
    private RestaurantMapper restaurantMapper;
    private MenuMapper menuMapper;
    private CategoryMapper categoryMapper;

    private OldReviewMapper oldReviewMapper;

    @Autowired
    public insertJSONdata(RestaurantMapper restaurantMapper, MenuMapper menuMapper, CategoryMapper categoryMapper, OldReviewMapper oldReviewMapper) {
        this.restaurantMapper = restaurantMapper;
        this.menuMapper = menuMapper;
        this.categoryMapper = categoryMapper;
        this.oldReviewMapper = oldReviewMapper;
    }

    //음식점 json 파일파싱 후 DB에 저장
    @Test
    public void insertMenu() throws Exception {

        //json 파일  파싱하기
        Object ob = new JSONParser().parse(new FileReader("D:\\SODA\\SODA-APIserver\\src\\main\\java\\com\\soda\\apiserver\\restaurant\\insert_data\\file_json\\total_information_list_2.json"));
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        //리스트로 변환
        List<JSONObject> restaurantList = (List) js.get("한식");

        for (int i = 0; i < restaurantList.size(); i++) {
            //레스토랑 ID 추출
            int restaurantId = Integer.valueOf((String) restaurantList.get(i).get("id"));

            //레스토랑 ID에 맞는 메뉴 데이터를 리스트로 변환
            List<JSONObject> menuList = (List) restaurantList.get(i).get("menus");

            //메뉴 리스트에서 메뉴 정보 추출 후 DB에 저장
            for (int menuNum = 0; menuNum < menuList.size(); menuNum++) {
                System.out.println((String) menuList.get(menuNum).get("name"));
                String menuName = (String) menuList.get(menuNum).get("name") == null ? "" : (String) menuList.get(menuNum).get("name");
                String price = (String) menuList.get(menuNum).get("price") == null ? "" : (String) menuList.get(menuNum).get("price");

                //데이터 전달할 DTO 생성
                MenuDto menuDto = new MenuDto();

                //추출한 데이터 메뉴 DTO에 넣기
                menuDto.setMenuName(menuName);
                menuDto.setPrice(price);
                menuDto.setRestaurantId(restaurantId);

                //menuDto에 있는 정보로 DB에 insert
                System.out.println(menuDto);
                menuMapper.insertMenu(menuDto);
            }
        }
    }

    //메뉴 데이터 파싱 후 DB에 저장
    @Test
    public void insertRestaurant() throws Exception {
        //json 파일  파싱하기
        Object ob = new JSONParser().parse(new FileReader(""));
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        List<JSONObject> kFood = (List) js.get("치킨");
        int result = 0;
        System.out.println(kFood);
        for (int i = 0; i < kFood.size(); i++) {
            int id = Integer.valueOf((String) kFood.get(i).get("id"));
            String name = (String) kFood.get(i).get("name") != null ? (String) kFood.get(i).get("name") : "";
            String address = (String) kFood.get(i).get("address");
            String bizhorInfo = (String) kFood.get(i).get("bizhoriInfo") != null ? (String) kFood.get(i).get("bizhoriInfo") : "";
            double x = (Double) kFood.get(i).get("x");
            double y = (Double) kFood.get(i).get("y");
            String phone = (String) kFood.get(i).get("phone");
            String description = (String) kFood.get(i).get("description");
            String image = (String) kFood.get(i).get("image") != null ? (String) kFood.get(i).get("image") : "";

            //음식점 데이터 DTO에 넣기
            RestaurantDto restaurantDto = new RestaurantDto();
            restaurantDto.setId(id);
            restaurantDto.setName(name);
            restaurantDto.setAddress(address);
            restaurantDto.setBizhorInfo(bizhorInfo);
            restaurantDto.setX(x);
            restaurantDto.setY(y);
            restaurantDto.setDescription(description);
            restaurantDto.setPhone(phone);
            restaurantDto.setImage(image);
            restaurantDto.setViews(0);

            restaurantMapper.insertRestaurantList(restaurantDto);
            System.out.println(restaurantDto);
        }
    }

    
    //음식점 DB에 별점 추가
    @Test
    public void insertRatingToRestaurant() throws Exception {
        String filePath= "D:\\SODA\\SODA-APIserver\\src\\main\\java\\com\\soda\\apiserver\\restaurant\\insert_data\\file_json\\review_list.json";
        //json 파일  파싱하기
        Object ob = new JSONParser().parse(new FileReader(filePath));
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        //리스트로 변환
        List<JSONObject> reviewList = (List) js.get("한식");

        for (int i = 0; i < reviewList.size(); i++) {
            //음식점 ID 추출
            int restaurantId = Integer.valueOf((String) reviewList.get(i).get("id"));
            
            //해당 음식점의 별점 추출
            double rating = reviewList.get(i).get("별점") == null? null : Double.valueOf((String)reviewList.get(i).get("별점"));

            //음식점 DB에 별점 넣기
            System.out.println("음식점 : " + restaurantId + ", 별점 : " + rating );

        }
    }


    @Test
    public void insertCategory() throws Exception {
        String filePath = "D:\\SODA\\SODA-APIserver\\src\\main\\java\\com\\soda\\apiserver\\restaurant\\insert_data\\file_json\\test_tt.json";
        //json 파일  파싱하기
        Object ob = new JSONParser().parse(new FileReader(filePath));
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        //리스트로 변환
        List<JSONObject> categoryList = (List) js.get("data");

        for (int i = 0; i < categoryList.size(); i++) {

            //카테고리 이름 추출
            String name = categoryList.get(i).get("etc") == null ? null : (String) categoryList.get(i).get("etc");

            //카테고리 이미지 추출
            String url = categoryList.get(i).get("url") == null ? null : (String) categoryList.get(i).get("url");

            CategoryDto categoryDto = new CategoryDto();

            categoryDto.setCategoryName(name);
            categoryDto.setImage(url);

            categoryMapper.insertCategory(categoryDto);
            System.out.println(categoryDto);

        }
    }

    @Test
    public void jsonParser(){
        String jsonData = "[{\"key\":\"value\"},{\"key2\" :\"value2\"}]";
        String data = "{ \"data\" : " + jsonData + "}";
        JSONParser parser = new JSONParser();
        Object ob = null;
        try {
            ob = parser.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        //리스트로 변환
        List<JSONObject> jsList = (List) js.get("data");

        System.out.println(jsList);
    }

    @Test
    public void insertOldReview() throws Exception {
        String filePath = "D:\\SODA\\SODA-APIserver\\src\\main\\java\\com\\soda\\apiserver\\restaurant\\insert_data\\file_json\\review_list.json";
        //json 파일  파싱하기
        Object ob = new JSONParser().parse(new FileReader(filePath));
        //타입캐스팅
        JSONObject js = (JSONObject) ob;
        //리스트로 변환
        List<JSONObject> reviewList = (List) js.get("한식");

        for (int i = 0; i < reviewList.size(); i++) {

            //레스토랑 아이디 추출
            int restaurantId = reviewList.get(i).get("id") == null ? null : Integer.parseInt((String) reviewList.get(i).get("id"));

            Iterator reviews = reviewList.get(i).keySet().iterator();
            //json 파일에서 데이터가 들어있는 key값 가져오기
            String key = null;
            while (reviews.hasNext())
            {
                String reviewsKey = (String) reviews.next();
                if(!reviewsKey.equals("id") && !reviewsKey.equals("별점")  && !reviewsKey.equals("from") ){
                    key = reviewsKey;
                }
            }
            new ArrayList<String>(){};
            //찾은 값 출력 DB에 넣기
            List<JSONObject> reviewResult = (List) reviewList.get(i).get(key) ==null? new ArrayList<>(0) : (List) reviewList.get(i).get(key);
            
            for(int j=0; j<reviewResult.size(); j++){
                String body = reviewResult.get(j).get("body") == null ? null : (String) reviewResult.get(j).get("body");
                String thumbnail = reviewResult.get(j).get("thumbnail") == null? null : (String) reviewResult.get(j).get("thumbnail");
                OldReviewDto oldReviewDto = new OldReviewDto();

                oldReviewDto.setRestaurantId(restaurantId);
                oldReviewDto.setBody(body);
                oldReviewDto.setThumbnail(thumbnail);
                oldReviewMapper.insertOldReview(oldReviewDto);
                System.out.println(oldReviewDto);
            }



        }
    }
}
