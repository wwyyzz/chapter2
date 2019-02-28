package com.smart.web;

import com.smart.domain.User;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: chapter2
 * @description: loginController
 * @author: Jun Wang
 * @create: 2019-02-28 12:56
 */

//1.标注成为一个Spring MVC的Controller
@Controller
public class LoginController {
    private UserService userService;

    //2.负责处理/index.html的请求
    @RequestMapping(value = "/index.html")
    public String loginPage(){
        return "login";
    }
    //3.负责处理/loginCheck.html的请求
    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand){
        boolean isValidUser =
                userService.hasMatchUser(loginCommand.getUserName(),
                        loginCommand.getPassword());
        if (!isValidUser)
        {
            return new ModelAndView("login", "error", "username or password incorrect");
        }else
        {
            User user = userService.findUserByUserName(loginCommand.getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user", user);
            return new ModelAndView("main");
        }
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}
