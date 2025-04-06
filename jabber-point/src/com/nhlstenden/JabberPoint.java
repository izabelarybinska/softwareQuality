package com.nhlstenden;

import com.nhlstenden.Accessors.Accessor;
import com.nhlstenden.Accessors.XMLAccessor;
import com.nhlstenden.Slide.SlideViewerFrame;
import com.nhlstenden.Slide.Style;

import javax.swing.JOptionPane;

import java.io.IOException;

/**
 * com.nhlstenden.JabberPoint Main Programma
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class JabberPoint {
    protected static final String IOERR = "IO Error: ";
    protected static final String JABERR = "Jabberpoint Error ";
    protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

    /**
     * Main Program
     */
    public static void main(String[] argv) {

        Style.createStyles();
        Presentation presentation = new Presentation();
        new SlideViewerFrame(JABVERSION, presentation);
        try {
            if (argv.length == 0) {
                Accessor.getDemoAccessor().loadFile(presentation, "");
            } else {
                new XMLAccessor().loadFile(presentation, argv[0]);
            }
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    IOERR + ex, JABERR,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}