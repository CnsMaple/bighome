package com.example.bighome;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.*;

public class AllMysql {

    private static final String userTable = "user";

    private static final String userTableCheck = "CREATE TABLE if not exists `" + userTable + "` (" +
            "  `usrname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL," +
            "  `usrpassword` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL," +
            "  `money` double NOT NULL DEFAULT '500000'," +
            "  PRIMARY KEY (`usrname`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static final String showDataTable = "showdata";

    private static final String showDataTableCheck = "CREATE TABLE if not exists `" + showDataTable + "` ( " +
            "`name` varchar(100) NOT NULL," +
            "`lv` double NOT NULL," +
            "`starttime` datetime NOT NULL," +
            "`endtime` datetime NOT NULL," +
            "`barmoney` double NOT NULL," +
            "`startmoney` double NOT NULL," +
            "`summoney` double NOT NULL," +
            "`havemoney` double NOT NULL," +
            "PRIMARY KEY (`name`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static final String userShowDataTable = "usershowdata";

    private static final String userShowDataTableCheck = "CREATE TABLE if not exists `" + userShowDataTable + "` (" +
            "`username` varchar(100) NOT NULL," +
            "`showdataname` varchar(100) NOT NULL," +
            "`havemoney` double NOT NULL," +
            "PRIMARY KEY (`username`,`showdataname`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";


    private static final String financingTable = "financingdata";

    private static final String financingTableCheck = "CREATE TABLE if not exists `" + financingTable + "` (" +
            "`code` varchar(100) NOT NULL," +
            "`name` varchar(100) NOT NULL," +
            "`category` varchar(100) NOT NULL," +
            "`issuer` varchar(100) NOT NULL," +
            "`raiseMethod` varchar(100) NOT NULL," +
            "`salesArea` varchar(100) NOT NULL," +
            "`riskLevel` varchar(100) NOT NULL," +
            "`status` varchar(100) NOT NULL," +
            "`netValue` varchar(100) NOT NULL," +
            "`latestTip` varchar(100) NOT NULL," +
            "`details` varchar(100) NOT NULL," +
            "`notice` varchar(100) NOT NULL," +
            "`time` date NOT NULL," +
            "PRIMARY KEY (`code`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    Connection connection = null;

