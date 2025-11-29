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
public class Samsung implements Phone {
    private int volume;
    private boolean isPowerOn;

    public Samsung() {
        this.volume = 40;
        this.isPowerOn = false;
    }

    @Override
    public void powerOn() {
        isPowerOn = true;
        System.out.println("Samsung menyala...");
    }

    @Override
    public void powerOff() {
        isPowerOn = false;
        System.out.println("Samsung mati.");
    }

    @Override
    public void volumeUp() {
        if (isPowerOn) {
            if (volume < MAX_VOLUME) volume++;
            System.out.println("Volume Samsung naik: " + volume);
        } else {
            System.out.println("Hp mati!");
        }
    }

    @Override
    public void volumeDown() {
        if (isPowerOn) {
            if (volume > MIN_VOLUME) volume--;
            System.out.println("Volume Samsung turun: " + volume);
        } else {
            System.out.println("Hp mati!");
        }
    }

    public int getVolume() {
        return volume;
    }
}
