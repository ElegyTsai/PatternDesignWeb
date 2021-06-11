# swagger-doc


<a name="overview"></a>
## 概览

### 版本信息
*版本* : 1.0


### 联系方式
*名字* : elegy


### URI scheme
*域名* : localhost:8080  
*基础路径* : /


### 标签

* user-controller : User Controller




<a name="paths"></a>
## 资源

<a name="user-controller_resource"></a>
### User-controller
User Controller


<a name="getcodeusingget"></a>
#### 获取手机验证码
```
GET /api/home/getCode
```


##### 说明
用get


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**mobile**  <br>*可选*|mobile|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Response](#response)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/home/getCode
```


###### 请求 body
```
json :
{ }
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "rspCode" : "string",
  "rspMsg" : "string"
}
```


<a name="registerusingpost"></a>
#### 手机用户注册
```
POST /api/home/register/mobile
```


##### 说明
只能是POST,其他值保持空缺，实际上只需要Mobile,username,password,validationCode这几个属性


##### 参数

|类型|名称|类型|
|---|---|---|
|**Query**|**accountNonExpired**  <br>*可选*|boolean|
|**Query**|**accountNonLocked**  <br>*可选*|boolean|
|**Query**|**active**  <br>*可选*|boolean|
|**Query**|**authorities[0].authority**  <br>*可选*|string|
|**Query**|**createTime**  <br>*可选*|integer (int64)|
|**Query**|**credentialsNonExpired**  <br>*可选*|boolean|
|**Query**|**email**  <br>*可选*|string|
|**Query**|**enabled**  <br>*可选*|boolean|
|**Query**|**id**  <br>*可选*|integer (int64)|
|**Query**|**lastModify**  <br>*可选*|integer (int64)|
|**Query**|**mobile**  <br>*可选*|string|
|**Query**|**outDate**  <br>*可选*|string|
|**Query**|**password**  <br>*可选*|string|
|**Query**|**roles[0].available**  <br>*可选*|boolean|
|**Query**|**roles[0].description**  <br>*可选*|string|
|**Query**|**roles[0].id**  <br>*可选*|integer (int64)|
|**Query**|**roles[0].permissions[0].available**  <br>*可选*|boolean|
|**Query**|**roles[0].permissions[0].id**  <br>*可选*|integer (int64)|
|**Query**|**roles[0].permissions[0].name**  <br>*可选*|string|
|**Query**|**roles[0].permissions[0].permission**  <br>*可选*|string|
|**Query**|**roles[0].permissions[0].permissions**  <br>*可选*|< object > array(multi)|
|**Query**|**roles[0].permissions[0].resourceType**  <br>*可选*|string|
|**Query**|**roles[0].permissions[0].url**  <br>*可选*|string|
|**Query**|**roles[0].roleName**  <br>*可选*|string|
|**Query**|**roles[0].users[0].accountNonExpired**  <br>*可选*|boolean|
|**Query**|**roles[0].users[0].accountNonLocked**  <br>*可选*|boolean|
|**Query**|**roles[0].users[0].active**  <br>*可选*|boolean|
|**Query**|**roles[0].users[0].authorities[0].authority**  <br>*可选*|string|
|**Query**|**roles[0].users[0].createTime**  <br>*可选*|integer (int64)|
|**Query**|**roles[0].users[0].credentialsNonExpired**  <br>*可选*|boolean|
|**Query**|**roles[0].users[0].email**  <br>*可选*|string|
|**Query**|**roles[0].users[0].enabled**  <br>*可选*|boolean|
|**Query**|**roles[0].users[0].id**  <br>*可选*|integer (int64)|
|**Query**|**roles[0].users[0].lastModify**  <br>*可选*|integer (int64)|
|**Query**|**roles[0].users[0].mobile**  <br>*可选*|string|
|**Query**|**roles[0].users[0].outDate**  <br>*可选*|string|
|**Query**|**roles[0].users[0].password**  <br>*可选*|string|
|**Query**|**roles[0].users[0].username**  <br>*可选*|string|
|**Query**|**roles[0].users[0].validationCode**  <br>*可选*|string|
|**Query**|**username**  <br>*可选*|string|
|**Query**|**validationCode**  <br>*可选*|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Response](#response)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/home/register/mobile
```


###### 请求 query
```
json :
{
  "accountNonExpired" : true,
  "accountNonLocked" : true,
  "active" : true,
  "authorities[0].authority" : "string",
  "createTime" : 0,
  "credentialsNonExpired" : true,
  "email" : "string",
  "enabled" : true,
  "id" : 0,
  "lastModify" : 0,
  "mobile" : "string",
  "outDate" : "string",
  "password" : "string",
  "roles[0].available" : true,
  "roles[0].description" : "string",
  "roles[0].id" : 0,
  "roles[0].permissions[0].available" : true,
  "roles[0].permissions[0].id" : 0,
  "roles[0].permissions[0].name" : "string",
  "roles[0].permissions[0].permission" : "string",
  "roles[0].permissions[0].permissions" : "object",
  "roles[0].permissions[0].resourceType" : "string",
  "roles[0].permissions[0].url" : "string",
  "roles[0].roleName" : "string",
  "roles[0].users[0].accountNonExpired" : true,
  "roles[0].users[0].accountNonLocked" : true,
  "roles[0].users[0].active" : true,
  "roles[0].users[0].authorities[0].authority" : "string",
  "roles[0].users[0].createTime" : 0,
  "roles[0].users[0].credentialsNonExpired" : true,
  "roles[0].users[0].email" : "string",
  "roles[0].users[0].enabled" : true,
  "roles[0].users[0].id" : 0,
  "roles[0].users[0].lastModify" : 0,
  "roles[0].users[0].mobile" : "string",
  "roles[0].users[0].outDate" : "string",
  "roles[0].users[0].password" : "string",
  "roles[0].users[0].username" : "string",
  "roles[0].users[0].validationCode" : "string",
  "username" : "string",
  "validationCode" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "rspCode" : "string",
  "rspMsg" : "string"
}
```




<a name="definitions"></a>
## 定义

<a name="response"></a>
### Response

|名称|说明|类型|
|---|---|---|
|**rspCode**  <br>*可选*|**样例** : `"string"`|string|
|**rspMsg**  <br>*可选*|**样例** : `"string"`|string|





