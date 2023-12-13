package com.example.bighome;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "OperatorShowDataServlet", value = "/opsd")
public class OperatorShowDataServlet extends HttpServlet {

    AllMysql conn = null;

    public void init() {
        conn = new AllMysql();
        if (conn.connection == null) {
            // 抛出一个异常
            try {
                throw new ServletException("数据库连接失败");
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 读取请求体
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // 将请求体解析为JSON对象
        String jsonString = requestBody.toString();
//        System.out.println("Received JSON: " + jsonString);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        if (jsonObject == null) {
            System.out.println("[argv op] parse json error");
            return;
        }
        if (jsonObject.getString("money") == null) {
            System.out.println("[argv op] username or name is null");
            return;
        }
        if (jsonObject.getString("money").isEmpty()) {
            System.out.println("[argv op] username or name is null");
            return;
        }

        JSONObject loginStatus = conn.getLoginStatus(request);

        if (loginStatus.getString("isLogin").equals("no")) {
            System.out.println("[argv op] is no login");
            return;
        }

        String name = "安富 200110期";
        String username = loginStatus.getString("username");
        String money = jsonObject.getString("money");

        JSONObject preData = conn.getShowData(name, username);
// js:
//        if (money < this.startmoney) {
//            this.message = "金额小于可投起始金额";
//            return;
//        } else if (this.endmoney - money < 0) {
//            this.message = "金额大于可投剩余金额";
//            return;
//        } else if (money > this.usermoney) {
//            this.message = "金额大于用户账户余额";
//            return;
//        }else if (new Date(this.endtime).getTime() < new Date().getTime()) {
//            this.message = "已截止";
//            return;
//        }
        // java:
        JSONObject ret = new JSONObject();

        double newMoney = Double.parseDouble(money);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(preData.getString("endtime"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        long timestamp = date.getTime();

        if (newMoney < Double.parseDouble(preData.getString("startmoney"))) {
            System.out.println("[argv op] money < startmoney");
            ret.put("message", "金额小于可投起始金额");
        } else if ((Double.parseDouble(preData.getString("summoney")) - Double.parseDouble(preData.getString("havemoney"))) - newMoney < 0) {
            System.out.println("[argv op] endmoney - money < 0");
            ret.put("message", "金额大于可投剩余金额");
        } else if (newMoney > Double.parseDouble(preData.getString("usermoney"))) {
            System.out.println("[argv op] money > usermoney");
            ret.put("message", "金额大于用户账户余额");
        } else if (timestamp < System.currentTimeMillis()) {
            System.out.println("[argv op] endtime < now");
            ret.put("message", "已截止");
        } else {
            conn.insertOrUpdateUserShowDataTable(username, name, money);
            ret.put("message", "投资成功");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ret.toJSONString());

    }

    public void destroy() {
        conn.closeMysql();
    }

}