package com.huskehhh.dayzepoch;

import com.huskehhh.dayzepoch.database.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DayZ {

    public static void main(String[] args) {

        System.out.println("Starting up Program.");
        MySQL m = new MySQL("hostname", "3306", "database", "user", "pass");
        for (int i = 4900; i < 7600; i++) {
            try {
                ResultSet res = m.querySQL("SELECT buy FROM `Traders_DATA` WHERE id=" + i + ";");
                System.out.println("SELECT buy FROM `Traders_DATA` WHERE id=" + i + ";");
                while (res.next()) {


                    // [2,"ItemGoldBar10oz",1]
                    String buy = res.getString("buy");
                    System.out.println("Retrieved 'buy' of " + buy);

                    buy = buy.replaceAll("\\[", "").replaceAll("\\]", "");

                    String[] split = buy.split(",");

                    System.out.println(split[0]);
                    System.out.println(split[1]);
                    System.out.println(split[2]);

                    /**
                     * split[0] = "[2"
                     * split[1] = ""ItemGoldBar10oz""
                     * split[2] = "1]"
                     */

                    String item = split[1];
                    String cqty = split[0];
                    String type = split[2];

                    int newQty = Integer.parseInt(cqty);
                    newQty = newQty * 5;

                    String build = "[" + (Integer.toString(newQty)) + "," + item + "," + type + "]";

                    System.out.println(build);

                    m.updateSQL("UPDATE `dayz`.`Traders_DATA` SET `buy` = '" + build + "' WHERE `Traders_DATA`.`id` = " + i + ";");
                    System.out.println("UPDATE `dayz`.`Traders_DATA` SET `buy` = '" + build + "' WHERE `Traders_DATA`.`id` = " + i + ";");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


}
