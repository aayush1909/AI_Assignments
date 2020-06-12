import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/*Initial
 * 1 2 3
 * 5 6 0
 * 7 8 4
 * 
 * Final
 * 0 2 3 
 * 1 5 6
 * 7 8 4
 * */

class MatUtil
{
	public static void copy(int a[][], int b[][])
	{
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[i].length; j++)
			{
				b[i][j] = a[i][j];
			}
		}
	}
	
	public static String convertToString(int a[][])
	{
		String result = "";
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a[i].length; j++)
			{
				result += a[i][j] + " ";
			}
			result += "\n";
		}
		
		return result;
	}
	
}

class Node
{
	public int mat[][];
	public int level;
	Node(int arr[][])
	{
		mat = new int[3][3];
		MatUtil.copy(arr, mat);
		level = 0;
	}
	Node()
	{
	}
	public String toString()
	{
		String result = "Node Level : " + level + "\n";
		result += MatUtil.convertToString(mat);
		return result;
	}
}

class ScannedNode
{
	public Node node;
	public int scanOrder;
}

public class Search 
{
	public static int compareNodes(Node a, Node b)
	{
		int result = 0;
		
		for(int i = 0; i < a.mat.length; i++)
		{
			for(int j = 0; j < a.mat[i].length; j++)
			{
				if(a.mat[i][j] != b.mat[i][j])
				{
					result++;
				}
				
			}
			
		}	
		return (result - 1);
	}
	public static void copyNode(Node a, Node b)
	{
		for(int i = 0; i < a.mat.length; i++)
		{
			for(int j = 0; j < a.mat[i].length; j++)
			{
					b.mat[i][j] = a.mat[i][j];
			}	
		}
	}
	
