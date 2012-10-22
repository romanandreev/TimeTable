import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/** Расписание на день недели */
@XmlType(name="weekday")
class Day {
    private DayType dayType;
    private List<TwoHourClass> twoHourClasses;

    /** День недели */
    public DayType getDayType() {
        return dayType;
    }

    String[][] getData() {
        String[][] Data = new String[twoHourClasses.size() + 1][4];
        Data[0][0] = "";
        for (int j = 1; j < 4; j++) {
            if (j == 1) {
                Data[0][j] = "============================";
            } else {
                Data[0][j] = "<---";
            }
        }
        for (int j = 0; j < twoHourClasses.size(); j++) {
            String[][] DataTHC = twoHourClasses.get(j).getData();
            Data[j + 1][0] = Integer.valueOf(j + 1).toString();
            for (int k = 0; k < DataTHC[0].length; k++) {
                Data[j + 1][k + 1] = DataTHC[0][k];
            }
        }

        return Data;
    }

    @XmlAttribute(name="id")
    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    /** Пары в этот день */
    @XmlElement(name="two_hours")
    public List<TwoHourClass> getTwoHourClasses() {
        if (twoHourClasses == null) {
            twoHourClasses = new ArrayList<TwoHourClass>();
        }
        return twoHourClasses;
    }

    public void setTwoHourClasses(List<TwoHourClass> twoHourClasses) {
        this.twoHourClasses = twoHourClasses;
    }

    @XmlType
    @XmlEnum(String.class)
    static enum DayType {
        @XmlEnumValue("mon") MONDAY, 
        @XmlEnumValue("tue") TUESDAY, 
        @XmlEnumValue("wed") WEDNESDAY,
        @XmlEnumValue("thu") THURSDAY, 
        @XmlEnumValue("fri") FRIDAY, 
        @XmlEnumValue("sat") SATURDAY, 
        @XmlEnumValue("sun") SUNDAY
    }
}
