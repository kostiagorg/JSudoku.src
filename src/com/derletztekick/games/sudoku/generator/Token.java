 /**********************************************************************
 *                              Token                                   *
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

public class Token {
	private final int maxlen;
	private int number, row, col, lastIndex=0, inputnumber=-1;
	private int canceledValues[] = null;
	private boolean isHiddenToken=false;
	public Token(int number, int row, int col, int maxlen) {
		this.number = number;
		this.maxlen = maxlen;
		this.canceledValues = new int[this.maxlen];
		this.setRow(row);
		this.setColumn(col);
	}
	
	public boolean isHiddenToken() {
		return this.isHiddenToken;
	}
	
	public void isHiddenToken(boolean isHiddenToken) {
		this.isHiddenToken = isHiddenToken;
	}
	
	public void setInputNumber(int inputnumber) {
		this.inputnumber = inputnumber;
	}
	
	public int getInputNumber() {
		return this.inputnumber;
	}
	
	public void setNumber(int number) {
		this.number = number>this.maxlen?1:number;
	}
	
	public int getNumber() {
		return this.number;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int col) {
		this.col = col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.col;
	}
	
	public boolean isFirstToken() {
		return this.row == 0 && this.col == 0;
	}
	
	public boolean isCanceledValues(int val) {
		for (int i=0; i<this.maxlen; i++)
			if (val == this.canceledValues[i])
				return true;
		return false;
	}
	
	public void addCanceledValues(int val) {
		val = val>this.maxlen?1:val;
		this.canceledValues[lastIndex] = val;
		this.lastIndex++;
	}
}
