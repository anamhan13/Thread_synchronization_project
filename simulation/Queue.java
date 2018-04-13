package simulation;

import java.util.ArrayList;
import java.util.List;

public class Queue extends Thread{
	
	private volatile List<Task> tasks = new ArrayList<Task>();
	private volatile int waitingTime; 
	private final int queueID;
	private volatile int emptyQueueTime;
	private int noTasks;
	private volatile int avgService;

	public Queue(int queueID) {
		this.queueID = queueID;
		this.waitingTime=0;
		this.emptyQueueTime=0;
		this.avgService=0;
		this.noTasks=0;
	}
	
	public int getQueueSize() {
		return tasks.size();
	}
	
	public List<Task> getTasks() {
		return this.tasks;
	}
	
	public int getQueueID() {
		return queueID;
	}
	
	public int getEmptyQueueTime() {
		return emptyQueueTime;
	}
	
	public int getWaitingTime() {
		return this.waitingTime;
	}
	
	public void setWaitingTime(int newTime) {
		this.waitingTime = newTime;
	}
	
	public synchronized int findTotalWaitingTime() {
		int waitingTime = 0;
		for (Task task : tasks) {
			waitingTime += task.getServiceTime();
		}
		this.setWaitingTime(waitingTime);
		return waitingTime;
	}

	public synchronized void addTask(Task task) {
		this.noTasks++;
		tasks.add(task);
		int more=0;
		if (tasks.size()==1) {
			more = task.getArrivalTime();
		}
		int newWaitingTime = this.findTotalWaitingTime()+more;
		this.setWaitingTime(newWaitingTime);
	}
	
	public synchronized void removeTask() {
		int newWaitingTime = this.findTotalWaitingTime() - tasks.get(0).getServiceTime();
		this.setWaitingTime(newWaitingTime);
		System.out.printf("Customer no. %d left queue no. %d at the moment %d\n",tasks.get(0).getID(),this.getQueueID(),this.findTotalWaitingTime()+tasks.get(0).getArrivalTime());
		tasks.remove(tasks.get(0));
	}
	
	public double getAvgService() {
		return (double)this.avgService/(double)this.noTasks;
	}
	
	public double getAvgEmptyTime() {
		return (double)this.emptyQueueTime/(double)this.noTasks;
	}

	public void run() {
		
		while (true) {			
			try {
				if (tasks.size()!=0) {
					Thread.sleep(tasks.get(0).getServiceTime()*1000);
					this.removeTask();
				} else {
					this.emptyQueueTime++;
				}
			} catch(Exception e) {
				System.out.print("");
			} 	
				
		}
		
	}
	
}
