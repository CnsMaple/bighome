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

@WebServlet(name = "GetFinancingDataServlet", value = "/getFinancingData")
public class GetFinancingDataServlet extends HttpServlet {

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 读取请求体
//        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//        StringBuilder requestBody = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            requestBody.append(line);
//        }
//
//        // 将请求体解析为JSON对象
//        String jsonString = requestBody.toString();
//        System.out.println("Received JSON: " + jsonString);
//        JSONObject jsonObject = JSONObject.parseObject(jsonString);
//        if (jsonObject == null) {
//            System.out.println("parse json error");
//            return;
//        }

        String type = request.getParameter("type");
        if (type == null) {
            System.out.println("[argv] get financing data error, the type is null");
            return;
        }

        JSONObject loginData = conn.getLoginStatus(request);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (loginData.getString("isLogin").equals("yes")) {
            if (type.equals("financing")) {
                response.getWriter().write(conn.getFinancingData().toString());
            } else if (type.equals("year")) {
                response.getWriter().write(conn.getYearGroupData().toString());
            } else if (type.equals("notice")) {
                String year = request.getParameter("year");
                if (year == null) {
                    System.out.println("[argv] get notice error, year is null");
                    return;
                }
                response.getWriter().write(conn.getNoticeData(year).toString());
            }else{
                System.out.println("[argv] get financing data error, the type is not match");
                return;
            }
        } else {
            System.out.println("[argv] get financing data error, the user is not login");
        }

    }

    public void destroy() {
        conn.closeMysql();
    }

}