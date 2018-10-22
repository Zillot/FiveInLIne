package com.koma.filforever.RootFolder.Data;

import java.io.Serializable;

//состояние блока
public enum CellSatus implements Serializable {
    //Нуждаеться в прощете для взрыва, Стабилен, Появляеться, Изчезает, в процессе перемещения, как раз переместился
    NeedToProccess, Stable, Apearing, Disapearing, Moving, JustMovedOn
}
