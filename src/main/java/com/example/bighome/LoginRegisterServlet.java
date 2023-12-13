package com.example.bighome;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

@WebServlet(name = "LoginRegisterServlet", value = "/lg")
public class LoginRegisterServlet extends HttpServlet {

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("loginRegister.html");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
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
        boolean flagNoNamePd = false;
        if (jsonObject == null) {
            System.out.println("[argv login] parse json error");
            return;
        }
        if (jsonObject.getString("username") == null || jsonObject.getString("password") == null) {
            System.out.println("[argv login] username or password is null");
            flagNoNamePd = true;
        }


        if (jsonObject.getString("type") == null) {
            System.out.println("[argv login] type is null");
            return;
        }

        JSONObject jsonObject1 = new JSONObject();
        HttpSession session = request.getSession();

        if (jsonObject.getString("type").equals("login")) {
            if (flagNoNamePd) {
                return;
            }
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            if (conn.haveUserData(username, password)) {
                jsonObject1.put("status", "success");
                jsonObject1.put("message", "登录成功");
                session.setAttribute("username", username);
                session.setAttribute("password", password);
                response.addCookie(new Cookie("username", username));
                response.addCookie(new Cookie("password", password));
                response.addCookie(new Cookie("SameSite", "None"));
            } else {
                jsonObject1.put("status", "failed");
                jsonObject1.put("message", "用户不存在或密码错误");
            }
        } else if (jsonObject.getString("type").equals("register")) {
            if (flagNoNamePd) {
                return;
            }
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            if (conn.haveUserData(username)) {
                jsonObject1.put("status", "failed");
                jsonObject1.put("message", "用户名已被注册");
            } else {
                conn.insertUserTable(username, password, "500000");
                jsonObject1.put("status", "success");
                jsonObject1.put("message", "注册成功");
            }
        } else if (jsonObject.getString("type").equals("loginOut")) {
            // delete session
            session.invalidate();
            System.out.println("[argv loginOut] clear session");
            // delete cookie
            response.addCookie(new Cookie("username", ""));
            response.addCookie(new Cookie("password", ""));
            response.addCookie(new Cookie("SameSite", "None"));
            System.out.println("[argv loginOut] clear cookie");
        } else {
            System.out.println("[argv login] type is not match");
            return;
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonObject1.toJSONString());
    }


    public void destroy() {
        conn.closeMysql();
    }
}