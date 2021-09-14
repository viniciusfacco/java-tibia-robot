/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tibiarobot;

import java.util.ArrayList;

/**
 *
 * @author Vinicius
 */
public class Char {
    
    private boolean ativo;
    private String name;
    private ArrayList<String> hotkeys;
    
    public Char(boolean pativo, String pname){
        ativo = pativo;
        name = pname;
        hotkeys = new ArrayList();
    }
    
    public void addHotKey(String hk){
        hotkeys.add(hk);
    }
    
    public ArrayList<String> getHotKeys(){
        return hotkeys;
    }
    
    public boolean getAtivo(){
        return ativo;
    }
    
    public String getName(){
        return name;
    }
    
}
