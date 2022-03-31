/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication105;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mohamad ayoubi A1911244
 */
public class JavaApplication105 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //prompt the user to enter his os specs
        Scanner input = new Scanner(System.in);
        int processorsnb;
        System.out.println("How many processors does your OS have access to?");
        processorsnb = input.nextInt();

        List<String> processores1 = new ArrayList<>();
        for (int i = 0; i < processorsnb; i++) {
            processores1.add("proc " + (i + 1));
        }

        int mainmemory;
        System.out.println("What is your accessible main memory size (MB)?");
        mainmemory = input.nextInt();

        int cycletime;
        System.out.println("Enter the clock cycle time (s):");
        cycletime = input.nextInt();

        int timeinterval;
        System.out.println("Enter the time interval between process entries (s):");
        timeinterval = input.nextInt();

        system system1 = new system(processorsnb, mainmemory, cycletime, timeinterval, processores1);
        //prompt the user to enter the number of the processes needed to execute
        int numberofprocess;
        System.out.println("Enter number of processes to simulate: ");
        numberofprocess = input.nextInt();
        Process Processes[] = new Process[numberofprocess];

        //prompt the user to enter each process info 
        for (int i = 0; i < Processes.length; i++) {
            String title;
            System.out.println("Enter the title of process #" + (i + 1) + " :");
            title = input.next();

            int size;
            System.out.println("Enter the size of the memory required (MB):");
            size = input.nextInt();

            int duration;
            System.out.println("Enter Time needed to execute (s):");
            duration = input.nextInt();

            int numberoffiles;
            System.out.println("How many files need to be locked?");
            numberoffiles = input.nextInt();

            files filess[] = new files[numberoffiles];
            //prompt he user to enter used files info
            for (int j = 0; j < filess.length; j++) {
                String filename;
                System.out.println("Enter filename # " + (j + 1));
                filename = input.next();
                int lockstart, durationfile;
                System.out.println("Lock start and duration (in sec) after start:");
                lockstart = input.nextInt();
                durationfile = input.nextInt();
                filess[j] = new files(filename, lockstart, durationfile);
            }
            Processes[i] = new Process(title, size, duration, filess);
        }

        int initialtime = 0;

        System.out.print("---------- Displaying Progress ---------- \n");
        //execute the processes;
        execute(Processes, system1, initialtime);
    }

    public static void execute(Process[] a, system b, int time) {
        do {
            //dis[lay time
            System.out.println("time:" + time);
            //execute each process with each process depend on his state
            for (int i = 0; i < a.length; i++) {
                //define new process if not defined
                if (a[i].State == null) {
                    if (i * b.timeinterval <= time) {
                        //check for avaible memory
                        if (a[i].size <= b.avaiblememory) {
                            //change process state to new and allocate memory
                            a[i].State = "new";
                            a[i].resources = "all  Available";
                            a[i].allocatedmemory = a[i].size;
                            b.avaiblememory -= a[i].size;
                            b.allocatedmemory += a[i].size;
                            //check for the main memory and compare it to process size
                            //change process state to new suspended
                        } else if (a[i].size > b.mainmemory) {
                            a[i].State = "new suspended";
                            a[i].resources = "Insufficient Memory(process cant be executed)";

                            //change process state to new-suspended and allocated all the availble memory 
                        } else {

                            a[i].State = "new suspended";
                            a[i].resources = "Insufficient Memory";
                            a[i].allocatedmemory = b.avaiblememory;
                            b.avaiblememory = 0;
                            b.allocatedmemory = b.mainmemory;

                        }
                    }

                } else if (a[i].State == "new suspended") {
                    //check for the main memory and compare it to process size
                    //change process state toe exit
                    if (a[i].size > b.mainmemory) {
                        a[i].State = "exit";
                        a[i].resources = null;
                    } else {
                        //check for avaible memory and if yes change process state to new and allocate memory
                        if (b.avaiblememory + a[i].allocatedmemory >= a[i].size) {

                            b.avaiblememory -= (a[i].size - a[i].allocatedmemory);
                            b.allocatedmemory += (a[i].size - a[i].allocatedmemory);
                            a[i].allocatedmemory = a[i].size;
                            a[i].State = "new";
                            a[i].resources = "all available";
                        }

                        //if new check for proccessors availability and allocated a process to execute
                        if (a[i].State == "new") {
                            if (b.avaibleprocesors.size() > 0) {
                                a[i].State = "ready";
                                a[i].processor = b.avaibleprocesors.get(0);
                                b.runningprocesors.add(b.avaibleprocesors.get(0));
                                b.avaibleprocesors.remove(0);
                                a[i].resources = "all  available";
                            } else {
                                a[i].State = "ready-suspended";
                                a[i].resources = "no  available processor";
                            }
                        }
                    }
                    //for proccessors availability and allocated a process to execute
                } else if (a[i].State == "new" || a[i].State == "ready-suspended") {
                    if (b.avaibleprocesors.size() > 0) {
                        a[i].State = "ready";
                        a[i].processor = b.avaibleprocesors.get(0);
                        b.runningprocesors.add(b.avaibleprocesors.get(0));
                        b.avaibleprocesors.remove(0);
                        a[i].resources = "all  Available";
                    } else {
                        a[i].State = "ready-suspended";
                        a[i].resources = "no  Available processor";
                    }
                    //change state to running and alloacte the process time
                } else if (a[i].State == "ready") {
                    a[i].State = "running";
                    a[i].starttime = (time - 2 * b.cycletime);

                } else if (a[i].State == "blocked") {
                    //check if files are avaible
                    int index = 0;
                    for (int k = 0; k < a[i].filess.length; k++) {
                        if (b.usedfiles.contains(a[i].filess[k].name)) {
                            index++;
                        }
                    }
                    //change state to ready and resources to available
                    if (index == 0) {
                        a[i].State = "ready";
                        a[i].resources = "all available";
                    }

                } else if (a[i].State == "running") {
                    //check if the process neede any files
                    if (a[i].filess.length > 0) {
                        for (int k = 0; k < a[i].filess.length; k++) {
                            if (!a[i].filess[k].locked) {
                                if (time - a[i].starttime >= a[i].filess[k].lockstart) {
                                    //if the needed file in use change state to blocked
                                    if (b.usedfiles.contains(a[i].filess[k].name)) {
                                        a[i].State = "blocked";
                                        a[i].resources = "file #" + (k + 1) + " locked";
                                        //add the needed files to the system cuttent used files
                                    } else {
                                        b.usedfiles.add(a[i].filess[k].name);
                                        a[i].filess[k].locked = true;
                                    }
                                }
                                //remove the files the system cuttent used files if the needed time for them in done
                            } else {
                                if (time - a[i].starttime >= a[i].filess[k].lockstart + a[i].filess[k].duration) {
                                    if (b.usedfiles.contains(a[i].filess[k].name)) {
                                        b.usedfiles.remove(a[i].filess[k].name);
                                        a[i].executedfile++;
                                    }
                                }
                            }
                        }
                        //if the process time to execute and time to lock file is over move process to exit state
                        if (a[i].executedfile == a[i].filess.length && time - a[i].starttime >= a[i].duration) {
                            a[i].State = "exit";
                        }
                        //if the process time to execute move process to exit state
                    } else {
                        if (time - a[i].starttime >= a[i].duration) {
                            a[i].State = "exit";
                        }
                    }

                    //if files in exit state free the allocated memory and processor
                    if (a[i].State == "exit") {
                        b.runningprocesors.remove(a[i].processor);
                        b.avaibleprocesors.add(a[i].processor);
                        a[i].processor = null;
                        b.avaiblememory += a[i].allocatedmemory;
                        b.allocatedmemory -= a[i].allocatedmemory;
                        a[i].allocatedmemory = 0;
                        a[i].resources = null;
                    }
                }

            }
            //dispaly os details
            System.out.println("processors:");
            System.out.println("    Available: " + b.avaibleprocesors.toString());
            System.out.println("    runnig: " + b.runningprocesors.toString());
            System.out.println("    Available memory = " + b.avaiblememory + " allocated memory = " + b.allocatedmemory + "\n");
            //display each processor deatail
            for (int t = 0; t < a.length; t++) {
                if (a[t].State != null && a[t].State != "exit2") {
                    System.out.println("process = " + a[t].title
                            + "\n    state: " + a[t].State);
                    if (a[t].processor != null) {
                        System.out.println("    processor :" + a[t].processor);
                    }
                    if (a[t].allocatedmemory > 0) {
                        System.out.println("    allocated memory = " + a[t].allocatedmemory);
                    }
                    if (a[t].resources != null) {
                        System.out.println("    resources= " + a[t].resources + "\n");
                    }
                    if (a[t].State == "exit") {
                        a[t].State = "exit2";
                    }
                }
            }
            //incremeant time
            time = (time + b.cycletime);
            System.out.println("\n-------------------------");

        } while (ended(a));

    }
    //check if all the proccesors are executed

    public static boolean ended(Process[] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i].State == "exit2") {
                count++;
            }
        }
        if (count == a.length) {
            return false;
        } else {
            return true;
        }
    }
}
