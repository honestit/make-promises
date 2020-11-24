package pl.honestit.projects.promises.model.promise;

public enum RejectReason {

    INCOMPLETE_DATA("Promise doesn't have all required data"),
    PAST_DEADLINE("Promise deadline must not be before promise creation");

    private final String description;

    RejectReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
