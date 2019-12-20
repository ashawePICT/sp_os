package shortestJobFirst;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

import process_wireframe.SortByArrivalTime;
import process_wireframe.Process;

public class SJF {
	static Scanner SC;
	static int TOTAL_NUMBER_OF_PROCESSES;
	
	public static void main(String[] args) {
		SC = new Scanner(System.in);
		Vector<Process> all_processes = new Vector<Process>();
		
		System.out.println("1. Preemptive\n 2. Non-Preemptive");
		int choice = SC.nextInt();
		if(choice == 1)
			preemptive(all_processes);
		else
			non_preemptive(all_processes);
	}

	private static void non_preemptive(Vector<Process> process_list) {
		Vector<Process> all_processes = new Vector<Process>();

		System.out.println("Enter total number of processes");
		TOTAL_NUMBER_OF_PROCESSES = SC.nextInt();
		
		for (int i = 0; i < TOTAL_NUMBER_OF_PROCESSES; i++) {
			System.out.println("Enter Burst time for P"+i);
			float burstTime = SC.nextFloat();
			Process p = new Process("p"+i,burstTime);
			process_list.add(p);
		}
		
		Collections.sort(process_list, new SortByArrivalTime());
		
		while(!process_list.isEmpty()){
			Process p = process_list.firstElement();
			process_list.remove(0);
			for(Process p1 : process_list){
				p1._wait(p.get_burst_time());
			}
			p.complete_execution();
			all_processes.add(p);
		}
		
		float totalWaitingTime = 0;
		float totalTurnaroundTime = 0;
		
		while(!all_processes.isEmpty()){
			Process p = all_processes.firstElement();
			float waiting_time = p.get_waiting_time();
			float turnaround = p.get_turnaround_time();
			totalWaitingTime += waiting_time;
			totalTurnaroundTime += turnaround;
			System.out.println("Waiting time for " + p.process_name + " is " + waiting_time);
			System.out.println("Turnaround time for " + p.process_name + " is " + turnaround);
			System.out.println();
			all_processes.remove(0);
		}
		
		System.out.println("Avg Waiting time = " + totalWaitingTime/TOTAL_NUMBER_OF_PROCESSES );
		System.out.println("Avg Turnaround time = " + totalTurnaroundTime/TOTAL_NUMBER_OF_PROCESSES );
		
	}

	private static void preemptive(Vector<Process> process_list) {
		// TODO Auto-generated method stub
		
	}
}