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
public class Process {

    public String title;
    public int size;
    public int duration;
    public files filess[];
    public String State;
    public String resources;
    public String processor;
    public int allocatedmemory;
    public int starttime;
    public int executedfile;

    public Process(String title, int size, int duration, files filess[]) {
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.filess = filess;
        this.State = null;
        this.resources = null;
        this.processor = null;
        this.allocatedmemory = 0;
        this.starttime = -1;
        this.executedfile = 0;
    }
}
