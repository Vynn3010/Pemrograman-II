/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan7_Interface;

/**
 *
 * @author calvi
 */
public class Main {
    public static void main(String[] args) {

        Phone samsung = new Samsung();
        Phone oppo = new Oppo();
        Phone xiaomi = new Xiaomi();
        Phone iphone = new iPhone();
        
        PhoneUser user1 = new PhoneUser(samsung);        
        PhoneUser user2 = new PhoneUser(oppo);
        PhoneUser user3 = new PhoneUser(xiaomi);
        PhoneUser user4 = new PhoneUser(iphone);
        
        //User Samsung
        System.out.println("== User 1 (Samsung) ==");
        user1.turnOnThePhone();
        user1.makePhoneLouder();
        user1.makePhoneSilent();
        user1.turnOffThePhone();
        
        //User Oppo
        System.out.println("== User 2 (Oppo) ==");
        user2.turnOnThePhone();
        user2.makePhoneLouder();
        user2.makePhoneSilent();
        user2.turnOffThePhone();
        
        //User Xiaomi
        System.out.println("== User 3 (Xiaomi)==");
        user3.turnOnThePhone();
        user3.makePhoneLouder();
        user3.makePhoneSilent();
        user3.turnOffThePhone();
        
        //User iPhone
        System.out.println("== User 4 (iPhone)==");
        user4.turnOnThePhone();
        user4.makePhoneLouder();
        user4.makePhoneSilent();
        user4.turnOffThePhone();
    }
}

