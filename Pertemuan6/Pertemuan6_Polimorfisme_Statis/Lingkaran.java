/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pertemuan6_Polimorfisme_Statis;

/**
 *
 * @author calvi
 */
public class Lingkaran {
    //menghitung luas menggunakan jari2
    float luas(float jari2) {
        return (float) (Math.PI * jari2 * jari2);
    }

    double luas(double diameter) {
        return (double) (Math.PI * diameter * 1/4);
    }

    public static void main(String[] args) {

        Lingkaran L = new Lingkaran();

        float jari2 = 10;
        double d = 100;

        System.out.println("Luas Lingkaran Dengan Jari-Jari = " + L.luas(jari2));
        System.out.println("Luas Lingkaran Dengan Diameter = " + L.luas(d));
    }
}