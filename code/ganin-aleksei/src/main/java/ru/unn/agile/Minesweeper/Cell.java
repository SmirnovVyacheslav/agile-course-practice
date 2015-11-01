package ru.unn.agile.Minesweeper;

/**
 * Created by aleksei on 01.11.15.
 */
public class Cell {
    private boolean bomb = false;
    private boolean issue = false;
    private boolean flag = false;
    private char value = 0;

    public void setBomb(){
        bomb = true;
    }
    public boolean isBomb(){
        return  bomb;
    }

    public void setValue(char val){
        value = val;
    }

    public char getValue(){
        return value;
    }

    public void setIssue(){
        issue = true;
    }

    public boolean isIssue(){
        return issue;
    }

    public void unsetIssue() {
        issue = false;
    }

    public void setFlag(){
        flag = true;
    }

    public boolean isFlag(){
        return  flag;
    }

    public void unsetFlag(){
        flag = false;
    }
}
