 /**********************************************************************
 *                            InputField                                *
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import com.derletztekick.games.sudoku.generator.Token;

public class InputField extends JPanel implements DocumentListener, KeyListener, MouseListener {
	private static final long serialVersionUID = 921755284566702701L;
	private JTextField noticeField = new JTextField();
	NumberFormat format = NumberFormat.getInstance();
	private JFormattedTextField numberField = null;
	private final int number;
	private final Token token;
	public InputField(Token t) {
		this.token = t;
		this.number = this.token.getNumber();
		this.init();
	}
	
	public void addListener(EventListener l) {
		this.numberField.getDocument().addDocumentListener((DocumentListener)l);
		this.numberField.addKeyListener((KeyListener)l);
	}
	
	
	private void init() {
		this.setLayout(new BorderLayout(5,5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 7, 5, 7));
		try {
			this.numberField = new JFormattedTextField(new MaskFormatter("#"));
		} catch (ParseException e) {
			e.printStackTrace();
			this.numberField = new JFormattedTextField(this.format);
			this.numberField.setDocument(new IntegerDocument(1));
			((NumberFormatter)this.numberField.getFormatter()).setAllowsInvalid(false);
		}
		this.numberField.setHorizontalAlignment(JTextField.CENTER);
		Font font = this.noticeField.getFont();
		this.numberField.setFont(new Font(font.getFontName(), font.getStyle(), 18));
		
		this.numberField.getDocument().addDocumentListener(this);
		this.numberField.setPreferredSize(new Dimension((int)this.noticeField.getPreferredSize().getWidth(), 18));
		this.numberField.setMinimumSize(new Dimension((int)this.noticeField.getPreferredSize().getWidth(), 14));
		this.numberField.setDisabledTextColor(Color.DARK_GRAY);
		this.numberField.addKeyListener(this);
		
		
		this.noticeField.setPreferredSize(new Dimension((int)this.noticeField.getPreferredSize().getWidth(), 12));
		this.noticeField.setFont(new Font(font.getFontName(), font.getStyle(), 10));
		
		if (!this.token.isHiddenToken()) {
			this.numberField.setValue(this.token.getNumber());
			this.numberField.setEnabled(false);
		}
		else {
			this.add(this.noticeField, BorderLayout.SOUTH);
		}
		this.add(this.numberField, BorderLayout.CENTER);
		
	}
	
	private void setInputeValue(String s) {
		try {
			this.token.setInputNumber( Integer.parseInt( s ) );
		} catch (Exception e) {
			this.token.setInputNumber( -1 );
		} 
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		this.remove(this.noticeField);
		this.numberField.setEnabled(enabled);
		this.noticeField = null;
	}
	
	public void showSolution() {
		this.token.setNumber(this.number);
		this.token.setInputNumber(this.number);
		this.numberField.setValue(this.number);
		this.setEnabled(false);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		try {
			this.setInputeValue( doc.getText(0, doc.getLength()) );
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		try {
			this.setInputeValue( doc.getText(0, doc.getLength()) );
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Document doc = e.getDocument();
		try {
			this.setInputeValue( doc.getText(0, doc.getLength()) );
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			this.setInputeValue( ((JTextField)e.getSource()).getText() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}
	
}
