package process_wireframe;

import java.util.Comparator;

public class Process {
	private final float burst_time;
	private final float arrival_time;
	private float remaining_execution_time;
	private boolean isComplete;
	
	public float time_in_system;
	public String process_name;
	
	public Process( String name, float burst_time ){
		this.process_name = name;
		this.burst_time = burst_time;
		this.arrival_time = 0;
		this.remaining_execution_time = burst_time;
		this.time_in_system = 0;
		isComplete = false;
	}
	
	public Process( String name, float burst_time, float arrival_time ){
		this.process_name = name;
		this.burst_time = burst_time;
		this.arrival_time = arrival_time;
		this.remaining_execution_time = burst_time;
		this.time_in_system = 0;
		isComplete = false;
	}
	
	public void _wait( float waiting_time_period ){
		this.time_in_system+=waiting_time_period;
	}
	
	public void _execute( float clock_cycles ){
		if( !isComplete ){
			if( remaining_execution_time > clock_cycles ){
				remaining_execution_time-=clock_cycles;
				this.time_in_system+=clock_cycles;
				if( remaining_execution_time == 0 )
					isComplete = true;
			}
		}
	}
	
	public void complete_execution(){
		this.time_in_system+=remaining_execution_time;
		remaining_execution_time = 0;
		isComplete = true;
	}
	
	public float get_waiting_time(){
		return time_in_system - arrival_time - burst_time;
	}
	
	public float get_turnaround_time(){
		return time_in_system - arrival_time;
	}
	
	public float get_arrival_time(){
		return arrival_time;
	}
	
	public boolean isComplete(){
		return isComplete;
	}
	
	public float get_burst_time(){
		return burst_time;
	}
	
}