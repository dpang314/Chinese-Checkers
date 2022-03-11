import java.awt.Color;
import java.util.*;

public class ArushiStrategy extends Player {

	private int callGM=0;
	private ArrayList<Position> moveQueue;
	private ArrayDeque<Move> optimalJumpChain;
	private boolean moveCalc=false;

	public ArushiStrategy (Color color, String playerName) {
		super(color, playerName);
	}

	public boolean isHuman() {
		return false;
	}

	public Move getMove(Board board) {
		int bestIndex;
		Move currentMove;
		//when you've called get move queue size-1 times (0 to queue size-2):
		//set calledGM to 0
		//set the move arrayList to an empty arrayList
		if (!moveCalc) {
			moveCalc=true;
			bestIndex=calculateMove(board);

		} else if (optimalJumpChain.size()==0) {

		}

	}

	private int calculateMove(Board board) {
		ArrayList<ArrayList<Position>> neighborConfs = 
				new ArrayList<ArrayList<Position>>(0);

		for (int i=0; i<=9; i++) {
			ArrayList<Position> pegStep = new ArrayList<Position>(0);
			pegStep.add(this.posArr.get(i));
			pegStep.add(generateConfs(this.posArr.get(i), board));
			neighborConfs.add(pegStep);
		}

		double [] rwDists = new double[10];

		for (int i=0; i<=9; i++) {
			rwDists[i]=randomWalk(i, neighborConfs.get(i).get(1), board);
		}

		//find the smallest randomWalk distance average

		double smallestRW=(double) Double.MAX_VALUE;
		int smallestRWInd=0;
		for (int i=0; i<=9; i++) {
			if (rwDists[i]<smallestRW) {
				smallestRWInd=i;
				smallestRW=rwDists[i];
			}
		}

		//Move thisTurn = new Move(neighborConfs.get(smallestRWInd).get(0), endPos, this);

		return smallestRWInd;
	}

	private double randomWalk(int tracker, Position newPos, Board board) {
		int length=5;
		double numRW=5;
		Board tbRW = board.clone(); //tbRW=test board random walk
		Position [] simulation = new Position[10];
		for (int i=0; i<=9; i++) {
			if (i!=tracker) {
				simulation[i]=this.posArr.get(i);
			} else {
				simulation[i]=newPos;
			}
		}

		double avgDistEst=0;

		for (int r=1; r<=numRW; r++) {
			//make an array that's a copy of simulation
			Position [] copySIM = new Position[10];
			for (int j=0; j<=9; j++) {
				copySIM[j]=simulation[j];
			}
			for (int l=1; l<=length; l++) {
				int randInd = (int) (Math.random()*9+0); //int btwn 0, 9 inclusive
				Position next = generateConfs(copySIM[randInd], tbRW);
				tbRW.fillPos(next);
				//empty the position in tbRW that simulation[randInd] was initially in
				tbRW.clearPos(copySIM[randInd]);
				copySIM[randInd]=next;
			}
			avgDistEst+=sumDistances(copySIM);
		}

		avgDistEst/=numRW;

		return avgDistEst;
	}


	private Position generateConfs(Position pos, Board current) {
		Position next = new Position(pos.getRow(), pos.getColumn());
		int movingPegDist = pegDistance(pos);
		ArrayList<Position> possMoves = current.possibleMoves(pos, false);

		if (possMoves.size()>0) {
			int nextMoveIndex=0;
			boolean goodStep=false;
			for (int i=0; i<possMoves.size(); i++) {
				int possDist = pegDistance(possMoves.get(i));
				if (possDist<=movingPegDist) {
					movingPegDist=possDist;
					nextMoveIndex=i;
					goodStep=true;
				}
			}

			if (goodStep) {
				//stop if the best next configuration via this greedy alg is an adjacent step
				if (!(checkHop(pos, possMoves.get(nextMoveIndex)))) {
					next=possMoves.get(nextMoveIndex);
				} else {
					boolean improvingJumps=true;
					Position newCurr = possMoves.get(nextMoveIndex); //newCurr: new current pos within that turn
					int currDist = pegDistance(newCurr);
					while (improvingJumps) {
						ArrayList<Position> jumps = current.possibleJumpMoves(newCurr);
						nextMoveIndex = 0;
						goodStep=false;
						for (int i=0; i<jumps.size(); i++) {
							int possDist = pegDistance(jumps.get(i));
							if (possDist<=currDist) {
								nextMoveIndex=i;
								currDist=possDist;
								goodStep=true;
							}
						}

						if (goodStep) {
							newCurr = jumps.get(nextMoveIndex);
						} else {
							improvingJumps=false;
						}
					}

					next = newCurr;
				}
			}
		}

		return next;
	}

