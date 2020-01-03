package roundRobin;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import process_wireframe.Process;
import process_wireframe.SortByBurstTime;
import process_wireframe.SortByRemainingBurstTime;

public class RoundRobbinScheduling {
	static Scanner SC;
	static int TOTAL_NUMBER_OF_PROCESSES;
	
	public static void main(String[] args) {
		SC = new Scanner(System.in);
		Vector<Process> all_processes = new Vector<Process>();
		non_preemptive(all_processes);
	}

	private static void non_preemptive(Vector<Process> process_list) {
		Vector<Process> all_processes = new Vector<Process>();
		System.out.println("Enter time slice");
		int timeSlice = SC.nextInt();
		System.out.println("Enter total number of processes");
		TOTAL_NUMBER_OF_PROCESSES = SC.nextInt();
		int total_burst_time = 0;
		
		for (int i = 0; i < TOTAL_NUMBER_OF_PROCESSES; i++) {
			System.out.println("Enter Burst time for P"+i);
			float burstTime = SC.nextFloat();
			total_burst_time+=burstTime;
			System.out.println("Enter Arrival time for P"+i);
			float arrivalTime = SC.nextFloat();
			Process p = new Process("p"+i,burstTime,arrivalTime);
			process_list.add(p);
		}
	
		int totalExecutionTime = 0;
		Vector<Process> available_processes = new Vector<>();
		if(process_list.size() != available_processes.size())
		while(!process_list.isEmpty()){
			for( Process p : process_list){
				if(p.get_arrival_time() <= totalExecutionTime){
					if(!available_processes.contains(p))
						available_processes.add(p);
				}
			}
			
			float remainingTime = 0;
			if(!available_processes.isEmpty()) {
				if(available_processes.firstElement().isComplete())
				{
					process_list.remove(available_processes.firstElement());
					all_processes.add(available_processes.firstElement());
					available_processes.remove(0);
				}
			}
			if(!available_processes.isEmpty()) {
				remainingTime = available_processes.firstElement().get_remaining_time();
				float execution_time = timeSlice;
				if(remainingTime < timeSlice)
					execution_time = remainingTime;
				available_processes.firstElement()._execute(timeSlice);
				available_processes.firstElement()._wait(-execution_time);
				for(Process p : available_processes){
					p._wait(execution_time);
				}
				Process first = available_processes.firstElement();
				available_processes.remove(0);
				available_processes.add(first);
			}
			if(remainingTime> timeSlice)
				totalExecutionTime+=timeSlice;
			else
				totalExecutionTime+=remainingTime;
		}
	
		float totalWaitingTime = 0;
		float totalTurnaroundTime = 0;
		
		for(Process p : all_processes){
			float waiting_time = p.get_waiting_time();
			float turnaround = p.get_turnaround_time();
			totalWaitingTime += waiting_time;
			totalTurnaroundTime += turnaround;
			System.out.println("Waiting time for " + p.process_name + " is " + waiting_time);
			System.out.println("Turnaround time for " + p.process_name + " is " + turnaround);
			System.out.println();
		}
		System.out.println("Avg Waiting time = " + totalWaitingTime/TOTAL_NUMBER_OF_PROCESSES );
		System.out.println("Avg Turnaround time = " + totalTurnaroundTime/TOTAL_NUMBER_OF_PROCESSES );
	}
}
