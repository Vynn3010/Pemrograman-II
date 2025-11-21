/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan6_Method_Getter_Setter;

/**
 *
 * @author calvi
 */
public class Main {
    public static void main(String[] args) {

        WiFiUser wifi = new WiFiUser();

        wifi.SetUsername("user");
        wifi.SetPassword("user_123");

        System.out.println("Username WiFi = " + wifi.GetUsername());
        System.out.println("Password WiFi = " + wifi.GetPassword());
    }
}

