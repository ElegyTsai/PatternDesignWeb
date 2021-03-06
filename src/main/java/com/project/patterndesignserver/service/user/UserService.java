package com.project.patterndesignserver.service.user;

import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.util.result.Response;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    public Response registerByEmail(User user);

    public Response activeUserByEmail(String sid,String email);

    public Response updatePassword(User user);

    public Response login(User user);

    public Response logout();

    public Response sendPhoneMessage(String phoneNumber);

    public Response registerByMobile(User user, HttpServletResponse response);


}
