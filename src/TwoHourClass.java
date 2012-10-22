import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/** Пара */
@XmlType(name="two_hours")
class TwoHourClass {
    private Integer index;
    private List<Lesson> lessons;

    /**
     * Номер пары
     */
    public Integer getIndex() {
        return index;
    }

    @XmlAttribute(name="no")
    public void setIndex(Integer index) {
        this.index = index;
    }

    String[][] getData() {
        String[][] Data = new String[1][3];
        for (Lesson lesson : getLessons()) {
            String[][] NData = lesson.getData();
            for (int j = 0; j < NData[0].length; j++) {
                if (NData[0][j].length() > 0) {
                    Data[0][j] = NData[0][j];
                }
            }
        }
        return Data;
    }

    /**
     * Занятия на данной паре у разных специализаций.
     */
    @XmlElement(name="lesson")
    public List<Lesson> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<Lesson>();
        }
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
