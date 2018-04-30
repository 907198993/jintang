package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/9/26.
 */

public class XianShiTimeObj   {

    /**
     * period_time_id : 1
     * begin_time : 1517792400
     * end_time : 1517796000
     */
    private int beiShu=1000;

    private int period_time_id;
    private long begin_time;
    private long end_time;

    public int getPeriod_time_id() {
        return period_time_id;
    }

    public void setPeriod_time_id(int period_time_id) {
        this.period_time_id = period_time_id;
    }

    public long getBegin_time() {
        return begin_time*beiShu;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time*beiShu;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }
}
