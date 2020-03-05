package kennedy.co.za.web.rest.business.rules.helpers;

public class Percentage {
    private Long value;
    private Integer percentage;
    private final Integer percentageBound = 100;
    private Long percentageValue;

    public Percentage(Long value, Integer percentage) {
        this.value = value;
        this.percentage = percentage;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getPercentageBound() {
        return percentageBound;
    }


    public Long calculatePercentageValue() {
        Integer fraction = Math.toIntExact(value / percentageBound);
        percentageValue = Long.valueOf(fraction * percentage);
        return percentageValue;
    }
}
