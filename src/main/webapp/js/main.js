function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}



function IsEmail(item){
	var etext
	var elen
	var i
	var aa
	var uyear,umonth,uday
	etext=item;
	elen=etext.length;
	if (elen<5)
 		return true;
	i= etext.indexOf("@",0)
	if (i==0 || i==-1 || i==elen-1)
 		return true;
	else
 		{if (etext.indexOf("@",i+1)!=-1)
  			return true;}
		if (etext.indexOf("..",i+1)!=-1)
 		return true;
	i=etext.indexOf(".",0)
	if (i==0 || i==-1 || etext.charAt(elen-1)=='.')
 		return true;
	if ( etext.charAt(0)=='-' ||  etext.charAt(elen-1)=='-')
 		return true;
	if ( etext.charAt(0)=='_' ||  etext.charAt(elen-1)=='_')
 		return true;
	for (i=0;i<=elen-1;i++)
		{ aa=etext.charAt(i)
 		 if (!((aa=='.') || (aa=='@') || (aa=='-') ||(aa=='_') || (aa>='0' && aa<='9') || (aa>='a' && aa<='z') || (aa>='A' && aa<='Z')))
   			return true;
		}
	return false;
}

/**
 * 提示框
 * @param msg 提示信息
 * @param sign 提示类型
 * @param ok 回调函数/类型为confirm时，确定按钮的回调函数
 * @param can 弹窗消失延迟时间/类型为confirm时，取消按钮的回调函数
 */
function scscms_alert(msg,sign,ok,can){
    var c_=false;//是否已经关闭窗口，解决自动关闭与手动关闭冲突
    sign=sign||"";
    var s="<div id='mask_layer'></div><div id='scs_alert'><div id='alert_top'></div><div id='alert_bg'><table width='260' align='center' border='0' cellspacing='0' cellpadding='1'><tr>";
    if (sign!="")s+="<td width='45'><div id='inco_"+sign+"'></div></td>";
    s+="<td id='alert_txt'>"+msg+"</td></tr></table>";
    if (sign=="confirm"){
        s+="<a href='javascript:void(0)' id='confirm_ok'>确 定</a><a href='javascript:void(0)' id='confirm_cancel'>取 消</a>";
    }else{
        s+="<a href='javascript:void(0)' id='alert_ok'>确 定</a>"
    }
    s+="</div><div id='alert_foot'></div></div>";
    $("body").append(s);
    $("#scs_alert").css("margin-top",-($("#scs_alert").height()/2)+"px"); //使其垂直居中
    $("#scs_alert").focus(); //获取焦点，以防回车后无法触发函数

    if (typeof can == "number"){
        //定时关闭提示
        setTimeout(function(){
            close_info();
        },can*1000);
    }
    function close_info(){
        //关闭提示窗口
        if(!c_){
            $("#mask_layer").fadeOut("fast",function(){
                $("#scs_alert").remove();
                $(this).remove();
            });
            c_=true;
        }
    }
    $("#alert_ok").click(function(){
        close_info();
        if(typeof(ok)=="function")ok();
    });
    $("#confirm_ok").click(function(){
        close_info();
        if(typeof(ok)=="function")ok();
    });
    $("#confirm_cancel").click(function(){
        close_info();
        if(typeof(can)=="function")can();
    });
    function modal_key(e){
        e = e||event;
        close_info();
        var code = e.which||event.keyCode;
        if (code == 13 || code == 32){if(typeof(ok)=="function")ok()}
        if (code == 27){if(typeof(can)=="function")can()}
    }
    //绑定回车与ESC键
    if (document.attachEvent)
        document.attachEvent("onkeydown", modal_key);
    else
        document.addEventListener("keydown", modal_key, true);
}