	public static int[] searchElement(Node a, int element)
	{
		int result[] = new int[2];
		result[0] = -1;
		result[1] = -1;
		for(int i = 0; i < a.mat.length; i++)
		{
			for(int j = 0; j < a.mat[i].length; j++)
			{
				if(a.mat[i][j] == element)
				{
					result[0] = i;
					result[1] = j;
					
					return result;
				}			
			}			
		}
		return result;
	}
	public static Vector<Node> getChildren(Node a)
	{
		int pos[] = searchElement(a, 0);
		Vector<Node> res = new Vector<Node>();
		for(int direction = 0; direction < 4; direction++)
		{
			Node b = new Node();
			b.level = a.level + 1;
			b.mat = new int[3][3];
			copyNode(a,b);
			switch(direction)
			{
			case 2:
			{//UP
				if(pos[0] != 0)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0] - 1][pos[1]];
					b.mat[pos[0] - 1][pos[1]] = 0;
					res.add(b);
				}

			}break;
			case 3:
			{//down
				if(pos[0] != 2)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0] + 1][pos[1]];
					b.mat[pos[0] + 1][pos[1]] = 0;
					res.add(b);
				}

			}break;
			case 0:
			{//left
				if(pos[1] != 0)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0]][pos[1] - 1];
					b.mat[pos[0]][pos[1] - 1] = 0;
					res.add(b);
				}

			}break;
			case 1:
			{//right
				if(pos[1] != 2)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0]][pos[1] + 1];
					b.mat[pos[0]][pos[1] + 1] = 0;
					res.add(b);
				}

			}break;
			default:
			{
				System.out.println("ERROR!!!!!!!!!!");
			}break;

			}
		}
		return res;
}
	public static void main(String args[]) throws FileNotFoundException
	{
		PrintWriter bfsOutput = new PrintWriter("bfs_output_0.txt");
		PrintWriter dfsOutput = new PrintWriter("dfs_output_0.txt");
		int fileLimit = 5000;

		String bfsOutputString = "";
		String dfsOutputString = "";
		int stringLimit = 10000;
		int t=0;
		int arr[][] = { {1, 2, 3},
				  		{5, 6, 0},
				  		{7, 8, 4}};
		

		int arr2[][] = {{0, 2, 3},
				  		{1, 5, 6},
				  		{7, 8, 4}};
		
		Node initialState = new Node(arr);
		Node finalState = new Node(arr2);
		
		// NOTE(KARAN) : BFS SEARCH
		Vector<Vector<Node>> bfsNodePathTaken = new Vector<Vector<Node>>(); 
		Queue<Node> bfsQueue = new LinkedList<>();
		bfsQueue.add(initialState);
		int currentLine = -1;
		int lastLevel = -1;
		while(!bfsQueue.isEmpty())
		{
			Node currentNode = bfsQueue.remove();
			if(lastLevel != currentNode.level)
			{
				currentLine++;
				lastLevel = currentNode.level;
				bfsNodePathTaken.add(new Vector<Node>());
			}
			bfsNodePathTaken.get(currentLine).add(currentNode);

			if(compareNodes(currentNode, finalState) == -1)
			{
				break;
			}
			
			Vector<Node> children = getChildren(currentNode);
			for(int child = 0; child < children.size(); child++)
			{
				boolean alreadyScanned = false;
				for(int i = 0; i < bfsNodePathTaken.size(); i++)
				{
					for(int j = 0; j < bfsNodePathTaken.get(i).size(); j++)
					{
						if(compareNodes(children.get(child), bfsNodePathTaken.get(i).get(j)) == -1)
						{
							alreadyScanned = true;
							break;
						}
					}
				}

				if(alreadyScanned)
				{
					continue;
				}
				else
				{
					bfsQueue.add(children.get(child));
				}
			}
		}
		
		/*DISPLAY RESULTS*/
		int bfsFileLines = 0;
		int bfsFileNumber = 0;
		bfsOutputString +="OPERATORS"+"\n";
		bfsOutputString +="LEFT,RIGHT,UP,DOWN"+"\n";
		System.out.println("OPERATORS");
		System.out.println("LEFT,RIGHT,UP,DOWN");
		bfsOutputString +="INITIAL STATE"+"\n";
		System.out.println("INITIAL STATE:");
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(arr[i][j]+" ");
				bfsOutputString += Integer.toString(arr[i][j])+" ";
			
			}
			System.out.println();
			bfsOutputString +="\n";
		}
		bfsOutputString +="GOAL STATE"+"\n";
		System.out.println("GOAL STATE:");
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(arr2[i][j]+" ");
				bfsOutputString += Integer.toString(arr2[i][j])+" ";
			
			}
			System.out.println();
			bfsOutputString +="\n";
		}
		System.out.println("----------------------- BFS -------------------------------------");
		bfsOutputString += "----------------------- BFS -------------------------------------\n";
		bfsFileLines++;
		int spacePerNode = 11;
		int nodesParsed = 0;
		
		for(int line = 0; line < bfsNodePathTaken.size(); line++)
		{
			
			if(bfsFileLines >= fileLimit)
			{
				bfsFileLines = 0;
				bfsOutput.close();
				bfsFileNumber++;
				bfsOutput = new PrintWriter("bfs_output_"+ bfsFileNumber + ".txt");
			}
			
			if(bfsOutputString.length() >= stringLimit)
			{
				bfsOutput.println(bfsOutputString);
				bfsOutputString = "";
			}
		
			System.out.println("------------------------------ Tree Level: " + line + "------------------------------");
			bfsOutputString += "------------------------------ Tree Level: " + line + "------------------------------\n";
			bfsFileLines++;
			for(int row = -1; row < 3; row++)
			{
				if(row == -1)
				{
					for(int nodeIndex = 0; nodeIndex < bfsNodePathTaken.get(line).size(); nodeIndex++)
					{
		String string = "Node " + (nodesParsed + nodeIndex);				System.out.print("Node " + (nodesParsed + nodeIndex));
						t=nodesParsed+nodeIndex;
				bfsOutputString += string;
			for(int space = 0; space < spacePerNode - string.length(); space++)
						{							System.out.print(" ");
							bfsOutputString += " ";
						}
						System.out.print("|");
						bfsOutputString += "|";
					} 
				}
				else 
				{
					for(int nodeIndex = 0; nodeIndex < bfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						System.out.print("   ");
						bfsOutputString += "   ";
						for(int column = 0; column < 3; column++)
						{
		System.out.print(bfsNodePathTaken.get(line).get(nodeIndex).mat[row][column] + " ");
							bfsOutputString += bfsNodePathTaken.get(line).get(nodeIndex).mat[row][column] + " ";
						}
						System.out.print("  |");
						bfsOutputString += "  |";
					}
				}
				
				System.out.print("\n");
				bfsOutputString += "\n";
				bfsFileLines++;
			}
			nodesParsed += bfsNodePathTaken.get(line).size();
			
		}
		bfsOutputString += "COST OF BFS IS:"+t;
		System.out.println("COST OF BFS IS:"+t);
		Vector<Vector<ScannedNode>> dfsNodePathTaken = new Vector<Vector<ScannedNode>>();
		Stack<Node> dfsStack = new Stack<Node>();
		dfsStack.push(initialState);
		int scannedNodes = 0;
		while(!dfsStack.isEmpty())
		{
			Node currentNode = dfsStack.pop();
			
			while(currentNode.level >= dfsNodePathTaken.size())
			{
				dfsNodePathTaken.add(new Vector<ScannedNode>());
			}
			ScannedNode scannedNode = new ScannedNode();
			scannedNode.node = currentNode;
			scannedNode.scanOrder = scannedNodes;
			dfsNodePathTaken.get(currentNode.level).add(scannedNode);
			
			if(compareNodes(currentNode, finalState) == -1)
			{
				break;
			}
			
			Vector<Node> children = getChildren(currentNode);
			for(int child = children.size()-1; child>=0; child--)
			{
				boolean alreadyScanned = false;
				for(int i = 0; i < dfsNodePathTaken.size(); i++)
				{
					for(int j = 0; j < dfsNodePathTaken.get(i).size(); j++)
					{
						if(compareNodes(children.get(child), dfsNodePathTaken.get(i).get(j).node) == -1)
						{
							alreadyScanned = true;
							break;
						}
					}
				}

				if(alreadyScanned)
				{
					continue;
				}
				else
				{
					dfsStack.push(children.get(child));
				}
			}

			scannedNodes++;
		}
		
		/*DISPLAY RESULTS*/
		int dfsFileLines = 0;
		int dfsFileNumber = 0;
		System.out.println();
		dfsOutputString +="OPERATORS"+"\n";
		dfsOutputString +="LEFT,RIGHT,UP,DOWN"+"\n";
		
		
