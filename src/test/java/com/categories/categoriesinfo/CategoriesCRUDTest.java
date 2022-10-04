package com.categories.categoriesinfo;

import com.categories.constants.EndPoints;
import com.categories.model.CategoriesPojo;
import com.categories.testbase.TestBase;
import com.categories.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.yecht.Data;

import java.util.HashMap;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasValue;

public class CategoriesCRUDTest extends TestBase {

    static String id = "85s" + TestUtils.getRandomValue();
    static String name = "toy" + TestUtils.getRandomValue();

    static String categoriesid;

    @Title("This will create a new categories")
    @Test
    public void test001()
    {
        CategoriesPojo categoriesPojo =new CategoriesPojo();
        categoriesPojo.setId(id);
        categoriesPojo.setName(name);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);

    }
    @Title("Verify if student was created")
    @Test
    public void test002()
    {
        String c1 = "data.findAll{it.name='";
        String c2 = "'}.get(0)";

        HashMap<String,Object> categoriesMap = SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(c1+name+c2);
        Assert.assertThat(categoriesMap, hasValue(name));
        categoriesid = (String) categoriesMap.get("id");

    }
    @Title("Update the user and verify the updated information")
    @Test
    public void test003()
    {
        name = name + "update";

        CategoriesPojo categoriesPojo =new CategoriesPojo();
        categoriesPojo.setId(id);
        categoriesPojo.setName(name);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesid",categoriesid)
                .body(categoriesPojo)
                .when()
                .put(EndPoints.UPDATE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        String c1 = "data.findAll{it.name='";
        String c2 = "'}.get(0)";
        HashMap<Data.Str,Object> categoriesMap = SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(c1+name+c2);
        Assert.assertThat(categoriesMap, hasValue(name));

    }

    @Title("Delete the categories and verify if the categories is deleted")
    @Test
    public void test004()
    {
        SerenityRest.given()
                .pathParam("categoriesid",categoriesid)
                .when()
                .delete(EndPoints.DELETE_CATEGORIES_BY_ID)
                .then()
                .statusCode(200);
        given().log().all()
                .pathParam("categoriesid",categoriesid)
                .when()
                .get()
                .then()
                .statusCode(404);

    }


}
