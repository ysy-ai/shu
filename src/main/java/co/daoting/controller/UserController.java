package co.daoting.controller;


import co.daoting.common.ResultDTO;
import co.daoting.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wn
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "登录-用户")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "login", notes = "登录")
    public ResultDTO login(String username, String password, String verifyCode) {

        ResultDTO resultDTO = userService.login(username, password, verifyCode);

        return resultDTO;
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "changePassword", notes = "更改密码")
    public ResultDTO changePassword(Long userid, String passwordOld, String passwordNew) {

        ResultDTO resultDTO = userService.changePassword(userid, passwordOld, passwordNew);

        return resultDTO;
    }
}

