package dev.m7mqd.stormymc.ranks;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

public enum DurationsEnum {
    // HOPPER: EMPTY | 1 DAY | 7 DAYS | 30 DAYS | EMPTY
    ONE_DAY(1, 1L, TimeUnit.DAYS),
    ONE_WEEK(2, 7L, TimeUnit.DAYS),
    ONE_MONTH(3, 30L, TimeUnit.DAYS);

    DurationsEnum(int position, long duration, TimeUnit timeUnit){
        this.position = position;
        this.duration = duration;
        this.timeUnit = timeUnit;
    }
    private final @Getter int position;
    private final @Getter long duration;
    private final @Getter TimeUnit timeUnit;

    public static DurationsEnum getFromPosition(int position){
        for(DurationsEnum durationsEnum : DurationsEnum.values())
            if(durationsEnum.getPosition() == position) return durationsEnum;
        return null;
    }
    @Override
    public String toString() {
        return duration + " " + timeUnit;
    }
}
