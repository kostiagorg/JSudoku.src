 /**********************************************************************
 *                             JSudoku                                  *
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

package com.derletztekick.games.sudoku;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

import com.derletztekick.games.sudoku.gui.GamePad;

public class JSudoku extends JFrame implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = 5894296110932091304L;
	public final static int SUDOKU_TYPE = 9;
	private JSpinner hiddenFields;
	private JCheckBox xSudokuBox;
	private GamePad gamePad;
	private JButton startAndSolveButton;
	public JSudoku(int frameWidth, int frameHeight) {
		super("JSudoku by Michael L\u00f6sler [http://derletztekick.com]");
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		String icon = "/com/derletztekick/games/sudoku/icon/derletztekick.com.png";

	    try {
	    	this.setIconImage(new ImageIcon(getClass().getResource(icon)).getImage());
	    }
	    catch ( Exception e ) { 
	    	e.printStackTrace();
	    };
	    
	    this.setSize(          new Dimension(frameWidth,frameHeight) );
	    this.setMinimumSize(   new Dimension((int)(0.95*frameWidth),(int)(0.97*frameHeight)) );
	    this.setPreferredSize( new Dimension(frameWidth,frameHeight) );
	    this.setLocationRelativeTo(null);
	    Container contentPane = getContentPane();
	    contentPane.setLayout( new BorderLayout(5, 5) );
	    
	    this.startAndSolveButton = new JButton("Show Solution");
	    this.startAndSolveButton.setName("Start new Game");
	    this.startAndSolveButton.setPreferredSize(new Dimension(150, (int)this.startAndSolveButton.getPreferredSize().getHeight()));
	    this.startAndSolveButton.addActionListener(this);
	    
	    this.xSudokuBox	= new JCheckBox("X-Sudoku", false);
	    this.xSudokuBox.setEnabled(false);
	    
	    this.hiddenFields = new JSpinner(new SpinnerNumberModel(SUDOKU_TYPE*SUDOKU_TYPE/2-SUDOKU_TYPE, SUDOKU_TYPE-SUDOKU_TYPE/2, SUDOKU_TYPE*(SUDOKU_TYPE-1)+SUDOKU_TYPE/2, 1));
	    this.hiddenFields.setEnabled(false);
	    
	    JPanel panel = new JPanel();
	    JLabel label = new JLabel("Number of hidden Fields: ");
	    panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	    panel.add(label);
	    panel.add(this.hiddenFields);
	    panel.add(this.xSudokuBox);
	    panel.add(this.startAndSolveButton);
	    panel.setBorder(BorderFactory.createEtchedBorder());
	    
	    this.gamePad = new GamePad(SUDOKU_TYPE, ((SpinnerNumberModel)this.hiddenFields.getModel()).getNumber().intValue(), this.xSudokuBox.isSelected());
	    this.gamePad.addPropertyChangeListener( this );
	    
	    this.add( this.gamePad, BorderLayout.CENTER );
	    this.add( panel, BorderLayout.SOUTH );

	    this.pack();
	    this.setResizable(true);
	    this.setVisible(true);
	}

	private void setButtonLabel() {
		String oldLabel = this.startAndSolveButton.getText();
		String newLabel = this.startAndSolveButton.getName();
		this.startAndSolveButton.setText(newLabel);
		this.startAndSolveButton.setName(oldLabel);
		this.hiddenFields.setEnabled(this.gamePad.isSolved());
		this.xSudokuBox.setEnabled(this.gamePad.isSolved());
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.gamePad.isSolved()) {
			this.gamePad.solve();
		}
		else {
			this.gamePad.initSudoku(SUDOKU_TYPE, ((SpinnerNumberModel)this.hiddenFields.getModel()).getNumber().intValue(), this.xSudokuBox.isSelected());		
		}
		this.setButtonLabel();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("IS_SOLVED")) {
			this.setButtonLabel();
		}
		
	}
	
	public static void main(String[] args) {
		new JSudoku(550,570);
	}
}
