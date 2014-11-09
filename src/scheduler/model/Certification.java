package scheduler.model;

import scheduler.model.collections.Certifications;

public class Certification extends BaseItem<Certification> {
    private static Certifications certifications = new Certifications();
    private String                description;

    public static Certification create(long id, String description) {
        if (!certifications.containsKey(id)) {
            certifications.put(new Certification(id, description));
        }
        return certifications.get(id);
    }

    private Certification(long id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
