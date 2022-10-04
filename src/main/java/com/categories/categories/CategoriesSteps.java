package com.categories.categories;

import com.categories.constants.EndPoints;
import com.categories.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.yecht.Data;

import java.util.HashMap;

public class CategoriesSteps {

    @Step("Creating category with id :{0}, name: {1}")
    public ValidatableResponse createCategories(String id, String name)
    {
        CategoriesPojo categoriesPojo =new CategoriesPojo();
        categoriesPojo.setId(id);
        categoriesPojo.setName(name);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);

    }
    @Step("getting categories info by name:{0}")
    public HashMap<String,Object> getCategoriesInfoByName(String name)
    {
        String c1 = "data.findAll{it.name='";
        String c2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(c1+name+c2);

    }
    @Step("update categories info with categoriesid:{0},id: {1}, name: {2]")
    public ValidatableResponse updateCategories(String categoriesid, String id,String name )
    {
        CategoriesPojo categoriesPojo =new CategoriesPojo();
        categoriesPojo.setId(id);
        categoriesPojo.setName(name);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesid",categoriesid)
                .body(categoriesPojo)
                .when()
                .put(EndPoints.UPDATE_CATEGORIES_BY_ID)
                .then();

    }
    @Step("deleting categories information with categoriesid:{0}")
    public ValidatableResponse deleteCategoriesInfoById(String categoriesid)
    {
        return SerenityRest.given()
                .pathParam("categoriesid",categoriesid)
                .when()
                .delete(EndPoints.DELETE_CATEGORIES_BY_ID)
                .then();

    }
    @Step("getting categories info by categoriesid:{0]")
    public ValidatableResponse getCategoriesInfoBycategoriesid(String categoriesid)
    {
        return SerenityRest.given().log().all()
                .pathParam("categoriesid",categoriesid)
                .when()
                .get(EndPoints.DELETE_CATEGORIES_BY_ID)
                .then();

    }



}
