package com.project.patterndesignserver.member;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.model.member.User;

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

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){

        User user1 = new User();
        user1.setCreateTime("now");
        user1.setEmail("123@qq.com");
        user1.setMobile("110");
        user1.setPassword("password123");
        user1.setUsername("me");
        user1.setId(1);
        user1.setLastModify("now");
        userMapper.add(user1);
    }
    @Test
    public void test2(){
        userMapper.deleteByID(1);
    }
    @Test
    public void test3(){
        User user1= userMapper.getUserByID(1);

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
}
