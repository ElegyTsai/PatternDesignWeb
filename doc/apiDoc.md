# swagger-doc


<a name="overview"></a>
## 概览

### 版本信息
*版本* : 1.0


### 联系方式
*名字* : elegy


### URI scheme
*域名* : localhost:8081  
*基础路径* : /


### 标签

* latest-used-material-controller : Latest Used Material Controller
* user-controller : User Controller
* user-image-controller : User Image Controller
* 公共素材管理 : Public Image Controller




<a name="paths"></a>
## 资源

<a name="latest-used-material-controller_resource"></a>
### Latest-used-material-controller
Latest Used Material Controller


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
只能是POST,其他值保持空缺，实际上只需要Mobile,username,password,validationCode这几个属性，如果注册成功，还会额外返回一个token用于后续登陆


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


<a name="testusingget"></a>
#### test
```
GET /api/home/test
```


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|enum (FAIL, SUCCESS, UserNotFound, UsernameNotFound, UsernameUsed, PhoneUsed, CodeError, UserNotAuthenticated, EmailUsed, EmailError, TimeOut, PasswordError, UserNotExisted, UserDisabled, KeyWrong, LackInfo, UploadFailed, SuffixError)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/home/test
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="user-image-controller_resource"></a>
### User-image-controller
User Image Controller


<a name="deleteusingdelete_1"></a>
#### delete
```
DELETE /api/img/user/delete
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**UUID**  <br>*可选*|UUID|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Result](#result)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/img/user/delete
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
  "code" : "string",
  "data" : "object",
  "msg" : "string"
}
```


<a name="getimageusingget_1"></a>
#### getImage
```
GET /api/img/user/getImage/{url}
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Path**|**url**  <br>*必填*|url|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string (byte)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `image/jpeg`


##### HTTP请求示例

###### 请求 path
```
/api/img/user/getImage/string
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="getnailusingget_1"></a>
#### getNail
```
GET /api/img/user/getNail/{url}
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Path**|**url**  <br>*必填*|url|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string (byte)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `image/jpeg`


##### HTTP请求示例

###### 请求 path
```
/api/img/user/getNail/string
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="queryallusingget"></a>
#### queryAll
```
GET /api/img/user/queryAll
```


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Result](#result)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/img/user/queryAll
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "object",
  "msg" : "string"
}
```


<a name="uploadusingpost_1"></a>
#### upload
```
POST /api/img/user/upload
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**FormData**|**pic**  <br>*必填*|pic|file|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Result«string»](#e249bf1902de7f75aaed353ffea96339)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `multipart/form-data`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/img/user/upload
```


###### 请求 formData
```
json :
"file"
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```


<a name="16c2a3032aac25b1ea1d1ed61f073446"></a>
### 公共素材管理
Public Image Controller


<a name="deleteusingdelete"></a>
#### 根据图片id删除图片
```
DELETE /api/img/public/delete
```


##### 说明
id是数据库的id，（这个api没什么用）


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**id**  <br>*可选*|id|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Response](#response)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/img/public/delete
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


<a name="getimageusingget"></a>
#### 查看高清图片
```
GET /api/img/public/getImage/{tag}/{url}
```


##### 说明
get方法，需要tag和图片的UUID用byte数组传递图片


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Path**|**tag**  <br>*必填*|tag|string|
|**Path**|**url**  <br>*必填*|url|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string (byte)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `image/png`
* `image/jpeg`


##### HTTP请求示例

###### 请求 path
```
/api/img/public/getImage/string/string
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="getnailusingget"></a>
#### 查看缩略图片
```
GET /api/img/public/getNail/{tag}/{url}
```


##### 说明
get方法，需要tag和图片的UUID用byte数组传递图片


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Path**|**tag**  <br>*必填*|tag|string|
|**Path**|**url**  <br>*必填*|url|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string (byte)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `image/jpeg`


##### HTTP请求示例

###### 请求 path
```
/api/img/public/getNail/string/string
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="querytagusingget"></a>
#### 根据图片分类查询图片
```
GET /api/img/public/queryByTag
```


##### 说明
get方法，需要提供tag返回一个列表，列表里的每个对象包含了一张图片的高清图url和缩略图url，还有图片自己的文件名


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**tag**  <br>*可选*|tag|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< [PublicImageResult](#publicimageresult) > array|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/img/public/queryByTag
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
[ {
  "imageUrl" : "string",
  "thumbNailUrl" : "string",
  "uuid" : "string"
} ]
```


<a name="uploadusingpost"></a>
#### 上传图片到公共素材库
```
POST /api/img/public/upload
```


##### 说明
Post方法，pic存放图片，tag是图片所属的种类permission是赋予的权限默认为all，返回信息为图片的高清图url,理论上来说只有管理员才能上传，为了便于测试没有添加授权验证


##### 参数

|类型|名称|说明|类型|默认值|
|---|---|---|---|---|
|**Query**|**permission**  <br>*可选*|permission|string|`"all"`|
|**Query**|**tag**  <br>*必填*|tag|string||
|**FormData**|**pic**  <br>*必填*|pic|file||


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Result«string»](#e249bf1902de7f75aaed353ffea96339)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `multipart/form-data`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/api/img/public/upload
```


###### 请求 query
```
json :
{
  "permission" : "string",
  "tag" : "string"
}
```


###### 请求 formData
```
json :
"file"
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : "string",
  "data" : "string",
  "msg" : "string"
}
```




<a name="definitions"></a>
## 定义

<a name="publicimageresult"></a>
### PublicImageResult

|名称|说明|类型|
|---|---|---|
|**imageUrl**  <br>*可选*|**样例** : `"string"`|string|
|**thumbNailUrl**  <br>*可选*|**样例** : `"string"`|string|
|**uuid**  <br>*可选*|**样例** : `"string"`|string|


<a name="response"></a>
### Response

|名称|说明|类型|
|---|---|---|
|**rspCode**  <br>*可选*|**样例** : `"string"`|string|
|**rspMsg**  <br>*可选*|**样例** : `"string"`|string|


<a name="result"></a>
### Result

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `"string"`|string|
|**data**  <br>*可选*|**样例** : `"object"`|object|
|**msg**  <br>*可选*|**样例** : `"string"`|string|


<a name="c1781e5e2dbb907f97761770e9a345e6"></a>
### Result«List»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `"string"`|string|
|**data**  <br>*可选*|**样例** : `[ "object" ]`|< object > array|
|**msg**  <br>*可选*|**样例** : `"string"`|string|


<a name="e249bf1902de7f75aaed353ffea96339"></a>
### Result«string»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `"string"`|string|
|**data**  <br>*可选*|**样例** : `"string"`|string|
|**msg**  <br>*可选*|**样例** : `"string"`|string|





