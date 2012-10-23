import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** Расписание на семестр */
@XmlRootElement(name="timetable")
public class Timetable {

    private Integer year;
    private Integer semester;
    private List<Day> days;

    String[][] getData() {
        int n = 1;
        for (Day weekday : getDays()) {
            n += weekday.getTwoHourClasses().size() + 1;
        }
        String[][] data = new String[n][4];
        data[0][1] = "sa";
        data[0][2] = "sm";
        data[0][3] = "mm";
        int tk = 1;
        for (Day day : getDays()) {
            String[][] dayData = day.getData();
            for (int j = 0; j < dayData.length; j++) {
                for (int k = 0; k < dayData[j].length; k++) {
                    data[j + tk][k] = dayData[j][k];
                }
            }
            tk += day.getTwoHourClasses().size() + 1;
        }
        return data;
    }

    /** Отобразить расписание */
    void show() {
        RowHeaderTable tb = new RowHeaderTable(getData());
    }

    /** Год */
    public Integer getYear() {
        return year;
    }

    @XmlAttribute(name="year")
    public void setYear(Integer year) {
        this.year = year;
    }

    /** Номер семестра */
    public Integer getSemester() {
        return semester;
    }

    @XmlAttribute(name="semester")
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    /**
     * Список расписаний по дням недели
     */
    @XmlElement(name="weekday")
    public List<Day> getDays() {
        if (days == null) {
            days = new ArrayList<Day>();
        }
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
