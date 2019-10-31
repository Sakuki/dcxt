var pageSize = 8;    //每页显示的记录条数
var curPage=0;        //当前页
var lastPage;        //最后页
var direct=0;        //方向
var len;            //总行数
var page;            //总页数
var begin;
var end;

$(document).ready(function display(){
    len =$("#table_page tr").length - 1;    // 求这个表的总行数，剔除第一行介绍
    page=len % pageSize==0 ? len/pageSize : Math.floor(len/pageSize)+1;//根据记录条数，计算页数
    // alert("page==="+page);
    curPage=1;    // 设置当前为第一页
    displayPage(1);//显示第一页

    document.getElementById("btn0").innerHTML="当前 " + curPage + "/" + page + " 页    每页 ";    // 显示当前多少页
    document.getElementById("sum").innerHTML="数据总量 " + len + "";        // 显示数据量
    document.getElementById("pageSize").value = pageSize;



    $("#btn_first").click(function firstPage(){    // 首页
        curPage=1;
        direct = 0;
        displayPage();
    });
    $("#btn_pre").click(function frontPage(){    // 上一页
        direct=-1;
        displayPage();
    });
    $("#btn_next").click(function nextPage(){    // 下一页
        direct=1;
        displayPage();
    });
    $("#btn_last").click(function lastPage(){    // 尾页
        curPage=page;
        direct = 0;
        displayPage();
    });
    $("#btn_turn").click(function changePage(){    // 转页
        curPage=document.getElementById("changePage").value * 1;
        if (!/^[1-9]\d*$/.test(curPage)) {
            alert("请输入正整数");
            return ;
        }
        if (curPage > page) {
            alert("超出数据页面");
            return ;
        }
        direct = 0;
        displayPage();
    });


});

function displayPage(){
    if(curPage <=1 && direct==-1){
        direct=0;
        alert("已经是第一页了");
        return;
    } else if (curPage >= page && direct==1) {
        direct=0;
        alert("已经是最后一页了");
        return ;
    }

    lastPage = curPage;

    // 修复当len=1时，curPage计算得0的bug
    if (len > pageSize) {
        curPage = ((curPage + direct + len) % len);
    } else {
        curPage = 1;
    }


    document.getElementById("btn0").innerHTML="当前 " + curPage + "/" + page + " 页    每页 ";        // 显示当前多少页

    begin=(curPage-1)*pageSize + 1;// 起始记录号
    end = begin + 1*pageSize - 1;    // 末尾记录号


    if(end > len ) end=len;
    $("#table_page tr").hide();    // 首先，设置这行为隐藏
    $("#table_page tr").each(function(i){    // 然后，通过条件判断决定本行是否恢复显示
        if((i>=begin && i<=end) || i==0 )//显示begin<=x<=end的记录
            $(this).show();
    });

}
