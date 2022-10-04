package com.categories.categoriesinfo;

import com.categories.categories.CategoriesSteps;
import com.categories.testbase.TestBase;
import com.categories.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCURDTestwithSteps extends TestBase {

    static String id = "85s" + TestUtils.getRandomValue();
    static String name = "toy" + TestUtils.getRandomValue();

    static String categoriesid;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a new categories")
    @Test
    public void test001()
    {
        ValidatableResponse response = categoriesSteps.createCategories(id,name);
        response.log().all().statusCode(201);

    }
    @Title("Verify if categories is created")
    @Test
    public void test002()
    {
        HashMap<String,Object> categoriesMap = categoriesSteps.getCategoriesInfoByName(name);
        Assert.assertThat(categoriesMap, hasValue(name));
        categoriesid = (String) categoriesMap.get("id");
        System.out.println(categoriesid);

    }
    @Title("update the user information")
    @Test
    public void test003()
    {
        name = name + "update";

        categoriesSteps.updateCategories(categoriesid,id,name);
        HashMap<String,Object> categoriesMap = categoriesSteps.getCategoriesInfoByName(name);
        Assert.assertThat(categoriesMap, hasValue(name));


    }
    @Title("Delete categories info by categoriesid and verify its deleted")
    @Test
    public void test004()
    {
        categoriesSteps.deleteCategoriesInfoById(categoriesid).log().all().statusCode(200);
        categoriesSteps.getCategoriesInfoBycategoriesid(categoriesid).log().all().statusCode(404);

    }


}
