package scheduler.model.collections;

import scheduler.model.Certification;

public class Certifications extends BaseCollection<Certification> {

    public boolean meetsRequirements(Certifications requirements) {
        for (Certification required : requirements) {
            if (!contains(required)) {
                return false;
            }
        }
        return true;
    }

}
