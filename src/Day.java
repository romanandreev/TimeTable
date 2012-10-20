import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

class Day {
    private DayType dayType;
    List<TwoHourClass> twoHourClasses;
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
    String[][] getData() {
        String[][] Data = new String[twoHourClasses.size() + 1][4];
        Data[0][0] = "==";
        for (int j = 1; j < 4; j++) {
            Data[0][j] = "============================";
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
