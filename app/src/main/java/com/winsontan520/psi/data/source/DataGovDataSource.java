package com.winsontan520.psi.data.source;

import com.winsontan520.psi.data.model.DataWrapper;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Winson Tan on 17/4/18.
 */

public interface DataGovDataSource {
    @GET("environment/psi")
    Observable<DataWrapper> getPSI();
}
