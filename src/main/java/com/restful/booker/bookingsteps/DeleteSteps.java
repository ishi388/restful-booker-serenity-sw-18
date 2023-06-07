package com.restful.booker.bookingsteps;

import com.restful.booker.constants.EndPoints;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class DeleteSteps {

    @Step("Deleting Booking information with token {0} and bookingId: {1}")
    public ValidatableResponse deleteUser(String token,int bookingid) {
        return SerenityRest.given().log().all()
                .header("Cookie", "token="+token)
                .pathParam("bookingId", bookingid)
                .when()
                .delete(EndPoints.DELETE_BY_ID)
                .then();
    }
    @Step("Getting booking information with token {0} and bookingId: {1}")
    public ValidatableResponse getUserbyId(String token, int bookingid){
        return SerenityRest.given().log().all()
                .header("Cookie", "token="+token)
                .pathParam("bookingId", bookingid)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }
}