	private ArrayDeque<Move> confQueue(ArrayList<Position> posArr, Board current) {
		ArrayDeque<Move> ret = new ArrayDeque<Move>();
		for(int i = 0; i<posArr.size()-1; i++) {
			Move newJump = new Move(posArr.get(i), posArr.get(i), this);
			ret.add(newJump);
		}
		return ret;
	}

	private ArrayList<Position> generateConfArr(Position pos, Board current) {
		ArrayList<Position> path = new ArrayList<Position>();

		Position next = new Position(pos.getRow(), pos.getColumn());
		int movingPegDist = pegDistance(pos);
		ArrayList<Position> possMoves = current.possibleMoves(pos, false);

		if (possMoves.size()>0) {
			int nextMoveIndex=0;
			boolean goodStep=false;
			for (int i=0; i<possMoves.size(); i++) {
				int possDist = pegDistance(possMoves.get(i));
				if (possDist<=movingPegDist) {
					movingPegDist=possDist;
					nextMoveIndex=i;
					goodStep=true;
					next=possMoves.get(nextMoveIndex);
				}
			}
			
			path.add(next);

			if (goodStep) {
				//stop if the best next configuration via this greedy alg is an adjacent step
				if (!(checkHop(pos, possMoves.get(nextMoveIndex)))) {
					next=possMoves.get(nextMoveIndex);
				} else {
					boolean improvingJumps=true;
					Position newCurr = possMoves.get(nextMoveIndex); //newCurr: new current pos within that turn
					int currDist = pegDistance(newCurr);
					while (improvingJumps) {
						ArrayList<Position> jumps = current.possibleJumpMoves(newCurr);
						nextMoveIndex = 0;
						goodStep=false;
						for (int i=0; i<jumps.size(); i++) {
							int possDist = pegDistance(jumps.get(i));
							if (possDist<=currDist) {
								nextMoveIndex=i;
								currDist=possDist;
								goodStep=true;
							}
						}

						if (goodStep) {
							newCurr = jumps.get(nextMoveIndex);
						} else {
							improvingJumps=false;
						}
					}
				}
			}
		} else {
			path.add(next);
		}

		return path;
	}

		//this is for one hop within a turn-not a chain of hops
		private boolean checkHop (Position start, Position end) {
			boolean isHop=false;

			if (start.getRow()==end.getRow()) {
				if (Math.abs(start.getColumn()-end.getColumn()) == 2) {
					isHop = true;
				}
			} else if (Math.abs(start.getRow()-end.getRow()) == 2) {
				isHop = true;
			}

			return isHop;
		}

		//instead of typing out the same for loop many times, this makes the code 
		//much easier to read.
		private int sumDistances(Position [] pegs) {
			int sumDist=0;
			for (int i=0; i<=9; i++) {
				Position pos = pegs[i];
				sumDist+=pegDistance(pos);
			}

			return sumDist;
		}

		private int pegDistance(Position pos) {

			int distanceEst = 0;
			int winReg = this.getWR();

			//Finds the index of the tip of each win region
			//since getting pieces into the tip is ideal

			int tipInd = 0;
			if (winReg==2||winReg==4)
				tipInd = 3;
			else {
				tipInd = 0;
			}
			//finds the goal position based on player color

			Position[] winArea = Board.homeAll.get(this.getWR());

			Position goal = winArea[tipInd];
			//Gets current peg's position
			//estimates distance by row/column
			distanceEst = Math.abs(pos.getRow()-goal.getRow())+
					Math.abs(pos.getColumn()-goal.getColumn());

			return distanceEst;
		}



	}

