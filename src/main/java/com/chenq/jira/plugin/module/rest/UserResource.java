package com.chenq.jira.plugin.module.rest;

import com.atlassian.jira.util.Page;
import com.atlassian.jira.util.PageRequest;
import com.atlassian.plugin.RequirePermission;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.chenq.jira.plugin.api.ao.entity.UserEntity;
import com.chenq.jira.plugin.api.ao.service.GroupService;
import com.chenq.jira.plugin.api.ao.service.UserService;
import com.chenq.jira.plugin.module.rest.convert.UserConverter;
import com.chenq.jira.plugin.module.rest.vo.UserBean;
import com.chenq.jira.plugin.util.PageHelper;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * 2020/7/14 20:27
 * Created by chenq
 */
@Path("/user")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final GroupService groupService;

    @GET
    // 开启匿名访问，不需要登录也可以访问
    @AnonymousAllowed
    @Path("{userId}")
    public Response getUser(@PathParam("userId") Integer id) {
        UserEntity userEntity = userService.get(id);

        return Response.ok(UserConverter.convert(userEntity)).build();
    }

    /**
     * 根据用户名查询用户
     */
    @GET
    // 限制管理员权限的用户才能访问
    @RequirePermission("admin")
    @Path("query")
    public Response queryUser(@QueryParam("name") String name) {
        UserEntity[] userEntities = userService.queryByName(name);

        return Response.ok(Arrays.asList(userEntities).stream().map(UserConverter::convert)).build();
    }

    /**
     * 根据用户名分页查询用户
     */
    @GET
    @Path("pageQuery")
    public Response pageQueryUser(@QueryParam("name") String name,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) {
        PageRequest request = PageHelper.request(pageNum, pageSize);
        Page<UserEntity> userPage = userService.pageUser(name, request);

        return Response.ok(PageHelper.toBean(userPage, UserConverter::convert)).build();
    }

    /**
     * 表单提交方式
     */
    @POST
    @Path("form")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public Response createUser(@FormParam("name") String name, @FormParam("age") Integer age, @FormParam("address") String address,
            @FormParam("groupNo") String groupNo) {
        UserBean userBean = new UserBean(name, address, age, groupNo);
        userService.create(userBean);

        return Response.ok().build();
    }

    /**
     * json提交方式
     */
    @POST
    @Path("json")
    public Response createUser(UserBean userBean) {
        userService.create(userBean);

        return Response.ok().build();
    }

}
