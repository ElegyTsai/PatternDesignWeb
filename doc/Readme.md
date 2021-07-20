# 补充文档 

## 接口文档介绍     

### 注册接口         
接口文档见/doc/api_doc    
  
### 登陆接口
* url /api/login/process
* method post
* param: uname 用户名或者手机号   password  密码    isRememberMe: 是否记住我，可以延长token的有效时间
* return  失败时 rspCode rspMsg ,失败原因有三种。密码错误，用户名没注册，用户被锁定。 成功时 多一个用户名和token
![postman范例](.README_images/dcfb6a08.png)        
  ![错误返回](.README_images/a2a09274.png)
  
