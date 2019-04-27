// CatfoOD 2008-7-3 ÉÏÎç10:51:27

package jeo.ui;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class BeoLookAndFeel {
	private static final long serialVersionUID = -7910172386503018494L;

	public BeoLookAndFeel() {
		UIDefaults ui = UIManager.getDefaults();
		initClassDefaults(ui);
	}

	protected void initClassDefaults(UIDefaults table) {
		
		final String BeoPackageName = "beo.ui.";
		
		Object[] uiDefaults = {
			   "ButtonUI", BeoPackageName + "BeoButtonUI",
//			 "CheckBoxUI", BeoPackageName + "BeoCheckBoxUI",
//	             "ColorChooserUI", BeoPackageName + "BeoColorChooserUI",
//	       "FormattedTextFieldUI", BeoPackageName + "BeoFormattedTextFieldUI",
//			  "MenuBarUI", BeoPackageName + "BeoMenuBarUI",
//			     "MenuUI", BeoPackageName + "BeoMenuUI",
//			 "MenuItemUI", BeoPackageName + "BeoMenuItemUI",
//		 "CheckBoxMenuItemUI", BeoPackageName + "BeoCheckBoxMenuItemUI",
//	      "RadioButtonMenuItemUI", BeoPackageName + "BeoRadioButtonMenuItemUI",
//		      "RadioButtonUI", BeoPackageName + "BeoRadioButtonUI",
//		     "ToggleButtonUI", BeoPackageName + "BeoToggleButtonUI",
//			"PopupMenuUI", BeoPackageName + "BeoPopupMenuUI",
//		      "ProgressBarUI", BeoPackageName + "BeoProgressBarUI",
			"ScrollBarUI", BeoPackageName + "BeoScrollBarUI",
//		       "ScrollPaneUI", BeoPackageName + "BeoScrollPaneUI",
//			"SplitPaneUI", BeoPackageName + "BeoSplitPaneUI",
//			   "SliderUI", BeoPackageName + "BeoSliderUI",
//			"SeparatorUI", BeoPackageName + "BeoSeparatorUI",
//			  "SpinnerUI", BeoPackageName + "BeoSpinnerUI",
//		 "ToolBarSeparatorUI", BeoPackageName + "BeoToolBarSeparatorUI",
//	       "PopupMenuSeparatorUI", BeoPackageName + "BeoPopupMenuSeparatorUI",
//		       "TabbedPaneUI", BeoPackageName + "BeoTabbedPaneUI",
//			 "TextAreaUI", BeoPackageName + "BeoTextAreaUI",
			"TextFieldUI", BeoPackageName + "BeoTextFieldUI",
//		    "PasswordFieldUI", BeoPackageName + "BeoPasswordFieldUI",
//			 "TextPaneUI", BeoPackageName + "BeoTextPaneUI",
//	               "EditorPaneUI", BeoPackageName + "BeoEditorPaneUI",
//			     "TreeUI", BeoPackageName + "BeoTreeUI",
			    "LabelUI", BeoPackageName + "BeoLabelUI",
//			     "ListUI", BeoPackageName + "BeoListUI",
//			  "ToolBarUI", BeoPackageName + "BeoToolBarUI",
//			  "ToolTipUI", BeoPackageName + "BeoToolTipUI",
//			 "ComboBoxUI", BeoPackageName + "BeoComboBoxUI",
//			    "TableUI", BeoPackageName + "BeoTableUI",
//		      "TableHeaderUI", BeoPackageName + "BeoTableHeaderUI",
//		    "InternalFrameUI", BeoPackageName + "BeoInternalFrameUI",
//		      "DesktopPaneUI", BeoPackageName + "BeoDesktopPaneUI",
//		      "DesktopIconUI", BeoPackageName + "BeoDesktopIconUI",
//		       "OptionPaneUI", BeoPackageName + "BeoOptionPaneUI",
		            "PanelUI", BeoPackageName + "BeoPanelUI",
//			 "ViewportUI", BeoPackageName + "BeoViewportUI",
//			 "RootPaneUI", BeoPackageName + "BeoRootPaneUI",
//		            "ComponentUI", 
		     "JFileChooserUI", "javax.swing.plaf.basic.BasicFileChooserUI",
		};
		
		table.putDefaults(uiDefaults);
	}
	
    protected void initSystemColorDefaults(UIDefaults table)
    {
		String[] defaultSystemColors = {
//			  	        "desktop", "#005C5C", /* Color of the desktop background */
//				  "activeCaption", "#000080", /* Color for captions (title bars) when they are active. */
//			      "activeCaptionText", "#FFFFFF", /* Text color for text in captions (title bars). */
//			    "activeCaptionBorder", "#C0C0C0", /* Border color for caption (title bar) window borders. */
//			        "inactiveCaption", "#808080", /* Color for captions (title bars) when not active. */
//			    "inactiveCaptionText", "#C0C0C0", /* Text color for text in inactive captions (title bars). */
//			  "inactiveCaptionBorder", "#C0C0C0", /* Border color for inactive caption (title bar) window borders. */
//				         "window", "#FFFFFF", /* Default color for the interior of windows */
//				   "windowBorder", "#000000", /* ??? */
//				     "windowText", "#000000", /* ??? */
//					   "menu", "#C0C0C0", /* Background color for menus */
//				       "menuText", "#000000", /* Text color for menus  */
					   "text", "#ffffff", /* Text background color */
				       "textText", "#ffffff", /* Text foreground color */
				  "textHighlight", "#ffffff", /* Text background color when selected */
			      "textHighlightText", "#000000", /* Text color when selected */
			       "textInactiveText", "#808080", /* Text color when disabled */
//				        "control", "#C0C0C0", /* Default color for controls (buttons, sliders, etc) */
//				    "controlText", "#000000", /* Default color for text in controls */
//			       "controlHighlight", "#C0C0C0", /* Specular highlight (opposite of the shadow) */
//			     "controlLtHighlight", "#FFFFFF", /* Highlight color for controls */
//				  "controlShadow", "#808080", /* Shadow color for controls */
//			        "controlDkShadow", "#000000", /* Dark shadow color for controls */
//				      "scrollbar", "#E0E0E0", /* Scrollbar background (usually the "track") */
//					   "info", "#FFFFE1", /* ??? */
//				       "infoText", "#000000"  /* ??? */
		};
    }
}
