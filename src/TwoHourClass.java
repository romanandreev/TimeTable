import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

class TwoHourClass {
    private Integer index;
    private List<Lesson> lessons;
    TwoHourClass(Element e) throws Exception {
        index = Parser.getIntValue(e,"no");
        System.out.println(index);
        NodeList nl = e.getElementsByTagName("lesson");
        lessons = new ArrayList<Lesson>();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {
                lessons.add(new Lesson((Element) nl.item(i)));
            }
        }
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
