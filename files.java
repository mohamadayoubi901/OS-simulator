/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication105;

/**
 *
 * @author mohammad ayoubi
 */
public class files {

    public String name;
    public int lockstart;
    public int duration;
    public boolean locked;

    public files(String name, int lockstart, int duration) {
        this.name = name;
        this.lockstart = lockstart;
        this.duration = duration;
        this.locked = false;
    }
}
