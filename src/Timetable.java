import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

class Timetable {
    private Integer year;
    private Integer semester;
    private List<Day> days;
    Timetable(Element e) throws Exception {
        year = Parser.getIntValue(e,"year");
        semester = Parser.getIntValue(e,"semester");
        NodeList nl = e.getElementsByTagName("weekday");
        days = new ArrayList<Day>();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {
                days.add(new Day((Element) nl.item(i)));
            }
        }
    }
    static void show() {
        AnotherTableEx tb = new AnotherTableEx();

    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
