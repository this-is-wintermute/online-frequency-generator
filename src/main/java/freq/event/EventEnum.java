package freq.event;

public enum EventEnum
{
    NORMAL_OPERATION("normalOperation", 3000),
    HIGH_FREQUENCY("highFrequencyEvent", 4200),
    LOW_FREQUENCY("lowFrequencyEvent", 4200),
    DATA_INVALID("frequencyDataInvalid", 2400),
    DATA_UNAVAILABLE("frequencyDataUnavailable", 30000);

    private String eventName;
    private long   eventThresholdMilliseconds;


    private EventEnum(String eventName, long eventThresholdMillis)
    {
        this.eventName = eventName;
        this.eventThresholdMilliseconds = eventThresholdMillis;
    }


    public String getEventName()
    {
        return this.eventName;
    }


    public long getEventActivationThresholdInMilliseconds()
    {
        return this.eventThresholdMilliseconds;
    }
}