function checkReg() {
    var temp;
    temp = new String(document.reg.password.value);
    if(document.reg.name.value == "") {
        $('#message').html("<font color=\"#F60018\">* 请输入用户名!</font>");
        return false;
    }
    if(document.reg.password.value == "") {
        $('#passwordMsg').html("<font color=\"#F60018\">* 请输入密码!</font>")
        return false;
    }else if(temp.length < 6 || temp.length > 8) {
        $('#passwordMsg').html("<i class=\"fa fa-times-circle\" style=\"font-size:14px;color:red\"></i><font color=\"#F60018\"> 您的密码少于6位或多于16位!</font>")
        return false;
    }
    if(document.reg.password2.value == "") {
        $('#password2Msg').html("<font color=\"#F60018\">* 请再次输入密码!</font>")
        return false;
    } else if(document.reg.password.value != document.reg.password2.value) {
        $('#password2Msg').html("<i class=\"fa fa-times-circle\" style=\"font-size:14px;color:red\"></i><font color=\"#F60018\"> 两次密码不一致!</font>")
        return false;
    }
    if(document.reg.email.value != "" & IsEmail(document.reg.email.value)) {
        $('#emailMsg').html("<i class=\"fa fa-times-circle\" style=\"font-size:14px;color:red\"></i><font color=\"#F60018\"> 您的E-mail不符合规范!</font>")
        return false;
    }
    $.post("https://estore.bvear.com/customer", $('#regForm').serialize(), function (data) {
        if(data.status == 200){
            scscms_alert("注册成功，现在去登录？","confirm",function(){
                window.location.href="https://estore.bvear.com/login.html";
            },function(){
                window.location.href="https://estore.bvear.com/index.html";
            });
        }else {
            scscms_alert("注册失败，您的用户名可能被抢注了","error");
        }
    }, "json")
}

/**
 * 获取url参数值
 * @param name 参数名
 * @returns {*} 参数值
 */
