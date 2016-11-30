 /**********************************************************************
 *                          SudokuGenerator                             *
 ************************************************************************
 * Copyright (C) by Michael Loesler, http//derletztekick.com            *
 *                                                                      *
 * This program is free software; you can redistribute it and/or modify *
 * it under the terms of the GNU General Public License as published by *
 * the Free Software Foundation; either version 3 of the License, or    *
 * (at your option) any later version.                                  *
 *                                                                      *
 * This program is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 * GNU General Public License for more details.                         *
 *                                                                      *
 * You should have received a copy of the GNU General Public License    *
 * along with this program; if not, see <http://www.gnu.org/licenses/>  *
 * or write to the                                                      *
 * Free Software Foundation, Inc.,                                      *
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.            *
 *                                                                      *
  **********************************************************************/

package com.derletztekick.games.sudoku.generator;

import java.util.LinkedHashSet;
import java.util.Set;

public class SudokuGenerator {
	private int sudokuType = 9;
	private Token[][] sudokuPad;
	private boolean isComplete = false;
	private int level = 1;
	private Set<Integer> hiddenToken = new LinkedHashSet<Integer>();
	private boolean isStepForward = true, isXSudoku = false;
	public SudokuGenerator(int sudokuType, int level, boolean isXSudoku) {
		this.sudokuType = sudokuType;
		this.level = level;
		this.isXSudoku = isXSudoku;
		this.sudokuPad = new Token[this.sudokuType][this.sudokuType];

	    while(this.hiddenToken.size() < this.level){
	    	this.hiddenToken.add(this.getRandomInteger(this.sudokuType*this.sudokuType));
	    }
	    //System.out.println(this.hiddenToken);
	    
		this.initEmptyPad();
		Token token = new Token(this.getRandomInteger(this.sudokuType), 0, 0, this.sudokuType);
		//this.createSudoku(token, true);
		
		do {
			token = this.createSudokuStep(token);
		} while (token != null && !this.isComplete);
		
		
		for (Integer i : this.hiddenToken) {
			int r = (int)((i-1)/this.sudokuType);
			int c = i - r*this.sudokuType -1;
			Token t = this.getTokenAt(r, c);
			t.isHiddenToken(true);
		}
		
		//this.print();
	}
	
	public boolean isComplete() {
		return this.isComplete;
	}
	
	public void print() {
		for (int i=0; i<this.sudokuType; i++) {
			for (int j=0; j<this.sudokuType; j++) {
				System.out.print( this.getTokenAt(i, j).getNumber()+"   ");
			}
			System.out.println();
		}
	}
	
	public void initEmptyPad() {
		for (int i=0; i<this.sudokuType; i++) {
			for (int j=0; j<this.sudokuType; j++) {
				Token token = new Token(0,i,j,this.sudokuType);
				this.setTokenAt(token, i, j);
			}
		}
	}
	
	public Token getTokenAt(int row, int column) {
		return this.sudokuPad[row][column];
	}
	
	private void setTokenAt(Token token, int row, int column) {
		this.sudokuPad[row][column] = token;
	}
	
