package dev.m7mqd.stormymc.ranks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Getter
public enum DurationsEnum {
    // HOPPER: EMPTY | 1 DAY | 7 DAYS | 30 DAYS | EMPTY

    ONE_DAY(1, 1L, TimeUnit.DAYS),
    ONE_WEEK(2, 7L, TimeUnit.DAYS),
    ONE_MONTH(3, 30L, TimeUnit.DAYS);

    private final int position;
    private final long duration;
    private final TimeUnit timeUnit;
    private final static DurationsEnum[] values = values();
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
