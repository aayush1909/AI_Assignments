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
 * 1 2 3 
 * 5 8 6
 * 0 7 4
* */

class DepthSearchResult
{
	Vector<Vector<ScannedNode>> pathTaken;
	int solutionNodeNumber;
	int nodesSearched;
	int cost;
};

class BreadthSearchResult
{
	Vector<Vector<Node>> pathTaken;
	int solutionNodeNumber;
	int nodesSearched;
	int cost;
};

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
			case 0:
			{//UP
				if(pos[0] != 0)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0] - 1][pos[1]];
					b.mat[pos[0] - 1][pos[1]] = 0;
					res.add(b);
				}

			}break;
			case 1:
			{//down
				if(pos[0] != 2)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0] + 1][pos[1]];
					b.mat[pos[0] + 1][pos[1]] = 0;
					res.add(b);
				}

			}break;
			case 2:
			{//left
				if(pos[1] != 0)
				{
					b.mat[pos[0]][pos[1]] = b.mat[pos[0]][pos[1] - 1];
					b.mat[pos[0]][pos[1] - 1] = 0;
					res.add(b);
				}

			}break;
			case 3:
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
		
	public static DepthSearchResult depthSearch(Node initialState, Node finalState, int maxLevelLimit)
	{
		DepthSearchResult result = new DepthSearchResult();
		
		Vector<Vector<ScannedNode>> dfsNodePathTaken = new Vector<Vector<ScannedNode>>();
		Stack<Node> dfsStack = new Stack<Node>();
		dfsStack.push(initialState);
		int solutionNodeNumber = -1;
		int cost = -1;
		
		int scannedNodes = 0;
		
		while(!dfsStack.isEmpty())
		{
			Node currentNode = dfsStack.pop();
			scannedNodes++;
			
			while(currentNode.level >= dfsNodePathTaken.size())
			{
				dfsNodePathTaken.add(new Vector<ScannedNode>());
			}
			ScannedNode scannedNode = new ScannedNode();
			scannedNode.node = currentNode;
			scannedNode.scanOrder = scannedNodes - 1;
			dfsNodePathTaken.get(currentNode.level).add(scannedNode);
			
			if(compareNodes(currentNode, finalState) == -1)
			{
				cost = scannedNode.node.level;
				solutionNodeNumber = scannedNode.scanOrder;
				break;
			}
			
			Vector<Node> children = getChildren(currentNode);
			for(int child = 0; child < children.size(); child++)
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
					if(maxLevelLimit >= 0)
					{
						if(children.get(child).level > maxLevelLimit)
						{
							continue;
						}
					}
					
					dfsStack.push(children.get(child));
				}
			}
			
		}
		
		result.pathTaken = dfsNodePathTaken;
		result.cost = cost;
		result.nodesSearched = scannedNodes;
		result.solutionNodeNumber = solutionNodeNumber;
		return result;
	}

	public static String convertDepthSearchResultToString(DepthSearchResult result)
	{
		int spacePerNode = 11;
		String outputString = "";
		Vector<Vector<ScannedNode>> dfsNodePathTaken = result.pathTaken;
		if(result.solutionNodeNumber != -1)
		{
			outputString  += "Solution Found : Node " + result.solutionNodeNumber + "\n";
		}
		else 
		{
			outputString  += "Solution Not Found\n";
		}
		
		for(int line = 0; line < dfsNodePathTaken.size(); line++)
		{
			
			outputString += "------------------------------ Tree Level: " + line + "------------------------------\n";

			for(int row = -1; row < 3; row++)
			{
				if(row == -1)
				{
					for(int nodeIndex = 0; nodeIndex < dfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						String string = "Node " + (dfsNodePathTaken.get(line).get(nodeIndex).scanOrder);
						outputString  += string;
						for(int space = 0; space < spacePerNode - string.length(); space++)
						{
							outputString += " ";
						}
						outputString += "|";
					} 
				}
				else 
				{
					for(int nodeIndex = 0; nodeIndex < dfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						outputString += "   ";
						for(int column = 0; column < 3; column++)
						{
					
							outputString += dfsNodePathTaken.get(line).get(nodeIndex).node.mat[row][column] + " ";
						}
						outputString  += "  |";
					}
				}	
				outputString += "\n";
			}
		}

		
		return outputString;
	}
	
	public static BreadthSearchResult breadthSearch(Node initialState, Node finalState)
	{
		BreadthSearchResult result = new BreadthSearchResult();
		int solutionNodeNumber = -1;
		int cost = -1;
		Vector<Vector<Node>> bfsNodePathTaken = new Vector<Vector<Node>>(); 
		Queue<Node> bfsQueue = new LinkedList<>();
		bfsQueue.add(initialState);
		
		int currentLine = -1;
		int lastLevel = -1;
		
		int nodesSearched = -1;
		while(!bfsQueue.isEmpty())
		{
			Node currentNode = bfsQueue.remove();
			nodesSearched++;
			if(lastLevel != currentNode.level)
			{
				currentLine++;
				lastLevel = currentNode.level;
				bfsNodePathTaken.add(new Vector<Node>());
			}
			bfsNodePathTaken.get(currentLine).add(currentNode);

			if(compareNodes(currentNode, finalState) == -1)
			{
				cost = currentNode.level;
				solutionNodeNumber = nodesSearched;
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

		result.solutionNodeNumber = solutionNodeNumber;
		result.cost = cost;
		result.pathTaken = bfsNodePathTaken;
		result.nodesSearched = nodesSearched + 1;

		return result;
	}
	
	public static String convertBreadthSearchResultToString(BreadthSearchResult result)
	{
		String outputString = "";
		Vector<Vector<Node>> bfsNodePathTaken = result.pathTaken;
		int spacePerNode = 11;
		
		if(result.solutionNodeNumber != -1)
		{
			outputString  += "Solution Found : Node " + result.solutionNodeNumber + "\n";
		}
		else 
		{
			outputString  += "Solution Not Found\n";
		}
		
		int nodesParsed = 0;
		for(int line = 0; line < bfsNodePathTaken.size(); line++)
		{
						
			outputString += "------------------------------ Tree Level: " + line + "------------------------------\n";
			
			for(int row = -1; row < 3; row++)
			{
				if(row == -1)
				{
					for(int nodeIndex = 0; nodeIndex < bfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						String string = "Node " + (nodesParsed + nodeIndex);
			
						outputString += string;
						for(int space = 0; space < spacePerNode - string.length(); space++)
						{
							
							outputString += " ";
						}
						
						outputString  += "|";
					} 
				}
				else 
				{
					for(int nodeIndex = 0; nodeIndex < bfsNodePathTaken.get(line).size(); nodeIndex++)
					{
						
						outputString += "   ";
						for(int column = 0; column < 3; column++)
						{
							outputString += bfsNodePathTaken.get(line).get(nodeIndex).mat[row][column] + " ";
						}
						
						outputString  += "  |";
					}
				}
				outputString += "\n";
			}
			nodesParsed += bfsNodePathTaken.get(line).size();
		}
		
		return outputString;
	}
	
	
	
	public static void main(String args[]) throws FileNotFoundException
	{
		PrintWriter bfsOutput = new PrintWriter("allSearchOutputs\\bfs_output.txt");
		PrintWriter dfsOutput = new PrintWriter("allSearchOutputs\\dfs_output.txt");
		PrintWriter dlsOutput = new PrintWriter("allSearchOutputs\\dls_output.txt");
		PrintWriter idsOutput = null;
		
		String bfsOutputString = "";
		String dfsOutputString = "";
		String dlsOutputString = "";
		
		
		
		int arr[][] = { {1, 2, 3},
				  		{5, 6, 0},
				  		{7, 8, 4}};
		

		int arr2[][] = {{1, 2, 3},
				  		{5, 8, 6},
				  		{0, 7, 4}};
		
		Node initialState = new Node(arr);
		Node finalState = new Node(arr2);
		
		System.out.println("Initial State: ");
		System.out.println(MatUtil.convertToString(initialState.mat));
		
		System.out.println("\nFinal State: ");
		System.out.println(MatUtil.convertToString(finalState.mat));
		
		System.out.println("Operators : move space in : left, right, up, down");

		BreadthSearchResult  bfsResult = breadthSearch(initialState, finalState);
		bfsOutputString = convertBreadthSearchResultToString(bfsResult);
		
		DepthSearchResult dfsResult = depthSearch(initialState, finalState, -1);
		dfsOutputString = convertDepthSearchResultToString(dfsResult);
		
		int levelLimit = 10;
		DepthSearchResult dlsResult = depthSearch(initialState, finalState, levelLimit);
		dlsOutputString = convertDepthSearchResultToString(dlsResult);

		
		int idsStoppingLimit = 2000;
		int totalIdsCost = 0;
		DepthSearchResult idsResult = new DepthSearchResult();
		idsResult.nodesSearched = 0;
		Vector<String> idsOutputStrings = new Vector<String>();
		for(int currentIterativeLevel = 0; currentIterativeLevel < idsStoppingLimit; currentIterativeLevel++)
		{
			DepthSearchResult idsResultTemp = depthSearch(initialState, finalState, currentIterativeLevel);
			idsOutputStrings.add(convertDepthSearchResultToString(idsResultTemp));
			
			idsResult.solutionNodeNumber = idsResultTemp.solutionNodeNumber;
			idsResult.cost = idsResultTemp.cost;
			idsResult.pathTaken = idsResultTemp.pathTaken;
			System.out.println(idsResultTemp.nodesSearched);
			idsResult.nodesSearched += idsResultTemp.nodesSearched;
			
			if(idsResult.solutionNodeNumber != -1)
			{
				break;
			}
		}
		
		System.out.println("Costs: (a cost of -1 means that solution was not found");
		System.out.println("BFS: " + bfsResult.cost + " | Nodes Searched : " + bfsResult.nodesSearched);
		//System.out.println("DFS: " + dfsResult.cost + " | Nodes Searched : " + dfsResult.nodesSearched);
		System.out.println("DLS: " + dlsResult.cost + " | Nodes Searched : " + dlsResult.nodesSearched);
		System.out.println("IDS: " + idsResult.cost + " | Nodes Searched : " + idsResult.nodesSearched);

		bfsOutput.println(bfsOutputString);
		bfsOutput.close();
		
		dfsOutput.println(dfsOutputString);
		dfsOutput.close();
		
		dlsOutput.println(dlsOutputString);
		dlsOutput.close();
		
		for(int i = 0; i < idsOutputStrings.size(); i++)
		{
			idsOutput = new PrintWriter("allSearchOutputs\\idsOutputs\\ids_output_level_" + i + ".txt");
			idsOutput.println(idsOutputStrings.get(i));
			idsOutput.close();			
		}
	}
}