	private Token createSudokuStep(Token token) {
		if (token.getColumn() > this.sudokuType-1 && token.getRow() >= this.sudokuType-1) {
			this.isComplete = true;
			return null;
		}

		if (token.getColumn() >= this.sudokuType) {
			token.setColumn(0);
			token.setRow(token.getRow()+1);
		}
		else if (token.getColumn() < 0) {
			token.setColumn(this.sudokuType-1);
			token.setRow(token.getRow()-1);
		}

		if (!isStepForward){
			token = this.getTokenAt(token.getRow(), token.getColumn());
			if (!token.isCanceledValues(token.getNumber()) && !token.isFirstToken())
				token.addCanceledValues(token.getNumber());
			this.setTokenAt(new Token(0, token.getRow(), token.getColumn(), this.sudokuType), token.getRow(), token.getColumn());
		}
	
		for (int l=0; l<this.sudokuType; l++){

			if (this.canInsert(token) && !token.isCanceledValues(token.getNumber())){
				if (!token.isFirstToken())
					token.addCanceledValues(token.getNumber());
				this.setTokenAt(token, token.getRow(), token.getColumn());
				this.isStepForward = true;
				return new Token(this.getRandomInteger(this.sudokuType), token.getRow(), token.getColumn()+1, this.sudokuType);
			}
			else {
				token.setNumber(token.getNumber()+1>this.sudokuType?1:token.getNumber()+1);
			}
		}

		this.setTokenAt(new Token(0, token.getRow(), token.getColumn(), this.sudokuType), token.getRow(), token.getColumn());
		this.isStepForward = false;
		return new Token(0, token.getRow(), token.getColumn()-1, this.sudokuType);
	}
	
//	private boolean createSudoku(Token token, boolean isStepForward) {
//		if (token.getColumn() > this.sudokuType-1 && token.getRow() >= this.sudokuType-1) {
//			this.isComplete = true;
//			return true;
//		}
//
//		if (token.getColumn() >= this.sudokuType) {
//			token.setColumn(0);
//			token.setRow(token.getRow()+1);
//		}
//		else if (token.getColumn() < 0) {
//			token.setColumn(this.sudokuType-1);
//			token.setRow(token.getRow()-1);
//		}
//
//		if (!isStepForward){
//			token = this.getTokenAt(token.getRow(), token.getColumn());
//			if (!token.isCanceledValues(token.getNumber()) && !token.isFirstToken())
//				token.addCanceledValues(token.getNumber());
//			this.setTokenAt(new Token(0, token.getRow(), token.getColumn(), this.sudokuType), token.getRow(), token.getColumn());
//		}
//	
//		for (int l=0; l<this.sudokuType; l++){
//
//			if (this.canInsert(token) && !token.isCanceledValues(token.getNumber())){
//				if (!token.isFirstToken())
//					token.addCanceledValues(token.getNumber());
//				this.setTokenAt(token, token.getRow(), token.getColumn());
//				
//				return this.createSudoku(new Token(this.getRandomInteger(this.sudokuType), token.getRow(), token.getColumn()+1, this.sudokuType), true);
//			}
//			else {
//				token.setNumber(token.getNumber()+1>this.sudokuType?1:token.getNumber()+1);
//			}
//		}
//
//		this.setTokenAt(new Token(0, token.getRow(), token.getColumn(), this.sudokuType), token.getRow(), token.getColumn());
//		return this.createSudoku(new Token(0, token.getRow(), token.getColumn()-1, this.sudokuType), false);
//	}
	
	private boolean canInsert(Token token) {
		for (int i=0; i<this.sudokuType; i++) {
			Token colToken = this.getTokenAt(i, token.getColumn());
			Token rowToken = this.getTokenAt(token.getRow(),i);
			Token diag1Token = this.getTokenAt(this.sudokuType-1-i,i);
			Token diag2Token = this.getTokenAt(i,i);
			if (colToken.getNumber() == token.getNumber())
				return false;
			else if (rowToken.getNumber() == token.getNumber())
				return false;
			else if (this.isXSudoku && this.sudokuType-1-token.getRow() == token.getColumn() && diag1Token.getNumber() == token.getNumber())
				return false;	
			else if (this.isXSudoku && token.getRow() == token.getColumn() && diag2Token.getNumber() == token.getNumber())
				return false;
		}
		
		int quat = (int)Math.sqrt(this.sudokuType);
		for (int i=(token.getRow()/quat)*quat; i<(token.getRow()/quat)*quat+quat; i++) {
			for (int j=(token.getColumn()/quat)*quat; j<(token.getColumn()/quat)*quat+quat; j++) {
				Token quatToken = this.getTokenAt(i, j);
				if (quatToken.getNumber() == token.getNumber()) {
					return false;
				}
			}
		}
					
		return true;	
	}
	
	public boolean isSolved(){
		for (Integer i : this.hiddenToken) {
			int r = (int)((i-1)/this.sudokuType);
			int c = i - r*this.sudokuType -1;
			Token token = this.getTokenAt(r, c);
			int usrVal = token.getInputNumber();
			if (usrVal<=0)
				return false;
			token.setNumber(0);
		}
		for (Integer i : this.hiddenToken) {
			int r = (int)((i-1)/this.sudokuType);
			int c = i - r*this.sudokuType -1;
			Token token = this.getTokenAt(r, c);
			int usrVal = token.getInputNumber();	
			if (!this.canInsert(new Token(usrVal, r, c, this.sudokuType)))
				return false;
		}
		return true;
	}
	
	
	public int getRandomInteger(int pMaximum) {
		return  (int) ((Math.random()*pMaximum)+1);
    }
}
