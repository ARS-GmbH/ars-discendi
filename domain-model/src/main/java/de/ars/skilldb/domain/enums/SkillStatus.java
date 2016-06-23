package de.ars.skilldb.domain.enums;

public enum SkillStatus {
    SUBMITTED("beantragt"), APPROVED("freigegeben"), REJECTED("abgelehnt"), DELETED("gelöscht");

    private final String name;

    private SkillStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
