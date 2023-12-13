package com.example.bighome;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(name = "ShowDataServlet", value = {"/showData"})
public class ShowDataServlet extends HttpServlet {

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
            System.out.println("[argv showData post] parse json error");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject loginData = conn.getLoginStatus(request);

        if (loginData.getString("isLogin").equals("yes")) {
            System.out.println("[argv showData post] showData is login");
            if (jsonObject.getString("username") == null || jsonObject.getString("name") == null) {
                System.out.println("[argv showData post] username or name is null");
                return;
            }
            if (jsonObject.getString("username").isEmpty() || jsonObject.getString("name").isEmpty()) {
                System.out.println("[argv showData post] username or name is null");
                return;
            }

            String name = "安富 200110期";
            String username = loginData.getString("username");

            response.getWriter().write(conn.getShowData(name, username).toJSONString());

        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginRegister.html");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject loginData = conn.getLoginStatus(request);

        if (loginData.getString("isLogin").equals("yes")) {
            System.out.println("[argv showData get] showData is login");
            String page = request.getParameter("page");
            if (page == null) {
                System.out.println("[argv showData get] no set page it will be default");
                page = "showData";
            }
//            System.out.println("page:" + page);
            switch (page) {
                case "notice": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("notice.html");
                    try {
                        dispatcher.forward(request, response);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "financing": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("financing.html");
                    try {
                        dispatcher.forward(request, response);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "showData": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("showData.html");
                    try {
                        dispatcher.forward(request, response);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "404page": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("404page.html");
                    try {
                        dispatcher.forward(request, response);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "about": {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("about.html");
                    try {
                        dispatcher.forward(request, response);
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                default:
                    System.out.println("[argv showData get] page error");
                    break;
            }
        } else {
            System.out.println("[argv showData get] ShowDataServlet get no login");
            RequestDispatcher dispatcher = request.getRequestDispatcher("loginRegister.html");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void destroy() {
        conn.closeMysql();
    }

}