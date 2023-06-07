package com.restful.booker.bookinginfo;

import com.restful.booker.bookingsteps.CreateSteps;
import com.restful.booker.bookingsteps.DeleteSteps;
import com.restful.booker.bookingsteps.ReadSteps;
import com.restful.booker.bookingsteps.UpdateSteps;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCRUDTest {

    static String username = "admin";
    static String password = "password123";
    static String firstname = "Tina";
    static String lastname = "Potts";
    static int totalprice = 100;
    static boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static String checkin = "2023-01-10";
    static String checkout = "2023-01-20";
    static int bookingid;
    static String token;
    @Steps
    CreateSteps create;

    @Title("Creating token")
    @Test
    public void test001() {
        ValidatableResponse response = create.createToken(username, password);
        response.log().all().statusCode(200);
        token = response.extract().path("token");
        System.out.println(token);

    }

    @Title("Creating booking")
    @Test
    public void test002() {
        ValidatableResponse response = create.createBooking(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
        response.log().all().statusCode(200);
        bookingid = response.extract().path("bookingid");
        HashMap<String,Object>value = response.extract().path("");
        Assert.assertThat(value,hasValue(bookingid));
    }

    @Steps
    ReadSteps readSteps;

    @Title("Get all bookingid")
    @Test
    public void test003() {
        ValidatableResponse response = readSteps.getAllId();
        response.log().all().statusCode(200);
        List<String> booking = response.extract().path("bookingid");
        Assert.assertTrue(booking.contains(bookingid));
    }

    @Title("Get single bookingID")
    @Test
    public void test004() {
        ValidatableResponse response = readSteps.getSingleId(bookingid);
        response.log().all().statusCode(200);
        HashMap<String,Object>value = response.extract().path("");
        Assert.assertThat(value,hasValue(firstname));
    }

    @Steps
    UpdateSteps updateSteps;

    @Title("update booking")
    @Test
    public void test005() {
        firstname = firstname + "_updated";
        updateSteps.updateBooking(token, bookingid, firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
        ValidatableResponse response = updateSteps.getUserbyId(token,bookingid);
        response.log().all().statusCode(200);
        HashMap<String,Object> value = response.extract().path("");
        Assert.assertThat(value,hasValue(firstname));
    }

    @Steps
    DeleteSteps deleteSteps;

    @Title("Delete the booking and verify if the booking is deleted!")
    @Test
    public void test006() {
        deleteSteps.deleteUser(token, bookingid).statusCode(201);
        deleteSteps.getUserbyId(token, bookingid).statusCode(404);
    }

}
