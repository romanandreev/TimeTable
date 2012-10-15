import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

class Day {
    private DayType dayType;
    private List<TwoHourClass> twoHourClasses;
    Day(Element e) throws Exception {
        dayType = Parser.getDayType(Parser.getTextValue(e,"id"));
        System.out.println(dayType);
        NodeList nl = e.getElementsByTagName("two_hours");
        twoHourClasses = new ArrayList<TwoHourClass>();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {
                twoHourClasses.add(new TwoHourClass((Element) nl.item(i)));
            }
        }
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public List<TwoHourClass> getTwoHourClasses() {
        return twoHourClasses;
    }

    public void setTwoHourClasses(List<TwoHourClass> twoHourClasses) {
        this.twoHourClasses = twoHourClasses;
    }

    static enum DayType {
        MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
