import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class Parser {
    static String getTextValue(Element ele, String tagName) throws Exception {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl == null || nl.getLength() == 0) {
            String s = ele.getAttribute(tagName);
            if (s.equals("")) {
                return null;
            }
            return s;
        }
        if (nl.getLength() > 1) {
            throw new Exception("More than one argument!");
        }
        Element el = (Element)nl.item(0);
        if (el.getFirstChild() == null) {
            return null;
        }
        textVal = el.getFirstChild().getNodeValue();
        return textVal;
    }
    static Day.DayType getDayType(String s) {
        if (s.equals("mon"))
            return Day.DayType.MONDAY;
        else
        if (s.equals("tue"))
            return Day.DayType.TUESDAY;
        else
        if (s.equals("wed"))
            return Day.DayType.WEDNESDAY;
        else
        if (s.equals("thu"))
            return Day.DayType.THURSDAY;
        else
        if (s.equals("fri"))
            return Day.DayType.FRIDAY;
        else
        if (s.equals("sat"))
            return Day.DayType.SATURDAY;
        else
        if (s.equals("sun"))
            return Day.DayType.SUNDAY;
        else
            return null;
    }
    static Integer getIntValue(Element ele, String tagName) throws Exception {
        String s = getTextValue(ele,tagName);
        if (s == null) {
            return null;
        }
        return Integer.parseInt(s);
    }
    static Integer getMask(String s) {
        if (s.equals("all")) return 1 + 2 + 4;
        if (s.equals("sa")) return 1;
        if (s.equals("sm")) return 2;
        if (s.equals("mm")) return 4;
        return 0;
    }
}
