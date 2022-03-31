/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication105;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mohammad ayoubi
 */
public class system {

    public int processorsnb;
    List<String> avaibleprocesors = new ArrayList<>();
    List<String> runningprocesors = new ArrayList<>();
    List<String> usedfiles = new ArrayList<>();
    public int mainmemory;
    public int cycletime;
    public int timeinterval;
    public int avaiblememory;
    public int allocatedmemory;

    public system(int processorsnb, int mainmemory, int cycletime, int timeinterval, List processors) {
        this.processorsnb = processorsnb;
        this.mainmemory = mainmemory;
        this.cycletime = cycletime;
        this.timeinterval = timeinterval;
        this.avaiblememory = mainmemory;
        this.allocatedmemory = 0;
        this.avaibleprocesors = processors;
        /*this.runningprocesors=null;
    this.usedfiles=null;*/
    }

}
