package com.swpu.cl.contrallor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swpu.cl.mapper.UserMapper;
import com.swpu.cl.pojo.User;
import com.swpu.cl.utils.ResponseMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * FileName: UserContrallor
 * Author: hu_sir
 * Date:   2024/6/3 10:38
 * Description: User控制器
 * History:
 * <author>      <time>     <version>     <desc>
 * 作者姓名       修改时间      版本号         描述
 */
@RestController // @Contrallor + @ResponseBody 相应给前端的数据是json
@RequestMapping("/front/user") // 配置访问路径 修饰在类上就是类请求路径，修饰在方法上就是方法的请求路径
public class UserController {
    // 注入 UserMapper 接口对象
    @Resource
    private UserMapper userMapper;
    // 注入 RedisTemplate 对象
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 登录：http://localhost:8088/front/user/login
    @PostMapping("/login") // 只接收 Post 请求
    // @RequestBody 获取请求体，前端传来的 json 数据
    public ResponseMessage login(@RequestBody User user) {
        // 获取前端传过来的账号和密码
        String username = user.getUsername();
        String password = user.getPassword();
        // 验证账号是否为 null
        if (StringUtils.isEmpty(username))
            return new ResponseMessage(500,"username is null");
        // 验证密码是否为 null
        if (StringUtils.isEmpty(password))
            return new ResponseMessage(500,"password is null");
        // 查看当前用户是否已经登录了，即通过 redis 查询数据，如果可以找到表示登录了，否则未登录
        User redisUser = (User) redisTemplate.opsForValue().get(username);// 登录成功的时候，存储一个数据在 redis 中，key 为当前用的 username，value 就是用户数据内容
        // 判断 redisUser 是否为 null
        if(Objects.nonNull(redisUser))
            return new ResponseMessage(200,"当前用户已经登录了");
        // 未登录用户：首先通过账号查询用户信息（保证 user 的 user_state 值为 0），如果存在就验证码密码，否则返回用户名不存在
        /*
         * mp 提供的 selectOne 方法，查询一条数据，参数就是一个条件构造器对象
         * */
        User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (Objects.isNull(findUser))
            return new ResponseMessage(500,"用户名不存在");
        // 查询出来这个人，比对密码
        if (!(findUser.getPassword().equals(password)))
            return new ResponseMessage(500,"密码错误");
        // 登录成功
        redisTemplate.opsForValue().set(username,findUser); // 把数据内容存入 redis 中
        return new ResponseMessage(200,"登录成功");
    }

    // 退出登录
    @GetMapping("/logout")
    public ResponseMessage logout(String username){
        if(StringUtils.isEmpty(username)){
            return new ResponseMessage(500,"当前用户未登录");
        }
        redisTemplate.delete(username);
        return new ResponseMessage(200,"退出成功");
    }

    // 检查用户名是否存在
    @GetMapping("/checkUsername")
    public ResponseMessage checkUsername(@RequestParam String username) {
        // 在数据库中查找是否存在该用户名（大小写敏感）
        User existingUser = userMapper.selectOne(new QueryWrapper<User>().apply("BINARY username = {0}", username));

        if (Objects.nonNull(existingUser)) {
            return new ResponseMessage(500, "用户名已存在", true);
        } else {
            return new ResponseMessage(200, "用户名可用", false);
        }
    }


    // 注册：http://localhost:8088/front/user/register
    @PostMapping("/register")
    public ResponseMessage register(@RequestBody User user) {
        // 获取前端传来的数据
        String username = user.getUsername();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        Integer userAge = user.getUserAge();
        String userGender = user.getUserGender();
        String userEmail = user.getUserEmail();
        String userPhone = user.getUserPhone();

        if (StringUtils.isEmpty(username))
            return new ResponseMessage(500, "用户名不能为空");
        if (StringUtils.isEmpty(password))
            return new ResponseMessage(500, "密码不能为空");
        if (StringUtils.isEmpty(confirmPassword))
            return new ResponseMessage(500, "确认密码不能为空");
        if (!password.equals(confirmPassword))
            return new ResponseMessage(500, "密码和确认密码不一致");
        if (Objects.isNull(userAge))
            return new ResponseMessage(500, "年龄不能为空");
        if (StringUtils.isEmpty(userGender))
            return new ResponseMessage(500, "性别不能为空");
        if (StringUtils.isEmpty(userEmail))
            return new ResponseMessage(500, "邮箱不能为空");
        if (StringUtils.isEmpty(userPhone))
            return new ResponseMessage(500, "电话不能为空");

        int result = userMapper.insert(user);
        if (result > 0)
            return new ResponseMessage(200, "注册成功");
        else
            return new ResponseMessage(500, "注册失败");
    }

    // 获取用户的所有信息
    @GetMapping("/userCenter/userInfo")
    public ResponseMessage userInfo(@RequestParam String username) {
        System.out.println("Fetching user info for username: " + username);
        User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        System.out.println("Found user: " + findUser);
        if (Objects.isNull(findUser))
            return new ResponseMessage(500,"用户名不存在，获取信息失败");
        return new ResponseMessage(200, "获取当前用户信息", findUser);
    }

    /**
     * 修改用户密码
     * @param username 用户账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @PostMapping("/changePassword")
    public ResponseMessage changePassword(@RequestParam String username , @RequestParam String oldPassword , @RequestParam String newPassword) {
        //先获取当前登录者的信息、
        User findUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (Objects.isNull(findUser)){
            return new ResponseMessage(500,"用户名不存在，获取信息失败");
        }
        //判断当前的登陆者的密码与旧密码是否一致
        if(findUser.getPassword().equals(oldPassword)){
            //若一致则更换新密码
            findUser.setPassword(newPassword);
            userMapper.updateById(findUser);
            return new ResponseMessage(200, "修改密码成功");
        }
        return new ResponseMessage(500, "修改密码失败");
    }


    // 更新用户信息
    @PostMapping("/updateUserInfo")
    public ResponseMessage updateUserInfo(@RequestBody User user){
        if(userMapper.updateById(user) > 0){
            return new ResponseMessage(200, "更新成功");
        }
        return new ResponseMessage(500, "更新失败");
    }


    // 上传头像
    @RequestMapping(value = "/upload")
    public ResponseMessage uploadImg(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String fileName = UUID.randomUUID().toString().replace("-", "") + originalFilename.substring(originalFilename.lastIndexOf("."));
                // 构建真实的文件保存路径
                Path path = Paths.get("D:/test/workspace/images/mall/" + fileName);
                // 确保目录路径存在
                Files.createDirectories(path.getParent());
                // 将上传文件保存到指定位置
                file.transferTo(path);
                return new ResponseMessage(200, "上传成功",fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseMessage(500, "上传失败");
            }
        }
        else {
            return new ResponseMessage(500, "上传失败");
        }
    }
}