function getUrlParam(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

/**
 * 发送加购请求
 * @param productId
 */
function addProduct(productId) {
    var url = "https://estore.bvear.com/shoppingCar/" + productId;
    $.post(url, function (data) {
        if(data.status == 200){
            scscms_alert("加购成功","ok");
        }else {
            scscms_alert("加购失败","error");
        }
    })
}

/**
 * 创建首页的商品列表
 * @param pageInfo
 */
function createBookTable(pageInfo) {
    var tableData = "<tr>\n" +
        "    <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>序号</b></font></td>\n" +
        "    <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>产品名称</b></font></td>\n" +
        "    <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>价格</b></font></td>\n" +
        "    <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>操作</b></font></td>\n" +
        "  </tr>";
    var books = pageInfo.list;
    for(var i = 0; i < books.length; i++){
        tableData += "<tr>";"+  +"
        tableData += "<td class=tablebody2 valign=middle align=center>"+ (i+1) +"</td>";
        tableData += "<td class=tablebody1 valign=middle>&nbsp;&nbsp;<a href=\"productDetail.html?productId="+ books[i].id +"\">"+ books[i].name +"</a></td>";
        tableData += "<td class=tablebody2 valign=middle align=center>￥"+ books[i].price +"</td>";
        tableData += "<td class=tablebody1 valign=middle align=center><a href=\"javascript:addProduct("+ books[i].id +")\">";
        tableData += "<img border=\"0\" src=\"images/car_new.gif\" width=\"97\" height=\"18\"></a> </td>";
        tableData += "</tr>";
    }
    tableData += "<tr align=\"center\" bgcolor=\"#FFFFFF\">";
    tableData += "<td colspan=\"5\">共"+ pageInfo.total +"条记录 每页 3 条 第"+ pageInfo.pageNum +"/"+ pageInfo.pages +"页&nbsp;";
    tableData += "<a href=\"javascript:showBookList(1);\">首页</a>&nbsp;";
    if(pageInfo.hasPreviousPage){
        tableData += "<a href=\"javascript:showBookList("+ pageInfo.prePage +");\">上一页</a>&nbsp;";
    }
    if(pageInfo.hasNextPage){
        tableData += "<a href=\"javascript:showBookList("+ pageInfo.nextPage +");\">下一页</a>&nbsp;";
    }
    tableData += "<a href=\"javascript:showBookList("+ pageInfo.lastPage +");\">尾页</a>";
    tableData += "</td></tr>";

    $('#bookListTable').html(tableData);
}

/**
 * 创建购物车列表
 * @param lines
 */
function createLinesTable(lines) {
    var totalcost = 0;
    var tableData = "<tr>\n" +
        "        <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>序号</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>产品名称</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>价格</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>数量</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>合计</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>操作</b></font></td>\n" +
        "    </tr>\n";
    if(lines != null && lines.length != 0) {
        for (var i = 0; i < lines.length; i++) {
            totalcost += lines[i].cost;
            tableData += "<tr><form id=\"showLine" + i + "\" method=\"post\" action=\"\" name=\"f1\">";
            tableData += "<input type=\"hidden\" name=\"productid\" value=\"" + lines[i].book.id + "\">";
            tableData += "<input id=\"lineNumber" + i + "\" type=\"hidden\" name=\"number\" value=\"" + lines[i].num + "\">";
            tableData += "<td class=tablebody2 valign=middle align=center >" + (i + 1) + "</td>";
            tableData += "<td class=tablebody1 valign=middle>&nbsp;&nbsp;<a href=\"productDetail.html?productId=" + lines[i].book.id + "\">" + lines[i].book.name + "</a></td>";
            tableData += "<td class=tablebody2 valign=middle align=center>￥" + lines[i].book.price + "</td>"
            tableData += "<td class=tablebody1 valign=middle align=center><input id=\"lineNum" + i + "\" type=\"text\" name=\"num\" value=\"" + lines[i].num + "\" size=\"4\" onblur=\"javascript:updateLineNum(" + i + "," + lines[i].book.id + ",this.value)\"/></td>";
            tableData += "<td class=tablebody2 valign=middle align=left>&nbsp;&nbsp;￥" + lines[i].cost + "</td>";
            tableData += "<td class=tablebody1 valign=middle align=center >";
            tableData += "<input type=\"button\" value=\"删除\" onclick=\"javascript:removeProduct(" + lines[i].book.id + ");\">";
            tableData += "</form></tr>"
        }
        tableData += "<tr>";
        tableData += "<td class=tablebody1 valign=middle align=center colspan=\"4\">　</td>";
        tableData += "<td class=tablebody1 valign=middle align=left>&nbsp;&nbsp;<font color=\"#ff0000\"><b>￥" + totalcost + "</b></font></td>";
        tableData += "<td class=tablebody1 valign=middle align=center >　</td>";
        tableData += "</tr><tr>";
        tableData += "<td class=tablebody2 valign=middle align=center colspan=\"6\"><input type=\"button\" value=\"提交订单\" onclick=\"javascript:window.location='superUser/confirmOrder.html';\">&nbsp;&nbsp;";
        tableData += "<input type=\"button\" value=\"继续购物\" onclick=\"javascript:window.location='index.html';\">&nbsp;&nbsp;";
        tableData += "<input type=\"button\" value=\"清空购物车\" onclick=\"javascript:removeAllProduct();\"></td>";
        tableData += "</tr>";
    }else {
        tableData += "<tr>";
        tableData += "<td class=tablebody2 valign=middle align=center colspan=\"6\">空空如也</td>";
        tableData += "</tr>";
    }
    $('#showShoppingCar').html(tableData);
}

/**
 * 创建订单列表
 * @param orders
 */
function createOrderTable(orders) {
    var tableData = "<tr>\n" +
        "        <td valign=middle align=center height=25 background=\"../images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>序号</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"../images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>订单编号</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"../images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>订单金额</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"../images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>订单状态</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"../images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>付款方式</b></font></td>\n" +
        "        <td valign=middle align=center height=25 background=\"../images/bg2.gif\" width=\"\"><font color=\"#ffffff\"><b>操作</b></font></td>\n" +
        "    </tr>";
    if(orders != null && orders.length != 0){
        for (var i = 0; i < orders.length; i++) {
            var orderPayStatus = "未付款"
            var isPaid = false;
            if(orders[i].payStatus == "Paid"){
                orderPayStatus = "已付款";
                isPaid = true;
            }
            tableData += "<tr>";
            tableData += "<td class=tablebody2 valign=middle align=center>" + (i+1) + "</td>";
            tableData += "<td class=tablebody1 valign=middle align='center'><a href=\"order/" + orders[i].id + "\">" + orders[i].id + "</a></td>";
            tableData += "<td class=tablebody2 valign=middle align=center>￥"+ orders[i].cost +"</td>";
            tableData += "<td class=tablebody1 valign=middle align=center>"+ orderPayStatus +"</td>";
            tableData += "<td class=tablebody2 valign=middle align=center>"+ orders[i].payway +"</td>";
            tableData += "<td class=tablebody1 valign=middle style='padding-left: 50px;'>";
            tableData += "<input type=\"button\" value=\"删除\" onclick=\"javascript:deleteOrder("+ orders[i].id +");\">&nbsp;";
            tableData += "<input type=\"button\" value=\"明细\" onclick=\"javascript:window.location='orderinfo.html?orderId="+ orders[i].id +"';\">&nbsp;";
            if(isPaid == false){
                tableData += "<input type=\"button\" value=\"去付款\" onclick=\"javascript:window.location='../superUser/order/payForOrder?orderIndex="+i+"';\">";
            }
            tableData += "</td></tr>";
        }
    }else {
        tableData += "<tr>";
        tableData += "<td class=tablebody2 valign=middle align=center colspan=\"6\">空空如也</td>";
        tableData += "</tr>";
    }
    $('#showOrderTable').html(tableData);
}

/**
 * 追加用户信息到提交订单界面或订单详情界面的用户信息表里
 * @param userinfo 用户信息
 */
function appendUserinfoTable(userinfo) {
    if(userinfo.address1 == null)
        userinfo.address1 = "";
    if(userinfo.zip == null)
        userinfo.zip = "";
    if(userinfo.homePhone == null)
        userinfo.homePhone = "";
    if(userinfo.officePhone == null)
        userinfo.officePhone = "";
    if(userinfo.mobilePhone == null)
        userinfo.mobilePhone = "";
    if(userinfo.email == null)
        userinfo.email = "";
    var userinfoTable = "<tr>";
    userinfoTable += "<td width=\"40%\" class=\"tablebody2\" align=\"right\"><b>用户名</b>：</td>";
    userinfoTable += "<td width=\"60%\" class=\"tablebody1\">" + userinfo.name + "</td>";
    userinfoTable += "</tr><tr>";
    userinfoTable += "<td class=\"tablebody2\" align=\"right\"><b>联系地址</b>：</td>";
    userinfoTable += "<td class=\"tablebody1\">" + userinfo.address1 + "</td>";
    userinfoTable += "</tr><tr>";
    userinfoTable += "<td class=\"tablebody2\" align=\"right\"><b>邮编</b>：</td>";
    userinfoTable += "<td class=\"tablebody1\">" + userinfo.zip + "</td>";
    userinfoTable += "</tr><tr>";
    userinfoTable += "<td class=\"tablebody2\" align=\"right\"><b>家庭电话</b>：</td>";
    userinfoTable += "<td class=\"tablebody1\">" + userinfo.homePhone + "</td>";
    userinfoTable += "</tr><tr>";
    userinfoTable += "<td class=\"tablebody2\" align=\"right\"><b>办公室电话</b>：</td>";
    userinfoTable += "<td class=\"tablebody1\">" + userinfo.officePhone + "</td>";
    userinfoTable += "</tr><tr>";
    userinfoTable += "<td class=\"tablebody2\" align=\"right\"><b>手机</b>：</td>";
    userinfoTable += "<td class=\"tablebody1\">" + userinfo.mobilePhone + "</td>";
    userinfoTable += "</tr><tr>";
    userinfoTable += "<td class=\"tablebody2\" align=\"right\"><b>Email地址</b>：</td>";
    userinfoTable += "<td class=\"tablebody1\">" + userinfo.email + "</td>";
    userinfoTable += "</tr>";

    $('#table1').append(userinfoTable);
}

/**
 * 追加购物车记录或订单项到 提交订单界面 或 订单详情界面 的商品购物清单里
 * @param cartLines
 */
function appendLinesTable(lines) {
    var cartLinesTable = "";
    var totalCost = 0.0;
    for(var i = 0; i < lines.length; i++){
        totalCost += lines[i].cost;
        cartLinesTable += "<tr>";
        cartLinesTable += "<td class=tablebody2 valign=middle align=center>" + (i+1) + "</td>";
        cartLinesTable += "<td class=tablebody1 valign=middle>&nbsp;&nbsp;<a href=\"../productDetail.html?productId=" + lines[i].book.id + "\" target=\"_blank\">" + lines[i].book.name + "</a></td>";
        cartLinesTable += "<td class=tablebody2 valign=middle align=center>价格：￥" + lines[i].book.price + "</td>";
        cartLinesTable += "<td class=tablebody1 valign=middle align=center>数量：" + lines[i].num + "</td>";
        cartLinesTable += "<td class=tablebody2 valign=middle align=left>小计：￥" + lines[i].cost + "</td>";
        cartLinesTable += "</tr>";
    }
    cartLinesTable += "<tr>";
    cartLinesTable += "<td class=tablebody1 valign=middle align=center colspan=\"4\">　</td>";
    cartLinesTable += "<td class=tablebody1 valign=middle align=left>合计：<font color=\"#ff0000\"><b>￥" + totalCost + "</b></font></td>";
    cartLinesTable += "</tr>";

    $('#table3').append(cartLinesTable);
}