    public AllMysql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("[MYSQL] can not find mysql driver");
            e.printStackTrace();
        }
        String name = "root";
        String pd = "";
        String database = "czywork";
        String url = "jdbc:mysql://localhost:3306/" + database;
        try {
            connection = DriverManager.getConnection(url, name, pd);
            checkTable();
        } catch (SQLException e) {
            System.out.println("[MYSQL] can not connect mysql");
            e.printStackTrace();
        }
    }

    public void closeMysql() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("[MYSQL] closeMysql error");
            e.printStackTrace();
        }
    }

    private boolean selectFinancingDataNewTabel() {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from " + financingTable;
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL INIT] select financing new data error");
            e.printStackTrace();
        }
        return false;
    }

    private void insertFinancingDataNewTabel() {
        if (!selectFinancingDataNewTabel()) {
            return;
        }
        // 1.创建一个列表。2.有30条不同的数据。3.插入到数据库中
        String[][] data = {
                {"A120A0040", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2021-04-27"},
                {"A120A0041", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2020-04-27"},
                {"A120A0042", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2019-04-27"},
                {"A120A0043", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2022-04-27"},
                {"A120A0044", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2023-04-27"},
                {"A120A0045", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2018-04-27"},
                {"A120A0046", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2022-04-27"},
                {"A120A0047", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2022-04-27"},
                {"A120A0048", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2021-04-27"},
                {"A120A0049", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较低风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之双赢稳健美元一年持有理财产品", "2019-04-27"},
                {"A120A0050", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2021-04-27"},
                {"A120A0051", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2020-04-27"},
                {"A120A0052", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2019-04-27"},
                {"A120A0053", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2022-04-27"},
                {"A120A0054", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2023-04-27"},
                {"A120A0055", "同盈计划1239期", "血亏", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2018-04-27"},
                {"A120A0056", "同盈计划1239期", "理财", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2017-04-27"},
                {"A120A0057", "同盈计划1239期", "理财", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2016-04-27"},
                {"A120A0058", "同盈计划1239期", "理财", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2016-04-27"},
                {"A120A0059", "同盈计划1239期", "理财", "中信银行", "-", "-", "较高风险", "停止交易", "10000", "无", "请到营业网点咨询", "中信理财之高端理财产品", "2016-04-27"},
                {"A120A0060", "同盈计划1239期", "理财", "中信银行", "-", "-", "较低风险", "正在交易", "10000", "无", "请到营业网点咨询", "中信理财之稳健美元一年持有理财产品", "2016-04-27"},
                {"B120A0061", "同盈计划1239期", "股票", "中信银行", "-", "-", "较低风险", "正在交易", "10000", "无", "请到营业网点咨询", "中信理财之稳健美元一年持有理财产品", "2017-04-27"},
                {"B120A0062", "同盈计划1239期", "股票", "中信银行", "-", "-", "较低风险", "正在交易", "10000", "无", "请到营业网点咨询", "中信理财之稳健美元一年持有理财产品", "2017-04-27"},
                {"B120A0063", "同盈计划1239期", "股票", "中信银行", "-", "-", "较低风险", "正在交易", "10000", "无", "请到营业网点咨询", "中信理财之稳健美元一年持有理财产品", "2011-04-27"},
                {"B120A0064", "同盈计划1239期", "股票", "中信银行", "-", "-", "较低风险", "正在交易", "10000", "无", "请到营业网点咨询", "中信理财之稳健美元一年持有理财产品", "2012-04-27"},
                {"B120A0065", "同盈计划1239期", "股票", "中信银行", "-", "-", "较低风险", "正在交易", "10000", "无", "请到营业网点咨询", "中信理财之稳健美元一年持有理财产品", "2014-04-27"}

        };

        for (String[] strings : data) {
            try {
                String sql = "insert into " + financingTable + " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, strings[0]);
                preparedStatement.setString(2, strings[1]);
                preparedStatement.setString(3, strings[2]);
                preparedStatement.setString(4, strings[3]);
                preparedStatement.setString(5, strings[4]);
                preparedStatement.setString(6, strings[5]);
                preparedStatement.setString(7, strings[6]);
                preparedStatement.setString(8, strings[7]);
                preparedStatement.setString(9, strings[8]);
                preparedStatement.setString(10, strings[9]);
                preparedStatement.setString(11, strings[10]);
                preparedStatement.setString(12, strings[11]);
                preparedStatement.setDate(13, Date.valueOf(strings[12]));
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("[MYSQL INIT] insert financing new data error");
                e.printStackTrace();
            }


        }

//         try {
//             String sql = "insert into " + financingTable + " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//             preparedStatement.setString(1, "A120A0040");
//             preparedStatement.setString(2, "同盈计划1239期");
//             preparedStatement.setString(3, "理财");
//             preparedStatement.setString(4, "中信银行");
//             preparedStatement.setString(5, "-");
//             preparedStatement.setString(6, "-");
//             preparedStatement.setString(7, "较低风险");
//             preparedStatement.setString(8, "停止交易");
//             preparedStatement.setString(9, "10000");
//             preparedStatement.setString(10, "无");
//             preparedStatement.setString(11, "请到营业网点咨询");
//             preparedStatement.setString(12, "中信理财之双赢稳健美元一年持有理财产品");
// //            preparedStatement.setDate(13, new Date(System.currentTimeMillis()));
//             preparedStatement.setDate(13, Date.valueOf("2021-04-27"));
//             preparedStatement.execute();
//         } catch (SQLException e) {
//             System.out.println("[MYSQL INIT] insert financing new data error");
//             e.printStackTrace();
//         }
    }

    private boolean selectShowDataNewTabel() {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from " + showDataTable;
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL init] select showData error");
            e.printStackTrace();
        }
        return false;
    }

    private void insertShowDataNewTabel() {
        if (!selectShowDataNewTabel()) {
            return;
        }
        try {
            String sql = "insert into " + showDataTable + " values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "安富 200110期");
            preparedStatement.setString(2, "3.07");
            preparedStatement.setString(3, "2023-12-02 00:00:00");
            preparedStatement.setString(4, "2024-01-20 00:00:00");
            preparedStatement.setString(5, "1000");
            preparedStatement.setString(6, "10000");
            preparedStatement.setString(7, "500000");
            preparedStatement.setString(8, "10000");
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("[MYSQL INIT] insert new showData error");
            e.printStackTrace();
        }
    }

    public void checkTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(userTableCheck);
            statement.execute(showDataTableCheck);
            statement.execute(userShowDataTableCheck);
            statement.execute(financingTableCheck);
            insertShowDataNewTabel();
            insertFinancingDataNewTabel();
        } catch (SQLException e) {
            System.out.println("[MYSQL INIT] checkTable error");
            e.printStackTrace();
        }
    }

    public void updateShowDataTableOfHaveMoney(String name, String money) {
        try {
            String sql = "update " + showDataTable + " set havemoney =? where name =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String premoney = getShowDataTableOfHaveMoney(name);
//            System.out.println(Double.parseDouble(premoney) + Double.parseDouble(money));
            preparedStatement.setString(1, Double.parseDouble(premoney) + Double.parseDouble(money) + "");
            preparedStatement.setString(2, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("[MYSQL] update showData error");
            e.printStackTrace();
        }
    }

    public String getShowDataTableOfHaveMoney(String name) {
        try {
            String sql = "select havemoney from " + showDataTable + " where name =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("havemoney");
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] get showData error");
            e.printStackTrace();
        }
        return "0";
    }

    public void updateUserTableOfMoney(String usrname, String money) {
        try {
            String sql = "update " + userTable + " set money = ? where usrname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String premoney = getUserTableOfMoney(usrname);
            preparedStatement.setString(1, Double.parseDouble(premoney) - Double.parseDouble(money) + "");
            preparedStatement.setString(2, usrname);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("[MYSQL] update userTable error");
            e.printStackTrace();
        }
    }

    public String getUserTableOfMoney(String usrname) {
        try {
            String sql = "select money from " + userTable + " where usrname =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usrname);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("money");
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] get userTable error");
            e.printStackTrace();
        }
        return "0";
    }

    public void insertUserTable(String usrname, String usrpassword, String money) {
        try {
            String sql = "insert into " + userTable + " values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usrname);
            preparedStatement.setString(2, usrpassword);
            preparedStatement.setString(3, money);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("[MYSQL] insert userTable error");
            e.printStackTrace();
        }
    }

    public void insertOrUpdateUserShowDataTable(String usrname, String showdataname, String money) {
        JSONObject predata = selectUserShowDataTable(usrname, showdataname);
        if (predata == null) {
            System.out.println("[MYSQL] no have userShowData");
            return;
        }
        String premoney = predata.getString("havemoney");
        if (premoney == null) {
            try {
                String sql = "insert into " + userShowDataTable + " values(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, usrname);
                preparedStatement.setString(2, showdataname);
                preparedStatement.setString(3, money);
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("[MYSQL] insert userShowDataTable error");
                e.printStackTrace();
            }
        } else {
//            System.out.println("premoney is no null");
            try {
                String sql = "update " + userShowDataTable + " set havemoney =? where username =? and showdataname =?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, Double.parseDouble(money) + Double.parseDouble(premoney) + "");
                preparedStatement.setString(2, usrname);
                preparedStatement.setString(3, showdataname);
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("[MYSQL] update userShowDataTable error");
                e.printStackTrace();
            }
        }

        updateUserTableOfMoney(usrname, money);
        updateShowDataTableOfHaveMoney(showdataname, money);

    }

    public JSONObject selectUserShowDataTable(String usrname, String showdataname) {
        JSONObject jsonObject = new JSONObject();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from " + userShowDataTable + " where username = " + "'" + usrname + "'" + " and showdataname = " + "'" + showdataname + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                jsonObject.put("username", resultSet.getString("username"));
                jsonObject.put("showdataname", resultSet.getString("showdataname"));
                jsonObject.put("havemoney", resultSet.getString("havemoney"));
                return jsonObject;
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] select userShowDataTable error");
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void loginUserCheck() {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from " + userTable + "";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.print("usrname:" + resultSet.getString("usrname"));
                System.out.println("\tusrpassword:" + resultSet.getString("usrpassword"));
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] loginUserCheck error");
            e.printStackTrace();
        }
    }

    public boolean haveUserData(String usrname) {
        try {
            String sql = "select * from " + userTable + " where usrname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usrname);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] haveUserData error");
            e.printStackTrace();
            return false;
        }
    }

    public boolean haveUserData(String usrname, String usrpassword) {
        try {
            String sql = "select * from " + userTable + " where usrname = ? and usrpassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usrname);
            preparedStatement.setString(2, usrpassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] haveUserData error");
            e.printStackTrace();
            return false;
        }
    }

    public JSONObject getShowData(String name, String usrname) {
        JSONObject ret = new JSONObject();
        try {
            String sql = "select * from " + showDataTable + " where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                System.out.println(resultSet.getString("name"));
//                System.out.println(resultSet.getString("lv"));
//                System.out.println(resultSet.getString("starttime"));
//                System.out.println(resultSet.getString("endtime"));
//                System.out.println(resultSet.getString("barmoney"));
//                System.out.println(resultSet.getString("startmoney"));
//                System.out.println(resultSet.getString("summoney"));
//                System.out.println(resultSet.getString("havemoney"));
                ret.put("name", resultSet.getString("name"));
                ret.put("lv", resultSet.getString("lv"));
                ret.put("starttime", resultSet.getString("starttime"));
                ret.put("endtime", resultSet.getString("endtime"));
                ret.put("barmoney", resultSet.getString("barmoney"));
                ret.put("startmoney", resultSet.getString("startmoney"));
                ret.put("summoney", resultSet.getString("summoney"));
                ret.put("havemoney", resultSet.getString("havemoney"));
            } else {
                System.out.println("[MYSQL] no showData");
            }


            sql = "select * from " + userTable + " where usrname = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usrname);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ret.put("usermoney", resultSet.getString("money"));
            } else {
                System.out.println("[MYSQL] no userData");
            }
        } catch (SQLException e) {
            System.out.println("[MYSQL] getShowData error");
            e.printStackTrace();
        }
//        JSONObject money = selectUserShowDataTable(usrname, name);
//        System.out.println("money" + money.get("havemoney"));
        ret.put("userinputmoney", selectUserShowDataTable(usrname, name).get("havemoney"));
        return ret;
    }

    public JSONObject getLoginStatus(HttpServletRequest request) {
        JSONObject session = isSessionLogin(request);
        JSONObject cookie = isCookieLogin(request);

        if (cookie.get("isLogin").equals("yes")) {
            System.out.println("[Login] is cookie");
            return cookie;
        } else {
            System.out.println("[Login] is session");
            return session;
        }
    }

    public JSONObject isCookieLogin(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("[Cookies] cookies is null");
            jsonObject.put("isLogin", "no");
        }

        String username = "";
        String password = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    System.out.println("[Cookies] username:" + username);
                }
                if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                    System.out.println("[Cookies] password:" + password);
                }
            }
        }
        if (haveUserData(username, password)) {
            jsonObject.put("isLogin", "yes");
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } else {
            jsonObject.put("isLogin", "no");
        }

        return jsonObject;
    }

    public JSONObject isSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        JSONObject jsonObject = new JSONObject();

        if (session == null) {
            System.out.println("[Session] session is null");
            jsonObject.put("isLogin", "no");
        } else {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            System.out.println("[Session] username:" + username);
            System.out.println("[Session] password:" + password);

            if (haveUserData(username, password)) {
                jsonObject.put("isLogin", "yes");
                jsonObject.put("username", username);
                jsonObject.put("password", password);
            } else {
                jsonObject.put("isLogin", "no");
            }
        }

        return jsonObject;
    }

    public JSONArray getFinancingData() {
        String sql = "select * from " + financingTable;
        JSONArray jsonArray = new JSONArray();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("code", resultSet.getString("code"));
                tempJson.put("name", resultSet.getString("name"));
                tempJson.put("category", resultSet.getString("category"));
                tempJson.put("issuer", resultSet.getString("issuer"));
                tempJson.put("raiseMethod", resultSet.getString("raiseMethod"));
                tempJson.put("salesArea", resultSet.getString("salesArea"));
                tempJson.put("riskLevel", resultSet.getString("riskLevel"));
                tempJson.put("status", resultSet.getString("status"));
                tempJson.put("netValue", resultSet.getString("netValue"));
                tempJson.put("latestTip", resultSet.getString("latestTip"));
                tempJson.put("details", resultSet.getString("details"));
//                tempJson.put("notice", resultSet.getString("notice"));
//                tempJson.put("time", resultSet.getDate("time"));
                jsonArray.add(tempJson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }


    public JSONArray getNoticeData(String year) {
        JSONArray jsonArray = new JSONArray();
        try {
//            String sql = "select * from " + financingTable;
//            ResultSet resultSet = connection.createStatement().executeQuery(sql);
//            while (resultSet.next()) {
//                JSONObject tempJson = new JSONObject();
//                tempJson.put("notice", resultSet.getString("notice"));
//                tempJson.put("time", resultSet.getDate("time"));
//                jsonArray.add(tempJson);
//            }
            String sql = "select * from " + financingTable + " where time like '%" + year + "%'";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("notice", resultSet.getString("notice"));
                tempJson.put("time", resultSet.getDate("time"));
                tempJson.put("code", resultSet.getString("code"));
                jsonArray.add(tempJson);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }

    public JSONArray getYearGroupData() {
        JSONArray jsonArray = new JSONArray();
        try {
            // 获取所有年，然后分组
            String sql = "select year(time) as year from " + financingTable + " group by year(time) order by year(time) desc";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("year", resultSet.getString("year"));
                jsonArray.add(tempJson);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }
//    public static boolean executeSql(Connection connection, String sql) {
//        try {
//            Statement statement = connection.createStatement();
//            statement.execute(sql);
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("executeSql error");
//            return false;
//        }
//    }
}
