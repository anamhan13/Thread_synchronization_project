package simulation;
import java.util.Comparator;
import java.util.Random;

public class Task implements Comparable<Task>, Comparator<Task>{

	private int arrivalTime; //the latest possible time a client can receive a service
	private int serviceTime; //time needed by client to receive a complete service
	private int taskId;
	
	public Task(int id)
	{
		Random rand = new Random();
		this.arrivalTime = rand.nextInt();
		this.serviceTime =  rand.nextInt();
		this.taskId=id;
	}
	
	public Task(int id, int arrivalTime, int serviceTime)
	{
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
		this.taskId=id;
	}
	
	public int getID()
	{
		return this.taskId;
	}
	
	public void setID(int newID){
		this.taskId = newID;
	}
	
	public int getArrivalTime()
	{
		return this.arrivalTime;
	}
	
	public int getServiceTime()
	{
		return this.serviceTime;
	}
	
	public void setServiceTime(int newTime) {
		this.serviceTime = newTime;
	}

	public int compareTo(Task t) {
		if (this.arrivalTime<t.arrivalTime)
			return 1;
		else if (this.arrivalTime>t.arrivalTime)
			return -1;
		return 0;
	}

	public int compare(Task o1, Task o2) {
		if (o1.arrivalTime<o2.arrivalTime)
			return 1;
		else if (o1.arrivalTime>o2.arrivalTime)
			return -1;
		return 0;
	}
}
