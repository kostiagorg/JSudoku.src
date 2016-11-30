 /**********************************************************************
 *                             GamePad                                  *
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

package com.derletztekick.games.sudoku.gui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.derletztekick.games.sudoku.generator.SudokuGenerator;
import com.derletztekick.games.sudoku.generator.Token;

public class GamePad extends JPanel implements DocumentListener, KeyListener, MouseListener {
	private static final long serialVersionUID = -1544885985693657411L;
	private PropertyChangeSupport changes = new PropertyChangeSupport( this ); 
	private SudokuGenerator sudokuGenerator;
	private boolean isSolved = false;
	private InputField[][] inputFields;
	public GamePad(int sudokuType, int hiddenFields, boolean isXSudoku) {
		this.initSudoku(sudokuType, hiddenFields, isXSudoku);
	}
	
	public void initSudoku(int sudokuType, int hiddenFields, boolean isXSudoku) {
		this.isSolved = false;
		this.inputFields = new InputField[sudokuType][sudokuType];
		this.removeAll();
		this.sudokuGenerator = new SudokuGenerator(sudokuType, hiddenFields, isXSudoku);
		int sqrtSudokuType = (int)Math.sqrt(sudokuType);
		this.setLayout(new GridLayout(sqrtSudokuType, sqrtSudokuType));
		
		for (int k=0; k<sudokuType; k++) {
			int r = (int)(k/sqrtSudokuType);
			int c = k - r*sqrtSudokuType;
			JPanel pane = null;
			
			pane = new JPanel();
			pane.setLayout(new GridLayout(sqrtSudokuType, sqrtSudokuType));
			pane.setBorder(BorderFactory.createLoweredBevelBorder());
			this.add(pane);
			
			for (int i=r*sqrtSudokuType; i<r*sqrtSudokuType+sqrtSudokuType; i++) {
				for (int j=c*sqrtSudokuType; j<c*sqrtSudokuType+sqrtSudokuType; j++) {	
					Token token = this.sudokuGenerator.getTokenAt(i,j);
					InputField inputField = new InputField(token);
					inputField.addListener(this);
					pane.add(inputField);
					this.inputFields[i][j] = inputField;
				}
			}
		}
		this.validate();
		this.repaint();
		
		
		
//		this.setLayout(new GridLayout(sudokuType, sudokuType));
//		for (int i=0; i<sudokuType; i++) {
//			for (int j=0; j<sudokuType; j++) {	
//				Token token = this.sudokuGenerator.getTokenAt(i,j);
//				InputField inputField = new InputField(token);
//				inputField.addListener(this);
//				this.add(inputField);
//				
//			}
//		}
	}
	
	public boolean isSolved() {
		return this.isSolved;
	}
	
	public void solve() {
		this.isSolved = true;
		for (int i=0; i<this.inputFields.length; i++) {
			for (int j=0; j<this.inputFields[i].length; j++) {
				this.inputFields[i][j].showSolution();
			}
		}
	}
	
	private void checkSolution() {
		if (!this.isSolved) {
			this.isSolved = this.sudokuGenerator.isSolved();
			if (this.isSolved) {
				this.changes.firePropertyChange( "IS_SOLVED", !this.isSolved, this.isSolved ); 
				for (int i=0; i<this.inputFields.length; i++) {
					for (int j=0; j<this.inputFields[i].length; j++) {
						this.inputFields[i][j].setEnabled(false);
					}
				}
				JOptionPane.showMessageDialog(this,
						"Congratulations, you have completed JSUDOKU!",
						"JSudoku solved",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		this.checkSolution();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.checkSolution();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.checkSolution();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		this.checkSolution();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		this.checkSolution();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		this.checkSolution();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.checkSolution();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.checkSolution();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.checkSolution();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.checkSolution();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.checkSolution();
	}
	
	public void addPropertyChangeListener( PropertyChangeListener l ) { 
		this.changes.addPropertyChangeListener( l ); 
	} 
	 
	public void removePropertyChangeListener( PropertyChangeListener l ) { 
		this.changes.removePropertyChangeListener( l ); 
	} 

	
}
