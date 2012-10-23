import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

/** Содержит данные о всех курсах */
public class CourseMap {
    private Map<String, CourseInfo> dict = new HashMap<String, CourseInfo>();

    /** Добавить все элементы из списка курсов для одной специализации */
    public void addCourseInfoList(CourseInfoList courseInfoList) {
        for (CourseInfo course : courseInfoList.getCourses()) {
            dict.put(course.getAlias(), course);
        }
    }

    /** Список всех курсов */
    public Collection<CourseInfo> getCourses() {
        return dict.values();
    }

    /** Список всех идентификаторов */
    public Collection<String> getIdentifiers() {
        return dict.keySet();
    }

    /** Получить сведения о курсе по его идентификатору */
    public CourseInfo getCourseByAlias(String alias) {
        return dict.get(alias);
    }
}
