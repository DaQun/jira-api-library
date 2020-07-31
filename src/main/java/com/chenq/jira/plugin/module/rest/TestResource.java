package com.chenq.jira.plugin.module.rest;

import com.chenq.jira.plugin.api.customfield.CustomFieldAPI;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * 2020/7/30 15:46
 * Created by chenq
 */
@Path("test")
@RequiredArgsConstructor
public class TestResource {

    private final CustomFieldAPI customFieldAPI;

    @GET
    @Path("createCF")
    public String createCF(@QueryParam("cfname") String cfname) {
        customFieldAPI.createCustomField(cfname);

        return null;
    }

}
