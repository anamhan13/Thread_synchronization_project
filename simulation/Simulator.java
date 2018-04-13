package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import exceptions.IllegalTimeException;
import userinterface.GUI;

public class Simulator extends Thread {

	public static int simulationPer;
	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private volatile int noOfQueues;
	private volatile List<Task> tasks = new ArrayList<Task>();
	private List<Queue> queuesT = new ArrayList<Queue>();
	private Random rand = new Random();
	
	private int peak;
	private int peakMax;
	private int peakTime;

	public Simulator(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int noOfQueues, int simulationPer) // vezi ce probleme mai poti intampina la input-uri
	{

		try {
			if (maxServiceTime < minServiceTime) {
				throw new IllegalTimeException();
			}
			if (maxArrivalTime < minArrivalTime) {
				throw new IllegalTimeException();
			}
			if (simulationPer < maxArrivalTime) {
				throw new IllegalTimeException();
			}
			if (simulationPer < minArrivalTime) {
				throw new IllegalTimeException();
			}
		} catch (IllegalTimeException e) {
			
			if (maxServiceTime < minServiceTime) {
				System.out.println("The maxServiceTime must be greater than the minServiceTime");
			}
			if (maxArrivalTime < minArrivalTime) {
				System.out.println("The maxArrivalTime must be greater than the minArrivalTime");
			}
			if (simulationPer < maxArrivalTime) {
				System.out.println("The simulation time must be greater than the maxArrivalTime");
			}
			if (simulationPer < minArrivalTime) {
				System.out.println("The simulation time must be greater than the minArrivalTime");
			}
		} 

		this.minArrivalTime = minArrivalTime;
		this.maxArrivalTime = maxArrivalTime;
		this.minServiceTime = minServiceTime;
		this.maxServiceTime = maxServiceTime;
		Simulator.simulationPer = simulationPer*1000;
		this.noOfQueues = noOfQueues;

		generateRandomTasks();
		for (int i = 0; i < this.noOfQueues; i++) { // run all the threads
			queuesT.add(new Queue(i));
		}
	}

	public void generateRandomTasks() {
		int time = 0;
		int sumArrival = 0;
		int i = 0;
		int arrivalTime = 0;
		int serviceTime = 0;

		while (time < simulationPer / 1000) {
			// in order to make sure that we do not exceed the boundaries of the
			// given intervals
			arrivalTime = rand.nextInt(this.maxArrivalTime - this.minArrivalTime + 1) + this.minArrivalTime;
			sumArrival += arrivalTime;
			serviceTime = rand.nextInt(this.maxServiceTime - this.minServiceTime + 1) + this.minServiceTime;
			this.tasks.add(new Task(i, sumArrival, serviceTime));
			i++;
			time += serviceTime;
		}
	}

	public int findShortestTime() {
		int index = 0;
		int min = Integer.MAX_VALUE;
		for (Queue queue : queuesT) {
			if (queue.getWaitingTime() < min) {
				min = queue.getWaitingTime();
				index = queue.getQueueID();
			}
		}
		return index;
	}

	public void run() { 
		for (int i = 0; i < this.noOfQueues; i++) {
			queuesT.get(i).start();
		}

		int index = 0;
		int i = 0;
		long startedTime = System.currentTimeMillis();
		int inBetween = 0;
		while (System.currentTimeMillis() - startedTime < simulationPer) {
			if (tasks.size() != 0) {
				queuesT.get(index).addTask(tasks.get(0));
				System.out.printf(
						"Customer no." + tasks.get(0).getID() + " was assigned to queue no. " + index
								+ " at the moment " + tasks.get(0).getArrivalTime() + ", service time: %d \n",
						tasks.get(0).getServiceTime());
			}
			if (tasks.size() >= 2) {
				try{
				inBetween = tasks.get(1).getArrivalTime() - tasks.get(0).getArrivalTime();
				} catch(Exception e) {
					System.out.print("");
				}
			} else if (tasks.size() == 1) {
				inBetween = tasks.get(0).getArrivalTime();
			}
			
			for(Queue q : queuesT) {
				peak += q.getQueueSize();
			}
			
			if(peak > peakMax) {
				peakMax = peak;
				peakTime = (int) ((System.currentTimeMillis() - startedTime)/1000);
			}

			try {
				
				GUI.textArea2.setText(null);
				for (i=0; i<this.noOfQueues;i++) {
					GUI.textArea2.append("Queue no. " + queuesT.get(i).getQueueID()+": "); 
					for (int j=0; j < queuesT.get(i).getQueueSize(); j++) {
						GUI.textArea2.append(queuesT.get(i).getTasks().get(j).getID()+", ");
					}
					GUI.textArea2.append("\n");
				}
				sleep(inBetween * 1000);
				if (tasks.size() != 0) {
					tasks.remove(tasks.get(0));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			index = this.findShortestTime();
			
		}

		System.out.println("End of simulation");
		GUI.textArea3.append("Peak time:"+ peakTime);
		GUI.textArea3.append("\n");
		for (Queue queue: this.queuesT) {
			GUI.textArea3.append("Average service time for queue"+queue.getQueueID()+": "+queue.getAvgService());
			GUI.textArea3.append("\n");
			GUI.textArea3.append("Average empty queue time for queue "+queue.getQueueID()+": "+Math.abs(queue.getAvgEmptyTime()));
			GUI.textArea3.append("\n");
		}
		
	}

}
