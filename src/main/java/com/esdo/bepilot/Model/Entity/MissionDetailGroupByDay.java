package com.esdo.bepilot.Model.Entity;


import java.math.BigDecimal;
import java.sql.Date;

public interface MissionDetailGroupByDay {
    Date getTime() ;
    int getTotalMission() ;
    BigDecimal getTotalMoney() ;

}
