<%--
  Created by IntelliJ IDEA.
  User: xijiaxiang
  Date: 2018/5/26
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>预售证信息汇总</title>
    <link rel="stylesheet" href="/SZFC2/js/layui/css/layui.css" media="all"/>
    <style type="text/css">
        a:link {
            color: #FF0000;
        }
    </style>
    <script type="text/javascript" src="/SZFC2/js/layui/layui.js"></script>
    <script type="text/javascript" src="/SZFC2/js/jquery/jquery.min.js"></script>
</head>
<body>
<div >
    <table id="CountTable" >

    </table>
</div>

<script>
    layui.use('table', function () {
        var table = layui.table;
        console.log("table init");
        table.render({ //其它参数在此省略
            elem: '#CountTable',
            cols: [[{field: 'yszNumber', title: '预售证号', width: 200},
                {field: 'projectName', title: '项目名', width: 200},
                {field: 'projectAddress', title: '项目地址', width: 300},
                {field: 'yszStartDate', title: '开始时间', width: 300}]],
            url: '/SZFC2/queryYSZ',
            page: true,
            limit:10,
            done: function (res, curr, count) {
                console.log("done: "+curr+","+count);
            }
        });
    });
</script>
</body>
</html>
