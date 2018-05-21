<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="/SZFC2/js/layui/css/layui.css" media="all"/>
    <style type="text/css" >
        a:link{
            color:#FF0000;
        }
    </style>

    <script type="text/javascript" src="/SZFC2/js/layui/layui.js"></script>
    <script type="text/javascript" src="/SZFC2/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
</head>
<body>
<div class="layui-row">
    <h2>每日二手成交量系统</h2>
</div>
<hr class="layui-bg-red">
<br/>
<div class="layui-row">
    <div class="layui-col-md1 layui-col-space5">
        选择时间
    </div>
    <div class="layui-col-md2 layui-col-space5">
        <input type="text" class="layui-input" id="timeRangePickerStart"/>&nbsp;
    </div>
    <div class="layui-col-md2 layui-col-space5">
        <input type="text" class="layui-input" id="timeRangePickerEnd"/>
    </div>
</div>
<br/>
<div class="layui-row layui-col-md-offset2">
    <input type="button" name="button" class="layui-btn layui-btn-radius" onclick="getResult()" value="确定"
           id="queryCountButton"/>
</div>

<br/>
<hr class="layui-bg-orange">

<div style="width: 600px;">
    <table id="queryStockDealCount" style="width:600px;">

    </table>
</div>
<div >
    excel文件下载：
    <a href="" id="downloadHref" ></a>
</div>


<!--增加内容-->
<br>
<hr class="layui-bg-red">
<br>
<p id="introduction" style="margin-left: 50px;">

</p>
<div id="container" style="width:600px;height:300px;">

</div>

<div id="container2" style="width:600px;height:300px;">

</div>


