/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tibiarobot;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author vfrodrigues
 */
public class TibiaRobot implements Runnable {
    
    public boolean executa;
    private final JTextArea log;
    private String appname;
    private final int tempoespera;
    private ArrayList<Char> chars;
    private boolean encontrouapp;

    TibiaRobot(JTextArea logarea, int ptempo) {
        log = logarea;
        tempoespera = ptempo * 1000;
        chars = new ArrayList();
        encontrouapp = false;
    }
    
    public void addChar(Char ch){
        chars.add(ch);
    }

    public void monitor(){
        
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(TibiaRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        executa = true;
        Calendar cal;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
        while (executa){
            for (Char character : chars){
                if (character.getAtivo()){
                    log.append("Selecionado Char " + character.getName() + ".\n");
                    log.setCaretPosition(log.getText().length());
                    ArrayList<String> hkeys = character.getHotKeys();
                    for (String hkey : hkeys) {
                        cal = Calendar.getInstance();
                        if(setFocusOnApp("- " + character.getName())){
                            robot.keyPress(getKey(hkey));
                            log.append(sdf.format(
                                    cal.getTime()) + 
                                    " - Char " + 
                                    character.getName() + 
                                    " - Comando " + 
                                    hkey + 
                                    " enviado\n");
                            log.setCaretPosition(log.getText().length());
                            robot.delay(1000);
                        } else {
                            log.append("Char " + character.getName() + " não encontrado.\n");
                            log.setCaretPosition(log.getText().length());
                            setFocusOnApp("Tibia");
                            robot.keyPress(KeyEvent.VK_ENTER);
                            robot.delay(10000);
                        }
                    }
                }
            }
            try {
                Thread.sleep(tempoespera);
            } catch (InterruptedException ex) {
                Logger.getLogger(TibiaRobot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void parar(){
        executa = false;
    }
    
    private boolean setFocusOnApp(String app){
        appname = app;
        encontrouapp = false;
        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows(new WNDENUMPROC() {
            int count = 0;

            public boolean callback(HWND hWnd, Pointer arg1) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                // get rid of this if block if you want all windows regardless
                // of whether
                // or not they have text
                if (wText.isEmpty()) {
                    return true;
                }

                //System.out.println("Found window with text " + hWnd + ", total " + ++count + " Text: " + wText);
                if (wText.endsWith(appname)) {
                    user32.SetForegroundWindow(hWnd);
                    encontrouapp = true;
                    return false;
                }
                return true;
            }
        }, null);
        // user32.SetFocus(hWnd);
        return encontrouapp;
    }

    @Override
    public void run() {
        monitor();
    }

    private int getKey(String key) {
        switch(key){
            case "F1": return KeyEvent.VK_F1;
            case "F2": return KeyEvent.VK_F2;
            case "F3": return KeyEvent.VK_F3;
            case "F4": return KeyEvent.VK_F4;
            case "F5": return KeyEvent.VK_F5;
            case "F6": return KeyEvent.VK_F6;
            case "F7": return KeyEvent.VK_F7;
            case "F8": return KeyEvent.VK_F8;
            case "F9": return KeyEvent.VK_F9;
            case "F10": return KeyEvent.VK_F10;
            case "F11": return KeyEvent.VK_F11;
            case "F12": return KeyEvent.VK_F12;
            default: return KeyEvent.VK_F1;
        }
    }
    
}
