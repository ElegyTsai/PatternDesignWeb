package com.project.patterndesignserver.service.user;

import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.util.result.Response;

public interface UserService {

    public Response registerByEmail(User user);

    public Response activeUserByEmail(String sid,String email);

    public Response updatePassword(User user);

    public Response login(User user);

    public Response logout();


}
