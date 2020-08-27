<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                layer.msg("sdasd");
                alert("dsad");
            });
            $("#textjson4").click(function () {
                var student = {
                    "id":1,
                    "name":"zs",
                    "age":12,
                    "sex":"男",
                    "address":{
                        "homeAddress":"山东省",
                        "schoolAddress":"江苏省"
                    },
                    "list":[{
                        "discipline":"Java EE",
                        "fraction":88
                    },{
                        "discipline":"Java SE",
                        "fraction":99

                    }
                        ],
                    "map":{
                        "k1":"m1",
                        "k2":"m2"
                    }
                };
                $.ajax({
                    "url":"testComplex.json",
                    "data":JSON.stringify(student),
                    "dataType":"json",
                    "type":"post",
                    "contentType":"application/json;charset=UTF-8",
                    "success":function (response) {
                       console.log(response);
                    },
                    "error":function (response) {
                        console.log(response);
                    }

                });
            });
            $("#textjson3").click(function () {
                var array = [5, 8, 12];
                console.log(array.length);
                var requestBody = JSON.stringify(array);
                console.log(requestBody.length);
                $.ajax({
                    "url": "textjson3.html",			// 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data":requestBody,
                    "contentType":"application/json;charset=UTF-8",	// 设置请求体的内容类型，告诉服务器端本次请求的请求体是JSON数据
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            });
            $("#textjson2").click(function () {
                $.ajax({
                    "url":"textjson2.html",
                    "data":{
                        "list[0]":1,
                        "list[1]":2
                    },
                    "type":"post",
                    "dateType":"text",
                    "success":function (response) {
                        alert(response);
                    },
                    "error":function (response) {
                        alert(response)
                    }
                });
            });
            $("#textjson").click(function () {
                $.ajax({
                    "url" : "textjson1.html",
                    "type":"post",
                    "data":{
                     "array" :  [8,5,7]
                    },
                    "dateType":"text",
                    "success" : function (response) {
                        alert(response);
                    },
                    "error":function (response) {
                        alert(response);
                    }
                });
            });
        });

    </script>
</head>
<body>
    <a href="querry.html">测试</a><br>
    <a href="test/activiti.html">测试activiti</a><br>
    <button id="textjson" >测试1</button><br>
    <button id="textjson2">测试2</button><br>
    <button id="textjson3">测试3</button><br>
    <button id="textjson4">测试4</button><br>
    <button id="btn">点我一下试试</button>
</body>
</html>