
package com.winsontan520.psi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataWrapper {

    @SerializedName("region_metadata")
    @Expose
    private List<RegionMeta> regionMetadata = null;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("api_info")
    @Expose
    private ApiInfo apiInfo;

    public List<RegionMeta> getRegionMetadata() {
        return regionMetadata;
    }

    public void setRegionMetadata(List<RegionMeta> regionMetadata) {
        this.regionMetadata = regionMetadata;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

}
