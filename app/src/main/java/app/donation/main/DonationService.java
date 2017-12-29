// This class will server as a local interface for interacting with the remote service.
package app.donation.main;

import java.util.List;

import app.donation.model.Donation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface DonationService
{
    @GET("/api/donations")
    Call<List<Donation>> getAllDonations();

    @POST("/api/candidates/{id}/donations")
    Call<Donation> createDonation(@Path("id") String id, @Body Donation donation);
}