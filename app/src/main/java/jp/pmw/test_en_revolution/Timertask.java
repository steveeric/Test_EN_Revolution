package jp.pmw.test_en_revolution;

import jp.pmw.test_en_revolution.room.Cell;

/**
 * Created by scr on 2014/12/11.
 */
public class Timertask implements Runnable{
    private Cell[][] cells;
    Timertask(Cell[][] cell){
        this.cells=cell;
    }

    @Override
    public void run(){
        for(int i = 0;i < this.cells.length; i++){
            for(int j = 0; j< this.cells[i].length; j++){
                if(cells[i][j].getPreAttendee() == 1){
                    if(cells[i][j].getPreAttendee()==1){
                        /*if(cells[i][j].getSeat().getPreAttendeeState()==0){
                            cells[i][j].getSeat().setPreAttendeeState(1);
                            break;
                        }*/
                    }
                }
            }
        }
    }
}
