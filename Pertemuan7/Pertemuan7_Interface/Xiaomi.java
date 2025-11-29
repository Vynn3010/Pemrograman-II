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
public class Xiaomi implements Phone {
    private int volume;
    private boolean isPowerOn;

    public Xiaomi() {
        this.volume = 50;
        this.isPowerOn = false;
    }

    @Override
    public void powerOn() {
        isPowerOn = true;
        System.out.println("Xiaomi menyala...");
    }

    @Override
    public void powerOff() {
        isPowerOn = false;
        System.out.println("Xiaomi mati.");
    }

    @Override
    public void volumeUp() {
        if (isPowerOn) {
            if (volume < MAX_VOLUME) volume++;
            System.out.println("Volume Xiaomi naik: " + volume);
        } else {
            System.out.println("Hp mati!");
        }
    }

    @Override
    public void volumeDown() {
        if (isPowerOn) {
            if (volume > MIN_VOLUME) volume--;
            System.out.println("Volume Xiaomi turun: " + volume);
        } else {
            System.out.println("Hp mati!");
        }
    }

    public int getVolume() {
        return volume;
    }
}
