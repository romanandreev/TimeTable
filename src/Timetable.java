import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

class Timetable {
    private Integer year;
    private Integer semester;
    List<Day> days;

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
    String[][] getData() {
        int n = 1;
        for (int i = 0; i < days.size(); i++) {
            n += days.get(i).twoHourClasses.size() + 1;
        }
        String[][] Data = new String[n][4];
        Data[0][1] = "sa";
        Data[0][2] = "sm";
        Data[0][3] = "mm";
        int tk = 1;
        for (int i = 0; i < days.size(); i++) {
            String[][] dayData = days.get(i).getData();
            for (int j = 0; j < dayData.length; j++) {
                for (int k = 0; k < dayData[j].length; k++) {
                    Data[j + tk][k] = dayData[j][k];
                }
            }
            tk += days.get(i).twoHourClasses.size() + 1;
        }
        return Data;
    }
    void show() {
        RowHeaderTable tb = new RowHeaderTable(getData());
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
