import behaviors.IDiskBehavior;



public class Disk {
	int[] stack;
	int position;
	IDiskBehavior diskBehavior;
	void performSorting () {
		diskBehavior.sort();
	}
	
}
