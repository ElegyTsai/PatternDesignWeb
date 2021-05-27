package com.project.patterndesignserver.member;

import com.project.patterndesignserver.mapper.member.*;
import com.project.patterndesignserver.model.member.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void test1(){

        User user1 = new User();
        user1.setCreateTime("now7");
        user1.setEmail("1234@qq.com");
        user1.setMobile("11011");
        user1.setPassword("password124577");
        user1.setUsername("weiwwww");
        user1.setLastModify("now7");
        userMapper.addUser(user1);
    }
    @Test
    public void test2(){
        User user = userMapper.selectUserById(6);
        if(user.isEnabled()){
            System.out.println("true");
        }
        else System.out.println("false");
        System.out.println(user.getRoles().isEmpty());
    }
    @Test
    public void test3(){
        User user1= userMapper.selectUserById(1);

    }
    @Test
    public void  testforpath(){
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        //Get the URLs

        URL[] paths = ((URLClassLoader)sysClassLoader).getURLs();
        for(int i=0; i< paths.length; i++)
        {
            System.out.println(paths[i].getFile());
        }

    }
    @Test
    public void test4(){
        try {

            // 读取配置文件 mybatis-config.xml
            InputStream config = Resources
                    .getResourceAsStream("mybatis-config.xml");
//            // 根据配置文件构建SqlSessionFactory
            SqlSessionFactory ssf = new SqlSessionFactoryBuilder()
                    .build(config);
//            // 通过 SqlSessionFactory 创建 SqlSession
            SqlSession ss = ssf.openSession();
            // SqlSession执行映射文件中定义的SQL，并返回映射结果
            /*
             * com.mybatis.mapper.UserMapper.selectUserById 为 UserMapper.xml
             * 中的命名空间+select 的 id
             */
//             查询一个用户
            User mu = ss.selectOne(
                    "com.mybatis.mapper.UserMapper.selectUserById", 1);
            System.out.println("Done! name :"+mu.getUsername());
//            // 添加一个用户
//            MyUser addmu = new MyUser();
//            addmu.setUname("陈恒");
//            addmu.setUsex("男");
//            ss.insert("com.project.patterndesignserver.mapper.member.mapperbyxml.UserMapper.addUser", addmu);
//            // 修改一个用户
//            MyUser updatemu = new MyUser();
//            updatemu.setUid(1);
//            updatemu.setUname("张三");
//            updatemu.setUsex("女");
//            ss.update("com.mybatis.mapper.UserMapper.updateUser", updatemu);
//            // 删除一个用户
//            ss.delete("com.mybatis.mapper.UserMapper.deleteUser", 3);
//            // 查询所有用户
//            List<MyUser> listMu = ss
//                    .selectList("com.mybatis.mapper.UserMapper.selectAllUser");
//            for (MyUser myUser : listMu) {
//                System.out.println(myUser);
//            }
            // 提交事务
            ss.commit();
//            // 关闭 SqlSession
            ss.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void test5(){
        User user1= userMapper.selectUserById(1);
        System.out.println("name:"+user1.getUsername());

        List<User> userList = userMapper.selectAllUser();
        System.out.println(userList.size());
    }

    @Test
    public void test6(){
        List<User> userList = userMapper.selectAllUser();
        System.out.println(userList.get(0).getUsername()+userList.get(0).getRoles().get(1).getRoleName());
        System.out.println(userList.get(1).getUsername()+userList.get(1).getRoles().get(0).getRoleName());
//        System.out.println(userList.size());
    }
    @Test
    public void test7(){
        User user1 = new User();

        user1.setEmail("12434@qq.com");
        user1.setMobile("1102");
        user1.setPassword("password1245");
        user1.setUsername("wei2");
        user1.setLastModify("now3");
        user1.setCreateTime("now3");
        userMapper.addUser(user1);
    }
    @Test
    public void test8(){
        userMapper.deleteUser(4);
    }

    @Test
    public void test9(){
        SysUser sysUser= new SysUser();

        sysUser.setUser_id(5);
        sysUser.setRole_id(1);
        sysUser.setEnabled(true);
        sysUserMapper.selectSysUserById(sysUser);
    }
    @Test
    public void test10(){
        SysUser sysUser= new SysUser();

        sysUser.setUser_id(5);
        sysUser.setRole_id(2);
        sysUser.setEnabled(true);
        sysUserMapper.addSysUser(sysUser);
    }
    @Test
    public void test11(){
        SysUser sysUser= new SysUser();

        sysUser.setUser_id(5);
        sysUser.setRole_id(2);
        sysUser.setEnabled(true);
        sysUserMapper.deleteSysUser(sysUser);
    }
    @Test
    public void test12(){
        UserPermission permission = userPermissionMapper.selectPermissionById(1);
        System.out.println(permission.getPermission());
    }
    @Test
    public void test13(){
        UserPermission permission = new UserPermission();
        permission.setName("test13");
        permission.setAvailable(Boolean.TRUE);
        permission.setUrl("/testuser");
        permission.setPermission("query");
        userPermissionMapper.addPermission(permission);
    }

    @Test
    public void test14(){
        userPermissionMapper.deletePermissionById(4);
    }
    @Test
    public void test15(){
        UserPermission permission = userPermissionMapper.selectPermissionById(5);
        permission.setName("test15");
        userPermissionMapper.updatePermission(permission);
    }

    @Test
    public void test16(){
        SysRole sysRole = new SysRole();
        sysRole.setName("test");
        sysRole.setPermission_id(2);
        sysRole.setRoleper_id(2);
        sysRoleMapper.addSysRole(sysRole);
    }
    @Test
    public void test17(){
        sysRoleMapper.deleteSysRole(3);
    }
    @Test
    public void test18(){
        SysRole sysRole = new SysRole();
        sysRole.setPermission_id(1);
        sysRole.setRoleper_id(1);
        SysRole res = sysRoleMapper.selectSysRoleById(sysRole);
        System.out.println(res.getName());
    }

    @Test
    public void test19(){
        List<SysRole> res = sysRoleMapper.selectSysRoleByRoleId(1);
        System.out.println(res.get(0).getName());
        System.out.println(res.get(1).getName());
    }

    @Test
    public void test20(){
        List<SysRole> res = sysRoleMapper.selectSysRoleByPermissionId(1);
        System.out.println(res.get(0).getName());
        System.out.println(res.get(1).getName());
    }
    @Test
    public void test21(){
        UserRole userRole =userRoleMapper.selectUserRoleById(1);
        System.out.println(userRole.getRoleName());
        System.out.println(userRole.getPermissions().get(0).getPermission());
    }

    @Test
    public void test22(){
        List<User> res = userRoleMapper.queryUserByRoleId(1);
        System.out.println(res.get(0).getUsername());
        System.out.println(res.get(1).getUsername());
    }
    @Test
    public void test23(){
        List<UserPermission> res = userRoleMapper.queryPermissionByRoleId(1);
        System.out.println(res.get(0).getPermission());
        System.out.println(res.get(1).getPermission());
    }
    @Test
    public void test24(){
        UserRole userRole=new UserRole();
        userRole.setDescription("level3");
        userRole.setAvailable(true);
        userRole.setRoleName("vip3");
        userRoleMapper.addUserRole(userRole);
    }

    @Test
    public void test25(){
        UserRole userRole = userRoleMapper.selectUserRoleById(3);
//        System.out.println(userRole.getPermissions());
        userRole.setDescription("level 3 for test25");
        userRoleMapper.updateUserRole(userRole);
    }
}