<script>
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

    var today = new Date();
    var before = today - 1000 * 60 * 60 * 24;
    var yesterday = new Date(before);
    var yesterdayDate = yesterday.format("yyyy-MM-dd")


    layui.use('laydate', function () {
        var laydate = layui.laydate;

        laydate.render({
            elem: '#timeRangePickerStart'
            , value: yesterdayDate
            , theme: 'molv'
        });
        laydate.render({
            elem: '#timeRangePickerEnd'
            , value: yesterdayDate
            , theme: 'molv'
        });
    });


    function getResult() {
        var startDate = document.getElementById("timeRangePickerStart").value;
        var endDate = document.getElementById("timeRangePickerEnd").value;
        console.log("startDate:" + startDate + " endDate:" + endDate)


        layui.use('table', function () {
            var table = layui.table;
            console.log("table init");
            table.render({ //其它参数在此省略
                elem: '#queryStockDealCount',
                cols: [[{field: 'areaName', title: '区域名', width: 100},
                    {field: 'zhu', title: '住房', width: 100},
                    {field: 'zhu_Area', title: '住房', width: 100},
                    {field: 'feiZhu', title: '非住房', width: 100},
                    {field: 'feiZhu_Area', title: '非住房', width: 100}]],
                //  data:[{"id":10000,"username":"user-0","sex":"女","city":"城市-0","sign":"签名-0","experience":255,"logins":24,"wealth":82830700,"classify":"作家","score":57},{"id":10001,"username":"user-1","sex":"男","city":"城市-1","sign":"签名-1","experience":884,"logins":58,"wealth":64928690,"classify":"词人","score":27},{"id":10002,"username":"user-2","sex":"女","city":"城市-2","sign":"签名-2","experience":650,"logins":77,"wealth":6298078,"classify":"酱油","score":31},{"id":10003,"username":"user-3","sex":"女","city":"城市-3","sign":"签名-3","experience":362,"logins":157,"wealth":37117017,"classify":"诗人","score":68},{"id":10004,"username":"user-4","sex":"男","city":"城市-4","sign":"签名-4","experience":807,"logins":51,"wealth":76263262,"classify":"作家","score":6},{"id":10005,"username":"user-5","sex":"女","city":"城市-5","sign":"签名-5","experience":173,"logins":68,"wealth":60344147,"classify":"作家","score":87},{"id":10006,"username":"user-6","sex":"女","city":"城市-6","sign":"签名-6","experience":982,"logins":37,"wealth":57768166,"classify":"作家","score":34},{"id":10007,"username":"user-7","sex":"男","city":"城市-7","sign":"签名-7","experience":727,"logins":150,"wealth":82030578,"classify":"作家","score":28},{"id":10008,"username":"user-8","sex":"男","city":"城市-8","sign":"签名-8","experience":951,"logins":133,"wealth":16503371,"classify":"词人","score":14},{"id":10009,"username":"user-9","sex":"女","city":"城市-9","sign":"签名-9","experience":484,"logins":25,"wealth":86801934,"classify":"词人","score":75},{"id":10010,"username":"user-10","sex":"女","city":"城市-10","sign":"签名-10","experience":1016,"logins":182,"wealth":71294671,"classify":"诗人","score":34},{"id":10011,"username":"user-11","sex":"女","city":"城市-11","sign":"签名-11","experience":492,"logins":107,"wealth":8062783,"classify":"诗人","score":6},{"id":10012,"username":"user-12","sex":"女","city":"城市-12","sign":"签名-12","experience":106,"logins":176,"wealth":42622704,"classify":"词人","score":54},{"id":10013,"username":"user-13","sex":"男","city":"城市-13","sign":"签名-13","experience":1047,"logins":94,"wealth":59508583,"classify":"诗人","score":63},{"id":10014,"username":"user-14","sex":"男","city":"城市-14","sign":"签名-14","experience":873,"logins":116,"wealth":72549912,"classify":"词人","score":8},{"id":10015,"username":"user-15","sex":"女","city":"城市-15","sign":"签名-15","experience":1068,"logins":27,"wealth":52737025,"classify":"作家","score":28},{"id":10016,"username":"user-16","sex":"女","city":"城市-16","sign":"签名-16","experience":862,"logins":168,"wealth":37069775,"classify":"酱油","score":86},{"id":10017,"username":"user-17","sex":"女","city":"城市-17","sign":"签名-17","experience":1060,"logins":187,"wealth":66099525,"classify":"作家","score":69},{"id":10018,"username":"user-18","sex":"女","city":"城市-18","sign":"签名-18","experience":866,"logins":88,"wealth":81722326,"classify":"词人","score":74},{"id":10019,"username":"user-19","sex":"女","city":"城市-19","sign":"签名-19","experience":682,"logins":106,"wealth":68647362,"classify":"词人","score":51},{"id":10020,"username":"user-20","sex":"男","city":"城市-20","sign":"签名-20","experience":770,"logins":24,"wealth":92420248,"classify":"诗人","score":87},{"id":10021,"username":"user-21","sex":"男","city":"城市-21","sign":"签名-21","experience":184,"logins":131,"wealth":71566045,"classify":"词人","score":99},{"id":10022,"username":"user-22","sex":"男","city":"城市-22","sign":"签名-22","experience":739,"logins":152,"wealth":60907929,"classify":"作家","score":18},{"id":10023,"username":"user-23","sex":"女","city":"城市-23","sign":"签名-23","experience":127,"logins":82,"wealth":14765943,"classify":"作家","score":30},{"id":10024,"username":"user-24","sex":"女","city":"城市-24","sign":"签名-24","experience":212,"logins":133,"wealth":59011052,"classify":"词人","score":76},{"id":10025,"username":"user-25","sex":"女","city":"城市-25","sign":"签名-25","experience":938,"logins":182,"wealth":91183097,"classify":"作家","score":69},{"id":10026,"username":"user-26","sex":"男","city":"城市-26","sign":"签名-26","experience":978,"logins":7,"wealth":48008413,"classify":"作家","score":65},{"id":10027,"username":"user-27","sex":"女","city":"城市-27","sign":"签名-27","experience":371,"logins":44,"wealth":64419691,"classify":"诗人","score":60},{"id":10028,"username":"user-28","sex":"女","city":"城市-28","sign":"签名-28","experience":977,"logins":21,"wealth":75935022,"classify":"作家","score":37},{"id":10029,"username":"user-29","sex":"男","city":"城市-29","sign":"签名-29","experience":647,"logins":107,"wealth":97450636,"classify":"酱油","score":27}]
                url: '/SZFC2/queryStockDealCount',
                where: {'startDate': startDate, 'endDate': endDate},
                done: function (res, curr, count) {
                    response = res;
                    //console.log("response: "+response.data[0].zhu);
                    showWechatArticle(response);
                    genPie(response, '住房成交量统计', 'container', 'zhu');
                    genPie(response, '非住房成交量统计', 'container2', 'feiZhu')
                }
            });
        });

        //实现文件超链接
        document.getElementById("downloadHref").href="/SZFC2/download?filename="+startDate+","+endDate+".xls&startDate="+startDate+"&endDate="+endDate+"&type=stockDealLocation";
        document.getElementById("downloadHref").innerHTML=startDate+"~"+endDate+".xls";
    }
    function showWechatArticle(response) {
        var zhu = calcuAll(response, 'zhu');
        var feizhu = calcuAll(response, 'feizhu');
        var zhuArea = calcuAll2(response, 'zhu');
        var feizhuArea = calcuAll2(response, 'feizhu');
        var areaAll=(zhuArea + feizhuArea);
        var text = yesterdayDate + '号二手房共计成交' + (zhu + feizhu) + '套，成交面积' +  parseFloat(Number(areaAll).toFixed(2)) + '平方米<br>'
            + '其中住宅成交' + zhu + '套，成交面积' + parseFloat(Number(zhuArea).toFixed(2)) + '平方米<br>'
            + '非住宅成交' + feizhu + '套，成交面积' + parseFloat(Number(feizhuArea).toFixed(2)) + '平方米<br>'
            +'住宅成交情况<br>'
            +'非住宅成交情况<br>'
            +'可售情况<br>';
        $("#introduction").html(text);
    }
    function calcuAll(response, zhuOrfeizhu) {
        var all = 0;
        for (var i in response.data) {

            if (zhuOrfeizhu == 'zhu') {
                all += response.data[i].zhu;
            } else {
                all += response.data[i].feiZhu;
            }
        }
        return all;
    }
    function calcuAll2(response, zhuOrfeizhu) {
        var all = 0;
        for (var i in response.data) {

            if (zhuOrfeizhu == 'zhu') {

                all += response.data[i].zhu_Area;
            } else {
                all += response.data[i].feiZhu_Area;
            }
        }
        return all;
    }
    function genPie(response, title, divId, zhuOrfeizhu) {
        //生成图
        var dom = document.getElementById(divId);
        var myChart = echarts.init(dom);
        var app = {};
        option = null;

        var legendData = [];
        var seriesData = [];
        var selected = {};
        //tempData=response.data;
        console.log(response.data[0].areaName);
        for (var i in response.data) {

            var regionName = response.data[i].areaName;
            console.log(regionName);
            legendData.push(regionName);
            if (zhuOrfeizhu == 'zhu') {
                seriesData.push({name: regionName, value: response.data[i].zhu});
            } else {
                seriesData.push({name: regionName, value: response.data[i].feiZhu});
            }
            selected[regionName] = i + 1;
        }

        option = {
            title: {
                text: title,
                subtext: '',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                type: 'scroll',
                orient: 'horizontal',
                //right: 10,
                left: 'center',
                top: 'bottom',
                //bottom: 20,
                data: legendData,

                selected: selected
            },
            series: [
                {
                    name: '区域',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '50%'],
                    data: seriesData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    markLine: {
                        label: {
                            normal: {
                                show: false
                            }
                        }
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    }

</script>

</body>
</html>