dfsOutputString +="INITIAL STATE"+"\n";
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				
				dfsOutputString += Integer.toString(arr[i][j])+" ";
			
			}
			
			dfsOutputString +="\n";
		}
		dfsOutputString +="GOAL STATE"+"\n";
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				
				dfsOutputString += Integer.toString(arr2[i][j])+" ";
			
			}
			
			dfsOutputString +="\n";
		
		
		}
		System.out.println("----------------------- DFS -------------------------------------");
		dfsOutputString += "----------------------- DFS -------------------------------------\n";
		
		dfsFileLines++;
		for(int line = 0; line < dfsNodePathTaken.size(); line++)
		{
			if(dfsFileLines >= fileLimit)
			{
				dfsFileLines = 0;
				dfsFileNumber++;
				dfsOutput.close();
				dfsOutput = new PrintWriter("dfs_output_"+ dfsFileNumber + ".txt");
			}
			
			if(dfsOutputString.length() >= stringLimit)
			{
				dfsOutput.println(dfsOutputString);
				dfsOutputString = "";
			}
              
			
			
			System.out.println("------------------------------ Tree Level: " + line + "------------------------------");
			dfsOutputString += "------------------------------ Tree Level: " + line + "------------------------------\n";
			dfsFileLines++;
			for(int row = -1; row < 3; row++)
			{
				if(row == -1)
				{
					for(int nodeIndex = 0; nodeIndex < dfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						String string = "Node " + (dfsNodePathTaken.get(line).get(nodeIndex).scanOrder);
						t=dfsNodePathTaken.get(line).get(nodeIndex).scanOrder;
						System.out.print("Node " + (dfsNodePathTaken.get(line).get(nodeIndex).scanOrder));
						dfsOutputString += string;
						for(int space = 0; space < spacePerNode - string.length(); space++)
						{
							System.out.print(" ");
							dfsOutputString += " ";
						}
						System.out.print("|");
						dfsOutputString += "|";
					} 
				}
				else 
				{
					for(int nodeIndex = 0; nodeIndex < dfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						System.out.print("   ");
						dfsOutputString += "   ";
						for(int column = 0; column < 3; column++)
						{
							System.out.print(dfsNodePathTaken.get(line).get(nodeIndex).node.mat[row][column] + " ");
							dfsOutputString += dfsNodePathTaken.get(line).get(nodeIndex).node.mat[row][column] + " ";
						}
						System.out.print("  |");
						dfsOutputString += "  |";
						
					}
				}	
				System.out.print("\n");
				dfsOutputString += "\n";
				dfsFileLines++;
			}
		}
		dfsOutputString +="COST OF DFS:"+t;
		System.out.println("COST OF DFS:"+t);
		bfsOutput.println(bfsOutputString);
		bfsOutput.close();
		dfsOutput.println(dfsOutputString);
		dfsOutput.close();
	}
}
