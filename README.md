# WebServer

这个简单的模拟服务器处理用户的请求 包括静态页面，servlet请求，静态资源等




HTTP协议：超文本传输协议 ，是浏览器与服务器之间传输通讯的协议。

浏览器向服务器泛送：请求
服务器向浏览器发送：响应

而请求与响应在HTTP中都用标准的协议格式，浏览器与服务器遵循该格式，并相互进行通讯。

HTTP请求的格式：
HTTP请求由三部分组成：请求行、消息头、消息正文
请求行格式：Method Request-URI HTTP-Version CRLF
例如：GET /index.html HTTP/1.1CRLF   CR：回车  LF：换行

消息头格式： name:value CRLF
例如：
HOST:doc.tedu.cnCRLF
Content-length:23CRLF
CRLF(消息头每一项信息后面都会有CRLF，当消息头所有信息发送完毕后，会单独发送一个CRLF)

消息正文：实际随请求发送过来的数据(POST请求用，GET请求该项为空)
                                 

                                 
                                 
                                 
                                 
HTTP响应的格式：响应包含三部分：状态行，响应头，响应正文

状态行的格式：
HTTP-Version Status-Code Reason-PhraseCRLF
   协议版本		状态值		 状态描述
例如：HTTP/1.1 200 OK                                  
                                 
响应头格式：
name:value
Content-Type:text/html
Comtent-Length:35

所有响应头信息完毕后也会单独发送一个CRLF，响应正文为服务端实际回应客户端的资源数据                               
                                 
                                 
                                 
    
    
状态代码（Status-Code）由3位数字组成，表示请求是否被理解或被满足。原因分析是
用简短的文字来描述状态代码产生的原因。状态代码用来支持自动操作，原因分析是为人类
用户准备的。客户端不需要检查或显示原因分析。
	
	状态代码的第一位数字定义了回应的类别，后面两位数字没有具体分类。首位数字有5
种取值可能：

o 1xx:：保留，将来使用。

o 2xx：成功 － 操作被接收、理解、接受（received, understood, accepted）。

o 3xx：重定向（Redirection）－ 要完成请求必须进行进一步操作。

o 4xx：客户端出错 － 请求有语法错误或无法实现。

o 5xx：服务器端出错 － 服务器无法实现合法的请求。

	HTTP/1.0的状态代码、原因解释在下面给出。下面的原因解释只是建议采用，可任意
更改，而不会对协议造成影响。完整的代码定义在第9节。
       Status-Code    = "200"   ; OK
                      | "201"   ; Created
                      | "202"   ; Accepted
                      | "204"   ; No Content
                      | "301"   ; Moved Permanently
                      | "302"   ; Moved Temporarily
                      | "304"   ; Not Modified
                      | "400"   ; Bad Request
                      | "401"   ; Unauthorized
                      | "403"   ; Forbidden
                      | "404"   ; Not Found
                      | "500"   ; Internal Server Error
                      | "501"   ; Not Implemented
                      | "502"   ; Bad Gateway
                      | "503"   ; Service Unavailable
                      | extension-code

       extension-code = 3DIGIT

       Reason-Phrase  = *<TEXT, excluding CR, LF>

	HTTP状态代码是可扩展的，而只有上述代码才可以被当前全部的应用所识别。HTTP
应用不要求了解全部注册的状态代码，当然，如果了解了更好。实际上，应用程序必须理解
任何一种状态代码，如果碰到不识别的情况，可根据其首位数字来判断其类型并处理。另外，
不要缓存无法识别的回应。

	例如，如果客户端收到一个无法识别的状态码431，可以安全地假定是请求出了问题，
可认为回应的状态码就是400。在这种情况下，用户代理应当在回应消息的实体中通知用户，
因为实体中可以包括一些人类可以识别的非正常状态的描述信息。
